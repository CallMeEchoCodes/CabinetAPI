package dev.callmeecho.cabinetapi.network;

import dev.callmeecho.cabinetapi.CabinetAPI;
import dev.callmeecho.cabinetapi.config.Config;
import dev.callmeecho.cabinetapi.config.ConfigHandler;
import dev.callmeecho.cabinetapi.config.annotations.Sync;
import dev.callmeecho.cabinetapi.util.ReflectionHelper;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// This is a class because I wanted to experiment with what a potential networking API could look like
public class ConfigSyncPacket implements CabinetPacket {
    public static final Identifier ID = new Identifier(CabinetAPI.MODID, "config_sync");

    @Override
    public void send(PlayerEntity playerEntity) {
        if (!(playerEntity instanceof ServerPlayerEntity player)) return;

        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

        NbtCompound root = new NbtCompound();
        NbtList list = new NbtList();

        ConfigHandler.getConfigs().forEach(config -> list.add(config.writeSyncTag()));

        root.put("configs", list);
        packet.writeNbt(root);
        ServerPlayNetworking.send(player, ID, packet);
    }

    @Override
    public void receive(NbtCompound root) {
        NbtList list = root.getList("configs", NbtElement.COMPOUND_TYPE);

        list.forEach(element -> {
            NbtCompound tag = (NbtCompound) element;
            Identifier name = Identifier.tryParse(tag.getString("name"));
            String json = tag.getString("data");

            Config clientConfig = ConfigHandler.getConfigByName(name);
            if (clientConfig == null) return;

            Config serverConfig = ConfigHandler.GSON.fromJson(json, clientConfig.getClass());

            for (Field field : serverConfig.getClass().getDeclaredFields()) {
                if (!field.isAnnotationPresent(Sync.class)) continue;

                ReflectionHelper.setFieldValue(clientConfig, field, ReflectionHelper.getFieldValue(serverConfig, field));
            }

            ConfigHandler.getConfigs().set(ConfigHandler.getConfigs().indexOf(clientConfig), clientConfig);
        });
    }

    public void register(boolean isClient) {
        if (!isClient)
            ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> server.execute(() -> {
                this.send(handler.player);
            }));
        else {
            ClientPlayNetworking.registerGlobalReceiver(ID, (client, handler, buf, responseSender) -> {
                NbtCompound root = buf.readNbt();
                if (root == null) return;

                client.execute(() -> this.receive(root));
            });

            ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
                ConfigHandler.reloadConfigs();
            });
        }
    }
}
