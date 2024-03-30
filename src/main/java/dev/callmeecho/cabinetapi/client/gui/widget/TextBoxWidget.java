package dev.callmeecho.cabinetapi.client.gui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

public class TextBoxWidget extends TextFieldWidget implements CabinetWidget {
    private final Setter<String> setter;

    private long lastTick = 0;

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

    /*? if 1.20.1 {*//*
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (Util.getMeasuringTimeMs() - lastTick > 50) {
            this.tick();
            lastTick = Util.getMeasuringTimeMs();
        }
        super.render(context, mouseX, mouseY, delta);
    }
    *//*?} */


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