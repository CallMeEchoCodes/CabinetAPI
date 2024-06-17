package dev.callmeecho.cabinetapi.client.particle;

import dev.callmeecho.cabinetapi.registry.ParticleTypeRegistrar;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;

public class CabinetParticleTypes implements ParticleTypeRegistrar {
    public static final SimpleParticleType DEBUG = FabricParticleTypes.simple();
}
