package dev.callmeecho.cabinetapi.mixin;

import com.mojang.authlib.properties.PropertyMap;
import dev.callmeecho.cabinetapi.CabinetAPI;
import net.minecraft.client.RunArgs;

/*? if 1.20.1 {*//*
import net.minecraft.client.util.Session;
*//*?} else {*/
import net.minecraft.client.session.Session;
import java.util.UUID;
/*?} */

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

        String username = System.getProperty("cabinetapi.development.username");
        /*? if 1.20.1 {*//*
        String uuid = System.getProperty("cabinetapi.development.uuid");
        if (uuid == null) {
            CabinetAPI.LOGGER.info("Development account not set, skipping...");
            return;
        }
        *//*?} else {*/
        if (!System.getProperties().containsKey("cabinetapi.development.uuid")) {
            CabinetAPI.LOGGER.info("Development account not set, skipping...");
            return;
        }
        UUID uuid = UUID.fromString(System.getProperty("cabinetapi.development.uuid"));
        /*?} */
        if (username == null) {
            CabinetAPI.LOGGER.info("Development account not set, skipping...");
            return;
        }

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
