package dev.callmeecho.testmod;

import dev.callmeecho.cabinetapi.client.ModMenuHelper;
import net.fabricmc.api.ClientModInitializer;

public class TestModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModMenuHelper.addConfig("testmod", TestConfig.class);
    }
}
