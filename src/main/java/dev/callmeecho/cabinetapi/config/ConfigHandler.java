package dev.callmeecho.cabinetapi.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import dev.callmeecho.cabinetapi.config.annotations.Comment;
import dev.callmeecho.cabinetapi.misc.ReflectionHelper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static dev.callmeecho.cabinetapi.CabinetAPI.LOGGER;


public class ConfigHandler {
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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

    public static <T extends Config> void saveConfig(Class<T> clazz) {
        T config = ReflectionHelper.instantiate(clazz);
        String json = ConfigHandler.GSON.toJson(config);

        ArrayList<String> lines = new ArrayList<>(Arrays.asList(json.split("\n")));
        HashMap<String, String> comments = new HashMap<>();
        ArrayList<String> newLines = new ArrayList<>(lines);

        for (Field field : config.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(Comment.class)) continue;

            Comment annotation = field.getAnnotation(Comment.class);
            comments.put(field.getName(), annotation.value());
        }


        for (String line : lines) {
            if (line.stripLeading().startsWith("//")) continue;
            if (line.contains(":")) {
                String key = line.split(":")[0].replace("\"", "").trim();
                String comment = comments.get(key);

                if (comment != null) {
                    newLines.add(lines.indexOf(line), "// " + comment);
                }
            }
        }

        Path path = config.getPath();
        if (!path.toFile().getParentFile().mkdirs())
            LOGGER.error("Failed to create config directory: " + path.toFile().getParentFile());


        try {
            Files.write(path, newLines);
        } catch (IOException e) {
            LOGGER.error("Failed to save config file: " + path, e);
        }
    }
}
