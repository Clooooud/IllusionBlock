package fr.cloud.illusionblock.registry;

import fr.cloud.illusionblock.block.entity.IllusionBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {

    public static BlockEntityType<IllusionBlockEntity> ILLUSION_BLOCK_ENTITY;

    public static void initialize() {
        ILLUSION_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "illusionblock:be", FabricBlockEntityTypeBuilder.create(IllusionBlockEntity::new, ModBlocks.ILLUSION_BLOCK, ModBlocks.ILLUSION_BLOCK_ETHEREAL).build(null));
    }
}
