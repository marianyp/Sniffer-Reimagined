package dev.mariany.snifferreimagined.mixin;

import dev.mariany.snifferreimagined.block.SRBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {
    @Inject(method = "isOf(Lnet/minecraft/block/Block;)Z", at = @At(value = "HEAD"), cancellable = true)
    public void isOf(Block block, CallbackInfoReturnable<Boolean> cir) {
        AbstractBlock.AbstractBlockState state = (AbstractBlock.AbstractBlockState) (Object) this;

        if (block == Blocks.FARMLAND && state.getBlock() == SRBlocks.ENRICHED_FARMLAND) {
            cir.setReturnValue(true);
        }
    }
}
