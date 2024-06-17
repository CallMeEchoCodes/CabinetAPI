package dev.callmeecho.cabinetapi.client.gui;

import dev.callmeecho.cabinetapi.client.gui.widget.CabinetWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ElementListWidget;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class OptionsScrollableWidget extends ElementListWidget<OptionsScrollableWidget.OptionEntry> {
    public OptionsScrollableWidget(MinecraftClient minecraftClient, int width, int height, int k, int l) {
        super(minecraftClient, width, height, k, l);
        this.centerListVertically = false;
    }

    @Override
    protected int getScrollbarX() {
        return super.getScrollbarX() + 32;
    }

    @Override
    public int getRowWidth() {
        return 400;
    }

    public void addOptions(CabinetWidget[] options) {
        for(int i = 0; i < options.length; i += 2) {
            CabinetWidget widget = options[i];
            CabinetWidget widget2 = i + 1 < options.length ? options[i + 1] : null;
            this.addEntry(new OptionEntry(widget, widget2, this.width));
        }
    }

    protected static class OptionEntry extends Entry<OptionEntry> {
        private final List<CabinetWidget> widgets;

        public OptionEntry(CabinetWidget widget, @Nullable CabinetWidget widget2, int width) {
            this.widgets = new ArrayList<>();

            widget.setWidth(310);
            if (widget2 != null) {
                widget2.setWidth(150);
                widget.setWidth(150);
            }


            widget.setX(width / 2 - 155);
            if (widget2 != null) {
                widget2.setX(width / 2 + 5);
            }

            this.widgets.add(widget);
            if (widget2 != null)
                this.widgets.add(widget2);
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            for (CabinetWidget widget : widgets) {
                widget.setY(y);
                widget.render(context, mouseX, mouseY, tickDelta);
            }
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return widgets;
        }

        @Override
        public List<? extends Element> children() {
            return widgets;
        }
    }
}
