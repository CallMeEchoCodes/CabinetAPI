package dev.callmeecho.cabinetapi.client.gui.widget;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CabinetButtonWidget extends ButtonWidget implements CabinetWidget {
    public CabinetButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress, NarrationSupplier narrationSupplier) {
        super(x, y, width, height, message, onPress, narrationSupplier);
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }
}
