package dev.callmeecho.cabinetapi.mixin;

import dev.callmeecho.cabinetapi.misc.ItemExtensions;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class ItemMixin implements ItemExtensions {
    @Unique
    public Item.Settings settings;
    
    @Inject(method = "<init>", at = @At("TAIL"))
    public void Item(Item.Settings settings, CallbackInfo ci) {
        this.settings = settings;
    }

    @Override
    public Item.Settings cabinetAPI$getSettings() {
        return settings;
    }
}
