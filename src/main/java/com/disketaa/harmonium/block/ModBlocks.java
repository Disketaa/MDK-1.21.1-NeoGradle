package com.disketaa.harmonium.block;

import com.disketaa.harmonium.Harmonium;
import com.disketaa.harmonium.block.custom.*;
import com.disketaa.harmonium.item.ModItems;
import com.disketaa.harmonium.sound.ModBlockSetType;
import com.disketaa.harmonium.sound.ModSoundType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Harmonium.MOD_ID);

	private static final class Properties {
		private static BlockBehaviour.Properties createMetalProperties(MapColor color, SoundType sound, float strength) {
			return BlockBehaviour.Properties.of()
				.mapColor(color)
				.sound(sound)
				.requiresCorrectToolForDrops()
				.strength(strength, 6.0f);
		}

		static final BlockBehaviour.Properties GENERIC_TIN = createMetalProperties(MapColor.TERRACOTTA_WHITE, ModSoundType.TIN, 2.0f);
		static final BlockBehaviour.Properties GENERIC_BRONZE = createMetalProperties(MapColor.TERRACOTTA_YELLOW, ModSoundType.TIN, 4.0f);

		static final BlockBehaviour.Properties TIN_BULB = BlockBehaviour.Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.sound(ModSoundType.TIN_BULB)
			.isRedstoneConductor((state, level, pos) -> false)
			.lightLevel(state -> state.getValue(TinBulbBlock.LIGHT_LEVEL))  // Use TinBulbBlock's property
			.requiresCorrectToolForDrops()
			.strength(2f, 6.0f);

		static final BlockBehaviour.Properties BRONZE_BULB = BlockBehaviour.Properties.of()
			.mapColor(MapColor.TERRACOTTA_ORANGE)
			.sound(ModSoundType.TIN_BULB)
			.isRedstoneConductor((state, level, pos) -> false)
			.lightLevel(state -> state.getValue(BronzeBulbBlock.LIT) ? state.getValue(BronzeBulbBlock.LIGHT_LEVEL) : 0)
			.requiresCorrectToolForDrops()
			.strength(4f, 6.0f);

		static final BlockBehaviour.Properties TIN_SOLDIER = BlockBehaviour.Properties.of()
			.sound(ModSoundType.TIN_SOLDIER)
			.noOcclusion()
			.strength(0.1f, 0.1f)
			.pushReaction(PushReaction.DESTROY);

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

		static final BlockBehaviour.Properties TIN_RAW = BlockBehaviour.Properties.of()
			.mapColor(MapColor.RAW_IRON)
			.requiresCorrectToolForDrops()
			.strength(4.0f, 6.0f);

		static final BlockBehaviour.Properties BRONZE_RAW = BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_ORANGE)
			.requiresCorrectToolForDrops()
			.strength(5.0f, 6.0f);
	}

	public static final DeferredBlock<Block> TIN_BLOCK = registerMetalBlock("tin_block", Properties.GENERIC_TIN);
	public static final DeferredBlock<Block> CHISELED_TIN = registerMetalBlock("chiseled_tin", Properties.GENERIC_TIN);
	public static final DeferredBlock<WaterloggedTransparentBlock> TIN_GRATE = registerBlock("tin_grate", () -> createGrate(TIN_BLOCK.get(), ModSoundType.TIN_GRATE));
	public static final DeferredBlock<Block> CUT_TIN = registerMetalBlock("cut_tin", Properties.GENERIC_TIN);
	public static final DeferredBlock<StairBlock> CUT_TIN_STAIRS = registerBlock("cut_tin_stairs", () -> new StairBlock(CUT_TIN.get().defaultBlockState(), Properties.GENERIC_TIN));
	public static final DeferredBlock<SlabBlock> CUT_TIN_SLAB = registerBlock("cut_tin_slab", () -> new SlabBlock(Properties.GENERIC_TIN));
	public static final DeferredBlock<DoorBlock> TIN_DOOR = registerBlock("tin_door", () -> new DoorBlock(ModBlockSetType.TIN, createDoorProperties(TIN_BLOCK.get())));
	public static final DeferredBlock<TrapDoorBlock> TIN_TRAPDOOR = registerBlock("tin_trapdoor", () -> new TrapDoorBlock(ModBlockSetType.TIN, createTrapdoorProperties(TIN_BLOCK.get())));
	public static final DeferredBlock<TinBulbBlock> TIN_BULB = registerBlock("tin_bulb", () -> new TinBulbBlock(Properties.TIN_BULB));
	public static final DeferredBlock<Block> BRONZE_BLOCK = registerMetalBlock("bronze_block", Properties.GENERIC_BRONZE);
	public static final DeferredBlock<Block> CHISELED_BRONZE = registerMetalBlock("chiseled_bronze", Properties.GENERIC_BRONZE);
	public static final DeferredBlock<WaterloggedTransparentBlock> BRONZE_GRATE = registerBlock("bronze_grate", () -> createGrate(BRONZE_BLOCK.get(), ModSoundType.TIN_GRATE));
	public static final DeferredBlock<Block> CUT_BRONZE = registerMetalBlock("cut_bronze", Properties.GENERIC_BRONZE);
	public static final DeferredBlock<StairBlock> CUT_BRONZE_STAIRS = registerBlock("cut_bronze_stairs", () -> new StairBlock(CUT_BRONZE.get().defaultBlockState(), Properties.GENERIC_BRONZE));
	public static final DeferredBlock<SlabBlock> CUT_BRONZE_SLAB = registerBlock("cut_bronze_slab", () -> new SlabBlock(Properties.GENERIC_BRONZE));
	public static final DeferredBlock<DoorBlock> BRONZE_DOOR = registerBlock("bronze_door", () -> new DoorBlock(ModBlockSetType.TIN, createDoorProperties(BRONZE_BLOCK.get())));
	public static final DeferredBlock<TrapDoorBlock> BRONZE_TRAPDOOR = registerBlock("bronze_trapdoor", () -> new TrapDoorBlock(ModBlockSetType.TIN, createTrapdoorProperties(BRONZE_BLOCK.get())));
	public static final DeferredBlock<BronzeBulbBlock> BRONZE_BULB = registerBlock("bronze_bulb", () -> new BronzeBulbBlock(Properties.BRONZE_BULB));
	public static final DeferredBlock<Block> TIN_ORE = registerBlock("tin_ore", () -> new Block(Properties.ORE_STONE));
	public static final DeferredBlock<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore", () -> new Block(Properties.ORE_DEEPSLATE));
	public static final DeferredBlock<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block", () -> new Block(Properties.TIN_RAW));
	public static final DeferredBlock<Block> BRONZE_BLEND_BLOCK = registerBlock("bronze_blend_block", () -> new Block(Properties.BRONZE_RAW));
	public static final DeferredBlock<TinSoldierBlock> TIN_SOLDIER = registerBlock("tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.TERRACOTTA_WHITE)));
	public static final DeferredBlock<TinSoldierBlock> WHITE_TIN_SOLDIER = registerBlock("white_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.SNOW)));
	public static final DeferredBlock<TinSoldierBlock> LIGHT_GRAY_TIN_SOLDIER = registerBlock("light_gray_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_LIGHT_GRAY)));
	public static final DeferredBlock<TinSoldierBlock> GRAY_TIN_SOLDIER = registerBlock("gray_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_GRAY)));
	public static final DeferredBlock<TinSoldierBlock> BLACK_TIN_SOLDIER = registerBlock("black_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_BLACK)));
	public static final DeferredBlock<TinSoldierBlock> BROWN_TIN_SOLDIER = registerBlock("brown_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_BROWN)));
	public static final DeferredBlock<TinSoldierBlock> RED_TIN_SOLDIER = registerBlock("red_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_RED)));
	public static final DeferredBlock<TinSoldierBlock> ORANGE_TIN_SOLDIER = registerBlock("orange_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_ORANGE)));
	public static final DeferredBlock<TinSoldierBlock> YELLOW_TIN_SOLDIER = registerBlock("yellow_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_YELLOW)));
	public static final DeferredBlock<TinSoldierBlock> LIME_TIN_SOLDIER = registerBlock("lime_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_LIGHT_GREEN)));
	public static final DeferredBlock<TinSoldierBlock> GREEN_TIN_SOLDIER = registerBlock("green_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_GREEN)));
	public static final DeferredBlock<TinSoldierBlock> CYAN_TIN_SOLDIER = registerBlock("cyan_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_CYAN)));
	public static final DeferredBlock<TinSoldierBlock> LIGHT_BLUE_TIN_SOLDIER = registerBlock("light_blue_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_LIGHT_BLUE)));
	public static final DeferredBlock<TinSoldierBlock> BLUE_TIN_SOLDIER = registerBlock("blue_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_BLUE)));
	public static final DeferredBlock<TinSoldierBlock> PURPLE_TIN_SOLDIER = registerBlock("purple_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_PURPLE)));
	public static final DeferredBlock<TinSoldierBlock> MAGENTA_TIN_SOLDIER = registerBlock("magenta_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_MAGENTA)));
	public static final DeferredBlock<TinSoldierBlock> PINK_TIN_SOLDIER = registerBlock("pink_tin_soldier", () -> new TinSoldierBlock(Properties.TIN_SOLDIER.mapColor(MapColor.COLOR_PINK)));
	public static DeferredBlock<ButtonBlock> TIN_BUTTON = null;
	public static DeferredBlock<ButtonBlock> BRONZE_BUTTON = null;

	private static DeferredBlock<Block> registerMetalBlock(String name, BlockBehaviour.Properties properties) {
		return registerBlock(name, () -> new Block(properties));
	}

	private static WaterloggedTransparentBlock createGrate(Block baseBlock, SoundType sound) {
		return new WaterloggedTransparentBlock(
			BlockBehaviour.Properties.ofFullCopy(baseBlock)
				.sound(sound)
				.noOcclusion()
				.isRedstoneConductor((state, level, pos) -> false)
				.isSuffocating((state, level, pos) -> false)
				.isViewBlocking((state, level, pos) -> false)
				.isValidSpawn((state, level, pos, type) -> false)
		);
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

		if (ModList.get().isLoaded("friendsandfoes")) {
			TIN_BUTTON = registerBlock("tin_button",
				() -> new UnreliableButtonBlock(
					ModBlockSetType.TIN,
					BlockBehaviour.Properties.of()
						.mapColor(MapColor.TERRACOTTA_WHITE)
						.sound(ModSoundType.TIN)
						.strength(0.5f, 0.5f)
						.noCollission()
				));

			BRONZE_BUTTON = registerBlock("bronze_button",
				() -> new ButtonBlock(
					ModBlockSetType.TIN,
					40,
					BlockBehaviour.Properties.of()
						.mapColor(MapColor.TERRACOTTA_YELLOW)
						.sound(ModSoundType.TIN)
						.strength(0.5f, 0.5f)
						.noCollission()
				));
		}
	}
}