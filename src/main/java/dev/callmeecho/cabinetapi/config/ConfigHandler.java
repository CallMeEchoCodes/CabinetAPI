package dev.callmeecho.cabinetapi.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import dev.callmeecho.cabinetapi.util.ReflectionHelper;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Handles the loading of config files.
 * In the future, this class will also handle aspects of the GUI.
 */
public class ConfigHandler {
    @ApiStatus.Internal
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected static final Gson GSON_NON_SYNC = new GsonBuilder().setPrettyPrinting().addSerializationExclusionStrategy(new NonSyncExclusionStrategy()).create();

    private static final List<Config> configs = new ArrayList<>();
    private static final HashMap<Identifier, Integer> configNames = new HashMap<>();

    /**
     * Get a config file. If the file does not exist, it will be created and saved.
     * Could also be described as a load function.
     *
     * @param clazz The class of the config file
     * @param <T> The type of the config file
     * @return The config file
     */
    @SuppressWarnings("unchecked")
    public static <T extends Config> T getConfig(Class<T> clazz) {
        T config = ReflectionHelper.instantiate(clazz);

        if (!Files.exists(config.getPath())) {
            config.save();

            configs.add(config);
            configNames.put(config.getName(), configs.size() - 1);

            return config;
        }

        try {
            List<String> lines = Files.readAllLines(config.getPath());
            lines.removeIf(line -> line.trim().startsWith("//"));
            StringBuilder stringBuilder = new StringBuilder();
            lines.forEach(stringBuilder::append);

            T loadedConfig = GSON.fromJson(stringBuilder.toString(), clazz);
            loadedConfig.save();

            if (configNames.containsKey(loadedConfig.getName())) {
                Config existingConfig = getConfigByName(loadedConfig.getName());
                for (Field field : clazz.getDeclaredFields()) {
                    ReflectionHelper.setFieldValue(existingConfig, field, ReflectionHelper.getFieldValue(loadedConfig, field));
                }

                return (T) existingConfig;
            }

            configs.add(loadedConfig);
            configNames.put(loadedConfig.getName(), configs.size() - 1);

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

    @ApiStatus.Internal
    public static List<Config> getConfigs() {
        return configs;
    }

    public static Config getConfigByName(Identifier name) {
        if (!configNames.containsKey(name)) return null;
        return configs.get(configNames.get(name));
    }

    public static void reloadConfigs() {
        List<Config> oldConfigs = new ArrayList<>(configs);
        for (Config config : oldConfigs) getConfig(config.getClass());
    }
}
