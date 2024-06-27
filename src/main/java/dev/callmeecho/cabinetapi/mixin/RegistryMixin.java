package dev.callmeecho.cabinetapi.mixin;

import dev.callmeecho.cabinetapi.block.CabinetBlock;
import dev.callmeecho.cabinetapi.item.CabinetItem;
import dev.callmeecho.cabinetapi.item.CabinetItemGroup;
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
        switch (entry) {
            case Block block -> {
                Block strippedBlock = ((CabinetBlock)block).cabinetapi$getStrippedBlock();
                if (strippedBlock != null) StrippableBlockRegistry.register(block, strippedBlock);

                if (((CabinetBlock)block).cabinetapi$isFlammable())
                    FlammableBlockRegistry.getDefaultInstance().add(
                            block,
                            ((CabinetBlock)block).cabinetapi$getBurn(),
                            ((CabinetBlock)block).cabinetapi$getSpread()
                    );
            }

            case Item item -> {
                CabinetItemGroup group = ((CabinetItem)item).cabinetapi$getGroup();
                if (group != null) group.addItem(item);
            }

            default -> {}
        }
    }
}
