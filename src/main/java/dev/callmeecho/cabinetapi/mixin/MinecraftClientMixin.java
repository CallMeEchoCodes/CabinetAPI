package dev.callmeecho.cabinetapi.mixin;

import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import dev.callmeecho.cabinetapi.misc.RunArgsNetworkExtensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "createUserApiService", at = @At("HEAD"))
    private void createUserApiService(YggdrasilAuthenticationService authService, RunArgs runArgs, CallbackInfoReturnable<UserApiService> cir) {
        if (!Boolean.getBoolean("fabric.development")) return;

        String username = System.getProperty("cabinetapi.development.username");
        String uuid = System.getProperty("cabinetapi.development.uuid");
        
        if (username == null || uuid == null) {
            System.out.println("CabinetAPI: Development account not set, skipping...");
            return;
        }
        System.out.printf("CabinetAPI: Using development account %s (%s)%n", username, uuid);
        Session session = new Session(username, uuid, runArgs.network.session.getAccessToken(), runArgs.network.session.getXuid(), runArgs.network.session.getClientId(), runArgs.network.session.getAccountType());
        ((RunArgsNetworkExtensions) runArgs.network).cabinetAPI$setSession(session);
    }
}
