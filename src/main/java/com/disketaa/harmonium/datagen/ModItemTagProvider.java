package com.disketaa.harmonium.datagen;

import com.disketaa.harmonium.Config;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {

	public ModItemTagProvider(PackOutput output,
	                          CompletableFuture<HolderLookup.Provider> lookupProvider,
	                          CompletableFuture<TagLookup<Block>> blockTags,
	                          @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTags, "harmonium", existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.@NotNull Provider provider) {
		if (Config.removeFlintKnife) {
			TagKey<Item> knivesTag = ItemTags.create(ResourceLocation.parse("farmersdelight:tools/knives"));
			tag(knivesTag).remove(ResourceLocation.parse("farmersdelight:flint_knife"));
		}
	}
}