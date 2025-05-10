package com.disketaa.harmonium.configuration;

import com.disketaa.harmonium.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Field;

public class ModConfigurationScreens extends Screen {
	private final Screen parent;
	private ModConfigurationScrollableList list;
	private static final int FOOTER_PADDING = 6;
	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 20;
	private static final int BUTTON_SPACING = 8;
	private static final int HEADER_HEIGHT = 32;
	private static final int FOOTER_HEIGHT = BUTTON_HEIGHT + FOOTER_PADDING * 2;

	public ModConfigurationScreens(Screen parent) {
		super(Component.translatable("config.harmonium.title"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		int headerPadding = (HEADER_HEIGHT - this.font.lineHeight) / 2 + 1;
		HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
		layout.setHeaderHeight(HEADER_HEIGHT);

		StringWidget title = new StringWidget(this.width, this.font.lineHeight, this.title, this.font);
		title.alignCenter();
		title.setY(headerPadding);
		layout.addToHeader(title);
		layout.setFooterHeight(FOOTER_HEIGHT);

		this.list = new ModConfigurationScrollableList(minecraft, width, layout.getContentHeight(),
			layout.getHeaderHeight(), height - FOOTER_HEIGHT);
		layout.addToContents(list);

		LinearLayout buttons = LinearLayout.horizontal().spacing(BUTTON_SPACING);
		buttons.addChild(Button.builder(Component.translatable("controls.reset"), button -> resetConfigs())
			.width(BUTTON_WIDTH).build());
		buttons.addChild(Button.builder(CommonComponents.GUI_DONE, button -> {
			Config.SPEC.save();
			onClose();
		}).width(BUTTON_WIDTH).build());

		buttons.arrangeElements();
		buttons.setX((width - buttons.getWidth()) / 2);
		buttons.setY(height - FOOTER_HEIGHT + FOOTER_PADDING);
		layout.addToFooter(buttons);
		layout.visitWidgets(this::addRenderableWidget);
		buildConfigContent();
	}

	private void buildConfigContent() {
		ModConfigurationBuilder builder = new ModConfigurationBuilder(list, 25, width - 50);
		Config.buildConfigScreen(builder);
	}

	private void resetConfigs() {
		try {
			for (Field field : Config.class.getDeclaredFields()) {
				if (ModConfigSpec.ConfigValue.class.isAssignableFrom(field.getType())) {
					ModConfigSpec.ConfigValue<?> configValue = (ModConfigSpec.ConfigValue<?>) field.get(null);
					if (configValue == null) continue;
					if (configValue.getDefault() instanceof Boolean) {
						((ModConfigSpec.BooleanValue)configValue).set((Boolean)configValue.getDefault());
					} else if (configValue.getDefault() instanceof Integer) {
						((ModConfigSpec.IntValue)configValue).set((Integer)configValue.getDefault());
					}
				}
			}
			if (minecraft != null) minecraft.execute(this::init);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Failed to reset config values", e);
		}
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		renderBackground(guiGraphics, mouseX, mouseY, partialTick);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}

	@Override
	public void onClose() {
		Config.SPEC.save();
		assert minecraft != null;
		minecraft.setScreen(parent);
	}
}