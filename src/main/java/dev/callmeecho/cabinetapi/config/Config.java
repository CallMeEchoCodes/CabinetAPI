package dev.callmeecho.cabinetapi.config;

import dev.callmeecho.cabinetapi.config.annotations.Comment;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static dev.callmeecho.cabinetapi.CabinetAPI.LOGGER;

public interface Config {
    String getName();
    
    default void save() {
        ConfigHandler.saveConfig(this.getClass());
    }
    
    default Path getPath() {
        return Paths.get(
                FabricLoader.getInstance().getConfigDir().toString(),
                "",
                String.format("%s.%s", getName(), getFileType())
        );
    }


    /**
     * @return File extension of the config file. Doesn't actually change the file format. Some may want to set this to .json5 for clarity.
     */
    default String getFileType() {
        return "json";
    }
}
