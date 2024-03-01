package dev.callmeecho.testmod;

import dev.callmeecho.cabinetapi.item.CabinetItemSettings;
import dev.callmeecho.cabinetapi.registry.ItemRegistrar;
import net.minecraft.item.Item;

@SuppressWarnings("unused")
public class TestModItemRegistrar implements ItemRegistrar {
    public static final Item TEST_ITEM = new Item(new CabinetItemSettings().group(TestMod.ITEM_GROUP));
}
