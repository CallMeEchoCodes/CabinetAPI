package dev.callmeecho.cabinetapi.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.Session;

@Environment(EnvType.CLIENT)
public interface RunArgsNetworkExtensions {
    void cabinetAPI$setSession(Session session);
}
