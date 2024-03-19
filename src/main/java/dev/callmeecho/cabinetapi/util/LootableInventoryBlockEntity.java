package dev.callmeecho.cabinetapi.util;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

/**
 * Helper class for block entities that have a loot table.
 * You could use the {@link net.minecraft.block.entity.LootableContainerBlockEntity} class, but it comes with a menu,
 * and you might not want that.
 */
public class LootableInventoryBlockEntity extends InventoryBlockEntity {
    public static final String LOOT_TABLE_KEY = "LootTable";
    public static final String LOOT_TABLE_SEED_KEY = "LootTableSeed";

    public LootableInventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state, size);
    }

    @Nullable
    protected Identifier lootTableId;
    protected long lootTableSeed;

    public void checkLootInteraction(@Nullable PlayerEntity player, boolean randomSeed) {
        if (world == null) return;
        if (this.lootTableId != null && this.world.getServer() != null) {
            LootTable lootTable = this.world.getServer().getLootManager().getLootTable(this.lootTableId);
            if (player instanceof ServerPlayerEntity) {
                Criteria.PLAYER_GENERATES_CONTAINER_LOOT.trigger((ServerPlayerEntity)player, this.lootTableId);
            }

            this.lootTableId = null;
            LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld)this.world)
                    .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(this.pos));
            if (player != null) {
                builder.luck(player.getLuck()).add(LootContextParameters.THIS_ENTITY, player);
            }

            lootTable.supplyInventory(
                    this,
                    builder.build(LootContextTypes.CHEST),
                    randomSeed ? world.getRandom().nextLong() : this.lootTableSeed
            );
        }
    }

    protected boolean deserializeLootTable(NbtCompound nbt) {
        if (!nbt.contains(LOOT_TABLE_KEY, NbtElement.STRING_TYPE)) return false;

        this.lootTableId = new Identifier(nbt.getString(LOOT_TABLE_KEY));
        this.lootTableSeed = nbt.getLong(LOOT_TABLE_SEED_KEY);
        return true;
    }

    protected boolean serializeLootTable(NbtCompound nbt) {
        if (this.lootTableId == null) return false;

        nbt.putString(LOOT_TABLE_KEY, this.lootTableId.toString());
        if (lootTableSeed != 0) nbt.putLong(LOOT_TABLE_SEED_KEY, this.lootTableSeed);
        return true;
    }

    public void setLootTable(Identifier id, long seed) {
        this.lootTableId = id;
        this.lootTableSeed = seed;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        inventory.clear();
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory);
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory);
        }
    }
}
