package fr.cloud.illusionblock.registry;

import fr.cloud.illusionblock.IllusionBlockMod;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static BlockItem ILLUSION_BLOCK_ITEM = new BlockItem(ModBlocks.ILLUSION_BLOCK, new Item.Settings().group(IllusionBlockMod.TAB));
    public static BlockItem ILLUSION_BLOCK_ETHEREAL_ITEM = new BlockItem(ModBlocks.ILLUSION_BLOCK_ETHEREAL, new Item.Settings().group(IllusionBlockMod.TAB));

    public static void initialize() {
        Registry.register(Registry.ITEM, "illusionblock:illusion_block", ILLUSION_BLOCK_ITEM);
        Registry.register(Registry.ITEM, "illusionblock:illusion_block_ethereal", ILLUSION_BLOCK_ETHEREAL_ITEM);
    }
}
