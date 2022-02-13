package fr.cloud.illusionblock.block.entity;

import fr.cloud.illusionblock.block.IllusionBlock;
import fr.cloud.illusionblock.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class IllusionBlockEntity extends BlockEntity {

    private static final int MAX_RANGE = 9;

    private boolean hasMimic = false;
    private int[] faced = {0, 0, 0};

    public IllusionBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ILLUSION_BLOCK_ENTITY, pos, state);
    }

    public boolean hasMimic() {
        return hasMimic;
    }

    private BlockState getNearestFacedBlockState() {
        if (!this.hasWorld() || !this.hasMimic) {
            return null;
        }
        return getWorld().getBlockState(getInstantFacedBlockPos());
    }

    /**
     * Get the state of the faced block, if the faced block is an illusion block, get the state of the block the faced illusion block is facing
     *
     * @return the state of the final block the Illusion Block is facing
     */
    public BlockState getDeepestFacedBlockState() {
        return getDeepestFacedBlockState(new HashSet<>());
    }

    private BlockState getDeepestFacedBlockState(Set<IllusionBlockEntity> past) {
        if (this.getNearestFacedBlockState() == null || past.contains(this) || past.size() > 32) {
            past.clear();
            return this.getCachedState();
        }

        Optional<Boolean> mimic = this.getNearestFacedBlockState().getOrEmpty(IllusionBlock.MIMIC);
        if (mimic.isPresent()) {
            if (mimic.get()) {
                IllusionBlockEntity blockEntity = (IllusionBlockEntity) this.getWorld().getBlockEntity(this.getInstantFacedBlockPos());

                if (blockEntity.getInstantFacedBlockPos().equals(this.getPos())) {
                    past.clear();
                    return this.getCachedState();
                }

                past.add(this);
                return blockEntity.getDeepestFacedBlockState(past);
            }
        }

        past.clear();
        return this.getNearestFacedBlockState();
    }

    public BlockPos getInstantFacedBlockPos() {
        return new BlockPos(faced[0], faced[1], faced[2]);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putIntArray("faced", faced);
        nbt.putBoolean("hasMimic", hasMimic);

        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        hasMimic = nbt.getBoolean("hasMimic");
        faced = nbt.getIntArray("faced");
    }

    public static void tick(World world, BlockPos pos, BlockState state, IllusionBlockEntity be) {
        Vec3i vector = state.get(IllusionBlock.FACING).getVector();
        BlockPos tempPos = pos.mutableCopy();
        int i = 0;
        do {
            tempPos = tempPos.add(vector);
            i++;
        } while ((!world.getBlockState(tempPos).isFullCube(world, tempPos) && world.getBlockState(tempPos).getOrEmpty(IllusionBlock.MIMIC).isEmpty()) && i < MAX_RANGE);

        be.hasMimic = (i < MAX_RANGE);
        world.setBlockState(pos, state.with(IllusionBlock.MIMIC, be.hasMimic));
        be.faced = (i >= MAX_RANGE) ? new int[]{0, 0, 0} : new int[]{tempPos.getX(), tempPos.getY(), tempPos.getZ()};
    }

}
