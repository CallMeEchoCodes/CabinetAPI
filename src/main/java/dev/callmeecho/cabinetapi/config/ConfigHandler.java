package dev.callmeecho.cabinetapi.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import dev.callmeecho.cabinetapi.util.ReflectionHelper;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


/**
 * Handles the loading of config files.
 * In the future, this class will also handle aspects of the GUI.
 */
public class ConfigHandler {
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Get a config file. If the file does not exist, it will be created and saved.
     * Could also be described as a load function.
     *
     * @param clazz The class of the config file
     * @param <T> The type of the config file
     * @return The config file
     */
    public static <T extends Config> T getConfig(Class<T> clazz) {
        T config = ReflectionHelper.instantiate(clazz);

        if (!Files.exists(config.getPath())) {
            config.save();
            return config;
        }

        try {
            List<String> lines = Files.readAllLines(config.getPath());
            lines.removeIf(line -> line.trim().startsWith("//"));
            StringBuilder stringBuilder = new StringBuilder();
            lines.forEach(stringBuilder::append);

            T loadedConfig = GSON.fromJson(stringBuilder.toString(), clazz);
            loadedConfig.save();

            return loadedConfig;
        } catch (JsonSyntaxException | IOException e) {
            throw new RuntimeException("Failed to load config file: " + config.getPath(), e);
        }
    }

    @ApiStatus.Internal
    public static void getNestedClasses(List<Class<?>> classes, Class<?> clazz) {
        classes.add(clazz);

        for (Class<?> nestedClass : clazz.getDeclaredClasses()) {
            getNestedClasses(classes, nestedClass);
        }
    }
}
