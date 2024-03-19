package dev.callmeecho.cabinetapi.config;

import dev.callmeecho.cabinetapi.config.annotations.Comment;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static dev.callmeecho.cabinetapi.CabinetAPI.LOGGER;

public interface Config {
    String getName();
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    default void save() {
        String json = ConfigHandler.GSON.toJson(this);

        ArrayList<String> lines = new ArrayList<>(Arrays.asList(json.split("\n")));
        HashMap<String, String> comments = new HashMap<>();
        TreeMap<Integer, String> insertions = new TreeMap<>();
        ArrayList<String> newLines = new ArrayList<>(lines);

        for (Field field : this.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(Comment.class)) continue;

            Comment annotation = field.getAnnotation(Comment.class);
            comments.put(field.getName(), annotation.value());
        }

        List<Class<?>> nestedClasses = new ArrayList<>();
        for (Class<?> clazz : this.getClass().getDeclaredClasses()) {
            ConfigHandler.getNestedClasses(nestedClasses, clazz);
        }

        for (Class<?> clazz : nestedClasses) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Comment.class)) continue;

                Comment annotation = field.getAnnotation(Comment.class);
                comments.put(field.getName(), annotation.value());
            }
        }

        for (String line : lines) {
            if (line.trim().startsWith("\"")) {
                String key = line.split(":")[0].replace("\"", "").trim();
                String comment = comments.get(key);
                String whitespaces = line.substring(0, line.indexOf("\""));

                if (comment.contains("\n"))
                    comment = whitespaces + "// " + String.join("\n" + whitespaces + "// ", comment.split("\n"));
                else
                    comment = whitespaces + "// " + comment;


                int index = lines.indexOf(line);
                insertions.put(index, comment);
            }
        }

        for (int index : insertions.descendingKeySet()) {
            newLines.add(index, insertions.get(index));
        }

        Path path = this.getPath();
        path.toFile().getParentFile().mkdirs();

        try {
            Files.write(path, newLines);
        } catch (IOException e) {
            LOGGER.error("Failed to save config file: " + path, e);
        }
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
