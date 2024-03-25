package dev.callmeecho.cabinetapi.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Simple utility for making item groups without having to register each item individually.
 */
public class CabinetItemGroup {
    private final List<Item> items = new ArrayList<>();
    
    private final Identifier id;
    private final ItemGroup group;

    public CabinetItemGroup(Identifier id, Supplier<ItemStack> icon) {
        this.id = id;
        this.group = FabricItemGroup.builder()
                .icon(icon)
                .displayName(Text.translatable("itemGroup.%s.%s".formatted(id.getNamespace(), id.getPath())))
                .entries((displayContext, entries)-> addEntries(entries))
                .build();
    }
    
    public CabinetItemGroup(Identifier id, ItemConvertible icon) {
        this(id, () -> new ItemStack(icon.asItem()));
    }

    /**
     * Initialize the item group. This will also freeze the item group, meaning no more items can be added.
     */
    public void initialize() {
        Registry.register(Registries.ITEM_GROUP, id, group);
    }

    @ApiStatus.Internal
    private void addEntries(ItemGroup.Entries entries) {
        for (Item item : items) {
            entries.add(new ItemStack(item));
        }
    }

    @ApiStatus.Internal
    public void addItem(Item item) {
        items.add(item);
    }
}
