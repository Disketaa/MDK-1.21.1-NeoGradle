package com.disketaa.harmonium.item.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;

public class ModChiselItem {
	private static final Minecraft minecraft = Minecraft.getInstance();

	public static boolean shouldRenderCustomOutline() {
		if (minecraft.player == null || minecraft.level == null) return false;
		if (minecraft.hitResult == null || minecraft.hitResult.getType() != HitResult.Type.BLOCK) return false;

		ItemStack mainHandItem = minecraft.player.getMainHandItem();
		return mainHandItem.is(net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "tools/chisel")));
	}

	public static void renderCustomOutline(PoseStack poseStack, MultiBufferSource bufferSource, BlockHitResult hitResult) {
		minecraft.gameRenderer.setRenderBlockOutline(false);

		BlockPos pos = hitResult.getBlockPos();
		double division = 1.0 / 8.0;
		Vec3 hitLoc = hitResult.getLocation().subtract(Vec3.atLowerCornerOf(pos));

		int xCell = (int)(hitLoc.x / division);
		int yCell = (int)(hitLoc.y / division);
		int zCell = (int)(hitLoc.z / division);

		xCell = Math.min(7, Math.max(0, xCell));
		yCell = Math.min(7, Math.max(0, yCell));
		zCell = Math.min(7, Math.max(0, zCell));

		double padding = 0.001;
		VoxelShape subShape = Shapes.box(
			xCell * division + padding,
			yCell * division + padding,
			zCell * division + padding,
			(xCell + 1) * division - padding,
			(yCell + 1) * division - padding,
			(zCell + 1) * division - padding
		);

		RenderSystem.disableDepthTest();
		RenderSystem.lineWidth(3.0f);

		VertexConsumer consumer = bufferSource.getBuffer(RenderType.lines());
		LevelRenderer.renderVoxelShape(
			poseStack,
			consumer,
			subShape,
			pos.getX(), pos.getY(), pos.getZ(),
			1.0F, 0.0F, 0.0F, 1.0F,
			false
		);

		RenderSystem.enableDepthTest();
		RenderSystem.lineWidth(1.0f);
	}
}