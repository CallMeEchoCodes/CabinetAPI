package dev.callmeecho.cabinetapi;

import dev.callmeecho.cabinetapi.client.particle.CabinetParticleTypes;
import dev.callmeecho.cabinetapi.devtools.CabinetDevtoolsRegistry;
import dev.callmeecho.cabinetapi.registry.RegistrarHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroups;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CabinetAPI implements ModInitializer {
    public static final String MODID = "cabinetapi";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final boolean DEBUG;

    static {
        boolean debug;
        debug = FabricLoader.getInstance().isDevelopmentEnvironment();
        if (System.getProperty("cabinetapi.debug") != null) debug = Boolean.getBoolean("cabinetapi.debug");

        DEBUG = debug;
    }
    
    @Override
    @ApiStatus.Internal
    public void onInitialize() {
        RegistrarHandler.process(CabinetParticleTypes.class, MODID);

        if (DEBUG) {
            LOGGER.info("Development mode enabled, registering devtools...");
            RegistrarHandler.process(CabinetDevtoolsRegistry.class, MODID);
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register(content -> content.add(CabinetDevtoolsRegistry.LOOT_LOADER));
        }
    }
}
