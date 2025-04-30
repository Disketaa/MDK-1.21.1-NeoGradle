package com.disketaa.harmonium;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.logging.Logger;

@EventBusSubscriber(modid = Harmonium.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	private static final ModConfigSpec.BooleanValue HIDE_CREATIVE_TAB = BUILDER
		.comment("Whether to hide Harmonium creative tab")
		.define("hide_creative_tab", false);

	private static final ModConfigSpec.IntValue TIN_BUTTON_SHORT_PRESS_DURATION = BUILDER
		.comment("Duration in ticks (0-20) for a short press of Tin Button")
		.defineInRange("tin_button.short_press_duration", 2, 0, 20);

	private static final ModConfigSpec.IntValue TIN_BUTTON_LONG_PRESS_DURATION = BUILDER
		.comment("Duration in ticks (0-20) for a long press of Tin Button")
		.defineInRange("tin_button.long_press_duration", 20, 0, 20);

	private static final ModConfigSpec.IntValue TIN_BUTTON_TRIGGER_CHANCE = BUILDER
		.comment("Chance (0-100%) for Tin Button to trigger its effect")
		.defineInRange("tin_button.trigger_chance", 25, 0, 100);

	static final ModConfigSpec SPEC = BUILDER.build();

	public static boolean hideCreativeTab;
	public static int tinButtonShortPressDuration;
	public static int tinButtonLongPressDuration;
	public static int tinButtonTriggerChance;

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
		hideCreativeTab = HIDE_CREATIVE_TAB.get();
		tinButtonShortPressDuration = TIN_BUTTON_SHORT_PRESS_DURATION.get();
		tinButtonLongPressDuration = TIN_BUTTON_LONG_PRESS_DURATION.get();
		tinButtonTriggerChance = TIN_BUTTON_TRIGGER_CHANCE.get();
	}
}