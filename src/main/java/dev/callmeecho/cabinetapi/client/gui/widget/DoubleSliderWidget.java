package dev.callmeecho.cabinetapi.client.gui.widget;

import dev.callmeecho.cabinetapi.client.gui.widget.CabinetWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class DoubleSliderWidget extends SliderWidget implements CabinetWidget {
    private final Getter<Double> getter;
    private final Setter<Double> setter;

    private final double min;
    private final double max;

    public DoubleSliderWidget(String translationKey, double min, double max, Getter<Double> getter, Setter<Double> setter) {
        super(0, 0, 0, 20, Text.translatable(translationKey), 0);
        this.getter = getter;
        this.setter = setter;

        Text tooltip = Text.translatableWithFallback(translationKey + ".tooltip", "");
        if (!tooltip.getString().isEmpty()) this.setTooltip(Tooltip.of(tooltip));

        this.min = min;
        this.max = max;

        this.value = (getter.get() - min) / (max - min);
        applyValue();
    }

    @Override
    protected void updateMessage() { }

    @Override
    public Text getMessage() {
        return Text.of(super.getMessage().getString()
                + ": "
                + String.format("%.2f", getter.get())
        );
    }

    @Override
    protected void applyValue() {
        value = MathHelper.clamp(value, 0.0, 1.0);
        setter.set(value * (max - min) + min);
    }

    /*? if 1.20.1 {*//*
    @Override
    public void setHeight(int height) { this.height = height; }
    *//*?} */
}
