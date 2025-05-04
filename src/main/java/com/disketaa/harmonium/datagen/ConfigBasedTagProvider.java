package com.disketaa.harmonium.datagen;

import com.disketaa.harmonium.Config;
import com.disketaa.harmonium.Harmonium;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public abstract class ConfigBasedTagProvider extends ItemTagsProvider {
	public ConfigBasedTagProvider(PackOutput output,
	                              CompletableFuture<HolderLookup.Provider> lookupProvider,
	                              CompletableFuture<TagLookup<Block>> blockTags,
	                              @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTags, Harmonium.MOD_ID, existingFileHelper);
	}

	protected void conditionalRemoveFromTag(String tagName, String configFlag, ResourceLocation itemToRemove) {
		TagBuilder builder = this.getOrCreateRawBuilder(ItemTags.create(ResourceLocation.parse(tagName)));
		if (Config.removeFlintKnife) {
			builder.remove(TagEntry.element(itemToRemove));
		}
	}
}