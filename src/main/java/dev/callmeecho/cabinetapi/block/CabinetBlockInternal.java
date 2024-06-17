package dev.callmeecho.cabinetapi.block;

import dev.callmeecho.cabinetapi.item.CabinetItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.WeakHashMap;

public class CabinetBlockInternal {
    private static final WeakHashMap<AbstractBlock.Settings, CabinetBlockInternal.Data> DATA = new WeakHashMap<>();

    public static CabinetBlockInternal.Data computeIfAbsent(AbstractBlock.Settings settings) { return DATA.computeIfAbsent(settings, s -> new CabinetBlockInternal.Data()); }

    public static CabinetBlockInternal.Data get(AbstractBlock.Settings settings) { return DATA.get(settings); }

    public static final class Data {
        @Nullable
        private CabinetItemGroup group;

        @Nullable
        private Block strippedBlock;

        private boolean flammable;

        private int burn = 5;
        private int spread = 5;

        public void group(CabinetItemGroup group) { this.group = group; }
        public @Nullable CabinetItemGroup getGroup() { return group; }

        public void strippedBlock(Block strippedBlock) { this.strippedBlock = strippedBlock; }
        public @Nullable Block getStrippedBlock() { return strippedBlock; }

        public void flammable() { this.flammable = true; }
        public boolean isFlammable() { return flammable; }

        public void burn(int burn) { this.burn = burn; }
        public int getBurn() { return burn; }

        public void spread(int spread) { this.spread = spread; }
        public int getSpread() { return spread; }
    }
}
