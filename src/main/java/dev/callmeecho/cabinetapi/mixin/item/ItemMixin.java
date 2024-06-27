package dev.callmeecho.cabinetapi.mixin.item;

import dev.callmeecho.cabinetapi.item.CabinetItem;
import dev.callmeecho.cabinetapi.item.CabinetItemGroup;
import dev.callmeecho.cabinetapi.item.CabinetItemInternal;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class ItemMixin implements CabinetItem {
    @Unique
    @Nullable
    private CabinetItemGroup group;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(Item.Settings settings, CallbackInfo ci) {
        CabinetItemInternal.Data data = CabinetItemInternal.computeIfAbsent(settings);
        if (data == null) return;

        group = data.getGroup();
    }

    @Override
    public @Nullable CabinetItemGroup cabinetapi$getGroup() { return group; }
}
