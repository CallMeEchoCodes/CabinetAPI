package dev.callmeecho.cabinetapi.client.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class BooleanButtonWidget extends CabinetButtonWidget {
    private final Getter<Boolean> getter;

    public BooleanButtonWidget(String translationKey, Getter<Boolean> getter, Setter<Boolean> setter) {
        super(
                0,
                0,
                0,
                20,
                Text.translatable(translationKey),
                button -> setter.set(!getter.get()),
                button -> null
        );

        Text tooltip = Text.translatableWithFallback(translationKey + ".tooltip", "");
        if (!tooltip.getString().isEmpty()) this.setTooltip(Tooltip.of(tooltip));

        this.getter = getter;
    }

    @Override
    public Text getMessage() {
        return Text.literal(super.getMessage().getString()
                + ": "
                + ScreenTexts.onOrOff(this.getter.get()).getString()
        );
    }
}
