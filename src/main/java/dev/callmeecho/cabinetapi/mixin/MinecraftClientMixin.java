package dev.callmeecho.cabinetapi.mixin;

import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import dev.callmeecho.cabinetapi.CabinetAPI;
import dev.callmeecho.cabinetapi.util.RunArgsNetworkExtensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.util.Session;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "createUserApiService", at = @At("HEAD"))
    private void createUserApiService(YggdrasilAuthenticationService authService, RunArgs runArgs, CallbackInfoReturnable<UserApiService> cir) {
        if (!CabinetAPI.DEBUG) return;

        String username = System.getProperty("cabinetapi.development.username");

        //#if MC>=12002
        //$$ UUID uuid = UUID.fromString(System.getProperty("cabinetapi.development.uuid"));
        //#else
        String uuid = System.getProperty("cabinetapi.development.uuid");
        //#endif
        
        if (username == null || uuid == null) {
            LOGGER.info("Development account not set, skipping...");
            return;
        }

        LOGGER.info(String.format("Using development account %s (%s)", username, uuid));

        Session session = new Session(
                username,
                uuid,
                runArgs.network.session.getAccessToken(),
                runArgs.network.session.getXuid(),
                runArgs.network.session.getClientId(),
                runArgs.network.session.getAccountType()
        );
        
        ((RunArgsNetworkExtensions) runArgs.network).cabinetAPI$setSession(session);
    }
}
