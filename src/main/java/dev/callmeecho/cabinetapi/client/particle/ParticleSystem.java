package dev.callmeecho.cabinetapi.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class ParticleSystem {
    public Vec3d velocity;
    public Vec3d offset;
    public Vec3d positionVariance;
    public int particleCount;
    public int spawnRate;
    public boolean randomizeVelocity;
    
    private int age;
    
    private final ParticleEffect type;
    private final Random random;
    
    public ParticleSystem(Vec3d velocity, Vec3d offset, Vec3d positionVariance, int particleCount, int spawnRate, boolean randomizeVelocity, ParticleEffect type) {
        this.velocity = velocity;
        this.offset = offset;
        this.positionVariance = positionVariance;
        this.particleCount = particleCount;
        this.spawnRate = spawnRate;
        this.randomizeVelocity = randomizeVelocity;
        this.type = type;
        this.random = Random.create();
    }
    
    public void tick(World world, BlockPos pos) {
        age++;
        
        if (age == spawnRate) {
            for (int i = 0; i < particleCount; i++) {
                float xRand = (2.0F * random.nextFloat() - 1F);
                float yRand = (2.0F * random.nextFloat() - 1F);
                float zRand = (2.0F * random.nextFloat() - 1F);
                double x = (pos.getX() + offset.getX()) + (xRand * positionVariance.getX());
                double y = (pos.getY() + offset.getY()) + (yRand * positionVariance.getY());
                double z = (pos.getZ() + offset.getZ()) + (zRand * positionVariance.getZ());
                if (randomizeVelocity) {
                    world.addParticle(type, x, y, z, xRand * velocity.x, yRand * velocity.y, zRand * velocity.z);
                } else {
                    world.addParticle(type, x, y, z, velocity.x, velocity.y, velocity.z);
                }
            }
            age = 0;
        }
    }
}
