package fr.cloud.illusionblock.block;

import fr.cloud.illusionblock.block.entity.IllusionBlockEntity;
import fr.cloud.illusionblock.registry.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IllusionBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty MIMIC = BooleanProperty.of("mimic");

    public IllusionBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(MIMIC, Boolean.FALSE));
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        IllusionBlockEntity blockEntity = (IllusionBlockEntity) world.getBlockEntity(pos);

        if (!blockEntity.hasMimic() || blockEntity.getDeepestFacedBlockState().getOrEmpty(MIMIC).isPresent()) {
            return super.calcBlockBreakingDelta(state, player, world, pos);
        }

        return blockEntity.getDeepestFacedBlockState().calcBlockBreakingDelta(player, world, pos);
    }

    @Override
    protected void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {
        IllusionBlockEntity blockEntity = (IllusionBlockEntity) world.getBlockEntity(pos);

        if (!blockEntity.hasMimic()) {
            super.spawnBreakParticles(world, player, pos, state);
        }

        super.spawnBreakParticles(world, player, pos, blockEntity.getDeepestFacedBlockState());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, MIMIC);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return state.get(MIMIC) ? BlockRenderType.INVISIBLE : BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.ILLUSION_BLOCK_ENTITY, IllusionBlockEntity::tick);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IllusionBlockEntity(pos, state);
    }

}
