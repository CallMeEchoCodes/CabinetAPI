package dev.callmeecho.cabinetapi.registry;

import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class RegistrarHandler {
    @ApiStatus.Internal
    public static List<Registrar<?>> registrars = List.of();
    
    /**
     * Initialize a registrar class and register all objects.
     * This should be called instead of calling Registrar.init() directly.
     * @param clazz Registrar class to process
     * @param namespace Namespace to register objects in
     */
    public static void process(Class<? extends Registrar<?>> clazz, String namespace) {
        try {
            Registrar<?> registrar = clazz.getConstructor().newInstance();
            registrar.init(namespace);
            registrars.add(registrar);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate registrar!", e);
        }
    }

    /**
     * Loop through all fields in a registrar class and run a function on each field.
     * Useful for data generation.
     * @param clazz Registrar class to loop through
     * @param action Function to run on each field
     * @param <T> Type of field to loop through
     */
    @SuppressWarnings("unchecked")
    public static <T> void forEach(Class<? extends Registrar<?>> clazz, RegistrarAction<T> action) {
        try {
            Registrar<?> registrar = clazz.getConstructor().newInstance();
            for (var field : registrar.getClass().getDeclaredFields()) {
                T value;
                try {
                    value = (T)field.get(null);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to access field " + field.getName(), e);
                }

                if (value != null && field.getType().isAssignableFrom(value.getClass())) { action.run(value); }
            }
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate registrar!", e);
        }
    }
    
    @FunctionalInterface
    public interface RegistrarAction<T> {
        void run(T value);
    }
}
