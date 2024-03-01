package dev.callmeecho.testmod;

import dev.callmeecho.cabinetapi.block.CabinetBlockSettings;
import dev.callmeecho.cabinetapi.registry.BlockRegistrar;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

@SuppressWarnings("unused")
public class TestModBlockRegistrar implements BlockRegistrar {
    public static final Block TEST_BLOCK_STRIPPED = new Block(FabricBlockSettings.create());
    public static final Block TEST_BLOCK = new Block(CabinetBlockSettings.create().strippedBlock(Blocks.STRIPPED_ACACIA_LOG).group(TestMod.ITEM_GROUP));


    @NoBlockItem
    public static final Block TEST_BLOCK_NO_BLOCKITEM = new Block(FabricBlockSettings.create());
}
