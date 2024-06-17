package dev.callmeecho.cabinetapi.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerLootComponent;
import net.minecraft.inventory.LootableInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

/**
 * Helper class for block entities that have a loot table.
 * You could use the {@link net.minecraft.block.entity.LootableContainerBlockEntity} class, but it comes with a menu,
 * and you might not want that.
 */
public class LootableInventoryBlockEntity extends InventoryBlockEntity implements LootableInventory {
    @Nullable
    protected RegistryKey<LootTable> lootTable;
    protected long lootTableSeed = 0L;

    protected LootableInventoryBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, int size) {
        super(blockEntityType, blockPos, blockState, size);
    }

    @Nullable
    public RegistryKey<LootTable> getLootTable() {
        return this.lootTable;
    }

    public void setLootTable(@Nullable RegistryKey<LootTable> registryKey) {
        this.lootTable = registryKey;
    }

    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    public void setLootTableSeed(long l) {
        this.lootTableSeed = l;
    }

    public boolean isEmpty() {
        this.generateLoot(null);
        return super.isEmpty();
    }

    public ItemStack getStack(int i) {
        this.generateLoot(null);
        return super.getStack(i);
    }

    public ItemStack removeStack(int i, int j) {
        this.generateLoot(null);
        return super.removeStack(i, j);
    }

    public ItemStack removeStack(int i) {
        this.generateLoot(null);
        return super.removeStack(i);
    }

    public void setStack(int i, ItemStack itemStack) {
        this.generateLoot(null);
        super.setStack(i, itemStack);
    }


    protected void readComponents(BlockEntity.ComponentsAccess componentsAccess) {
        super.readComponents(componentsAccess);
        ContainerLootComponent containerLootComponent = componentsAccess.get(DataComponentTypes.CONTAINER_LOOT);
        if (containerLootComponent != null) {
            this.lootTable = containerLootComponent.lootTable();
            this.lootTableSeed = containerLootComponent.seed();
        }

    }

    protected void addComponents(ComponentMap.Builder builder) {
        super.addComponents(builder);
        if (this.lootTable != null) {
            builder.add(DataComponentTypes.CONTAINER_LOOT, new ContainerLootComponent(this.lootTable, this.lootTableSeed));
        }

    }

    public void removeFromCopiedStackNbt(NbtCompound nbtCompound) {
        super.removeFromCopiedStackNbt(nbtCompound);
        nbtCompound.remove("LootTable");
        nbtCompound.remove("LootTableSeed");
    }
}
