package com.disketaa.harmonium;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Harmonium.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	private static final ModConfigSpec.BooleanValue SHOW_HARMONIUM_CREATIVE_TAB;
	private static final ModConfigSpec.BooleanValue ADD_TO_VANILLA_TABS;
	private static final ModConfigSpec.IntValue TIN_BUTTON_SHORT_PRESS_DURATION;
	private static final ModConfigSpec.IntValue TIN_BUTTON_LONG_PRESS_DURATION;
	private static final ModConfigSpec.IntValue TIN_BUTTON_TRIGGER_CHANCE;

	static {
		BUILDER.push("creative_tab");

		SHOW_HARMONIUM_CREATIVE_TAB = BUILDER
			.comment(" Whether to show the Harmonium creative tab in the creative menu")
			.comment(" World restart required")
			.comment(" Default: true")
			.worldRestart()
			.define("show_harmonium_creative_tab", true);

		ADD_TO_VANILLA_TABS = BUILDER
			.comment(" Whether to add Harmonium items to vanilla creative tabs")
			.comment(" World restart required")
			.comment(" Default: true")
			.define("add_to_vanilla_tabs", true);

		BUILDER.pop();

		BUILDER.push("tin_button");

		TIN_BUTTON_SHORT_PRESS_DURATION = BUILDER
			.comment(" Duration in ticks for a short press")
			.defineInRange("short_press_duration", 2, 0, 20);

		TIN_BUTTON_LONG_PRESS_DURATION = BUILDER
			.comment(" Duration in ticks for a long press")
			.defineInRange("long_press_duration", 20, 0, 20);

		TIN_BUTTON_TRIGGER_CHANCE = BUILDER
			.comment(" Chance (0-100) for the button to trigger when pressed")
			.defineInRange("trigger_chance", 25, 0, 100);

		BUILDER.pop();

		SPEC = BUILDER.build();
	}

	public static final ModConfigSpec SPEC;

	public static boolean showHarmoniumCreativeTab;
	public static boolean addToVanillaTabs;
	public static int tinButtonShortPressDuration;
	public static int tinButtonLongPressDuration;
	public static int tinButtonTriggerChance;

	@SubscribeEvent
	public static void onConfigLoad(ModConfigEvent event) {
		if (event.getConfig().getSpec() == SPEC) {
			showHarmoniumCreativeTab = SHOW_HARMONIUM_CREATIVE_TAB.get();
			addToVanillaTabs = ADD_TO_VANILLA_TABS.get();
			tinButtonShortPressDuration = TIN_BUTTON_SHORT_PRESS_DURATION.get();
			tinButtonLongPressDuration = TIN_BUTTON_LONG_PRESS_DURATION.get();
			tinButtonTriggerChance = TIN_BUTTON_TRIGGER_CHANCE.get();
		}
	}
}