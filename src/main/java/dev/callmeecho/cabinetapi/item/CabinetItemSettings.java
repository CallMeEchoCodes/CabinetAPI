package dev.callmeecho.cabinetapi.item;

import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.Nullable;

public class CabinetItemSettings extends FabricItemSettings {
    @Nullable
    private CabinetItemGroup group = null;
    
    public CabinetItemSettings group(CabinetItemGroup group) {
        this.group = group;
        return this;
    }

    @Nullable
    public CabinetItemGroup getGroup() {
        return group;
    }
    
    @Override
    public CabinetItemSettings equipmentSlot(EquipmentSlotProvider equipmentSlotProvider) {
        super.equipmentSlot(equipmentSlotProvider);
        return this;
    }


    @Override
    public CabinetItemSettings customDamage(CustomDamageHandler handler) {
        super.customDamage(handler);
        return this;
    }
    
    @Override
    public CabinetItemSettings food(FoodComponent foodComponent) {
        super.food(foodComponent);
        return this;
    }

    @Override
    public CabinetItemSettings maxCount(int maxCount) {
        super.maxCount(maxCount);
        return this;
    }

    @Override
    public CabinetItemSettings maxDamageIfAbsent(int maxDamage) {
        super.maxDamageIfAbsent(maxDamage);
        return this;
    }

    @Override
    public CabinetItemSettings maxDamage(int maxDamage) {
        super.maxDamage(maxDamage);
        return this;
    }

    @Override
    public CabinetItemSettings recipeRemainder(Item recipeRemainder) {
        super.recipeRemainder(recipeRemainder);
        return this;
    }

    @Override
    public CabinetItemSettings rarity(Rarity rarity) {
        super.rarity(rarity);
        return this;
    }

    @Override
    public CabinetItemSettings fireproof() {
        super.fireproof();
        return this;
    }

    @Override
    public CabinetItemSettings requires(FeatureFlag... features) {
        super.requires(features);
        return this;
    }
}
