package dev.callmeecho.cabinetapi;

import dev.callmeecho.cabinetapi.client.particle.CabinetParticleTypes;
import dev.callmeecho.cabinetapi.registry.RegistrarHandler;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CabinetAPI implements ModInitializer {
    public static final String MODID = "cabinetapi";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    
    @Override
    public void onInitialize() {
        RegistrarHandler.process(CabinetParticleTypes.class, MODID);
    }
}
