package com.disketaa.harmonystuff.block;

import com.disketaa.harmonystuff.HarmonyStuff;
import com.disketaa.harmonystuff.item.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister.Blocks	BLOCKS =
		DeferredRegister.createBlocks(HarmonyStuff.MOD_ID);
	// PROPERTIES
	private static final class Properties {

		static final BlockBehaviour.Properties GENERIC_TIN = BlockBehaviour.Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.sound(SoundType.COPPER)
			.requiresCorrectToolForDrops()
			.strength(2.0f, 6.0f);

		static final BlockBehaviour.Properties ORE_STONE = BlockBehaviour.Properties.of()
			.mapColor(MapColor.STONE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.strength(3.0f, 3.0f);

		static final BlockBehaviour.Properties ORE_DEEPSLATE = BlockBehaviour.Properties.of()
			.mapColor(MapColor.DEEPSLATE)
			.sound(SoundType.DEEPSLATE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.strength(4.5f, 3.0f);

		static final BlockBehaviour.Properties ORE_RAW = BlockBehaviour.Properties.of()
			.mapColor(MapColor.RAW_IRON)
			.requiresCorrectToolForDrops()
			.strength(5.5f, 6.0f);
	}

	// BLOCKS
	public static final DeferredBlock<Block> TIN_BLOCK = registerTinBlock("tin_block");
	public static final DeferredBlock<Block> CHISELED_TIN = registerTinBlock("chiseled_tin");
	public static final DeferredBlock<Block> TIN_GRATE = registerBlock("tin_grate", () -> createGrateBlock(TIN_BLOCK.get()));
	public static final DeferredBlock<Block> CUT_TIN = registerTinBlock("cut_tin");
	public static final DeferredBlock<StairBlock> CUT_TIN_STAIRS = registerBlock("cut_tin_stairs", () -> new StairBlock(CUT_TIN.get().defaultBlockState(), Properties.GENERIC_TIN));
	public static final DeferredBlock<SlabBlock> CUT_TIN_SLAB = registerBlock("cut_tin_slab", () -> new SlabBlock(Properties.GENERIC_TIN));
	public static final DeferredBlock<DoorBlock> TIN_DOOR = registerBlock("tin_door", () -> new DoorBlock(BlockSetType.COPPER, createDoorProperties(TIN_BLOCK.get())));
	public static final DeferredBlock<TrapDoorBlock> TIN_TRAPDOOR = registerBlock("tin_trapdoor", () -> new TrapDoorBlock(BlockSetType.COPPER, createTrapdoorProperties(TIN_BLOCK.get())));
	public static final DeferredBlock<Block> TIN_ORE = registerBlock("tin_ore", () -> new Block(Properties.ORE_STONE));
	public static final DeferredBlock<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore", () -> new Block(Properties.ORE_DEEPSLATE));
	public static final DeferredBlock<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block", () -> new Block(Properties.ORE_RAW));

	// METHODS
	private static DeferredBlock<Block> registerTinBlock(String name) {
		return registerBlock(name, () -> new Block(Properties.GENERIC_TIN));
	}

	private static Block createGrateBlock(Block baseBlock) {
		return new Block(BlockBehaviour.Properties.ofFullCopy(baseBlock)
			.sound(SoundType.COPPER_GRATE)
			.noOcclusion()
			.isRedstoneConductor((state, level, pos) -> false)
			.isSuffocating((state, level, pos) -> false)
			.isViewBlocking((state, level, pos) -> false)) {
			@Override
			public boolean skipRendering(@NotNull BlockState state, @NotNull BlockState adjacentState, @NotNull Direction direction) {
				return adjacentState.is(this) || super.skipRendering(state, adjacentState, direction);
			}
		};
	}

	private static BlockBehaviour.Properties createDoorProperties(Block baseBlock) {
		return BlockBehaviour.Properties.ofFullCopy(baseBlock)
			.noOcclusion()
			.pushReaction(PushReaction.DESTROY);
	}

	private static BlockBehaviour.Properties createTrapdoorProperties(Block baseBlock) {
		return BlockBehaviour.Properties.ofFullCopy(baseBlock)
			.noOcclusion()
			.isValidSpawn(Blocks::never);
	}

	private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> blockSupplier) {
		DeferredBlock<T> block = BLOCKS.register(name, blockSupplier);
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
		return block;
	}

	public static List<Item> getAllBlockItems() {
		List<Item> blockItems = new ArrayList<>();
		for (DeferredHolder<Block, ? extends Block> block : BLOCKS.getEntries()) {
			blockItems.add(block.get().asItem());
		}
		return blockItems;
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}