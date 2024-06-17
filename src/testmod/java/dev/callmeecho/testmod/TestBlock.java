package dev.callmeecho.testmod;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static dev.callmeecho.testmod.TestMod.CONFIG;

public class TestBlock extends Block {
    public TestBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult  onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, BlockHitResult blockHitResult) {
        if (world.isClient) {
            playerEntity.sendMessage(Text.of("Client thinks testString is: " + CONFIG.testString));
        } else {
            playerEntity.sendMessage(Text.of("Server thinks testString is: " + CONFIG.testString));
        }
        return ActionResult.SUCCESS;
    }
}
