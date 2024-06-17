package dev.callmeecho.cabinetapi.block;

import dev.callmeecho.cabinetapi.item.CabinetItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import org.jetbrains.annotations.Nullable;

public interface CabinetBlock {
    @Nullable
    Block cabinetapi$getStrippedBlock();

    @Nullable
    CabinetItemGroup cabinetapi$getGroup();

    int cabinetapi$getSpread();
    int cabinetapi$getBurn();
    boolean cabinetapi$isFlammable();

    interface Settings {
        default AbstractBlock.Settings strippedBlock(Block strippedBlock) {
            CabinetBlockInternal.computeIfAbsent((AbstractBlock.Settings)this).strippedBlock(strippedBlock);
            return (AbstractBlock.Settings)this;
        }

        default AbstractBlock.Settings flammable() {
            CabinetBlockInternal.computeIfAbsent((AbstractBlock.Settings)this).flammable();
            return (AbstractBlock.Settings)this;
        }

        default AbstractBlock.Settings spread(int spread) {
            CabinetBlockInternal.computeIfAbsent((AbstractBlock.Settings)this).spread(spread);
            return (AbstractBlock.Settings)this;
        }

        default AbstractBlock.Settings burn(int burn) {
            CabinetBlockInternal.computeIfAbsent((AbstractBlock.Settings)this).burn(burn);
            return (AbstractBlock.Settings)this;
        }

        default AbstractBlock.Settings group(CabinetItemGroup group) {
            CabinetBlockInternal.computeIfAbsent((AbstractBlock.Settings)this).group(group);
            return (AbstractBlock.Settings)this;
        }
    }
}
