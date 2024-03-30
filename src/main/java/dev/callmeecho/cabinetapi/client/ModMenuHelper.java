package dev.callmeecho.cabinetapi.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import dev.callmeecho.cabinetapi.client.gui.ConfigScreen;
import dev.callmeecho.cabinetapi.config.Config;
import dev.callmeecho.cabinetapi.config.ConfigHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ModMenuHelper {
    private static final HashMap<String, ConfigScreenFactory<?>> screens = new HashMap<>();

    public static void addConfig(String modid, Class<? extends Config> configClass) {
        if (!FabricLoader.getInstance().isModLoaded("modmenu")) return;
        screens.put(modid, (parent) -> new ConfigScreen(ConfigHandler.getConfig(configClass), parent));
    }

    @ApiStatus.Internal
    public static HashMap<String, ConfigScreenFactory<?>> getConfigScreens() {
        return screens;
    }
}
