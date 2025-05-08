package com.disketaa.harmonium.configuration;

import com.disketaa.harmonium.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ModConfigurationScreens extends Screen {
	private final Screen parent;
	private static final int TEXT_WIDTH = 250;
	private static final int BUTTON_WIDTH = 50;
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

		ModConfigurationBuilder builder = new ModConfigurationBuilder(
			this,
			startX,
			TEXT_WIDTH,
			BUTTON_WIDTH,
			BUTTON_HEIGHT,
			PADDING
		);

		builder.addBooleanConfig(Config.SHOW_HARMONIUM_CREATIVE_TAB, "config.harmonium.show_harmonium_creative_tab");
		builder.addBooleanConfig(Config.ADD_HARMONIUM_ITEMS_TO_OTHER_TABS, "config.harmonium.add_harmonium_items_to_other_tabs");
		builder.addBooleanConfig(Config.TIN_GENERATION, "config.harmonium.tin_generation");
		builder.addBooleanConfig(Config.REMOVE_STONE_TOOLS, "config.harmonium.remove_stone_tools");
		builder.addBooleanConfig(Config.REMOVE_FLINT_KNIFE, "config.harmonium.remove_flint_knife");
		builder.addIntConfig(Config.TIN_BUTTON_LONG_PRESS_DURATION, "config.harmonium.tin_button.long_press_duration", 0, Integer.MAX_VALUE);
		builder.addIntConfig(Config.TIN_BUTTON_SHORT_PRESS_DURATION, "config.harmonium.tin_button.short_press_duration", 0, Integer.MAX_VALUE);
		builder.addIntConfig(Config.TIN_BUTTON_FAILURE_CHANCE, "config.harmonium.tin_button.failure_chance", 0, 100);

		builder.addToScreen(this::addRenderableWidget);

		addRenderableWidget(Button.builder(CommonComponents.GUI_DONE,
				button -> {
					Config.SPEC.save();
					onClose();
				})
			.bounds((width - 200) / 2, height - 26, 200, 20)
			.build());
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
}