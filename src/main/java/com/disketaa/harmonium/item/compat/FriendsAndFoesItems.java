package com.disketaa.harmonium.item.compat;

import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class FriendsAndFoesItems {
	public static final Item WAXED_OXIDIZED_COPPER_BUTTON = getItem("friendsandfoes:waxed_oxidized_copper_button");

	private static Item getItem(String id) {
		return BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
	}
}