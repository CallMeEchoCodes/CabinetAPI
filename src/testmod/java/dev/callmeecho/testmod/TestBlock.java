package dev.callmeecho.testmod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static dev.callmeecho.testmod.TestMod.CONFIG;

public class TestBlock extends Block {
    public TestBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            player.sendMessage(Text.of("Client thinks testString is: " + CONFIG.testString));
        } else {
            player.sendMessage(Text.of("Server thinks testString is: " + CONFIG.testString));
        }
        return ActionResult.SUCCESS;
    }
}
