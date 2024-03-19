package dev.callmeecho.cabinetapi.devtools.item;

import dev.callmeecho.cabinetapi.util.LootableInventoryBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.loot.LootTable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class LootLoader extends Item {
    public LootLoader(Settings settings) {
        super(settings);
    }

    private static final MutableText NO_NAME = Text.translatable("item.cabinetapi.loot_loader.no_name");

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        if (player == null || world.isClient) return ActionResult.PASS;

        BlockPos pos = context.getBlockPos();
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity == null) return ActionResult.FAIL;
        if (!(blockEntity instanceof LootableInventoryBlockEntity) && !(blockEntity instanceof LootableContainerBlockEntity))
            return ActionResult.FAIL;

        ItemStack stack = player.getStackInHand(context.getHand());
        if (!stack.hasCustomName()) {
            player.sendMessage(NO_NAME, true);
            return ActionResult.FAIL;
        }

        String[] name = stack.getName().getString().split(":");
        Identifier lootTable = new Identifier(name[0], name[1]);

        MinecraftServer server = world.getServer();
        if (server == null) return ActionResult.FAIL;
        if (server.getLootManager().getLootTable(lootTable) == LootTable.EMPTY) {
            player.sendMessage(Text.translatable("item.cabinetapi.loot_loader.invalid", lootTable.toString()), true);
            return ActionResult.FAIL;
        }

        if (blockEntity instanceof LootableInventoryBlockEntity)
            ((LootableInventoryBlockEntity) blockEntity).setLootTable(lootTable, world.getRandom().nextLong());
        else
            ((LootableContainerBlockEntity) blockEntity).setLootTable(lootTable, world.getRandom().nextLong());

        player.sendMessage(Text.translatable("item.cabinetapi.loot_loader.success", lootTable.toString()), true);


        return ActionResult.SUCCESS;
    }
}
