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

import java.lang.reflect.Field;

import static net.neoforged.neoforgespi.ILaunchContext.LOGGER;

public class ModChiselItem {
	public static final Minecraft minecraft = Minecraft.getInstance();

	public static boolean shouldRenderCustomOutline() {
		if (minecraft.player == null || minecraft.hitResult == null || minecraft.hitResult.getType() != HitResult.Type.BLOCK) {
			return false;
		}

		ItemStack mainHandItem = minecraft.player.getMainHandItem();
		return mainHandItem.is(net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "tools/chisel")));
	}

	public static void setRenderEffects(boolean enabled) {
		try {
			Field outlineField = GameRenderer.class.getDeclaredField("renderBlockOutline");
			outlineField.setAccessible(true);
			outlineField.setBoolean(minecraft.gameRenderer, !enabled);
		} catch (Exception e) {
			LOGGER.error("Failed to modify render effects", e);
		}
	}

	public static void setRenderBlockOutline(boolean enabled) {
		try {
			Field field = GameRenderer.class.getDeclaredField("renderBlockOutline");
			field.setAccessible(true);
			field.setBoolean(minecraft.gameRenderer, enabled);
		} catch (NoSuchFieldException e) {
			LOGGER.error("Failed to find renderBlockOutline field", e);
		} catch (IllegalAccessException e) {
			LOGGER.error("Failed to access renderBlockOutline field", e);
		}
	}

	public static boolean getRenderBlockOutline() {
		try {
			Field field = GameRenderer.class.getDeclaredField("renderBlockOutline");
			field.setAccessible(true);
			return field.getBoolean(minecraft.gameRenderer);
		} catch (NoSuchFieldException e) {
			LOGGER.error("Failed to find renderBlockOutline field", e);
		} catch (IllegalAccessException e) {
			LOGGER.error("Failed to access renderBlockOutline field", e);
		}
		return false;
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