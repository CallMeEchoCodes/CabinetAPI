package dev.callmeecho.cabinetapi.config.network;

import com.mojang.datafixers.util.Pair;
import dev.callmeecho.cabinetapi.CabinetAPI;
import dev.callmeecho.cabinetapi.config.Config;
import dev.callmeecho.cabinetapi.config.ConfigHandler;
import dev.callmeecho.cabinetapi.config.annotations.Sync;
import dev.callmeecho.cabinetapi.network.CabinetPacketCodecs;
import dev.callmeecho.cabinetapi.network.CabinetS2CPacket;
import dev.callmeecho.cabinetapi.util.ReflectionHelper;
import dev.callmeecho.cabinetapi.util.Singleton;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConfigSyncPacket implements CabinetS2CPacket<ConfigSyncPayload> {
    public static final CustomPayload.Id<ConfigSyncPayload> ID = new CustomPayload.Id<>(Identifier.of(CabinetAPI.MODID, "config_sync"));
    public static final PacketCodec<ByteBuf, ConfigSyncPayload> CODEC =
            PacketCodec.tuple(
                    CabinetPacketCodecs.pair(
                            PacketCodecs.STRING,
                            PacketCodecs.STRING
                    ).collect(PacketCodecs.toList()),
                    ConfigSyncPayload::configs,
                    ConfigSyncPayload::new
            );

    public static final Singleton<ConfigSyncPacket> SINGLETON = new Singleton<>(ConfigSyncPacket.class);

    @Override
    public void receive(ConfigSyncPayload payload, ClientPlayNetworking.Context context) {
        List<Pair<String, String>> list = payload.configs();

        context.client().execute(() ->
                list.forEach(config -> {
                    Config clientConfig = ConfigHandler.getConfigByName(Identifier.tryParse(config.getFirst()));
                    if (clientConfig == null) return;

                    Config serverConfig = ConfigHandler.GSON.fromJson(config.getSecond(), clientConfig.getClass());

                    for (Field field : serverConfig.getClass().getDeclaredFields()) {
                        if (!field.isAnnotationPresent(Sync.class)) continue;

                        ReflectionHelper.setFieldValue(
                                clientConfig,
                                field,
                                ReflectionHelper.getFieldValue(serverConfig, field)
                        );
                    }

                    ConfigHandler.getConfigs().set(ConfigHandler.getConfigs().indexOf(clientConfig), clientConfig);
                }));
    }

    @Override
    public void register() {
        CabinetS2CPacket.super.register();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            List<Pair<String, String>> list = new ArrayList<>();
            ConfigHandler.getConfigs().forEach(config -> list.add(config.getSyncData()));
            ConfigSyncPayload payload = new ConfigSyncPayload(list);

            send(payload, handler.getPlayer());
        });
    }

    @Override
    public void registerClient() {
        CabinetS2CPacket.super.registerClient();
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ConfigHandler.reloadConfigs());
    }

    @Override
    public CustomPayload.Id<? extends ConfigSyncPayload> getId() {
        return ID;
    }

    @Override
    public <B extends PacketByteBuf> PacketCodec<? super B, ? extends CustomPayload> getCodec() { return CODEC; }
}
