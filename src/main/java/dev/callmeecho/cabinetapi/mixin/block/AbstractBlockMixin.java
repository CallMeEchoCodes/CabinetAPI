package dev.callmeecho.cabinetapi.mixin.block;

import dev.callmeecho.cabinetapi.block.CabinetBlock;
import dev.callmeecho.cabinetapi.block.CabinetBlockInternal;
import dev.callmeecho.cabinetapi.item.CabinetItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin implements CabinetBlock {
    @Unique
    @Nullable
    private Block strippedBlock;

    @Unique
    @Nullable
    private CabinetItemGroup group;

    @Unique
    private int spread = 5;

    @Unique
    private int burn = 5;

    @Unique
    private boolean flammable;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(AbstractBlock.Settings settings, CallbackInfo ci) {
        CabinetBlockInternal.Data data = CabinetBlockInternal.computeIfAbsent(settings);
        if (data == null) return;

        strippedBlock = data.getStrippedBlock();
        group = data.getGroup();
        spread = data.getSpread();
        burn = data.getBurn();
        flammable = data.isFlammable();
    }

    @Override
    public @Nullable Block cabinetapi$getStrippedBlock() {
        return strippedBlock;
    }

    @Override
    public @Nullable CabinetItemGroup cabinetapi$getGroup() {
        return group;
    }

    @Override
    public int cabinetapi$getSpread() {
        return spread;
    }

    @Override
    public int cabinetapi$getBurn() {
        return burn;
    }

    @Override
    public boolean cabinetapi$isFlammable() {
        return flammable;
    }
}
