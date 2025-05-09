package com.disketaa.harmonium;

import com.disketaa.harmonium.configuration.ModConfigurationBuilder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import java.util.*;

@EventBusSubscriber(modid = Harmonium.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
	public static final ModConfigSpec SPEC;
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
	private static final Map<String, List<ConfigEntry>> CONFIG_ENTRIES = new LinkedHashMap<>();
	private static String currentCategory = "";

	public static final ModConfigSpec.BooleanValue SHOW_HARMONIUM_CREATIVE_TAB;
	public static final ModConfigSpec.BooleanValue ADD_HARMONIUM_ITEMS_TO_OTHER_TABS;
	public static final ModConfigSpec.BooleanValue TIN_GENERATION;
	public static final ModConfigSpec.BooleanValue REMOVE_STONE_TOOLS;
	public static ModConfigSpec.BooleanValue REMOVE_FLINT_KNIFE = null;
	public static ModConfigSpec.IntValue TIN_BUTTON_SHORT_PRESS_DURATION = null;
	public static ModConfigSpec.IntValue TIN_BUTTON_LONG_PRESS_DURATION = null;
	public static ModConfigSpec.IntValue TIN_BUTTON_FAILURE_CHANCE = null;

	public static boolean showHarmoniumCreativeTab;
	public static boolean addHarmoniumItemsToOtherCreativeTabs;
	public static boolean tinGeneration;
	public static boolean removeStoneTools;
	public static boolean removeFlintKnife;
	public static int tinButtonShortPressDuration;
	public static int tinButtonLongPressDuration;
	public static int tinButtonFailureChance;

	static {
		pushCategory("creative_tab");
		SHOW_HARMONIUM_CREATIVE_TAB = defineBoolean("show_harmonium_creative_tab", true);
		ADD_HARMONIUM_ITEMS_TO_OTHER_TABS = defineBoolean("add_harmonium_items_to_other_tabs", true);
		popCategory();

		pushCategory("generation");
		TIN_GENERATION = defineBoolean("tin_generation", true);
		popCategory();

		if (ModList.get().isLoaded("friendsandfoes")) {
			pushCategory("blocks");
			TIN_BUTTON_SHORT_PRESS_DURATION = defineInt("tin_button_short_press_duration", 5, 0, 20);
			TIN_BUTTON_LONG_PRESS_DURATION = defineInt("tin_button_long_press_duration", 10, 0, 20);
			TIN_BUTTON_FAILURE_CHANCE = defineInt("tin_button_failure_chance", 25, 0, 100);
			popCategory();
		}

		pushCategory("items");
		REMOVE_STONE_TOOLS = defineBoolean("remove_stone_tools", true);
		if (ModList.get().isLoaded("farmersdelight")) REMOVE_FLINT_KNIFE = defineBoolean("remove_flint_knife", true);
		popCategory();

		SPEC = BUILDER.build();
	}

	private static void pushCategory(String path) {
		BUILDER.push(path);
		currentCategory = path;
		CONFIG_ENTRIES.putIfAbsent(path, new ArrayList<>());
	}

	private static void popCategory() {
		BUILDER.pop();
		currentCategory = "";
	}

	private static ModConfigSpec.BooleanValue defineBoolean(String path, boolean defaultValue) {
		String translationKey = "config.harmonium." + path;
		ModConfigSpec.BooleanValue value = BUILDER.translation(translationKey + ".tooltip").define(path, defaultValue);
		CONFIG_ENTRIES.get(currentCategory).add(new ConfigEntry(value, translationKey));
		return value;
	}

	private static ModConfigSpec.IntValue defineInt(String path, int defaultValue, int min, int max) {
		String translationKey = "config.harmonium." + path;
		ModConfigSpec.IntValue value = BUILDER.translation(translationKey + ".tooltip").defineInRange(path, defaultValue, min, max);
		CONFIG_ENTRIES.get(currentCategory).add(new ConfigEntry(value, translationKey, min, max));
		return value;
	}

	public static void buildConfigScreen(ModConfigurationBuilder builder) {
		CONFIG_ENTRIES.forEach((category, entries) -> {
			if (category.equals("blocks") && !ModList.get().isLoaded("friendsandfoes")) return;

			builder.addCategory("config.harmonium." + category);
			entries.forEach(entry -> {
				if (entry.translationKey.equals("config.harmonium.remove_flint_knife") && !ModList.get().isLoaded("farmersdelight")) return;

				if (entry.value instanceof ModConfigSpec.BooleanValue booleanValue) {
					builder.addBooleanConfig(booleanValue, entry.translationKey);
				} else if (entry.value instanceof ModConfigSpec.IntValue intValue) {
					builder.addIntConfig(intValue, entry.translationKey, entry.min, entry.max);
				}
			});
			builder.addSpacing(16);
		});
	}

	@SubscribeEvent
	public static void onLoad(ModConfigEvent event) {
		if (event.getConfig().getSpec() == SPEC) {
			showHarmoniumCreativeTab = SHOW_HARMONIUM_CREATIVE_TAB.get();
			addHarmoniumItemsToOtherCreativeTabs = ADD_HARMONIUM_ITEMS_TO_OTHER_TABS.get();
			tinGeneration = TIN_GENERATION.get();
			removeStoneTools = REMOVE_STONE_TOOLS.get();

			if (ModList.get().isLoaded("farmersdelight")) removeFlintKnife = REMOVE_FLINT_KNIFE.get();
			if (ModList.get().isLoaded("friendsandfoes")) {
				tinButtonShortPressDuration = TIN_BUTTON_SHORT_PRESS_DURATION.get();
				tinButtonLongPressDuration = TIN_BUTTON_LONG_PRESS_DURATION.get();
				tinButtonFailureChance = TIN_BUTTON_FAILURE_CHANCE.get();
			}
		}
	}

	private static class ConfigEntry {
		final ModConfigSpec.ConfigValue<?> value;
		final String translationKey;
		final int min;
		final int max;

		ConfigEntry(ModConfigSpec.ConfigValue<?> value, String translationKey) {
			this(value, translationKey, 0, 0);
		}

		ConfigEntry(ModConfigSpec.ConfigValue<?> value, String translationKey, int min, int max) {
			this.value = value;
			this.translationKey = translationKey;
			this.min = min;
			this.max = max;
		}
	}
}