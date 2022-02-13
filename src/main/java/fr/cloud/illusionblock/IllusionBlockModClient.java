package fr.cloud.illusionblock;

import fr.cloud.illusionblock.block.renderer.IllusionBlockRenderer;
import fr.cloud.illusionblock.registry.ModBlockEntities;
import fr.cloud.illusionblock.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class IllusionBlockModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ModBlocks.ILLUSION_BLOCK, ModBlocks.ILLUSION_BLOCK_ETHEREAL);
        BlockEntityRendererRegistry.register(ModBlockEntities.ILLUSION_BLOCK_ENTITY, IllusionBlockRenderer::new);
    }
}
