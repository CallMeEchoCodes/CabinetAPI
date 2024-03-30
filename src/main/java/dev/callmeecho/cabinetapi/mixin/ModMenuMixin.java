package dev.callmeecho.cabinetapi.mixin;

import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import dev.callmeecho.cabinetapi.client.ModMenuHelper;
import dev.callmeecho.cabinetapi.util.Condition;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ModMenu.class)
@Condition("modmenu")
@Environment(EnvType.CLIENT)
public class ModMenuMixin {
    @Shadow private static Map<String, ConfigScreenFactory<?>> configScreenFactories;

    @Inject(method = "getConfigScreen" , at = @At("HEAD"), remap = false)
    private static void getConfigScreen(String modid, Screen menuScreen, CallbackInfoReturnable<Screen> cir) {
        if (configScreenFactories.containsKey(modid)) return;
        ModMenuHelper.getConfigScreens().forEach(configScreenFactories::putIfAbsent);
    }
}
