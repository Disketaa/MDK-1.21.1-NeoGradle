package com.disketaa.harmonium.configuration;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModConfigurationBuilder {
	private final Screen screen;
	private final List<AbstractWidget> widgets = new ArrayList<>();
	private int currentY;
	private final int leftX;
	private final int buttonWidth;
	private final int buttonHeight;
	private final int buttonSpacing;

	public ModConfigurationBuilder(Screen screen, int leftX, int buttonWidth, int buttonHeight, int buttonSpacing) {
		this.screen = screen;
		this.leftX = leftX;
		this.buttonWidth = buttonWidth;
		this.buttonHeight = buttonHeight;
		this.buttonSpacing = buttonSpacing;
		this.currentY = screen.height / 6;
	}

	public void addCategory(String translationKey) {
		widgets.add(new Label(screen, leftX, currentY - 10, buttonWidth * 2, buttonHeight,
			Component.translatable(translationKey), true));
		currentY += buttonSpacing;
	}

	public void addBooleanConfig(ModConfigSpec.BooleanValue configValue, String translationKey) {
		CycleButton<Boolean> button = CycleButton.booleanBuilder(Component.translatable("options.on"),
				Component.translatable("options.off"))
			.withInitialValue(configValue.get())
			.create(leftX, currentY, buttonWidth, buttonHeight,
				Component.translatable(translationKey),
				(btn, value) -> configValue.set(value));
		widgets.add(button);
		currentY += buttonSpacing;
	}

	public void addIntConfig(ModConfigSpec.IntValue configValue, String translationKey, int min, int max) {
		EditBox field = new EditBox(screen.getMinecraft().font, leftX, currentY, buttonWidth, buttonHeight,
			Component.translatable(translationKey));
		field.setValue(String.valueOf(configValue.get()));
		field.setResponder(text -> {
			try {
				int value = Integer.parseInt(text);
				if (value >= min && value <= max) {
					configValue.set(value);
				}
			} catch (NumberFormatException ignored) {}
		});
		widgets.add(field);
		currentY += buttonSpacing;
	}

	public void addToScreen(Consumer<AbstractWidget> addWidget) {
		widgets.forEach(addWidget);
	}

	private static class Label extends AbstractWidget {
		private final Screen screen;

		public Label(Screen screen, int x, int y, int width, int height, Component message, boolean centered) {
			super(x, y, width, height, message);
			this.screen = screen;
			if (centered) {
				this.setX(x - this.getWidth() / 2);
			}
			this.active = false;
		}

		@Override
		public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
			guiGraphics.drawString(
				screen.getMinecraft().font,
				this.getMessage(),
				this.getX(),
				this.getY(),
				0xFFFFFF,
				true
			);
		}

		@Override
		protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
		}
	}
}