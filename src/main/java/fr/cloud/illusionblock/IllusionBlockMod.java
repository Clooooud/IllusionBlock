package fr.cloud.illusionblock;

import fr.cloud.illusionblock.registry.ModBlockEntities;
import fr.cloud.illusionblock.registry.ModBlocks;
import fr.cloud.illusionblock.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IllusionBlockMod implements ModInitializer {

	public static final String MOD_ID = "illusionblock";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ItemGroup TAB = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "general"), () -> new ItemStack(ModBlocks.ILLUSION_BLOCK));

	@Override
	public void onInitialize(){
		ModBlocks.initialize();
		ModItems.initialize();
		ModBlockEntities.initialize();

		LOGGER.info("IllusionBlock is loaded!");
	}
}
