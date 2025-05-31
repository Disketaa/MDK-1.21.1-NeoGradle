package com.disketaa.harmonium.util;

import com.disketaa.harmonium.item.custom.ModChiselItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class ModRenderEvents {
	@SubscribeEvent
	public static void onRenderLevelStage(RenderLevelStageEvent event) {
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) return;
		if (!ModChiselItem.shouldRenderCustomOutline()) return;
		if (!(Minecraft.getInstance().hitResult instanceof BlockHitResult blockHitResult)) return;

		PoseStack poseStack = event.getPoseStack();
		MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

		poseStack.pushPose();
		ModChiselItem.renderCustomOutline(poseStack, bufferSource, blockHitResult);
		poseStack.popPose();
	}
}