package com.disketaa.harmonium.item.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;

public class ModChiselItem {
	public static final Minecraft minecraft = Minecraft.getInstance();

	public static boolean shouldRenderCustomOutline() {
		if (minecraft.player == null || minecraft.hitResult == null || minecraft.hitResult.getType() != HitResult.Type.BLOCK) {
			return false;
		}

		ItemStack mainHandItem = minecraft.player.getMainHandItem();
		return mainHandItem.is(net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "tools/chisel")));
	}

	public static void renderCustomOutline(PoseStack poseStack, MultiBufferSource bufferSource, BlockHitResult hitResult) {
		if (minecraft.level == null || minecraft.player == null) return;

		BlockPos pos = hitResult.getBlockPos();
		BlockState state = minecraft.level.getBlockState(pos);
		VoxelShape shape = state.getShape(minecraft.level, pos, CollisionContext.of(minecraft.player));

		if (shape.isEmpty()) {
			shape = Shapes.block();
		}

		double division = 1.0 / 8.0;
		Vec3 hitLoc = hitResult.getLocation().subtract(Vec3.atLowerCornerOf(pos));

		int xCell = (int)(hitLoc.x / division);
		int yCell = (int)(hitLoc.y / division);
		int zCell = (int)(hitLoc.z / division);

		xCell = Math.min(7, Math.max(0, xCell));
		yCell = Math.min(7, Math.max(0, yCell));
		zCell = Math.min(7, Math.max(0, zCell));

		VoxelShape subShape = Shapes.box(
			xCell * division,
			yCell * division,
			zCell * division,
			(xCell + 1) * division,
			(yCell + 1) * division,
			(zCell + 1) * division
		);

		float red = 0.0F;
		float green = 1.0F;
		float blue = 0.0F;
		float alpha = 0.8F;

		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		VertexConsumer consumer = bufferSource.getBuffer(RenderType.lines());
		LevelRenderer.renderVoxelShape(
			poseStack,
			consumer,
			subShape,
			pos.getX(), pos.getY(), pos.getZ(),
			red, green, blue, alpha,
			false
		);
	}
}