package dev.callmeecho.cabinetapi.registry;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public interface Registrar<T> {
    /**
     * Get the registry to register objects in.
     * @return Registry to register objects in
     */
    Registry<T> getRegistry();
    
    /**
     * Register an object to the registry.
     * @param name Name of the object
     * @param namespace Namespace to register the object in
     * @param object Object to register
     * @param field Field the object is stored in
     */
    default void register(String name, String namespace, T object, Field field) { Registry.register(getRegistry(), new Identifier(namespace, name), object); };
    
    /**
     * Initialize the registrar and register all objects.
     * Do not call this method directly, use RegistrarHandler.process() instead.
     * @param namespace Namespace to register objects in
     */
    @SuppressWarnings("unchecked")
    @ApiStatus.Internal
    default void init(String namespace) {
        for (var field : this.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            T value;
            try {
                value = (T)field.get(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field " + field.getName(), e);
            }
            
            if (value != null && field.getType().isAssignableFrom(value.getClass())) { register(field.getName().toLowerCase(), namespace, value, field); }
        }
    }
}
