package dev.callmeecho.cabinetapi.mixin.block;

import dev.callmeecho.cabinetapi.block.CabinetBlock;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractBlock.Settings.class)
public class AbstractBlockSettingsMixin implements CabinetBlock.Settings {
}
