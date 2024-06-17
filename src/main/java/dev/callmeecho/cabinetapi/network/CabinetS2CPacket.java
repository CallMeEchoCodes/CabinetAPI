package dev.callmeecho.cabinetapi.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public interface CabinetS2CPacket<T extends CustomPayload> extends CabinetPacket {
    default void send(T payload, ServerPlayerEntity player) { ServerPlayNetworking.send(player, payload); }

    @Environment(EnvType.CLIENT)
    void receive(T payload, ClientPlayNetworking.Context context);

    @SuppressWarnings("unchecked")
    @Override
    default void register() {
        PayloadTypeRegistry.playS2C().register(
                (CustomPayload.Id<T>) getId(),
                (PacketCodec<? super RegistryByteBuf, T>) getCodec()
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    @Environment(EnvType.CLIENT)
    default void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(getId(), (payload, context) -> receive((T) payload, context));
    }
}
