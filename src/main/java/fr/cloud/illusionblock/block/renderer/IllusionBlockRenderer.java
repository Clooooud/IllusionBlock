package fr.cloud.illusionblock.block.renderer;

import fr.cloud.illusionblock.block.entity.IllusionBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Random;

public class IllusionBlockRenderer implements BlockEntityRenderer<IllusionBlockEntity> {

    public IllusionBlockRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(IllusionBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();

        if (!entity.hasMimic()) {
            return;
        }

        BlockState state = entity.getDeepestFacedBlockState();
        BakedModel model = blockRenderManager.getModel(state);
        blockRenderManager.getModelRenderer().renderSmooth(entity.getWorld(), model, state, entity.getPos(), matrices,
                vertexConsumers.getBuffer(state.isOpaque() ? RenderLayer.getCutout() : RenderLayer.getTranslucent()), true, new Random(), state.getRenderingSeed(entity.getPos()), OverlayTexture.DEFAULT_UV);
    }
}
