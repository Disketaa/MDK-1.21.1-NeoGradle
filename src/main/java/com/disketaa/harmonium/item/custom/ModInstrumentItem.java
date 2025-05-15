package com.disketaa.harmonium.item.custom;

import com.disketaa.harmonium.util.ModDataComponents;
import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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

import java.util.List;

public class ModInstrumentItem extends Item {
	private final Holder<SoundEvent> soundEvent;
	private final float volume;

	private static final float MIN_PITCH_ANGLE = -75f;
	private static final float MAX_PITCH_ANGLE = 75f;
	private static final int[] TEMPO_VALUES = {80, 40, 20, 10, 5};
	private static final String[] TEMPO_NAMES = {"Very Slow", "Slow", "Medium", "Fast", "Very Fast"};
	private static final Holder<SoundEvent> TEMPO_CHANGE_SOUND = BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.UI_BUTTON_CLICK.value());

	public ModInstrumentItem(Properties properties, Holder<SoundEvent> soundEvent, float volume) {
		super(properties.stacksTo(1).component(TEMPO_INDEX, 2));
		this.soundEvent = soundEvent;
		this.volume = volume;
	}

	public static final DataComponentType<Integer> TEMPO_INDEX = ModDataComponents.TEMPO_INDEX;

	@Override
	public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int remainingUseDuration) {
		if (!(entity instanceof Player player)) return;

		int tempoIndex = stack.getOrDefault(TEMPO_INDEX, 2);
		int currentInterval = TEMPO_VALUES[tempoIndex];

		if (remainingUseDuration % currentInterval == 0) {
			float pitchDegrees = Mth.clamp(player.getXRot(), MIN_PITCH_ANGLE, MAX_PITCH_ANGLE);
			int note = (int)Mth.map(pitchDegrees, MIN_PITCH_ANGLE, MAX_PITCH_ANGLE, 24, 0);
			note = Mth.clamp(note, 0, 24);

			float soundPitch = (float)Math.pow(2.0, (note - 12) / 12.0);

			if (level.isClientSide) {
				double x = player.getX() + player.getViewVector(1.0F).x * 1.2;
				double y = player.getEyeY() - 0.2 + player.getViewVector(1.0F).y * 1.2;
				double z = player.getZ() + player.getViewVector(1.0F).z * 1.2;

				level.addParticle(ParticleTypes.NOTE, x, y, z, note / 24.0, 0.0, 0.0);
			} else {
				level.playSound(null,
					player.getX(), player.getY(), player.getZ(),
					soundEvent.value(), SoundSource.RECORDS,
					volume, soundPitch);
				level.gameEvent(player, GameEvent.INSTRUMENT_PLAY, player.position());
				player.awardStat(Stats.ITEM_USED.get(this));
			}
		}
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
		ItemStack instrument = player.getItemInHand(hand);
		ItemStack otherHand = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
		if (otherHand.is(Items.CLOCK)) {
			if (!level.isClientSide) {
				int currentIndex = instrument.getOrDefault(TEMPO_INDEX, 2);
				int newIndex = (currentIndex + 1) % TEMPO_VALUES.length;
				instrument.set(TEMPO_INDEX, newIndex);

				float pitch = Mth.map(newIndex, 0, TEMPO_VALUES.length-1, 1.5f, 2.0f);

				level.playSound(null,
					player.getX(), player.getY(), player.getZ(),
					TEMPO_CHANGE_SOUND.value(), SoundSource.PLAYERS,
					0.5f, pitch);

				player.displayClientMessage(Component.translatable("item.harmonium.instrument.tempo_change",
					TEMPO_NAMES[newIndex]), true);
			}
			return InteractionResultHolder.sidedSuccess(instrument, level.isClientSide());
		}

		player.startUsingItem(hand);
		return InteractionResultHolder.consume(instrument);
	}

	@Override
	public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.TOOT_HORN;
	}

	@Override
	public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
		return 72000;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		int index = stack.getOrDefault(TEMPO_INDEX, 2);
		tooltip.add(Component.translatable("item.harmonium.instrument.tempo", TEMPO_NAMES[index])
			.withStyle(ChatFormatting.GRAY));
	}
}