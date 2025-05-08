package com.disketaa.harmonium.configuration;

import com.disketaa.harmonium.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
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
		int buttonSpacing = 24;

		ModConfigurationBuilder builder = new ModConfigurationBuilder(this, leftX, buttonWidth, buttonHeight, buttonSpacing);

		builder.addCategory("harmonium.config.creative_tab");
		builder.addBooleanConfig(Config.SHOW_HARMONIUM_CREATIVE_TAB, "harmonium.config.show_harmonium_creative_tab");
		builder.addBooleanConfig(Config.ADD_HARMONIUM_ITEMS_TO_OTHER_TABS, "harmonium.config.add_harmonium_items_to_other_tabs");

		builder.addCategory("harmonium.config.generation");
		builder.addBooleanConfig(Config.TIN_GENERATION, "harmonium.config.tin_generation");

		builder.addCategory("harmonium.config.items");
		builder.addBooleanConfig(Config.REMOVE_STONE_TOOLS, "harmonium.config.remove_stone_tools");
		builder.addBooleanConfig(Config.REMOVE_FLINT_KNIFE, "harmonium.config.remove_flint_knife");

		builder.addCategory("harmonium.config.tin_button");
		builder.addIntConfig(Config.TIN_BUTTON_SHORT_PRESS_DURATION, "harmonium.config.tin_button.short_press_duration", 0, 20);
		builder.addIntConfig(Config.TIN_BUTTON_LONG_PRESS_DURATION, "harmonium.config.tin_button.long_press_duration", 0, 20);
		builder.addIntConfig(Config.TIN_BUTTON_FAILURE_CHANCE, "harmonium.config.tin_button.failure_chance", 0, 100);

		builder.addToScreen(this::addRenderableWidget);

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