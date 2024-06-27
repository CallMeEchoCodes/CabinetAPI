package dev.callmeecho.cabinetapi.mixin.devtools;

import com.mojang.authlib.properties.PropertyMap;
import dev.callmeecho.cabinetapi.CabinetAPI;
import dev.callmeecho.cabinetapi.util.DevEnvironmentCondition;
import net.minecraft.client.RunArgs;

import net.minecraft.client.session.Session;
import java.util.UUID;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;

@Mixin(RunArgs.Network.class)
public class RunArgsNetworkMixin {
    @Mutable
    @Shadow @Final public Session session;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Session session, PropertyMap userProperties, PropertyMap profileProperties, Proxy proxy, CallbackInfo ci) {
        if (!CabinetAPI.DEBUG) return;

        if (!System.getProperties().containsKey("cabinetapi.development.username") || !System.getProperties().containsKey("cabinetapi.development.uuid")) {
            CabinetAPI.LOGGER.info("Development account not set, skipping...");
            return;
        }

        String username = System.getProperty("cabinetapi.development.username");
        String uuidString = System.getProperty("cabinetapi.development.uuid").replace("-", "");

        UUID uuid = UUID.fromString(uuidString.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));

        CabinetAPI.LOGGER.info(String.format("Using development account %s (%s)", username, uuid));
        this.session = new Session(
                username,
                uuid,
                session.getAccessToken(),
                session.getXuid(),
                session.getClientId(),
                session.getAccountType()
        );
    }
}
