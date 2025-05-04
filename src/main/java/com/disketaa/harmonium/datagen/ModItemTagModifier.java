package com.disketaa.harmonium.datagen;

import com.disketaa.harmonium.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;

public class ModItemTagModifier {
	public static void conditionalRemove(TagBuilder builder, String configFlag, ResourceLocation itemToRemove) {
		if (shouldRemove(configFlag)) {
			builder.remove(TagEntry.element(itemToRemove));
		}
	}

	public static void conditionalRemove(TagBuilder builder, String configFlag, String tagToRemove) {
		if (shouldRemove(configFlag)) {
			builder.remove(TagEntry.tag(ResourceLocation.parse(tagToRemove)));
		}
	}

	private static boolean shouldRemove(String configFlag) {
		return switch (configFlag) {
			case "remove_flint_knife" -> Config.removeFlintKnife;
			default -> false;
		};
	}
}