package dev.callmeecho.cabinetapi.client.particle;

import dev.callmeecho.cabinetapi.registry.ParticleTypeRegistrar;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;

public class CabinetParticleTypes implements ParticleTypeRegistrar {
    public static final DefaultParticleType DEBUG = FabricParticleTypes.simple();
}
