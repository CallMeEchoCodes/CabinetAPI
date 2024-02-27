package dev.callmeecho.cabinetapi.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface EntityRegistrar extends Registrar<EntityType<?>> {
    @Override
    default Registry<EntityType<?>> getRegistry() {
        return Registries.ENTITY_TYPE;
    }
}
