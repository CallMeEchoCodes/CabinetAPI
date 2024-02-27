package dev.callmeecho.cabinetapi.mixin;

import dev.callmeecho.cabinetapi.RunArgsNetworkInterface;
import net.minecraft.client.RunArgs;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.*;

@Mixin(RunArgs.Network.class)
public class RunArgsNetworkAccessor implements RunArgsNetworkInterface {
    @Mutable
    @Shadow @Final public Session session;
    
    @Override
    public void cabinetAPI$setSession(Session session) {
        this.session = session;
    }
}
