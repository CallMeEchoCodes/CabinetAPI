package dev.callmeecho.cabinetapi.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

/**
 * A simple particle for debugging purposes.
 * Generally intended for checking the location of a vector.
 * Best used with a particle system.
 */
@Environment(EnvType.CLIENT)
public class DebugParticle extends SpriteBillboardParticle {
    protected DebugParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
        this.scale = 0.05F;
    }

    @Override
    public ParticleTextureSheet getType() { return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE; }

    @Override
    public void tick() { this.markDead(); }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;
        
        public Factory(SpriteProvider spriteProvider) { this.spriteProvider = spriteProvider;}

        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            DebugParticle particle = new DebugParticle(world, x, y, z);
            particle.setSprite(spriteProvider);
            return particle;
        }
    }
}
