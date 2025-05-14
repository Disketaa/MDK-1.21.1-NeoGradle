package com.disketaa.harmonium.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class ModInstrumentItem extends Item {
	private final Holder<SoundEvent> soundEvent;
	private final float volume;

	public ModInstrumentItem(Properties properties, Holder<SoundEvent> soundEvent, float volume) {
		super(properties.stacksTo(1));
		this.soundEvent = soundEvent;
		this.volume = volume;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		player.startUsingItem(hand);

		float normalized = (player.getXRot() + 90f) / 180f;
		float soundPitch = Mth.lerp(normalized, 2.0f, 0.5f);
		int note = (int)((soundPitch - 0.5f) * 24f / 1.5f);
		note = Mth.clamp(note, 0, 24);

		if (level.isClientSide) {
			level.addParticle(ParticleTypes.NOTE,
				player.getX(),
				player.getEyeY() + 0.5,
				player.getZ(),
				note / 24.0, 0.0, 0.0);
		} else {
			level.playSound(null, player.getX(), player.getY(), player.getZ(),
				soundEvent.value(), SoundSource.RECORDS,
				volume, soundPitch);
			level.gameEvent(player, GameEvent.INSTRUMENT_PLAY, player.position());
			player.awardStat(Stats.ITEM_USED.get(this));
		}

		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.TOOT_HORN;
	}

	@Override
	public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
		return 20;
	}
}