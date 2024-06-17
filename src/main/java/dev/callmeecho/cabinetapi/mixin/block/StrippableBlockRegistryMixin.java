package dev.callmeecho.cabinetapi.mixin.block;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(StrippableBlockRegistry.class)
public class StrippableBlockRegistryMixin {
    @Redirect(method = "register", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/registry/StrippableBlockRegistry;requireNonNullAndAxisProperty(Lnet/minecraft/block/Block;Ljava/lang/String;)V"))
    private static void requireNonNull(Block block, String name) { Objects.requireNonNull(block, name + " cannot be null"); }
}
