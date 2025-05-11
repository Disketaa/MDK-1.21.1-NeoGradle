package com.disketaa.harmonium.configuration;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfigurationBuilder {
	private final ModConfigurationScrollableList list;

	public ModConfigurationBuilder(ModConfigurationScrollableList list) {
		this.list = list;
	}

	public void addCategory(String translationKey) {
		Component title = Component.translatable(translationKey).withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
		this.list.children().add(new ModConfigurationScrollableList.LabelEntry(title, true));
	}

	public void addBooleanConfig(ModConfigSpec.BooleanValue configValue, String translationKey) {
		String configId = translationKey.substring("config.harmonium.".length());
		Component tooltip = createTooltip(configId, translationKey, String.valueOf(configValue.getDefault()));
		Component label = Component.translatable(translationKey);
		this.list.children().add(new ModConfigurationScrollableList.BooleanEntry(label, tooltip, configValue.get(), configValue::set));
	}

	public void addIntConfig(ModConfigSpec.IntValue configValue, String translationKey, int min, int max) {
		String configId = translationKey.substring("config.harmonium.".length());
		Component tooltip = createTooltip(configId, translationKey, String.valueOf(configValue.getDefault()));
		Component label = Component.translatable(translationKey);
		this.list.children().add(new ModConfigurationScrollableList.IntEntry(label, tooltip, configValue.get(), text -> {
			try {
				int value = Integer.parseInt(text);
				if (value >= min && value <= max) configValue.set(value);
			} catch (NumberFormatException ignored) {}
		}));
	}

	private Component createTooltip(String configId, String translationKey, String defaultValue) {
		return Component.literal(configId).withStyle(ChatFormatting.YELLOW)
			.append("\n").append(Component.translatable(translationKey + ".tooltip").withStyle(ChatFormatting.WHITE))
			.append("\n").append(Component.translatable("config.default").append(": ").append(Component.literal(defaultValue)).withStyle(ChatFormatting.GRAY));
	}
}