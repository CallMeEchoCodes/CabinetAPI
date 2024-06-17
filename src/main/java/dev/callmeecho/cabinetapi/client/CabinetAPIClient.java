package dev.callmeecho.cabinetapi.client;

import dev.callmeecho.cabinetapi.client.particle.CabinetParticleTypes;
import dev.callmeecho.cabinetapi.client.particle.DebugParticle;
import dev.callmeecho.cabinetapi.config.network.ConfigSyncPacket;
import dev.callmeecho.cabinetapi.worldgen.biome.BiomeEffectsModificationManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static dev.callmeecho.cabinetapi.CabinetAPI.MODID;


public class CabinetAPIClient implements ClientModInitializer {
    private static final BiomeEffectsModificationManager BIOME_EFFECTS_MODIFICATION_MANAGER = new BiomeEffectsModificationManager();

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(CabinetParticleTypes.DEBUG, DebugParticle.Factory::new);
        ConfigSyncPacket.SINGLETON.getInstance().registerClient();

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(BIOME_EFFECTS_MODIFICATION_MANAGER);
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> BIOME_EFFECTS_MODIFICATION_MANAGER.apply(registries));
    }
}
