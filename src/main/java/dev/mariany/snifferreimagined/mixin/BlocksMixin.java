package dev.mariany.snifferreimagined.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.mariany.snifferreimagined.config.SRConfigHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchflowerBlock;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public class BlocksMixin {
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/AbstractBlock$Settings;create()Lnet/minecraft/block/AbstractBlock$Settings;",
                    ordinal = 0
            ),
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=torchflower"))
    )
    private static AbstractBlock.Settings modifyTorchflower(AbstractBlock.Settings properties) {
        return properties.luminance(blockState -> SRConfigHandler.getConfig().torchFlowerBrightness);
    }

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Blocks;createFlowerPotSettings()Lnet/minecraft/block/AbstractBlock$Settings;",
                    ordinal = 1
            )
    )
    private static AbstractBlock.Settings modifyPottedTorchflower(AbstractBlock.Settings properties) {
        return properties.luminance(blockState -> SRConfigHandler.getConfig().torchFlowerBrightness);
    }

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/AbstractBlock$Settings;create()Lnet/minecraft/block/AbstractBlock$Settings;",
                    ordinal = 0
            ),
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=torchflower_crop"))
    )
    private static AbstractBlock.Settings modifyTorchflowerCrop(AbstractBlock.Settings properties) {
        int torchFlowerBrightness = SRConfigHandler.getConfig().torchFlowerBrightness;

        return properties.luminance(blockState -> switch (blockState.get(TorchflowerBlock.AGE)) {
            case 0 -> 0;
            case 1 -> MathHelper.floor((float) torchFlowerBrightness / 2);
            default -> torchFlowerBrightness;
        });
    }
}
