package com.disketaa.harmonium.util;

import com.disketaa.harmonium.item.custom.ModChiselItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMaps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.jetbrains.annotations.NotNull;

public class ModRenderEvents {

	@SubscribeEvent
	public static void onRenderLevelStage(RenderLevelStageEvent event) {
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) return;
		if (!ModChiselItem.shouldRenderCustomOutline()) return;
		if (!(ModChiselItem.minecraft.hitResult instanceof BlockHitResult blockHitResult)) return;

		boolean oldOutline = ModChiselItem.getRenderBlockOutline();
		ModChiselItem.setRenderBlockOutline(false);

		try {
			RenderSystem.lineWidth(3.0f);
			Tesselator tesselator = Tesselator.getInstance();
			BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);
			MultiBufferSource.BufferSource bufferSource = new MultiBufferSource.BufferSource(
				new ByteBufferBuilder(RenderType.lines().bufferSize()),
				Object2ObjectSortedMaps.emptyMap()
			) {
				@Override
				public @NotNull VertexConsumer getBuffer(@NotNull RenderType type) {
					return buffer;
				}
			};

			try {
				ModChiselItem.renderCustomOutline(
					event.getPoseStack(),
					bufferSource,
					blockHitResult
				);
			} finally {
				bufferSource.endBatch();
				RenderSystem.lineWidth(1.0f);
			}
		} finally {
			ModChiselItem.setRenderBlockOutline(oldOutline);
		}
	}
}