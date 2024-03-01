package dev.callmeecho.cabinetapi.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.List;

public class ConfigHandler {
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    public static <T extends Config> T getConfig(Class<T> clazz) {
        T config;
        try {
            config = clazz.getConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate config!", e);
        }
        
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
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config file: " + config.getPath(), e);
        }
    }
}
