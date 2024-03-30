package dev.callmeecho.cabinetapi.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * A bunch of utilities to reduce boilerplate reflection code.
 */
public final class ReflectionHelper {
    private ReflectionHelper() {
    }

    /**
     * Instantiate a class with no arguments.
     *
     * @param clazz Class to instantiate
     * @param <T> The type of the class
     * @return A new instance of the class
     */
    public static <T> T instantiate(Class<T> clazz) {
        T instance;
        try {
            instance = clazz.getConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(
                    (e instanceof NoSuchMethodException ?
                            "No constructor without arguments found for class " :
                            "Failed to instantiate class "
                    ) + clazz.getName(),
                    e
            );
        }

        return instance;
    }

    /**
     * Instantiate a class with arguments.
     *
     * @param clazz Class to instantiate
     * @param args Arguments to pass to the constructor
     * @param <T> The type of the class
     * @return A new instance of the class
     */
    public static <T> T instantiate(Class<T> clazz, Object... args) {
        T instance;
        try {
            instance = clazz.getConstructor().newInstance(args);
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(
                    (e instanceof NoSuchMethodException ?
                            "No constructor with provided arguments found for class " :
                            "Failed to instantiate class "
                    ) + clazz.getName(),
                    e
            );
        }
        return instance;
    }

    /**
     * Find all static fields of a given type in a class and perform
     * an action on them.
     * 
     * @param clazz Class to search for fields in
     * @param target Type to find fields of
     * @param action Action to perform on each field
     * @param <T> Type of {@code clazz}
     * @param <F> Type of {@code target}
     */
    @SuppressWarnings("unchecked")
    public static <T, F> void forEachStaticField(Class<T> clazz, Class<F> target, FieldAction<F> action) {
        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;

            F value;
            try {
                value = (F) field.get(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field " + field.getName(), e);
            }
            
            if (value == null) continue;
            if (!target.isAssignableFrom(value.getClass())) continue;
            
            action.run(value, field.getName(), field);
        }
    }

    /**
     * Get the value of a field.
     * Will throw a {@link ClassCastException} if the field is not of the
     * expected type.
     *
     * @param instance Instance to get the field from
     * @param field Field to get the value of
     * @param <T> Type of the field
     * @return Value of the field
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object instance, Field field) {
        try {
            Object value = field.get(instance);
            if (value == null) return null;

            return (T) value;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access field " + field.getName(), e);
        }
    }

    /**
     * Set the value of a field.
     *
     * @param instance Instance to set the field on
     * @param field Field to set the value of
     * @param value Value to set the field to
     */
    public static void setFieldValue(Object instance, Field field, Object value) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to set field " + field.getName(), e);
        }
    }

    /**
     * Get the value of a static field.
     *
     * @param field Field to get the value of
     * @param <T> Type of the field
     * @return Value of the field
     */
    public static <T> T getFieldValue(Field field) {
        return getFieldValue(null, field);
    }

    /**
     * Set the value of a static field.
     *
     * @param field Field to set the value of
     * @param value Value to set the field to
     */
    public static void setFieldValue(Field field, Object value) {
        setFieldValue(null, field, value);
    }

    /**
     * Functional interface for actions to perform on fields.
     *
     * @param <T> Type of the field
     */
    @FunctionalInterface
    public interface FieldAction<T> {
        void run(T value, String name, Field field);
    }
}