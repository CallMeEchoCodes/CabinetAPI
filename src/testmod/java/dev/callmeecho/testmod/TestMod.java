package dev.callmeecho.testmod;

import dev.callmeecho.cabinetapi.config.ConfigHandler;
import dev.callmeecho.cabinetapi.item.CabinetItemGroup;
import dev.callmeecho.cabinetapi.registry.RegistrarHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMod implements ModInitializer {
    public final static TestConfig CONFIG = ConfigHandler.getConfig(TestConfig.class);
    public static final Logger LOGGER = LoggerFactory.getLogger("testmod");
    
    public static final CabinetItemGroup ITEM_GROUP = new CabinetItemGroup(Identifier.of("testmod", "item_group"), TestModBlockRegistrar.TEST_BLOCK);
    
    @Override
    public void onInitialize() {
        RegistrarHandler.process(TestModItemRegistrar.class, "testmod");
        RegistrarHandler.process(TestModBlockRegistrar.class, "testmod");
        StrippableBlockRegistry.register(Blocks.PURPUR_STAIRS, Blocks.QUARTZ_STAIRS);
        
        ITEM_GROUP.initialize();
    }
}
