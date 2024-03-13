package dev.callmeecho.cabinetapi.registry;

import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface ParticleTypeRegistrar extends Registrar<ParticleType<?>> {
    @Override
    default Registry<ParticleType<?>> getRegistry() {
        return Registries.PARTICLE_TYPE;
    }
}
