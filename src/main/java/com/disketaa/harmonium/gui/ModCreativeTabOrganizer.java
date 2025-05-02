package com.disketaa.harmonium.gui;

import com.disketaa.harmonium.Config;
import com.disketaa.harmonium.block.ModBlocks;
import com.disketaa.harmonium.item.FarmersDelightModItems;
import com.disketaa.harmonium.item.FriendsAndFoesModItems;
import com.disketaa.harmonium.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.*;

public class ModCreativeTabOrganizer {

	@SubscribeEvent
	public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (!Config.addHarmoniumItemsToOtherCreativeTabs) {
			return;
		}
		ResourceLocation tabId = event.getTabKey().location();

		if (tabId.equals(CreativeModeTabs.BUILDING_BLOCKS.location())) {
			addAfter(event, Items.WAXED_OXIDIZED_COPPER_BULB,
				List.of(
					ModBlocks.TIN_BLOCK.get(),
					ModBlocks.CHISELED_TIN.get(),
					ModBlocks.TIN_GRATE.get(),
					ModBlocks.CUT_TIN.get(),
					ModBlocks.CUT_TIN_STAIRS.get(),
					ModBlocks.CUT_TIN_SLAB.get(),
					ModBlocks.TIN_DOOR.get(),
					ModBlocks.TIN_TRAPDOOR.get(),
					ModBlocks.TIN_BULB.get()
				));
		}
		if (tabId.equals(CreativeModeTabs.COLORED_BLOCKS.location())) {
			addAfter(event, Items.PINK_CANDLE,
				List.of(
					ModBlocks.TIN_SOLDIER.get(),
					ModBlocks.WHITE_TIN_SOLDIER.get(),
					ModBlocks.LIGHT_GRAY_TIN_SOLDIER.get(),
					ModBlocks.GRAY_TIN_SOLDIER.get(),
					ModBlocks.BLACK_TIN_SOLDIER.get(),
					ModBlocks.BROWN_TIN_SOLDIER.get(),
					ModBlocks.RED_TIN_SOLDIER.get(),
					ModBlocks.ORANGE_TIN_SOLDIER.get(),
					ModBlocks.YELLOW_TIN_SOLDIER.get(),
					ModBlocks.LIME_TIN_SOLDIER.get(),
					ModBlocks.GREEN_TIN_SOLDIER.get(),
					ModBlocks.CYAN_TIN_SOLDIER.get(),
					ModBlocks.LIGHT_BLUE_TIN_SOLDIER.get(),
					ModBlocks.BLUE_TIN_SOLDIER.get(),
					ModBlocks.PURPLE_TIN_SOLDIER.get(),
					ModBlocks.MAGENTA_TIN_SOLDIER.get(),
					ModBlocks.PINK_TIN_SOLDIER.get()
				));
		}
		if (tabId.equals(CreativeModeTabs.NATURAL_BLOCKS.location())) {
			removeEntry(event, Items.IRON_ORE);
			removeEntry(event, Items.DEEPSLATE_IRON_ORE);
			addAfter(event, Items.DEEPSLATE_COPPER_ORE,
				List.of(
					ModBlocks.TIN_ORE.get(),
					ModBlocks.DEEPSLATE_TIN_ORE.get(),
					Items.IRON_ORE,
					Items.DEEPSLATE_IRON_ORE
				));
			removeEntry(event, Items.RAW_COPPER_BLOCK);
			addBefore(event, Items.RAW_IRON_BLOCK,
				List.of(
					Items.RAW_COPPER_BLOCK,
					ModBlocks.RAW_TIN_BLOCK.get()
				));
		}
		if (tabId.equals(CreativeModeTabs.FUNCTIONAL_BLOCKS.location())) {
			addAfter(event, Items.WAXED_OXIDIZED_COPPER_BULB,
				List.of(
					ModBlocks.TIN_BULB.get()
				));
			addAfter(event, Items.PINK_CANDLE,
				List.of(
					ModBlocks.TIN_SOLDIER.get(),
					ModBlocks.WHITE_TIN_SOLDIER.get(),
					ModBlocks.LIGHT_GRAY_TIN_SOLDIER.get(),
					ModBlocks.GRAY_TIN_SOLDIER.get(),
					ModBlocks.BLACK_TIN_SOLDIER.get(),
					ModBlocks.BROWN_TIN_SOLDIER.get(),
					ModBlocks.RED_TIN_SOLDIER.get(),
					ModBlocks.ORANGE_TIN_SOLDIER.get(),
					ModBlocks.YELLOW_TIN_SOLDIER.get(),
					ModBlocks.LIME_TIN_SOLDIER.get(),
					ModBlocks.GREEN_TIN_SOLDIER.get(),
					ModBlocks.CYAN_TIN_SOLDIER.get(),
					ModBlocks.LIGHT_BLUE_TIN_SOLDIER.get(),
					ModBlocks.BLUE_TIN_SOLDIER.get(),
					ModBlocks.PURPLE_TIN_SOLDIER.get(),
					ModBlocks.MAGENTA_TIN_SOLDIER.get(),
					ModBlocks.PINK_TIN_SOLDIER.get()
				));
		}
		if (tabId.equals(CreativeModeTabs.REDSTONE_BLOCKS.location())) {
			addAfter(event, Items.WAXED_OXIDIZED_COPPER_BULB,
				List.of(
					ModBlocks.TIN_BULB.get()
				));

			if (ModList.get().isLoaded("friendsandfoes")) {
				if (FriendsAndFoesModItems.WAXED_OXIDIZED_COPPER_BUTTON != Items.AIR) {
					addAfter(event, FriendsAndFoesModItems.WAXED_OXIDIZED_COPPER_BUTTON,
						List.of(ModBlocks.TIN_BUTTON.get()));
				}
			}
		}
		if (tabId.equals(CreativeModeTabs.INGREDIENTS.location())) {
			removeEntry(event, Items.RAW_IRON);
			addAfter(event, Items.RAW_COPPER,
				List.of(
					ModItems.BRONZE_SMELT.get(),
					ModItems.RAW_TIN.get(),
					Items.RAW_IRON
				));
			removeEntry(event, Items.NETHERITE_SCRAP);
			addBefore(event, Items.ANCIENT_DEBRIS,
				List.of(
					Items.NETHERITE_SCRAP
				));
			addBefore(event, Items.IRON_NUGGET,
				List.of(
					ModItems.COPPER_NUGGET.get(),
					ModItems.TIN_NUGGET.get(),
					ModItems.BRONZE_NUGGET.get()
				));
			removeEntry(event, Items.IRON_INGOT);
			addAfter(event, Items.COPPER_INGOT,
				List.of(
					ModItems.TIN_INGOT.get(),
					ModItems.BRONZE_INGOT.get(),
					Items.IRON_INGOT
				));
		}

		if (tabId.equals(ResourceLocation.parse("farmersdelight:farmersdelight"))) {
			if (FarmersDelightModItems.FLINT_KNIFE != Items.AIR) {
				removeEntry(event, FarmersDelightModItems.FLINT_KNIFE);
			}

			if (FarmersDelightModItems.IRON_KNIFE != Items.AIR) {
				addBefore(event, FarmersDelightModItems.IRON_KNIFE,
					List.of(
						ModItems.WOODEN_KNIFE.get(),
						ModItems.BRONZE_KNIFE.get()
					));
			}
		}
	}

	private static void addRelative(BuildCreativeModeTabContentsEvent event, Item referenceItem,
	                                List<ItemLike> itemsToAdd, boolean insertBefore) {
		boolean foundReference = false;
		ItemStack referenceStack = null;

		for (ItemStack stack : event.getParentEntries()) {
			if (stack.is(referenceItem)) {
				foundReference = true;
				referenceStack = stack;
				break;
			}
		}

		if (foundReference) {
			if (insertBefore) {
				for (ItemLike itemLike : itemsToAdd) {
					ItemStack stack = itemLike.asItem().getDefaultInstance();
					event.insertBefore(referenceStack, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
				}
			} else {
				for (int i = itemsToAdd.size() - 1; i >= 0; i--) {
					ItemLike itemLike = itemsToAdd.get(i);
					ItemStack stack = itemLike.asItem().getDefaultInstance();
					event.insertAfter(referenceStack, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
				}
			}
		} else {
			for (ItemLike itemLike : itemsToAdd) {
				event.accept(itemLike);
			}
		}
	}

	private static void addBefore(BuildCreativeModeTabContentsEvent event, Item referenceItem, List<ItemLike> itemsToAdd) {
		addRelative(event, referenceItem, itemsToAdd, true);
	}

	private static void addAfter(BuildCreativeModeTabContentsEvent event, Item referenceItem, List<ItemLike> itemsToAdd) {
		addRelative(event, referenceItem, itemsToAdd, false);
	}

	private static void removeEntry(BuildCreativeModeTabContentsEvent event, Item itemToRemove) {
		event.remove(itemToRemove.getDefaultInstance(),
			CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}
}