package dev.callmeecho.cabinetapi.registry;

import dev.callmeecho.cabinetapi.item.CabinetItemGroup;
import dev.callmeecho.cabinetapi.item.CabinetItemSettings;
import dev.callmeecho.cabinetapi.misc.ItemExtensions;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;

public interface ItemRegistrar extends Registrar<Item> {
    @Override
    default Registry<Item> getRegistry() {
        return Registries.ITEM;
    }
}
