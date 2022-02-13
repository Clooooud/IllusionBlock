package fr.cloud.illusionblock.registry;

import fr.cloud.illusionblock.block.IllusionBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static Block ILLUSION_BLOCK;
    public static Block ILLUSION_BLOCK_ETHEREAL;

    public static void initialize() {
        ILLUSION_BLOCK_ETHEREAL = Registry.register(Registry.BLOCK, "illusionblock:illusion_block_ethereal", new IllusionBlock(getSetting()));
        ILLUSION_BLOCK = Registry.register(Registry.BLOCK, "illusionblock:illusion_block", new IllusionBlock(getSetting().noCollision()));
    }

    private static AbstractBlock.Settings getSetting() {
        AbstractBlock.ContextPredicate never = (state, world, pos) -> false;
        return FabricBlockSettings.copyOf(Blocks.GLASS).suffocates(never).blockVision(never);
    }
}

