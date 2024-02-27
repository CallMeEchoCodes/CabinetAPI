package dev.callmeecho.cabinetapi;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.Session;

@Environment(EnvType.CLIENT)
public interface RunArgsNetworkInterface {
    void cabinetAPI$setSession(Session session);
}
