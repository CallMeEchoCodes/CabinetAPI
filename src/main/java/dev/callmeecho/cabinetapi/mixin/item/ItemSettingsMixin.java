package dev.callmeecho.cabinetapi.mixin.item;

import dev.callmeecho.cabinetapi.item.CabinetItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(Item.Settings.class)
public class ItemSettingsMixin implements CabinetItem.Settings {
}
