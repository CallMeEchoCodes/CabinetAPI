package dev.callmeecho.cabinetapi.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * An inventory that can be implemented by a BlockEntity
 */
public interface DefaultedInventory extends Inventory {
    DefaultedList<ItemStack> getItems();

    @Override
    default int size() { return getItems().size(); }

    @Override
    default boolean isEmpty() { return getItems().isEmpty(); }

    @Override
    default ItemStack getStack(int slot) { return getItems().get(slot); }

    @Override
    default ItemStack removeStack(int slot, int amount) {
        ItemStack stack = Inventories.splitStack(getItems(), slot, amount);
        notifyListeners();
        return stack;
    }

    @Override
    default ItemStack removeStack(int slot) {
        ItemStack stack = Inventories.removeStack(getItems(), slot);
        notifyListeners();
        return stack;
    }

    @Override
    default void setStack(int slot, ItemStack stack) {
        getItems().set(slot, stack);
        notifyListeners();   
    }

    @Override
    default boolean canPlayerUse(PlayerEntity player) { return true; }

    @Override
    default void clear() {
        getItems().clear();
        notifyListeners();   
    }

    default void notifyListeners() {
        markDirty();
        World world = getWorld();
        
        if (world != null && !world.isClient)
            world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Nullable
    World getWorld();
    BlockPos getPos();
    BlockState getCachedState();
}
