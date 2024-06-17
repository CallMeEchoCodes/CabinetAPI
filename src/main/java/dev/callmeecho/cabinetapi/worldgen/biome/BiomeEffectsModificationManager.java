package dev.callmeecho.cabinetapi.worldgen.biome;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.callmeecho.cabinetapi.CabinetAPI;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.biome.Biome;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class BiomeEffectsModificationManager extends SinglePreparationResourceReloader<Map<RegistryKey<Biome>, BiomeEffectsModification>> implements IdentifiableResourceReloadListener {
    private final Map<RegistryKey<Biome>, BiomeEffectsModification> modifications = new HashMap<>();
    private static final Gson GSON = new Gson();

    @Override
    protected Map<RegistryKey<Biome>, BiomeEffectsModification> prepare(ResourceManager manager, Profiler profiler) {
        ResourceFinder resourceFinder = ResourceFinder.json("biome_effects");

        for (Map.Entry<Identifier, Resource> entry : resourceFinder.findResources(manager).entrySet()) {
            Identifier id = entry.getKey();
            Resource resource = entry.getValue();
            JsonElement element = null;

            try {
                BufferedReader reader = resource.getReader();
                element = JsonHelper.deserialize(GSON, reader, JsonElement.class);
                reader.close();
            } catch (IOException e) {
                CabinetAPI.LOGGER.error("Couldn't parse data file {} from {}", resource, id, e);
            }

            BiomeEffectsModification modification = BiomeEffectsModification.CODEC.decode(JsonOps.INSTANCE, element)
                    .getOrThrow()
                    .getFirst();

            for (RegistryKey<Biome> biomeKey : modification.targets()) modifications.put(biomeKey, modification);
        }

        return modifications;
    }

    @Override
    protected void apply(Map<RegistryKey<Biome>, BiomeEffectsModification> prepared, ResourceManager manager, Profiler profiler) {
        if (MinecraftClient.getInstance().getNetworkHandler() == null) return;
        DynamicRegistryManager registryManager = MinecraftClient.getInstance().getNetworkHandler().getRegistryManager();

        apply(registryManager);
    }

    public void apply(DynamicRegistryManager registryManager) {
        Registry<Biome> biomes = registryManager.get(RegistryKeys.BIOME);

        for (Map.Entry<RegistryKey<Biome>, BiomeEffectsModification> entry : modifications.entrySet()) {
            RegistryKey<Biome> biomeId = entry.getKey();
            BiomeEffectsModification modifier = entry.getValue();
            Optional<Biome> biome = biomes.getOrEmpty(biomeId);

            if (biome.isEmpty()) continue;
            modifier.apply(biome.get());
        }
    }

    @Override
    public Identifier getFabricId() {
        return Identifier.of(CabinetAPI.MODID, "biome_effects");
    }
}
