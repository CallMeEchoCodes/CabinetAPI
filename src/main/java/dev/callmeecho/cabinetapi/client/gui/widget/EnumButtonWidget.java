package dev.callmeecho.cabinetapi.client.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class EnumButtonWidget extends CabinetButtonWidget {
    private final Getter<Enum<?>> getter;
    private final Setter<Enum<?>> setter;

    private final List<Enum<?>> enumValues = new ArrayList<>();

    private final String translationKey;

    public EnumButtonWidget(String translationKey, Getter<Enum<?>> getter, Setter<Enum<?>> setter, Enum<?> enumValue) {
        super(
                0,
                0,
                0,
                20,
                Text.translatable(translationKey),
                button -> { },
                button -> null
        );

        // onPress has to be done with an override or the compiler will shout at you

        this.getter = getter;
        this.setter = setter;

        List<?> values = Arrays.asList(enumValue.getClass().getEnumConstants());

        if (values.isEmpty()) throw new IllegalArgumentException("Enum values cannot be null");
        for (Object value : values) if (value instanceof Enum<?>) enumValues.add((Enum<?>) value);

        Text tooltip = Text.translatableWithFallback(translationKey + ".tooltip", "");
        if (!tooltip.getString().isEmpty()) this.setTooltip(Tooltip.of(tooltip));

        this.translationKey = translationKey;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        cycle();
    }

    private void cycle() {
        Enum<?> current = getter.get();
        int index = enumValues.indexOf(current);
        setter.set(enumValues.get((index + 1) % enumValues.size()));
    }

    @Override
    public Text getMessage() {
        return Text.of(super.getMessage().getString()
                + ": "
                + Text.translatable(translationKey + "." + getter.get().toString().toLowerCase()).getString()
        );
    }
}
