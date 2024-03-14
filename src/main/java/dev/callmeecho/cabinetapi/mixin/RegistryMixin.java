package dev.callmeecho.cabinetapi.mixin;

import dev.callmeecho.cabinetapi.block.CabinetBlockSettings;
import dev.callmeecho.cabinetapi.item.CabinetItemGroup;
import dev.callmeecho.cabinetapi.item.CabinetItemSettings;
import dev.callmeecho.cabinetapi.misc.ItemExtensions;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Registry.class)
public interface RegistryMixin {
    @Inject(method = "register(Lnet/minecraft/registry/Registry;Lnet/minecraft/registry/RegistryKey;Ljava/lang/Object;)Ljava/lang/Object;", at = @At("RETURN"))
    private static <V, T extends V> void register(Registry<V> registry, RegistryKey<V> key, T entry, CallbackInfoReturnable<T> cir) {
        if (entry instanceof Block block) {
            if (!(block.settings instanceof CabinetBlockSettings settings)) return;

            if (settings.getStrippedBlock() != null)
                StrippableBlockRegistry.register(block, settings.getStrippedBlock());
            if (settings.isFlammable())
                FlammableBlockRegistry.getDefaultInstance().add(block, settings.getBurn(), settings.getSpread());
        }
        
        if (entry instanceof Item item) {
            if (!(((ItemExtensions) item).cabinetAPI$getSettings() instanceof CabinetItemSettings settings)) return;

            CabinetItemGroup group = settings.getGroup();
            if (group != null) group.addItem(item);
        }
    }
}
