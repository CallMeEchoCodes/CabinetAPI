package dev.callmeecho.cabinetapi.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class InventoryBlockEntity extends BlockEntity implements DefaultedInventory {
    protected final DefaultedList<ItemStack> inventory;
    public InventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        this.inventory = DefaultedList.ofSize(size, ItemStack.EMPTY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    protected void readComponents(BlockEntity.ComponentsAccess componentsAccess) {
        super.readComponents(componentsAccess);
        componentsAccess.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).copyTo(this.getItems());
    }

    @Override
    protected void addComponents(ComponentMap.Builder builder) {
        super.addComponents(builder);
        builder.add(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.getItems()));
    }

    @Nullable
    @Override
    public World getWorld() {
        return super.getWorld();
    }

    @Override
    public BlockPos getPos() {
        return super.getPos();
    }

    @Override
    public BlockState getCachedState() {
        return super.getCachedState();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeFromCopiedStackNbt(NbtCompound nbtCompound) {
        nbtCompound.remove("Items");
    }
}
