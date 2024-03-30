package dev.callmeecho.cabinetapi.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;


/**
 * Represents a packet that can be sent and received.
 */
public interface CabinetPacket {
    void send(PlayerEntity player);
    void receive(NbtCompound buffer);
}
