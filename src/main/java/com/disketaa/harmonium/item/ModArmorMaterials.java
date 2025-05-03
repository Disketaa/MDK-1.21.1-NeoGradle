package com.disketaa.harmonium.item;

import com.disketaa.harmonium.Harmonium;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {
	public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
		DeferredRegister.create(Registries.ARMOR_MATERIAL, Harmonium.MOD_ID);

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BRONZE_ARMOR_MATERIAL =
		register("bronze", makeBronzeArmorMaterial());

	private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, ArmorMaterial material) {
		return ARMOR_MATERIALS.register(name, () -> material);
	}

	private static ArmorMaterial makeBronzeArmorMaterial() {
		EnumMap<ArmorItem.Type, Integer> protection = new EnumMap<>(ArmorItem.Type.class);
		protection.put(ArmorItem.Type.BOOTS, 2);
		protection.put(ArmorItem.Type.LEGGINGS, 3);
		protection.put(ArmorItem.Type.CHESTPLATE, 4);
		protection.put(ArmorItem.Type.HELMET, 2);
		protection.put(ArmorItem.Type.BODY, 4);

		return new ArmorMaterial(
			protection,
			15,
			SoundEvents.ARMOR_EQUIP_IRON,
			() -> Ingredient.of(ModItems.BRONZE_INGOT.get()),
			List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Harmonium.MOD_ID, "bronze"))),
			0.0f,
			0.0f
		);
	}

	public static void register(IEventBus eventBus) {
		ARMOR_MATERIALS.register(eventBus);
	}
}