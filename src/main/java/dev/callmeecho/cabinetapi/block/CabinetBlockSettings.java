package dev.callmeecho.cabinetapi.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class CabinetBlockSettings extends FabricBlockSettings {
    @Nullable
    private Block strippedBlock;
    
    private ToolType toolType = ToolType.NONE;

    public CabinetBlockSettings(AbstractBlock.Settings settings) {
        super(settings);
        if (settings instanceof CabinetBlockSettings cabinetBlockSettings) {
            strippedBlock = cabinetBlockSettings.strippedBlock;
            toolType = cabinetBlockSettings.toolType;
        }
    }

    protected CabinetBlockSettings() { super(); }
    public static CabinetBlockSettings create() {
        return new CabinetBlockSettings();
    }

    public static CabinetBlockSettings copyOf(AbstractBlock.Settings settings) { return new CabinetBlockSettings(settings); }
    
    @Nullable
    public Block getStrippedBlock() { return strippedBlock; }
    
    public CabinetBlockSettings strippedBlock(Block strippedBlock) {
        this.strippedBlock = strippedBlock;
        return this;
    }

    @Override
    public CabinetBlockSettings noCollision() {
        super.noCollision();
        return this;
    }

    @Override
    public CabinetBlockSettings nonOpaque() {
        super.nonOpaque();
        return this;
    }

    @Override
    public CabinetBlockSettings slipperiness(float value) {
        super.slipperiness(value);
        return this;
    }

    @Override
    public CabinetBlockSettings velocityMultiplier(float velocityMultiplier) {
        super.velocityMultiplier(velocityMultiplier);
        return this;
    }

    @Override
    public CabinetBlockSettings jumpVelocityMultiplier(float jumpVelocityMultiplier) {
        super.jumpVelocityMultiplier(jumpVelocityMultiplier);
        return this;
    }

    @Override
    public CabinetBlockSettings sounds(BlockSoundGroup group) {
        super.sounds(group);
        return this;
    }

    @Override
    public CabinetBlockSettings luminance(ToIntFunction<BlockState> luminanceFunction) {
        super.luminance(luminanceFunction);
        return this;
    }

    @Override
    public CabinetBlockSettings strength(float hardness, float resistance) {
        super.strength(hardness, resistance);
        return this;
    }

    @Override
    public CabinetBlockSettings breakInstantly() {
        super.breakInstantly();
        return this;
    }

    public CabinetBlockSettings strength(float strength) {
        super.strength(strength);
        return this;
    }

    @Override
    public CabinetBlockSettings ticksRandomly() {
        super.ticksRandomly();
        return this;
    }

    @Override
    public CabinetBlockSettings dynamicBounds() {
        super.dynamicBounds();
        return this;
    }

    @Override
    public CabinetBlockSettings dropsNothing() {
        super.dropsNothing();
        return this;
    }

    @Override
    public CabinetBlockSettings dropsLike(Block block) {
        super.dropsLike(block);
        return this;
    }

    @Override
    public CabinetBlockSettings air() {
        super.air();
        return this;
    }

    @Override
    public CabinetBlockSettings allowsSpawning(AbstractBlock.TypedContextPredicate<EntityType<?>> predicate) {
        super.allowsSpawning(predicate);
        return this;
    }

    @Override
    public CabinetBlockSettings solidBlock(AbstractBlock.ContextPredicate predicate) {
        super.solidBlock(predicate);
        return this;
    }

    @Override
    public CabinetBlockSettings suffocates(AbstractBlock.ContextPredicate predicate) {
        super.suffocates(predicate);
        return this;
    }

    @Override
    public CabinetBlockSettings blockVision(AbstractBlock.ContextPredicate predicate) {
        super.blockVision(predicate);
        return this;
    }

    @Override
    public CabinetBlockSettings postProcess(AbstractBlock.ContextPredicate predicate) {
        super.postProcess(predicate);
        return this;
    }

    @Override
    public CabinetBlockSettings emissiveLighting(AbstractBlock.ContextPredicate predicate) {
        super.emissiveLighting(predicate);
        return this;
    }
    
    @Override
    public CabinetBlockSettings requiresTool() {
        super.requiresTool();
        return this;
    }

    @Override
    public CabinetBlockSettings mapColor(MapColor color) {
        super.mapColor(color);
        return this;
    }

    @Override
    public CabinetBlockSettings hardness(float hardness) {
        super.hardness(hardness);
        return this;
    }

    @Override
    public CabinetBlockSettings resistance(float resistance) {
        super.resistance(resistance);
        return this;
    }

    @Override
    public CabinetBlockSettings offset(AbstractBlock.OffsetType offsetType) {
        super.offset(offsetType);
        return this;
    }

    @Override
    public CabinetBlockSettings noBlockBreakParticles() {
        super.noBlockBreakParticles();
        return this;
    }

    @Override
    public CabinetBlockSettings requires(FeatureFlag... features) {
        super.requires(features);
        return this;
    }

    @Override
    public CabinetBlockSettings mapColor(Function<BlockState, MapColor> mapColorProvider) {
        super.mapColor(mapColorProvider);
        return this;
    }

    @Override
    public CabinetBlockSettings burnable() {
        super.burnable();
        return this;
    }

    @Override
    public CabinetBlockSettings liquid() {
        super.liquid();
        return this;
    }

    @Override
    public CabinetBlockSettings solid() {
        super.solid();
        return this;
    }

    @Override
    public CabinetBlockSettings notSolid() {
        super.notSolid();
        return this;
    }

    @Override
    public CabinetBlockSettings pistonBehavior(PistonBehavior pistonBehavior) {
        super.pistonBehavior(pistonBehavior);
        return this;
    }

    @Override
    public CabinetBlockSettings instrument(Instrument instrument) {
        super.instrument(instrument);
        return this;
    }

    @Override
    public CabinetBlockSettings replaceable() {
        super.replaceable();
        return this;
    }

    public CabinetBlockSettings luminance(int luminance) {
        super.luminance(luminance);
        return this;
    }

    public CabinetBlockSettings drops(Identifier dropTableId) {
        super.drops(dropTableId);
        return this;
    }

    public enum ToolType {
        PICKAXE,
        AXE,
        SHOVEL,
        HOE,
        NONE
    }
}
