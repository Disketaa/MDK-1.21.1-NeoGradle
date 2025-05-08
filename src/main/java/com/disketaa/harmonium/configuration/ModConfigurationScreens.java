package com.disketaa.harmonium.configuration;

import com.disketaa.harmonium.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class ModConfigurationScreens extends Screen {
	private final Screen parent;
	private static final int TEXT_WIDTH = 170;
	private static final int BUTTON_WIDTH = 44;
	private static final int BUTTON_HEIGHT = 20;
	private static final int BOTTOM_BUTTON_WIDTH = 150;
	private static final int PADDING = 4;

	public ModConfigurationScreens(Screen parent) {
		super(Component.translatable("config.harmonium.title"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		this.clearWidgets();
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

		Config.buildConfigScreen(builder);
		builder.addToScreen(this::addRenderableWidget);

		final int buttonY = this.height - 26;
		final int totalButtonsWidth = BOTTOM_BUTTON_WIDTH * 2 + PADDING;
		final int buttonsStartX = (this.width - totalButtonsWidth) / 2;

		addRenderableWidget(Button.builder(Component.translatable("controls.reset"),
				button -> resetAllConfigs())
			.bounds(buttonsStartX, buttonY, BOTTOM_BUTTON_WIDTH, 20)
			.build());

		addRenderableWidget(Button.builder(CommonComponents.GUI_DONE,
				button -> {
					Config.SPEC.save();
					onClose();
				})
			.bounds(buttonsStartX + BOTTOM_BUTTON_WIDTH + PADDING, buttonY, BOTTOM_BUTTON_WIDTH, 20)
			.build());
	}

	private void resetAllConfigs() {
		try {
			for (Field field : Config.class.getDeclaredFields()) {
				if (ModConfigSpec.ConfigValue.class.isAssignableFrom(field.getType())) {
					ModConfigSpec.ConfigValue<?> configValue = (ModConfigSpec.ConfigValue<?>) field.get(null);
					if (configValue == null) {
						continue;
					}
					if (configValue.getDefault() instanceof Boolean) {
						((ModConfigSpec.BooleanValue)configValue).set((Boolean)configValue.getDefault());
					} else if (configValue.getDefault() instanceof Integer) {
						((ModConfigSpec.IntValue)configValue).set((Integer)configValue.getDefault());
					}
				}
			}
			if (this.minecraft != null) {
				this.minecraft.execute(this::init);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Failed to reset config values", e);
		}
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