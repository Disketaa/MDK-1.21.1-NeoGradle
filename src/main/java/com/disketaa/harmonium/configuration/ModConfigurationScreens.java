package com.disketaa.harmonium.configuration;

import com.disketaa.harmonium.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

public class ModConfigurationScreens extends Screen {
	private final Screen parent;
	private static final int TEXT_WIDTH = 250;
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 20;
	private static final int PADDING = 5;

	public ModConfigurationScreens(Screen parent) {
		super(Component.translatable("config.harmonium.title"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		super.init();

		int totalRowWidth = TEXT_WIDTH + BUTTON_WIDTH + PADDING;
		int startX = (this.width - totalRowWidth) / 2;
		int currentY = 40;

		addConfigRow(startX, currentY,
			Component.translatable("config.harmonium.show_harmonium_creative_tab"),
			Config.SHOW_HARMONIUM_CREATIVE_TAB);
		currentY += BUTTON_HEIGHT + PADDING;

		addConfigRow(startX, currentY,
			Component.translatable("config.harmonium.add_harmonium_items_to_other_tabs"),
			Config.ADD_HARMONIUM_ITEMS_TO_OTHER_TABS);
		currentY += BUTTON_HEIGHT + PADDING;

		addConfigRow(startX, currentY,
			Component.translatable("config.harmonium.tin_generation"),
			Config.TIN_GENERATION);
		currentY += BUTTON_HEIGHT + PADDING;

		addConfigRow(startX, currentY,
			Component.translatable("config.harmonium.remove_stone_tools"),
			Config.REMOVE_STONE_TOOLS);
		currentY += BUTTON_HEIGHT + PADDING;

		addConfigRow(startX, currentY,
			Component.translatable("config.harmonium.remove_flint_knife"),
			Config.REMOVE_FLINT_KNIFE);
		currentY += BUTTON_HEIGHT + PADDING;


		addIntConfigRow(startX, currentY,
			Component.translatable("config.harmonium.tin_button.long_press_duration"),
			Config.TIN_BUTTON_LONG_PRESS_DURATION, 20);
		currentY += BUTTON_HEIGHT + PADDING;

		addIntConfigRow(startX, currentY,
			Component.translatable("config.harmonium.tin_button.short_press_duration"),
			Config.TIN_BUTTON_SHORT_PRESS_DURATION, 20);
		currentY += BUTTON_HEIGHT + PADDING;

		addIntConfigRow(startX, currentY,
			Component.translatable("config.harmonium.tin_button.failure_chance"),
			Config.TIN_BUTTON_FAILURE_CHANCE, 100);
		currentY += BUTTON_HEIGHT + PADDING;

		addRenderableWidget(Button.builder(CommonComponents.GUI_DONE,
				button -> {
					Config.SPEC.save();
					onClose();
				})
			.bounds((width - 200) / 2, height - 26, 200, 20)
			.build());
	}

	private void addConfigRow(int startX, int y, Component label, ModConfigSpec.BooleanValue configValue) {
		Component labelWithColon = Component.translatable(label.getString() + ":");
		addRenderableWidget(new Label(startX, y, TEXT_WIDTH, BUTTON_HEIGHT, labelWithColon));

		int buttonX = startX + TEXT_WIDTH + PADDING;

		addRenderableWidget(CycleButton.booleanBuilder(
				Component.translatable("options.on"),
				Component.translatable("options.off"))
			.withInitialValue(configValue.get())
			.create(buttonX, y, BUTTON_WIDTH, BUTTON_HEIGHT,
				Component.empty(),
				(btn, value) -> configValue.set(value)));
	}

	private void addIntConfigRow(int startX, int y, Component label, ModConfigSpec.IntValue configValue, int max) {
		addRenderableWidget(new Label(startX, y, TEXT_WIDTH, BUTTON_HEIGHT, label));

		int fieldX = startX + TEXT_WIDTH + PADDING;

		EditBox field = new EditBox(this.font, fieldX, y, BUTTON_WIDTH, BUTTON_HEIGHT, Component.empty());
		field.setValue(String.valueOf(configValue.get()));
		field.setResponder(text -> {
			try {
				int value = Integer.parseInt(text);
				if (value >= 0 && value <= max) {
					configValue.set(value);
				}
			} catch (NumberFormatException ignored) {}
		});
		addRenderableWidget(field);
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		renderBackground(guiGraphics, mouseX, mouseY, partialTick);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 12, 0xFFFFFF);
	}

	@Override
	public void onClose() {
		Config.SPEC.save();
		assert this.minecraft != null;
		this.minecraft.setScreen(this.parent);
	}

	private class Label extends AbstractWidget {
		public Label(int x, int y, int width, int height, Component message) {
			super(x, y, width, height, message);
			this.active = false;
		}

		@Override
		public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
			int textY = this.getY() + (this.height - ModConfigurationScreens.this.font.lineHeight) / 2 + 1;

			guiGraphics.drawString(
				ModConfigurationScreens.this.font,
				this.getMessage(),
				this.getX(),
				textY,
				0xFFFFFF,
				true
			);
		}

		@Override
		protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
		}
	}
}