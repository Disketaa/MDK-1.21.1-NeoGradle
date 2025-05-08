package com.disketaa.harmonium.configuration;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModConfigurationBuilder {
	private final Screen screen;
	private final List<AbstractWidget> widgets = new ArrayList<>();
	private int currentY;
	private final int leftX;
	private final int textWidth;
	private final int buttonWidth;
	private final int buttonHeight;
	private final int buttonSpacing;

	public ModConfigurationBuilder(Screen screen, int leftX, int textWidth, int buttonWidth, int buttonHeight, int buttonSpacing) {
		this.screen = screen;
		this.leftX = leftX;
		this.textWidth = textWidth;
		this.buttonWidth = buttonWidth;
		this.buttonHeight = buttonHeight;
		this.buttonSpacing = buttonSpacing;
		this.currentY = 40;
	}

	public void addSpacing(int pixels) {
		currentY += pixels;
	}

	public void addCategory(String translationKey) {
		Component categoryTitle = Component.translatable(translationKey)
			.withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);

		int fullWidth = textWidth + buttonWidth + buttonSpacing;

		widgets.add(new ModLabelWidgets(
			screen,
			leftX,
			currentY - 10,
			fullWidth,
			buttonHeight,
			categoryTitle,
			true
		));
		currentY += buttonSpacing * 2;
	}

	public void addBooleanConfig(ModConfigSpec.BooleanValue configValue, String translationKey) {
		Component baseTooltip = Component.translatable(translationKey + ".tooltip");
		Component defaultLine = Component.translatable("config.default")
			.append(": ")
			.append(Component.literal(String.valueOf(configValue.getDefault())))
			.withStyle(ChatFormatting.GRAY);

		Component fullTooltip = baseTooltip.copy().append("\n").append(defaultLine);

		Component labelText = Component.translatable(translationKey);

		ModLabelWidgets label = new ModLabelWidgets(screen, leftX, currentY, textWidth, buttonHeight, labelText, false);
		label.setTooltip(Tooltip.create(fullTooltip));
		widgets.add(label);

		CycleButton<Boolean> button = CycleButton.booleanBuilder(Component.translatable("options.on"),
				Component.translatable("options.off"))
			.withInitialValue(configValue.get())
			.displayOnlyValue()
			.create(leftX + textWidth + buttonSpacing, currentY, buttonWidth, buttonHeight,
				Component.empty(),
				(btn, value) -> configValue.set(value));
		button.setTooltip(Tooltip.create(fullTooltip));
		widgets.add(button);

		currentY += buttonHeight + buttonSpacing;
	}

	public void addIntConfig(ModConfigSpec.IntValue configValue, String translationKey, int min, int max) {
		Component baseTooltip = Component.translatable(translationKey + ".tooltip");

		Component defaultLine = Component.translatable("config.default")
			.append(": ")
			.append(Component.literal(String.valueOf(configValue.getDefault())))
			.withStyle(ChatFormatting.GRAY);

		Component fullTooltip = baseTooltip.copy().append("\n").append(defaultLine);

		Component labelText = Component.translatable(translationKey);

		ModLabelWidgets label = new ModLabelWidgets(screen, leftX, currentY, textWidth, buttonHeight, labelText, false);
		label.setTooltip(Tooltip.create(fullTooltip));
		widgets.add(label);

		EditBox field = new EditBox(screen.getMinecraft().font, leftX + textWidth + buttonSpacing, currentY, buttonWidth, buttonHeight,
			Component.empty());
		field.setValue(String.valueOf(configValue.get()));
		field.setResponder(text -> {
			try {
				int value = Integer.parseInt(text);
				if (value >= min && value <= max) {
					configValue.set(value);
				}
			} catch (NumberFormatException ignored) {}
		});
		field.setTooltip(Tooltip.create(fullTooltip));
		widgets.add(field);

		currentY += buttonHeight + buttonSpacing;
	}

	public void addToScreen(Consumer<AbstractWidget> addWidget) {
		widgets.forEach(addWidget);
	}
}