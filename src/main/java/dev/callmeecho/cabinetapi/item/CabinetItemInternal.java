package dev.callmeecho.cabinetapi.item;

import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.WeakHashMap;

public final class CabinetItemInternal {
    private static final WeakHashMap<Item.Settings, Data> DATA = new WeakHashMap<>();

    public static Data computeIfAbsent(Item.Settings settings) { return DATA.computeIfAbsent(settings, s -> new Data()); }

    public static Data get(Item.Settings settings) { return DATA.get(settings); }

    public static final class Data {
        @Nullable
        private CabinetItemGroup group;

        public void group(CabinetItemGroup group) { this.group = group; }
        public @Nullable CabinetItemGroup getGroup() { return group; }
    }
}
