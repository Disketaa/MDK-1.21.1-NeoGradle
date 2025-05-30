package com.disketaa.harmonium.item;

import com.disketaa.harmonium.Harmonium;
import com.disketaa.harmonium.item.custom.ModInstrumentItem;
import com.disketaa.harmonium.item.custom.ModShieldItem;
import com.disketaa.harmonium.item.custom.ModKnifeItem;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Harmonium.MOD_ID);
	private static final List<DeferredItem<? extends Item>> REGISTERED_ITEMS = new ArrayList<>();

	public static final DeferredItem<Item> RAW_TIN = registerSimpleItem("raw_tin");
	public static final DeferredItem<Item> BRONZE_BLEND = registerSimpleItem("bronze_blend");
	public static final DeferredItem<Item> COPPER_NUGGET = registerSimpleItem("copper_nugget");
	public static final DeferredItem<Item> TIN_NUGGET = registerSimpleItem("tin_nugget");
	public static final DeferredItem<Item> TIN_INGOT = registerSimpleItem("tin_ingot");
	public static final DeferredItem<Item> BRONZE_NUGGET = registerSimpleItem("bronze_nugget");
	public static final DeferredItem<Item> BRONZE_INGOT = registerSimpleItem("bronze_ingot");
	public static final DeferredItem<ModInstrumentItem> FLUTE = registerInstrumentItem("flute", () -> new ModInstrumentItem(new Item.Properties(), BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.NOTE_BLOCK_FLUTE.value()), 1.0f));
	public static final DeferredItem<ModKnifeItem> WOODEN_KNIFE = registerConditionalTool("wooden_knife", () -> registerKnifeItem("wooden_knife", Tiers.WOOD, 0.5f, -2.0f), "farmersdelight");
	public static final DeferredItem<Item> BRONZE_CHISEL = registerSimpleItem("bronze_chisel");
	public static final DeferredItem<ModKnifeItem> BRONZE_KNIFE = registerConditionalTool("bronze_knife", () -> registerKnifeItem("bronze_knife", ModToolTiers.BRONZE, 0.5f, -2.0f), "farmersdelight");
	public static final DeferredItem<ShovelItem> BRONZE_SHOVEL = registerShovelItem("bronze_shovel", ModToolTiers.BRONZE, 1.5f, -3.0f);
	public static final DeferredItem<PickaxeItem> BRONZE_PICKAXE = registerPickaxeItem("bronze_pickaxe", ModToolTiers.BRONZE, 1, -2.8f);
	public static final DeferredItem<AxeItem> BRONZE_AXE = registerAxeItem("bronze_axe", ModToolTiers.BRONZE, 7.0f, -3.2f);
	public static final DeferredItem<HoeItem> BRONZE_HOE = registerHoeItem("bronze_hoe", ModToolTiers.BRONZE, -1, -2.0f);
	public static final DeferredItem<SwordItem> BRONZE_SWORD = registerSwordItem("bronze_sword", ModToolTiers.BRONZE, 3, -2.4f);
	public static final DeferredItem<ModShieldItem> BUCKLER = registerShieldItem("buckler", ModToolTiers.BRONZE);
	public static final DeferredItem<ArmorItem> BRONZE_HELMET = registerArmorItem("bronze_helmet", ModArmorMaterials.BRONZE_ARMOR_MATERIAL, ArmorItem.Type.HELMET);
	public static final DeferredItem<ArmorItem> BRONZE_CHESTPLATE = registerArmorItem("bronze_chestplate", ModArmorMaterials.BRONZE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE);
	public static final DeferredItem<ArmorItem> BRONZE_LEGGINGS = registerArmorItem("bronze_leggings", ModArmorMaterials.BRONZE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS);
	public static final DeferredItem<ArmorItem> BRONZE_BOOTS = registerArmorItem("bronze_boots", ModArmorMaterials.BRONZE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS);
	public static final DeferredItem<Item> BRONZE_HORSE_ARMOR = registerHorseArmorItem("bronze_horse_armor", ModArmorMaterials.BRONZE_ARMOR_MATERIAL, AnimalArmorItem.BodyType.EQUESTRIAN, false);

	private static DeferredItem<Item> registerSimpleItem(String name) {
		DeferredItem<Item> item = ITEMS.register(name, () -> new Item(new Item.Properties()));
		REGISTERED_ITEMS.add(item);
		return item;
	}

	private static DeferredItem<ModInstrumentItem> registerInstrumentItem(String name, Supplier<ModInstrumentItem> supplier) {
		DeferredItem<ModInstrumentItem> item = ITEMS.register(name, supplier);
		REGISTERED_ITEMS.add(item);
		return item;
	}

	public static DeferredItem<ModKnifeItem> registerKnifeItem(String name, Tier tier, float attackDamage, float attackSpeed) {
		DeferredItem<ModKnifeItem> item = ITEMS.register(name, () -> new ModKnifeItem(
			tier,
			attackDamage,
			attackSpeed,
			new Item.Properties()
		));
		REGISTERED_ITEMS.add(item);
		return item;
	}

	public static DeferredItem<SwordItem> registerSwordItem(String name, Tier tier, int attackDamage, float attackSpeed) {
		DeferredItem<SwordItem> item = ITEMS.register(name, () -> new SwordItem(
			tier,
			new Item.Properties()
				.component(DataComponents.ATTRIBUTE_MODIFIERS,
					SwordItem.createAttributes(tier, attackDamage, attackSpeed))
				.component(DataComponents.TOOL, new Tool(
						List.of(
							Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F),
							Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)
						),
						1.0F,
						2
					)
				)));
		REGISTERED_ITEMS.add(item);
		return item;
	}

	public static DeferredItem<ShovelItem> registerShovelItem(String name, Tier tier, float attackDamage, float attackSpeed) {
		DeferredItem<ShovelItem> item = ITEMS.register(name, () -> new ShovelItem(
			tier,
			new Item.Properties()
				.component(DataComponents.ATTRIBUTE_MODIFIERS,
					ShovelItem.createAttributes(tier, attackDamage, attackSpeed))
				.component(DataComponents.TOOL, new Tool(
					List.of(Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_SHOVEL, tier.getSpeed())),
					1.0f,
					tier.getUses()
				))
		));
		REGISTERED_ITEMS.add(item);
		return item;
	}

	public static DeferredItem<PickaxeItem> registerPickaxeItem(String name, Tier tier, float attackDamage, float attackSpeed) {
		DeferredItem<PickaxeItem> item = ITEMS.register(name, () -> new PickaxeItem(
			tier,
			new Item.Properties()
				.component(DataComponents.ATTRIBUTE_MODIFIERS,
					PickaxeItem.createAttributes(tier, attackDamage, attackSpeed))
				.component(DataComponents.TOOL, new Tool(
					List.of(Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_PICKAXE, tier.getSpeed())),
					1.0f,
					tier.getUses()
				))
		));
		REGISTERED_ITEMS.add(item);
		return item;
	}

	public static DeferredItem<AxeItem> registerAxeItem(String name, Tier tier, float attackDamage, float attackSpeed) {
		DeferredItem<AxeItem> item = ITEMS.register(name, () -> new AxeItem(
			tier,
			new Item.Properties()
				.component(DataComponents.ATTRIBUTE_MODIFIERS,
					AxeItem.createAttributes(tier, attackDamage, attackSpeed))
				.component(DataComponents.TOOL, new Tool(
					List.of(Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_AXE, tier.getSpeed())),
					1.0f,
					tier.getUses()
				))
		));
		REGISTERED_ITEMS.add(item);
		return item;
	}

	public static DeferredItem<HoeItem> registerHoeItem(String name, Tier tier, float attackDamage, float attackSpeed) {
		DeferredItem<HoeItem> item = ITEMS.register(name, () -> new HoeItem(
			tier,
			new Item.Properties()
				.component(DataComponents.ATTRIBUTE_MODIFIERS,
					HoeItem.createAttributes(tier, attackDamage, attackSpeed))
				.component(DataComponents.TOOL, new Tool(
					List.of(Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_HOE, tier.getSpeed())),
					1.0f,
					tier.getUses()
				))
		));
		REGISTERED_ITEMS.add(item);
		return item;
	}

	private static DeferredItem<ArmorItem> registerArmorItem(String name, Holder<ArmorMaterial> material, ArmorItem.Type type) {
		DeferredItem<ArmorItem> item = ITEMS.register(name, () -> new ArmorItem(
			material,
			type,
			new Item.Properties().durability(type.getDurability(10))
		));
		REGISTERED_ITEMS.add(item);
		return item;
	}

	private static DeferredItem<Item> registerHorseArmorItem(String name, Holder<ArmorMaterial> material, AnimalArmorItem.BodyType bodyType, boolean isFireResistant) {
		DeferredItem<Item> item = ITEMS.register(name, () -> new AnimalArmorItem(
			material,
			bodyType,
			isFireResistant,
			new Item.Properties().stacksTo(1)
		));
		REGISTERED_ITEMS.add(item);
		return item;
	}

	public static DeferredItem<ModShieldItem> registerShieldItem(String name, Tier tier) {
		DeferredItem<ModShieldItem> item = ITEMS.register(name, () -> new ModShieldItem(
			tier,
			2.0f,
			new Item.Properties()
		));
		REGISTERED_ITEMS.add(item);
		return item;
	}

	private static <T extends Item> DeferredItem<T> registerConditionalTool(String name, Supplier<DeferredItem<T>> supplier, String requiredMod) {
		if (ModList.get().isLoaded(requiredMod)) {
			DeferredItem<T> item = supplier.get();
			REGISTERED_ITEMS.add(item);
			return item;
		}
		return null;
	}

	public static List<DeferredItem<? extends Item>> getRegisteredItems() {
		return new ArrayList<>(REGISTERED_ITEMS);
	}

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}