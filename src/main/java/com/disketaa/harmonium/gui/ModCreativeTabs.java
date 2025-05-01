package com.disketaa.harmonium.gui;

import com.disketaa.harmonium.Config;
import com.disketaa.harmonium.Harmonium;
import com.disketaa.harmonium.block.ModBlocks;
import com.disketaa.harmonium.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.disketaa.harmonium.item.ModItems.ITEMS;

public class ModCreativeTabs {
	private static Supplier<Item> getItemById(String id) {
		return () -> ITEMS.getEntries().stream()
			.filter(item -> item.getId().getPath().equals(id))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException(id + " item not registered."))
			.get();
	}

	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER =
		DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Harmonium.MOD_ID);

	public static Supplier<CreativeModeTab> HARMONIUM_TAB = null;

	public static void register(IEventBus eventBus) {
		HARMONIUM_TAB = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register("harmonium_tab",
			() -> CreativeModeTab.builder()
				.icon(() -> new ItemStack(ModBlocks.CHISELED_TIN.get()))
				.title(Component.translatable("creativetab.harmonium.harmonium"))
				.displayItems((params, output) -> {
					if (Config.showHarmoniumCreativeTab) {
						for (Item blockItem : ModBlocks.getAllBlockItems()) {
							output.accept(blockItem);
						}
						ModItems.getRegisteredItems().forEach(item -> output.accept(item.get()));
					}
				})
				.build());

		CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus);
	}
}