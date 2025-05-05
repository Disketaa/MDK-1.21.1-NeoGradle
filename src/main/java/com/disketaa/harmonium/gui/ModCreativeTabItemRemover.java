package com.disketaa.harmonium.gui;

import com.disketaa.harmonium.Config;
import com.disketaa.harmonium.item.FarmersDelightModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class ModCreativeTabItemRemover {

	@SubscribeEvent
	public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
		removeFlintKnife(event);
		removeStoneTools(event);
	}

	private static void removeFlintKnife(BuildCreativeModeTabContentsEvent event) {
		if (!Config.removeFlintKnife) {
			return;
		}

		if (FarmersDelightModItems.FLINT_KNIFE != Items.AIR) {
			removeEntry(event, FarmersDelightModItems.FLINT_KNIFE);
		}
	}

	private static void removeStoneTools(BuildCreativeModeTabContentsEvent event) {
		if (!Config.removeStoneTools) {
			return;
		}

		removeEntry(event, Items.STONE_SHOVEL);
		removeEntry(event, Items.STONE_PICKAXE);
		removeEntry(event, Items.STONE_AXE);
		removeEntry(event, Items.STONE_HOE);
		removeEntry(event, Items.STONE_SWORD);
	}

	private static void removeEntry(BuildCreativeModeTabContentsEvent event, Item itemToRemove) {
		event.remove(itemToRemove.getDefaultInstance(),
			CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}
}