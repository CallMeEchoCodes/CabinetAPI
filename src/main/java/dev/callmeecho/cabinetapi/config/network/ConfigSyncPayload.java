package dev.callmeecho.cabinetapi.config.network;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.packet.CustomPayload;

import java.util.List;

public record ConfigSyncPayload(List<Pair<String, String>> configs) implements CustomPayload {
    @Override
    public Id<? extends CustomPayload> getId() {
        return ConfigSyncPacket.ID;
    }
}
