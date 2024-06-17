package dev.callmeecho.cabinetapi.client.gui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TextBoxWidget extends TextFieldWidget implements CabinetWidget {
    private final Setter<String> setter;

    public TextBoxWidget(String translationKey, Getter<String> getter, Setter<String> setter) {
        super(MinecraftClient.getInstance().textRenderer, 0, 0, 0, 20, Text.of(getter.get()));
        this.setter = setter;

        setPlaceholder(Text.translatableWithFallback(translationKey + ".placeholder", "").formatted(Formatting.DARK_GRAY));

        Text tooltip = Text.translatableWithFallback(translationKey + ".tooltip", "");
        if (!tooltip.getString().isEmpty()) this.setTooltip(Tooltip.of(tooltip));

        this.setText(getter.get());
        setSelectionEnd(0);
        setSelectionStart(0);
    }

    @Override
    public void write(String text) {
        super.write(text);
        this.setter.set(this.getText());
    }

    @Override
    public void eraseCharacters(int characterOffset) {
        super.eraseCharacters(characterOffset);
        this.setter.set(this.getText());
    }

    @Override
    public void eraseWords(int wordOffset) {
        super.eraseWords(wordOffset);
        this.setter.set(this.getText());
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }
}