package com.disketaa.harmonium.configuration;

import com.disketaa.harmonium.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

public class ModConfigurationScreens extends Screen {
	private final Screen parent;

	public ModConfigurationScreens(Screen parent) {
		super(Component.translatable("harmonium.config.title"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		super.init();

		int buttonWidth = Math.min(200, width / 2 - 20);
		int buttonHeight = 20;
		int leftX = width / 4;
		int rightX = width * 3 / 4 - buttonWidth;
		int currentY = height / 6;
		int buttonSpacing = 24;

		addRenderableWidget(new Label(leftX, currentY - 10, buttonWidth * 2, buttonHeight,
			Component.translatable("harmonium.config.creative_tab"), true) {
			@Override
			protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
			}
		});

		addRenderableWidget(CycleButton.booleanBuilder(Component.translatable("options.on"),
				Component.translatable("options.off"))
			.withInitialValue(Config.SHOW_HARMONIUM_CREATIVE_TAB.get())
			.create(leftX, currentY += buttonSpacing, buttonWidth, buttonHeight,
				Component.translatable("harmonium.config.show_harmonium_creative_tab"),
				(button, value) -> Config.SHOW_HARMONIUM_CREATIVE_TAB.set(value)));

		addRenderableWidget(CycleButton.booleanBuilder(Component.translatable("options.on"),
				Component.translatable("options.off"))
			.withInitialValue(Config.ADD_HARMONIUM_ITEMS_TO_OTHER_TABS.get())
			.create(leftX, currentY += buttonSpacing, buttonWidth, buttonHeight,
				Component.translatable("harmonium.config.add_harmonium_items_to_other_tabs"),
				(button, value) -> Config.ADD_HARMONIUM_ITEMS_TO_OTHER_TABS.set(value)));

		addRenderableWidget(new Label(leftX, currentY += (int) (buttonSpacing * 1.5), buttonWidth * 2, buttonHeight,
			Component.translatable("harmonium.config.generation"), true) {
			@Override
			protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
			}
		});

		addRenderableWidget(CycleButton.booleanBuilder(Component.translatable("options.on"),
				Component.translatable("options.off"))
			.withInitialValue(Config.TIN_GENERATION.get())
			.create(leftX, currentY += buttonSpacing, buttonWidth, buttonHeight,
				Component.translatable("harmonium.config.tin_generation"),
				(button, value) -> Config.TIN_GENERATION.set(value)));

		addRenderableWidget(new Label(leftX, currentY += (int) (buttonSpacing * 1.5), buttonWidth * 2, buttonHeight,
			Component.translatable("harmonium.config.items"), true) {
			@Override
			protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
			}
		});

		addRenderableWidget(CycleButton.booleanBuilder(Component.translatable("options.on"),
				Component.translatable("options.off"))
			.withInitialValue(Config.REMOVE_STONE_TOOLS.get())
			.create(leftX, currentY += buttonSpacing, buttonWidth, buttonHeight,
				Component.translatable("harmonium.config.remove_stone_tools"),
				(button, value) -> Config.REMOVE_STONE_TOOLS.set(value)));

		addRenderableWidget(CycleButton.booleanBuilder(Component.translatable("options.on"),
				Component.translatable("options.off"))
			.withInitialValue(Config.REMOVE_FLINT_KNIFE.get())
			.create(leftX, currentY += buttonSpacing, buttonWidth, buttonHeight,
				Component.translatable("harmonium.config.remove_flint_knife"),
				(button, value) -> Config.REMOVE_FLINT_KNIFE.set(value)));

		addRenderableWidget(new Label(leftX, currentY += (int) (buttonSpacing * 1.5), buttonWidth * 2, buttonHeight,
			Component.translatable("harmonium.config.tin_button"), true) {
			@Override
			protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
			}
		});

		EditBox shortPressField = new EditBox(this.font, leftX, currentY += buttonSpacing, buttonWidth, buttonHeight,
			Component.translatable("harmonium.config.tin_button.short_press_duration"));
		shortPressField.setValue(String.valueOf(Config.TIN_BUTTON_SHORT_PRESS_DURATION.get()));
		shortPressField.setResponder(text -> {
			try {
				int value = Integer.parseInt(text);
				if (value >= 0 && value <= 20) {
					Config.TIN_BUTTON_SHORT_PRESS_DURATION.set(value);
				}
			} catch (NumberFormatException ignored) {}
		});
		addRenderableWidget(shortPressField);

		EditBox longPressField = new EditBox(this.font, leftX, currentY += buttonSpacing, buttonWidth, buttonHeight,
			Component.translatable("harmonium.config.tin_button.long_press_duration"));
		longPressField.setValue(String.valueOf(Config.TIN_BUTTON_LONG_PRESS_DURATION.get()));
		longPressField.setResponder(text -> {
			try {
				int value = Integer.parseInt(text);
				if (value >= 0 && value <= 20) {
					Config.TIN_BUTTON_LONG_PRESS_DURATION.set(value);
				}
			} catch (NumberFormatException ignored) {}
		});
		addRenderableWidget(longPressField);

		EditBox failureChanceField = new EditBox(this.font, leftX, currentY += buttonSpacing, buttonWidth, buttonHeight,
			Component.translatable("harmonium.config.tin_button.failure_chance"));
		failureChanceField.setValue(String.valueOf(Config.TIN_BUTTON_FAILURE_CHANCE.get()));
		failureChanceField.setResponder(text -> {
			try {
				int value = Integer.parseInt(text);
				if (value >= 0 && value <= 100) {
					Config.TIN_BUTTON_FAILURE_CHANCE.set(value);
				}
			} catch (NumberFormatException ignored) {}
		});
		addRenderableWidget(failureChanceField);

		addRenderableWidget(Button.builder(CommonComponents.GUI_DONE,
				button -> {
					Config.SPEC.save();
					onClose();
				})
			.bounds(width / 2 - 100, height - 28, 200, 20)
			.build());
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		renderBackground(guiGraphics, mouseX, mouseY, partialTick);
		guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}

	@Override
	public void onClose() {
		Config.SPEC.save();
		assert this.minecraft != null;
		this.minecraft.setScreen(this.parent);
	}

	private abstract class Label extends AbstractWidget {
		public Label(int x, int y, int width, int height, Component message, boolean centered) {
			super(x, y, width, height, message);
			if (centered) {
				this.setX(x - this.getWidth() / 2);
			}
			this.active = false;
		}

		@Override
		public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
			guiGraphics.drawString(ModConfigurationScreens.this.font, this.getMessage(), this.getX(), this.getY(), 0xFFFFFF, true);
		}
	}
}