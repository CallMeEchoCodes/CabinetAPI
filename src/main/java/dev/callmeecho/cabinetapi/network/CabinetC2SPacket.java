package dev.callmeecho.cabinetapi.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public interface CabinetC2SPacket<T extends CustomPayload> extends CabinetPacket {
    @Environment(EnvType.CLIENT)
    default void send(T payload) { ClientPlayNetworking.send(payload); }

    void receive(T payload, ServerPlayNetworking.Context context);

    @SuppressWarnings("unchecked")
    @Override
    default void register() {
        PayloadTypeRegistry.playC2S().register(
                (CustomPayload.Id<T>) getId(),
                (PacketCodec<? super RegistryByteBuf, T>) getCodec()
        );
        ServerPlayNetworking.registerGlobalReceiver(getId(), (payload, context) -> receive((T) payload, context));
    }
}
