package dev.callmeecho.cabinetapi.util;

public class Singleton<T> {
    private T instance;
    private final Class<T> clazz;

    public Singleton(Class<T> clazz) { this.clazz = clazz; }
    public T getInstance() {
        synchronized (this) {
            if (instance == null) instance = ReflectionHelper.instantiate(clazz);
            return instance;
        }
    }
}
