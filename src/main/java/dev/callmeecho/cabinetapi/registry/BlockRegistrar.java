package dev.callmeecho.cabinetapi.registry;

import dev.callmeecho.cabinetapi.block.CabinetBlockSettings;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public interface BlockRegistrar extends Registrar<Block> {
    @Override
    default Registry<Block> getRegistry() {
        return Registries.BLOCK;
    }
    
    @Override
    default void register(String name, String namespace, Block object, Field field) {
        if (object.settings instanceof CabinetBlockSettings settings) {
            if (settings.getStrippedBlock() != null) { StrippableBlockRegistry.register(object, settings.getStrippedBlock()); }
        }
        Registry.register(getRegistry(), new Identifier(namespace, name), object);
        
        if (field.isAnnotationPresent(NoBlockItem.class)) return;
        registerBlockItem(object, namespace, name);
    }
    
    default void registerBlockItem(Block block, String namespace, String name) {
        Registry.register(Registries.ITEM, new Identifier(namespace, name), new BlockItem(block, new Item.Settings()));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface NoBlockItem {}
}
