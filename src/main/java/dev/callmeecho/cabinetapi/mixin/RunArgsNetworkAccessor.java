package dev.callmeecho.cabinetapi.mixin;

import dev.callmeecho.cabinetapi.util.RunArgsNetworkExtensions;
import net.minecraft.client.RunArgs;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.*;

@Mixin(RunArgs.Network.class)
public class RunArgsNetworkAccessor implements RunArgsNetworkExtensions {
    @Mutable
    @Shadow @Final public Session session;
    
    @Override
    public void cabinetAPI$setSession(Session session) {
        this.session = session;
    }
}
