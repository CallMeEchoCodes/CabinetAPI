package dev.callmeecho.cabinetapi.item;

import dev.callmeecho.cabinetapi.CabinetAPI;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class CabinetItemGroup {
    private final List<Item> items = new ArrayList<>();
    
    private final Identifier id;
    private final ItemGroup group;

    public CabinetItemGroup(Identifier id, Item icon) {
        this.id = id;
        this.group = FabricItemGroup.builder()
                .icon(() -> new ItemStack(icon))
                .displayName(Text.translatable("itemGroup.%s.%s".formatted(id.getNamespace(), id.getPath())))
                .entries((displayContext, entries)-> addEntries(entries))
                .build();
    }
    
    public void initialize() {
        Registry.register(Registries.ITEM_GROUP, id, group);
    }
    
    private void addEntries(ItemGroup.Entries entries) {
        for (Item item : items) {
            entries.add(new ItemStack(item));
        }
    }
    
    public void addItem(Item item) {
        items.add(item);
    }
}
