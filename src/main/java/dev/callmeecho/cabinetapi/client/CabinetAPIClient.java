package dev.callmeecho.cabinetapi.client;

import dev.callmeecho.cabinetapi.client.particle.CabinetParticleTypes;
import dev.callmeecho.cabinetapi.client.particle.DebugParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

import static dev.callmeecho.cabinetapi.CabinetAPI.CONFIG_SYNC_PACKET;

public class CabinetAPIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(CabinetParticleTypes.DEBUG, DebugParticle.Factory::new);
        CONFIG_SYNC_PACKET.register(true);
    }
}
