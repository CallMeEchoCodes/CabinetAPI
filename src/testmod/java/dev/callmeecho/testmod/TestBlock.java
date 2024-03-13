package dev.callmeecho.testmod;

import dev.callmeecho.cabinetapi.client.particle.CabinetParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class TestBlock extends Block {
    public TestBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Vec3d center = pos.toCenterPos();
//        world.addParticle(CabinetParticleTypes.DEBUG, center.getX(), center.getY() + 1, center.getZ(), 0, 0, 0);
    }
}
