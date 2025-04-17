package com.disketaa.harmonystuff.item;

import com.disketaa.harmonystuff.HarmonyStuff;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HarmonyStuff.MOD_ID);
	private static final List<DeferredItem<Item>> REGISTERED_ITEMS = new ArrayList<>();

	public static final DeferredItem<Item> WOODEN_KNIFE = registerSimpleItem("wooden_knife");
	public static final DeferredItem<Item> BRONZE_KNIFE = registerSimpleItem("bronze_knife");
	public static final DeferredItem<Item> RAW_TIN = registerSimpleItem("raw_tin");
	public static final DeferredItem<Item> COPPER_NUGGET = registerSimpleItem("copper_nugget");
	public static final DeferredItem<Item> TIN_NUGGET = registerSimpleItem("tin_nugget");
	public static final DeferredItem<Item> TIN_INGOT = registerSimpleItem("tin_ingot");

	private static DeferredItem<Item> registerSimpleItem(String name) {
		DeferredItem<Item> item = ITEMS.register(name, () -> new Item(new Item.Properties()));
		REGISTERED_ITEMS.add(item);
		return item;
	}

	public static List<DeferredItem<Item>> getRegisteredItems() {
		return new ArrayList<>(REGISTERED_ITEMS);
	}

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}