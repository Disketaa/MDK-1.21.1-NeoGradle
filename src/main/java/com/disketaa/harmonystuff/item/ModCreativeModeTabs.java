package com.disketaa.harmonystuff.item;

import com.disketaa.harmonystuff.HarmonyStuff;
import com.disketaa.harmonystuff.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.disketaa.harmonystuff.item.ModItems.ITEMS;

public class ModCreativeModeTabs {
	private static Supplier<Item> getItemById(String id) {
		return () -> ITEMS.getEntries().stream()
			.filter(item -> item.getId().getPath().equals(id))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException(id + " item not registered"))
			.get();
	}

	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER =
		DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HarmonyStuff.MOD_ID);

	public static final Supplier<CreativeModeTab> HARMONY_STUFF_TAB = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register("harmony_stuff_tab",
		() -> CreativeModeTab.builder()
			.icon(() -> new ItemStack(getItemById("tin_ingot").get()))
			.title(Component.translatable("creativetab.harmony_stuff.harmony_stuff"))
			.displayItems((itemDisplayParameters, output) -> {
				// Add blocks first
				output.accept(ModBlocks.TIN_BLOCK.get());
				output.accept(ModBlocks.CHISELED_TIN.get());
				output.accept(ModBlocks.TIN_GRATE.get());
				output.accept(ModBlocks.CUT_TIN.get());
				output.accept(ModBlocks.CUT_TIN_STAIRS.get());
				output.accept(ModBlocks.CUT_TIN_SLAB.get());
				output.accept(ModBlocks.TIN_DOOR.get());
				output.accept(ModBlocks.TIN_TRAPDOOR.get());
				output.accept(ModBlocks.TIN_ORE.get());
				output.accept(ModBlocks.DEEPSLATE_TIN_ORE.get());
				output.accept(ModBlocks.RAW_TIN_BLOCK.get());

				ModItems.getRegisteredItems().forEach(item -> output.accept(item.get()));
			}).build());

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus);
	}
}