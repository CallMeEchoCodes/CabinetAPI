package dev.callmeecho.cabinetapi.registry;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BlockEntityTypeRegistrar implements Registrar<BlockEntityType<?>> {
    @Override
    public Registry<BlockEntityType<?>> getRegistry() {
        return Registries.BLOCK_ENTITY_TYPE;
    }
}
