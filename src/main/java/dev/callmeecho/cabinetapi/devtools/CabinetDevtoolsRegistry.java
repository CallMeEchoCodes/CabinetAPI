package dev.callmeecho.cabinetapi.devtools;

import dev.callmeecho.cabinetapi.devtools.item.LootLoader;
import dev.callmeecho.cabinetapi.item.CabinetItemSettings;
import dev.callmeecho.cabinetapi.registry.ItemRegistrar;
import net.minecraft.item.Item;

public class CabinetDevtoolsRegistry implements ItemRegistrar {
    public static final Item LOOT_LOADER = new LootLoader(new CabinetItemSettings().maxCount(1));
}
