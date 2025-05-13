package com.disketaa.harmonium.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

public class ShieldItem extends TieredItem {

	public ShieldItem(Tier tier, float durabilityFactor, Properties properties) {
		super(tier, properties
			.durability((int)(tier.getUses() * durabilityFactor))
			.component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY));
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility ability) {
		return ItemAbilities.SHIELD_BLOCK.equals(ability);
	}

	@Override
	public boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
		return this.getTier().getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}
}