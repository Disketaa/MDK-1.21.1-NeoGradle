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
	private final boolean centered;

	public ModLabelWidgets(Screen screen, int x, int y, int width, int height, Component message, boolean centered) {
		super(x, y, width, height, message);
		this.minecraft = screen.getMinecraft();
		this.centered = centered;
		this.active = false;
	}

	@Override
	public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		int textY = this.getY() + (this.height - minecraft.font.lineHeight) / 2 + 1;
		Component renderedText = this.getMessage();

		if (minecraft.font.width(renderedText) > this.width) {
			String ellipsized = minecraft.font.ellipsize(renderedText, this.width).getString();
			if (ellipsized.endsWith(" ...")) {
				ellipsized = ellipsized.substring(0, ellipsized.length() - 4) + "...";
			}
			renderedText = Component.literal(ellipsized);
		}

		if (centered) {
			int textWidth = minecraft.font.width(renderedText);
			int centeredX = this.getX() + (this.width - textWidth) / 2;
			guiGraphics.drawString(
				minecraft.font,
				renderedText,
				centeredX,
				textY,
				0xFFFFFF,
				true
			);
		} else {
			guiGraphics.drawString(
				minecraft.font,
				renderedText,
				this.getX(),
				textY,
				0xFFFFFF,
				true
			);
		}

		// Tooltip rendering unchanged
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