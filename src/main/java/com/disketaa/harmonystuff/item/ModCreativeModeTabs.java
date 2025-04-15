package com.disketaa.harmonystuff.item;

import com.disketaa.harmonystuff.HarmonyStuff;
import com.disketaa.harmonystuff.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER =
		DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HarmonyStuff.MOD_ID);

	public static final Supplier<CreativeModeTab> HARMONY_STUFF_TAB = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register("harmony_stuff_tab",
		() -> CreativeModeTab
			.builder()
			.icon(() -> new ItemStack(ModItems.TIN_INGOT.get()))
			.title(Component.translatable("creativetab.harmony_stuff.harmony_stuff"))
			.displayItems((itemDisplayParameters, output) -> {
				output.accept(ModBlocks.TIN_BLOCK);
				output.accept(ModBlocks.CHISELED_TIN);
				output.accept(ModBlocks.TIN_GRATE);
				output.accept(ModBlocks.CUT_TIN);
				output.accept(ModBlocks.CUT_TIN_STAIRS);
				output.accept(ModBlocks.CUT_TIN_SLAB);
				output.accept(ModBlocks.TIN_DOOR);
				output.accept(ModBlocks.TIN_TRAPDOOR);
				output.accept(ModBlocks.TIN_ORE);
				output.accept(ModBlocks.DEEPSLATE_TIN_ORE);
				output.accept(ModBlocks.RAW_TIN_BLOCK);
				output.accept(ModItems.WOODEN_KNIFE);
				output.accept(ModItems.BRONZE_KNIFE);
				output.accept(ModItems.RAW_TIN);
				output.accept(ModItems.COPPER_NUGGET);
				output.accept(ModItems.TIN_NUGGET);
				output.accept(ModItems.TIN_INGOT);

			}).build());

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus);
	}
}