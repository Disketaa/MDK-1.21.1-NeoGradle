package com.disketaa.harmonium;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Harmonium.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
	public static final ModConfigSpec SPEC;
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.BooleanValue SHOW_HARMONIUM_CREATIVE_TAB;
	public static final ModConfigSpec.BooleanValue ADD_HARMONIUM_ITEMS_TO_OTHER_TABS;
	public static final ModConfigSpec.BooleanValue TIN_GENERATION;
	public static final ModConfigSpec.BooleanValue REMOVE_STONE_TOOLS;
	public static final ModConfigSpec.BooleanValue REMOVE_FLINT_KNIFE;
	public static final ModConfigSpec.IntValue TIN_BUTTON_SHORT_PRESS_DURATION;
	public static final ModConfigSpec.IntValue TIN_BUTTON_LONG_PRESS_DURATION;
	public static final ModConfigSpec.IntValue TIN_BUTTON_FAILURE_CHANCE;

	public static boolean showHarmoniumCreativeTab;
	public static boolean addHarmoniumItemsToOtherCreativeTabs;
	public static boolean tinGeneration;
	public static boolean removeStoneTools;
	public static boolean removeFlintKnife;
	public static int tinButtonShortPressDuration;
	public static int tinButtonLongPressDuration;
	public static int tinButtonFailureChance;

	static {
		BUILDER.push("creative_tab");
		SHOW_HARMONIUM_CREATIVE_TAB = BUILDER
			.comment(" Whether to show the Harmonium creative tab in the creative menu")
			.comment(" World restart required")
			.comment(" Default: true")
			.define("show_harmonium_creative_tab", true);
		ADD_HARMONIUM_ITEMS_TO_OTHER_TABS = BUILDER
			.comment(" Whether to add Harmonium items to other creative tabs")
			.comment(" World restart required")
			.comment(" Default: true")
			.define("add_harmonium_items_to_other_tabs", true);
		BUILDER.pop();

		BUILDER.push("generation");
		TIN_GENERATION = BUILDER
			.comment(" Whether to generate tin ore in overworld")
			.comment(" World restart required")
			.comment(" Default: true")
			.define("tin_generation", true);
		BUILDER.pop();

		BUILDER.push("items");
		REMOVE_STONE_TOOLS = BUILDER
			.comment(" Whether to remove stone tools")
			.comment(" World restart required")
			.comment(" Default: true")
			.define("remove_stone_tools", true);
		REMOVE_FLINT_KNIFE = BUILDER
			.comment(" Whether to remove Farmer's Delight flint knife")
			.comment(" World restart required")
			.comment(" Default: true")
			.define("remove_flint_knife", true);
		BUILDER.pop();

		BUILDER.push("tin_button");
		TIN_BUTTON_SHORT_PRESS_DURATION = BUILDER
			.comment(" Duration in ticks for a short press")
			.defineInRange("short_press_duration", 2, 0, 20);
		TIN_BUTTON_LONG_PRESS_DURATION = BUILDER
			.comment(" Duration in ticks for a long press")
			.defineInRange("long_press_duration", 20, 0, 20);
		TIN_BUTTON_FAILURE_CHANCE = BUILDER
			.comment(" Chance for the button to fail when pressed")
			.defineInRange("failure_chance", 25, 0, 100);
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

	@SubscribeEvent
	public static void onLoad(ModConfigEvent event) {
		if (event.getConfig().getSpec() == SPEC) {
			showHarmoniumCreativeTab = SHOW_HARMONIUM_CREATIVE_TAB.get();
			addHarmoniumItemsToOtherCreativeTabs = ADD_HARMONIUM_ITEMS_TO_OTHER_TABS.get();
			tinGeneration = TIN_GENERATION.get();
			removeStoneTools = REMOVE_STONE_TOOLS.get();
			removeFlintKnife = REMOVE_FLINT_KNIFE.get();
			tinButtonShortPressDuration = TIN_BUTTON_SHORT_PRESS_DURATION.get();
			tinButtonLongPressDuration = TIN_BUTTON_LONG_PRESS_DURATION.get();
			tinButtonFailureChance = TIN_BUTTON_FAILURE_CHANCE.get();
		}
	}
}