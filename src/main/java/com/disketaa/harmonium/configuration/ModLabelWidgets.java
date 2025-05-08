package com.disketaa.harmonium.configuration;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModLabelWidgets extends AbstractWidget {
	private final Minecraft minecraft;
	private Tooltip tooltip;

	public ModLabelWidgets(Screen screen, int x, int y, int width, int height, Component message, boolean centered) {
		super(x, y, width, height, message);
		this.minecraft = screen.getMinecraft();
		if (centered) {
			this.setX(x - this.getWidth() / 2);
		}
		this.active = false;
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		int textY = this.getY() + (this.height - minecraft.font.lineHeight) / 2 + 1;
		guiGraphics.drawString(
			minecraft.font,
			this.getMessage(),
			this.getX(),
			textY,
			0xFFFFFF,
			true
		);

		if (this.tooltip != null && this.isHovered()) {
			List<FormattedCharSequence> tooltipLines = this.tooltip.toCharSequence(minecraft);
			guiGraphics.renderTooltip(minecraft.font, tooltipLines, mouseX, mouseY);
		}
	}

	@Override
	protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
		narrationElementOutput.add(NarratedElementType.TITLE, this.getMessage());
		if (this.tooltip != null) {
			this.tooltip.updateNarration(narrationElementOutput);
		}
	}

	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}
}