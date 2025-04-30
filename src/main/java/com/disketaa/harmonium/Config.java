package com.disketaa.harmonium;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Harmonium.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	// CreativeTabs
	private static final ModConfigSpec.BooleanValue SHOW_HARMONIUM_CREATIVE_TAB = BUILDER
		.define("show_harmonium_creative_tab", true);

	private static final ModConfigSpec.BooleanValue ADD_TO_VANILLA_TABS = BUILDER
		.define("add_to_vanilla_tabs", true);

	// TinButton
	private static final ModConfigSpec.IntValue TIN_BUTTON_SHORT_PRESS_DURATION = BUILDER
		.defineInRange("tin_button.short_press_duration", 2, 0, 20); // Default: 2

	private static final ModConfigSpec.IntValue TIN_BUTTON_LONG_PRESS_DURATION = BUILDER
		.defineInRange("tin_button.long_press_duration", 20, 0, 20); // Default: 20

	private static final ModConfigSpec.IntValue TIN_BUTTON_TRIGGER_CHANCE = BUILDER
		.defineInRange("tin_button.trigger_chance", 25, 0, 100); // Default: 25

	public static final ModConfigSpec SPEC = BUILDER.build();

	public static boolean showHarmoniumCreativeTab;
	public static boolean addToVanillaTabs;
	public static int tinButtonShortPressDuration;
	public static int tinButtonLongPressDuration;
	public static int tinButtonTriggerChance;

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
		showHarmoniumCreativeTab = SHOW_HARMONIUM_CREATIVE_TAB.get();
		addToVanillaTabs = ADD_TO_VANILLA_TABS.get();
		tinButtonShortPressDuration = TIN_BUTTON_SHORT_PRESS_DURATION.get();
		tinButtonLongPressDuration = TIN_BUTTON_LONG_PRESS_DURATION.get();
		tinButtonTriggerChance = TIN_BUTTON_TRIGGER_CHANCE.get();
	}
}