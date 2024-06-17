package dev.callmeecho.cabinetapi.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public interface CabinetPacket {
    CustomPayload.Id<? extends CustomPayload> getId();
    <B extends PacketByteBuf> PacketCodec<? super B, ? extends CustomPayload> getCodec();

    void register();

    @Environment(EnvType.CLIENT)
    void registerClient();
}
