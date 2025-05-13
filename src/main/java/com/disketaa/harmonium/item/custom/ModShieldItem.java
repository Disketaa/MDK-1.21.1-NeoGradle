package com.disketaa.harmonium.item.custom;

import com.disketaa.harmonium.sound.ModSoundType;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.NotNull;

public class ModShieldItem extends ShieldItem {
	private final Tier tier;

	public ModShieldItem(Tier tier, float durabilityFactor, Properties properties) {
		super(properties
			.durability((int)(tier.getUses() * durabilityFactor))
			.component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY));
		this.tier = tier;
	}

	@Override
	public boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
		return this.tier.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public @NotNull Holder<SoundEvent> getEquipSound() {
		return ModSoundType.BUCKLER_EQUIP;
	}
}