package net.timtaran.interactivemc.body.player;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.Blocks;
import net.timtaran.interactivemc.physics.physics.body.client.VxRenderState;
import net.timtaran.interactivemc.physics.physics.body.client.body.renderer.VxRigidBodyRenderer;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class PlayerBodyPartRenderer extends VxRigidBodyRenderer<PlayerBodyPartRigidBody> {
    @Override
    public void render(PlayerBodyPartRigidBody body, PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks, int packedLight, VxRenderState renderState) {
        PlayerBodyPart partType = body.get(PlayerBodyPartRigidBody.DATA_BODY_PART);

        poseStack.pushPose();

        // 1. Apply Physics Transform (Rotation from Jolt Physics)
        var transform = renderState.transform;
        poseStack.mulPose(new Quaternionf(
                transform.getRotation().getX(),
                transform.getRotation().getY(),
                transform.getRotation().getZ(),
                transform.getRotation().getW()
        ));

        // 2. Scale visual model to match physics bounds
        // We calculate the scale factor required to stretch the model part to fill the rigid body's dimensions.
        float physicsHalfX = body.get(PlayerBodyPartRigidBody.DATA_HALF_EXTENTS).getX();
        float physicsHalfY = body.get(PlayerBodyPartRigidBody.DATA_HALF_EXTENTS).getY();
        float physicsHalfZ = body.get(PlayerBodyPartRigidBody.DATA_HALF_EXTENTS).getZ();

        //noinspection DuplicatedCode
        float scaleX = physicsHalfX * 2f;
        float scaleY = physicsHalfY * 2f;
        float scaleZ = physicsHalfZ * 2f;

        poseStack.translate(-0.5f, -0.5f, -0.5f);
        poseStack.scale(scaleX, scaleY, scaleZ);
        poseStack.translate(0.5f, 0.5f, 0.5f);

        // 4. Center Alignment
        // Align the specific body part so its pivot matches the rigid body's center.
        // applyPartOffset(poseStack, partType);

        // 5. Render a simple model.
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                Blocks.WHITE_TERRACOTTA.defaultBlockState(),
                poseStack,
                bufferSource,
                packedLight,
                OverlayTexture.NO_OVERLAY
        );

        poseStack.popPose();
    }
}
