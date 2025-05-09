package com.disketaa.harmonium.configuration;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModConfigurationBuilder {
	private final ModConfigurationScrollableList modConfigurationScrollableList;

	public ModConfigurationBuilder(ModConfigurationScrollableList modConfigurationScrollableList, int leftX, int contentWidth) {
		this.modConfigurationScrollableList = modConfigurationScrollableList;
	}

	public void addCategory(String translationKey) {
		Component categoryTitle = Component.translatable(translationKey).withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
		this.modConfigurationScrollableList.children().add(new ModConfigurationScrollableList.LabelEntry(categoryTitle, true) {
			@Override
			public @NotNull List<? extends GuiEventListener> children() {
				return List.of();
			}

			@Override
			public @NotNull List<? extends NarratableEntry> narratables() {
				return List.of();
			}
		});
	}

	public void addBooleanConfig(ModConfigSpec.BooleanValue configValue, String translationKey) {
		String configId = translationKey.substring("config.harmonium.".length());
		Component tooltip = createTooltip(configId, translationKey, String.valueOf(configValue.getDefault()));
		Component labelText = Component.translatable(translationKey);

		this.modConfigurationScrollableList.children().add(new ModConfigurationScrollableList.BooleanEntry(
			labelText,
			tooltip,
			configValue.get(),
			configValue::set
		));
	}

	public void addIntConfig(ModConfigSpec.IntValue configValue, String translationKey, int min, int max) {
		String configId = translationKey.substring("config.harmonium.".length());
		Component tooltip = createTooltip(configId, translationKey, String.valueOf(configValue.getDefault()));
		Component labelText = Component.translatable(translationKey);

		this.modConfigurationScrollableList.children().add(new ModConfigurationScrollableList.IntEntry(
			labelText,
			tooltip,
			configValue.get(),
			(String text) -> {
				try {
					int value = Integer.parseInt(text);
					if (value >= min && value <= max) {
						configValue.set(value);
					}
				} catch (NumberFormatException ignored) {}
			}
		));
	}

	private Component createTooltip(String configId, String translationKey, String defaultValue) {
		return Component.literal(configId).withStyle(ChatFormatting.YELLOW)
			.append("\n").append(Component.translatable(translationKey + ".tooltip").withStyle(ChatFormatting.WHITE))
			.append("\n").append(Component.translatable("config.default").append(": ").append(Component.literal(defaultValue)).withStyle(ChatFormatting.GRAY));
	}
}