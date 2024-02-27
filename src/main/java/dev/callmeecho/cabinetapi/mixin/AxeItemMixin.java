package dev.callmeecho.cabinetapi.mixin;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.block.enums.StairShape;
import net.minecraft.item.AxeItem;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;

// Un-hardcode stripping so it works on more block types.
@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Shadow @Final protected static Map<Block, Block> STRIPPED_BLOCKS;

    @Inject(method = "getStrippedState", at = @At("HEAD"), cancellable = true)
    private void getStrippedState(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
            if (state.getBlock() instanceof PillarBlock) return;
            if (state.getBlock() instanceof StairsBlock) {
                cir.setReturnValue(Optional.ofNullable(STRIPPED_BLOCKS.get(state.getBlock())).map((block) -> {
                    Direction facing = state.get(StairsBlock.FACING);
                    BlockHalf half = state.get(StairsBlock.HALF);
                    StairShape shape = state.get(StairsBlock.SHAPE);
                    
                    Boolean waterlogged = state.get(StairsBlock.WATERLOGGED);
                    return block.getDefaultState().with(StairsBlock.FACING, facing).with(StairsBlock.HALF, half).with(StairsBlock.WATERLOGGED, waterlogged).with(StairsBlock.SHAPE, shape);
                }));
                
                return;
            }
            
            if (state.getBlock() instanceof SlabBlock) {
                cir.setReturnValue(Optional.ofNullable(STRIPPED_BLOCKS.get(state.getBlock())).map((block) -> {
                    SlabType type = state.get(SlabBlock.TYPE);
                    Boolean waterlogged = state.get(SlabBlock.WATERLOGGED);
                    return block.getDefaultState().with(SlabBlock.TYPE, type).with(SlabBlock.WATERLOGGED, waterlogged);
                }));
                
                return;
            }
            
            // There are probably more blocks that need to be handled here.
            // I just added the ones I use in BingusExtras.
            // I was going to have it carry over all the blockstate,
            // but then I realised that would mean you couldn't convert between blocks with different blockstates.
            // If you need to maintain blockstate on another block, please make a PR!
            
            cir.setReturnValue(Optional.ofNullable(STRIPPED_BLOCKS.get(state.getBlock())).map(Block::getDefaultState));
    }
}
