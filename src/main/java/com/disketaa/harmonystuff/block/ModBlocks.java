package com.disketaa.harmonystuff.block;

import com.disketaa.harmonystuff.HarmonyStuff;
import com.disketaa.harmonystuff.item.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockGetter;
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
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister.Blocks	BLOCKS =
		DeferredRegister.createBlocks(HarmonyStuff.MOD_ID);

	// PROPERTIES
	private static final BlockBehaviour.Properties TIN =
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.requiresCorrectToolForDrops()
			.strength(2.0f)
			.explosionResistance(6.0f)
			.sound(SoundType.COPPER);

	// BLOCKS
	public static final DeferredBlock<Block> TIN_BLOCK = registerBlock("tin_block",
		() -> new Block(TIN));

	public static final DeferredBlock<Block> CHISELED_TIN = registerBlock("chiseled_tin",
		() -> new Block(TIN));

	public static final DeferredBlock<Block> TIN_GRATE = registerBlock("tin_grate",
		() -> new Block(BlockBehaviour.Properties.ofFullCopy(TIN_BLOCK.get())
			.sound(SoundType.COPPER_GRATE)
			.noOcclusion()
			.isRedstoneConductor((state, level, pos) -> false)
			.isSuffocating((state, level, pos) -> false)
			.isViewBlocking((state, level, pos) -> false)) {
		@Override
			public boolean skipRendering(@NotNull BlockState state, @NotNull BlockState adjacentState, @NotNull Direction direction) {
			return adjacentState.is(this) || super.skipRendering(state, adjacentState, direction);
		}});

	public static final DeferredBlock<Block> CUT_TIN = registerBlock("cut_tin",
		() -> new Block(TIN));

	public static final DeferredBlock<StairBlock> CUT_TIN_STAIRS = registerBlock("cut_tin_stairs",
		() -> new StairBlock(ModBlocks.CUT_TIN.get().defaultBlockState(), (TIN)));

	public static final DeferredBlock<SlabBlock> CUT_TIN_SLAB = registerBlock("cut_tin_slab",
		() -> new SlabBlock(TIN));

	public static final DeferredBlock<DoorBlock> TIN_DOOR = registerBlock("tin_door",
		() -> new DoorBlock(BlockSetType.COPPER, BlockBehaviour.Properties.ofFullCopy(TIN_BLOCK.get())
			.noOcclusion()
			.pushReaction(PushReaction.DESTROY)));

	public static final DeferredBlock<TrapDoorBlock> TIN_TRAPDOOR = registerBlock("tin_trapdoor",
		() -> new TrapDoorBlock(BlockSetType.COPPER, BlockBehaviour.Properties.ofFullCopy(TIN_BLOCK.get())
			.noOcclusion()
			.isValidSpawn(Blocks::never)));

	public static final DeferredBlock<Block> TIN_ORE = registerBlock("tin_ore",
		() -> new Block(BlockBehaviour.Properties.of()
			.mapColor(MapColor.STONE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.strength(3.0f)
			.explosionResistance(3.0f)
		));

	public static final DeferredBlock<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore",
		() -> new Block(BlockBehaviour.Properties.of()
			.mapColor(MapColor.DEEPSLATE)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.strength(4.5f)
			.explosionResistance(3.0f)
			.sound(SoundType.DEEPSLATE)
		));

	public static final DeferredBlock<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block",
		() -> new Block(BlockBehaviour.Properties.of()
			.mapColor(MapColor.RAW_IRON)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.requiresCorrectToolForDrops()
			.strength(5.5f)
			.explosionResistance(6.0f)
		));

	private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
		DeferredBlock<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static  <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}