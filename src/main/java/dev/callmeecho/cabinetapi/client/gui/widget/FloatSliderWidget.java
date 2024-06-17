package dev.callmeecho.cabinetapi.client.gui.widget;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class FloatSliderWidget extends SliderWidget implements CabinetWidget {
    private final Getter<Float> getter;
    private final Setter<Float> setter;

    private final float min;
    private final float max;

    public FloatSliderWidget(String translationKey, float min, float max, Getter<Float> getter, Setter<Float> setter) {
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
                + String.format("%.1f", getter.get())
        );
    }

    @Override
    protected void applyValue() {
        value = MathHelper.clamp(value, 0, 1);
        setter.set((float) (value * (max - min) + min));
    }

    @Override
    public void setHeight(int height) { this.height = height; }
}
