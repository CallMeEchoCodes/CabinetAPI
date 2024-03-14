package dev.callmeecho.cabinetapi.registry;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface BlockEntityTypeRegistrar extends Registrar<BlockEntityType<?>> {
    @Override
    default Registry<BlockEntityType<?>> getRegistry() {
        return Registries.BLOCK_ENTITY_TYPE;
    }
}
