package dev.callmeecho.cabinetapi.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.callmeecho.cabinetapi.CabinetAPI.LOGGER;

public interface Config {
    String getName();
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    default void save() {
        String json = ConfigHandler.GSON.toJson(this);
        
        Path path = getPath();
        path.toFile().getParentFile().mkdirs();

        try {
            Files.write(path, json.getBytes());
        } catch (IOException e) {
            LOGGER.error("Failed to save config file: " + path, e);
        }
        
        LOGGER.info("Saved config file: " + path);
    }
    
    default Path getPath() {
        return Paths.get(FabricLoader.getInstance().getConfigDir().toString(), "", String.format("%s.%s", getName(), "json"));
    }
}
