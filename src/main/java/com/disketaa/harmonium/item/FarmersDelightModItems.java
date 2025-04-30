package com.disketaa.harmonium.item;

import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class FarmersDelightModItems {
	public static final Item FLINT_KNIFE = getItem("farmersdelight:flint_knife");
	public static final Item IRON_KNIFE = getItem("farmersdelight:iron_knife");

	private static Item getItem(String id) {
		return BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
	}
}