package dev.callmeecho.testmod;

import dev.callmeecho.cabinetapi.registry.BlockRegistrar;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

@SuppressWarnings("unused")
public class TestModBlockRegistrar implements BlockRegistrar {
    public static final Block TEST_BLOCK_STRIPPED = new Block(AbstractBlock.Settings.create());
    public static final Block TEST_BLOCK = new TestBlock(AbstractBlock.Settings.create().strippedBlock(Blocks.STRIPPED_ACACIA_LOG).group(TestMod.ITEM_GROUP));

    @NoBlockItem
    public static final Block TEST_BLOCK_NO_BLOCKITEM = new Block(AbstractBlock.Settings.create());
}
