package com.disketaa.harmonium.configuration;

import com.disketaa.harmonium.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ModConfigurationScreens extends Screen {
	private final Screen parent;
	private static final int TEXT_WIDTH = 170;
	private static final int BUTTON_WIDTH = 44;
	private static final int BUTTON_HEIGHT = 20;
	private static final int PADDING = 4;

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

		Config.buildConfigScreen(builder);
		builder.addToScreen(this::addRenderableWidget);

		addRenderableWidget(Button.builder(CommonComponents.GUI_DONE,
				button -> {
					Config.SPEC.save();
					onClose();
				})
			.bounds((width - 200) / 2, height - 26, 150, 20)
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