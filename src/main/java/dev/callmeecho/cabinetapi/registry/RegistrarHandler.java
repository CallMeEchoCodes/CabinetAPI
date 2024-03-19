package dev.callmeecho.cabinetapi.registry;

import dev.callmeecho.cabinetapi.util.ReflectionHelper;

import java.util.ArrayList;
import java.util.List;

public class RegistrarHandler {
    public static final List<Registrar<?>> registrars = new ArrayList<>();
    
    /**
     * Initialize a registrar class and register all objects.
     * This should be called instead of calling Registrar.init() directly.
     * @param clazz Registrar class to process
     * @param namespace Namespace to register objects in
     */
    public static void process(Class<? extends Registrar<?>> clazz, String namespace) {
        Registrar<?> registrar = ReflectionHelper.instantiate(clazz);
        registrar.init(namespace);
        registrars.add(registrar);
    }
    
    @FunctionalInterface
    public interface RegistrarAction<T> {
        void run(T value);
    }
}
