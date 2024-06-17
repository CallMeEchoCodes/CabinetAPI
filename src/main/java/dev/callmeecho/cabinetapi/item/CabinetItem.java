package dev.callmeecho.cabinetapi.item;

import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

public interface CabinetItem {
    @Nullable
    CabinetItemGroup cabinetapi$getGroup();

    interface Settings {
        default Item.Settings group(CabinetItemGroup group) {
            CabinetItemInternal.computeIfAbsent((Item.Settings)this).group(group);
            return (Item.Settings)this;
        }
    }
}
