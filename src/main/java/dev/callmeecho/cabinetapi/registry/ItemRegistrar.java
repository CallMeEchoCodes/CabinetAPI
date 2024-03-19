package dev.callmeecho.cabinetapi.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface ItemRegistrar extends Registrar<Item> {
    @Override
    default Registry<Item> getRegistry() {
        return Registries.ITEM;
    }
}
