package dev.callmeecho.cabinetapi.devtools;

import dev.callmeecho.cabinetapi.devtools.item.LootLoader;
import dev.callmeecho.cabinetapi.registry.ItemRegistrar;
import net.minecraft.item.Item;

/**
 * Registrar for the in-game dev tools
 * Will only be registered if debug mode is enabled
 */
public class CabinetDevtoolsRegistry implements ItemRegistrar {
    public static final Item LOOT_LOADER = new LootLoader(new Item.Settings().maxCount(1));
}
