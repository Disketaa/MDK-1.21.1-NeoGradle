package com.disketaa.harmonium.configuration;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.function.Consumer;

public class ModConfigurationScrollableList extends ContainerObjectSelectionList<ModConfigurationScrollableList.Entry> {
	private static final int TEXT_PADDING = 5;
	private static final int ITEM_HEIGHT = 24;
	private static final int BUTTON_WIDTH = 44;
	private static final int BUTTON_HEIGHT = 20;
	private static final int COLOR_WHITE = 0xFFFFFF;

	public ModConfigurationScrollableList(Minecraft minecraft, int width, int height, int y0, int y1) {
		super(minecraft, width, height, y0, ITEM_HEIGHT);
	}

	@Override
	public void updateWidgetNarration(@NotNull NarrationElementOutput output) {
		List<? extends NarratableEntry> narratables = children().stream()
			.filter(NarratableEntry.class::isInstance)
			.map(NarratableEntry.class::cast)
			.toList();

		if (!narratables.isEmpty()) narratables.getFirst().updateNarration(output.nest());
		else output.add(NarratedElementType.USAGE, Component.translatable("narration.component_list.usage"));
	}

	public abstract static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
		public abstract int getHeight();
	}

	public static class LabelEntry extends Entry {
		private final Component label;
		private final boolean centered;

		public LabelEntry(Component label, boolean centered) {
			this.label = label;
			this.centered = centered;
		}

		@Override
		public void render(@NotNull GuiGraphics gui, int index, int top, int left, int width, int height,
		                   int mouseX, int mouseY, boolean hovering, float partialTick) {
			int textY = top + (height - Minecraft.getInstance().font.lineHeight) / 2;
			if (centered) {
				int textWidth = Minecraft.getInstance().font.width(label);
				gui.drawString(Minecraft.getInstance().font, label, left + (width - textWidth) / 2, textY, COLOR_WHITE);
			} else {
				gui.drawString(Minecraft.getInstance().font, label, left, textY, COLOR_WHITE);
			}
		}

		@Override
		public @NotNull List<? extends GuiEventListener> children() {
			return ImmutableList.of();
		}

		@Override
		public @NotNull List<? extends NarratableEntry> narratables() {
			return ImmutableList.of();
		}

		@Override
		public int getHeight() {
			return ITEM_HEIGHT;
		}
	}

	public static class BooleanEntry extends Entry {
		private final CycleButton<Boolean> button;
		private final Component label;
		private final Tooltip tooltip;

		public BooleanEntry(Component label, Component tooltip, boolean initialValue, Consumer<Boolean> onChanged) {
			this.label = label;
			this.button = CycleButton.booleanBuilder(
					Component.translatable("options.on"),
					Component.translatable("options.off"))
				.withInitialValue(initialValue)
				.displayOnlyValue()
				.create(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, Component.empty(), (btn, value) -> onChanged.accept(value));
			this.button.setTooltip(Tooltip.create(tooltip));
			this.tooltip = Tooltip.create(tooltip);
		}

		@Override
		public void render(GuiGraphics gui, int index, int top, int left, int width, int height,
		                   int mouseX, int mouseY, boolean hovering, float partialTick) {
			gui.drawString(Minecraft.getInstance().font, label, left, top + TEXT_PADDING, COLOR_WHITE);
			button.setX(left + width - BUTTON_WIDTH);
			button.setY(top);
			button.render(gui, mouseX, mouseY, partialTick);
			if (hovering && tooltip != null) {
				gui.renderTooltip(Minecraft.getInstance().font, tooltip.toCharSequence(Minecraft.getInstance()), mouseX, mouseY);
			}
		}

		@Override
		public @NotNull List<? extends GuiEventListener> children() {
			return ImmutableList.of(button);
		}

		@Override
		public @NotNull List<? extends NarratableEntry> narratables() {
			return ImmutableList.of(button);
		}

		@Override
		public int getHeight() {
			return ITEM_HEIGHT;
		}
	}

	public static class IntEntry extends Entry {
		private final EditBox editBox;
		private final Component label;
		private final Tooltip tooltip;

		public IntEntry(Component label, Component tooltip, int initialValue, Consumer<String> onChanged) {
			this.label = label;
			this.editBox = new EditBox(Minecraft.getInstance().font, 0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, Component.empty());
			this.editBox.setValue(String.valueOf(initialValue));
			this.editBox.setResponder(onChanged);
			this.editBox.setTooltip(Tooltip.create(tooltip));
			this.tooltip = Tooltip.create(tooltip);
		}

		@Override
		public void render(GuiGraphics gui, int index, int top, int left, int width, int height,
		                   int mouseX, int mouseY, boolean hovering, float partialTick) {
			gui.drawString(Minecraft.getInstance().font, label, left, top + TEXT_PADDING, COLOR_WHITE);
			editBox.setX(left + width - BUTTON_WIDTH);
			editBox.setY(top);
			editBox.render(gui, mouseX, mouseY, partialTick);
			if (hovering && tooltip != null) {
				gui.renderTooltip(Minecraft.getInstance().font, tooltip.toCharSequence(Minecraft.getInstance()), mouseX, mouseY);
			}
		}

		@Override
		public @NotNull List<? extends GuiEventListener> children() {
			return ImmutableList.of(editBox);
		}

		@Override
		public @NotNull List<? extends NarratableEntry> narratables() {
			return ImmutableList.of(editBox);
		}

		@Override
		public int getHeight() {
			return ITEM_HEIGHT;
		}
	}

	public static class EmptyEntry extends Entry {
		private final int height;

		public EmptyEntry(int height) {
			this.height = height;
		}

		@Override
		public void render(@NotNull GuiGraphics gui, int index, int top, int left, int width, int height,
		                   int mouseX, int mouseY, boolean hovering, float partialTick) {}

		@Override
		public int getHeight() {
			return height;
		}

		@Override
		public @NotNull List<? extends GuiEventListener> children() {
			return ImmutableList.of();
		}

		@Override
		public @NotNull List<? extends NarratableEntry> narratables() {
			return ImmutableList.of();
		}
	}
}