package dev.callmeecho.cabinetapi.client.gui.widget;

import dev.callmeecho.cabinetapi.client.gui.widget.CabinetWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class IntSliderWidget extends SliderWidget implements CabinetWidget {
    private final Getter<Integer> getter;
    private final Setter<Integer> setter;

    private final int min;
    private final int max;

    public IntSliderWidget(String translationKey, int min, int max, Getter<Integer> getter, Setter<Integer> setter) {
        super(0, 0, 0, 20, Text.translatable(translationKey), 0);
        this.getter = getter;
        this.setter = setter;

        Text tooltip = Text.translatableWithFallback(translationKey + ".tooltip", "");
        if (!tooltip.getString().isEmpty()) this.setTooltip(Tooltip.of(tooltip));

        this.min = min;
        this.max = max;

        this.value = (double) (getter.get() - min) / (max - min);
        applyValue();
    }

    @Override
    protected void updateMessage() { }

    @Override
    public Text getMessage() {
        return Text.of(super.getMessage().getString()
                + ": "
                + getter.get()
        );
    }

    @Override
    protected void applyValue() {
        this.value = MathHelper.clamp(value, 0, 1.0);

        setter.set((int) Math.round(this.value * (max - min) + min));
    }

    /*? if 1.20.1 {*/
    @Override
    public void setHeight(int height) { this.height = height; }
    /*?} */
}
