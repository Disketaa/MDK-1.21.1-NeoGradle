package com.disketaa.harmonystuff.item;

import com.disketaa.harmonystuff.HarmonyStuff;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HarmonyStuff.MOD_ID);

	public static final DeferredItem<Item> TIN_INGOT = ITEMS.register("tin_ingot",
		() -> new Item(new Item.Properties()));

	public static final DeferredItem<Item> RAW_TIN = ITEMS.register("raw_tin",
		() -> new Item(new Item.Properties()));

	public static final DeferredItem<Item> TIN_NUGGET = ITEMS.register("tin_nugget",
		() -> new Item(new Item.Properties()));

	public static final DeferredItem<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
		() -> new Item(new Item.Properties()));

	public static final DeferredItem<Item> WOODEN_KNIFE = ITEMS.register("wooden_knife",
		() -> new Item(new Item.Properties()));

	public static final DeferredItem<Item> BRONZE_KNIFE = ITEMS.register("bronze_knife",
		() -> new Item(new Item.Properties()));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}