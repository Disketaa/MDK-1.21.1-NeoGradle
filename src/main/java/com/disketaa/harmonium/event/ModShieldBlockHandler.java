package com.disketaa.harmonium.event;

import com.disketaa.harmonium.item.ModItems;
import com.disketaa.harmonium.sound.ModSoundType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;

public class ModShieldBlockHandler {
	@SubscribeEvent
	public static void onShieldBlock(LivingShieldBlockEvent event) {
		LivingEntity entity = event.getEntity();

		ItemStack shield = entity.getUseItem();

		if (!entity.isBlocking() || !(shield.getItem() instanceof ShieldItem)) {
			return;
		}

		if (shield.is(ModItems.BUCKLER.get())) {
			entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
				ModSoundType.BUCKLER_BLOCK.get(), entity.getSoundSource(), 1.0F, 1.0F);
		}
	}
}