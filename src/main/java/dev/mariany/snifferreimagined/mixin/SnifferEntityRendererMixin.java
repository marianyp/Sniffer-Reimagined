package dev.mariany.snifferreimagined.mixin;

import dev.mariany.snifferreimagined.client.render.entity.state.SnifferVariantRenderState;
import dev.mariany.snifferreimagined.entity.passive.SnifferVariantState;
import net.minecraft.client.render.entity.SnifferEntityRenderer;
import net.minecraft.client.render.entity.state.SnifferEntityRenderState;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnifferEntityRenderer.class)
public class SnifferEntityRendererMixin {
    @Inject(
            method = "getTexture(Lnet/minecraft/client/render/entity/state/SnifferEntityRenderState;)Lnet/minecraft/util/Identifier;",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void getTexture(
            SnifferEntityRenderState snifferEntityRenderState,
            CallbackInfoReturnable<Identifier> cir
    ) {
        if (snifferEntityRenderState instanceof SnifferVariantRenderState snifferVariantRenderState) {
            cir.setReturnValue(snifferVariantRenderState.snifferReimagined$getTexture());
        }
    }

    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/passive/SnifferEntity;Lnet/minecraft/client/render/entity/state/SnifferEntityRenderState;F)V",
            at = @At(value = "TAIL")
    )
    public void updateRenderState(
            SnifferEntity snifferEntity,
            SnifferEntityRenderState snifferEntityRenderState,
            float f,
            CallbackInfo ci
    ) {
        if (snifferEntity instanceof SnifferVariantState snifferVariantState) {
            if (snifferEntityRenderState instanceof SnifferVariantRenderState snifferVariantRenderState) {
                snifferVariantRenderState.snifferReimagined$setTexture(
                        snifferVariantState.snifferReimagined$getVariant().value().assetInfo().texturePath()
                );
            }
        }
    }
}
