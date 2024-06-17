package dev.callmeecho.cabinetapi.client.gui.widget;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;

public interface CabinetWidget extends Selectable, Drawable, Positionable, Element {
    int getWidth();
    int getHeight();

    void setWidth(int width);
    void setHeight(int height);

    @FunctionalInterface
    interface Getter<T> {
        T get();
    }

    @FunctionalInterface
    interface Setter<T> {
        void set(T value);
    }
}
