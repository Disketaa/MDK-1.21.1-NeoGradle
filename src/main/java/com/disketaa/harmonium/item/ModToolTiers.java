package com.disketaa.harmonium.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
	public static final Tier BRONZE = new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL,
		160, 4f, 1f, 12, () -> Ingredient.of(ModItems.BRONZE_INGOT));

}