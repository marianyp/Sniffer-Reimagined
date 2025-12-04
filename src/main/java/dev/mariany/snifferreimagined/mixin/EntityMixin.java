package dev.mariany.snifferreimagined.mixin;

import dev.mariany.snifferreimagined.component.SRComponents;
import dev.mariany.snifferreimagined.entity.passive.SnifferVariantState;
import net.minecraft.component.ComponentType;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SnifferEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    @Nullable
    protected static <T> T castComponentValue(
            ComponentType<T> type,
            @Nullable Object value
    ) {
        return null;
    }

    @Shadow
    protected abstract <T> boolean copyComponentFrom(ComponentsAccess from, ComponentType<T> type);

    @Inject(method = "get", at = @At(value = "HEAD"), cancellable = true)
    public <T> void injectGet(ComponentType<? extends T> type, CallbackInfoReturnable<T> cir) {
        Entity entity = (Entity) (Object) this;

        if (entity instanceof SnifferVariantState snifferVariantState && type == SRComponents.SNIFFER_VARIANT) {
            cir.setReturnValue(castComponentValue(type, snifferVariantState.snifferReimagined$getVariant()));
        }
    }

    @Inject(method = "copyComponentsFrom(Lnet/minecraft/component/ComponentsAccess;)V", at = @At(value = "TAIL"))
    protected void injectCopyComponentsFrom(ComponentsAccess from, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;

        if (entity instanceof SnifferEntity) {
            this.copyComponentFrom(from, SRComponents.SNIFFER_VARIANT);
        }
    }

    @Inject(method = "setApplicableComponent", at = @At(value = "HEAD"))
    protected <T> void injectSetApplicableComponent(
            ComponentType<T> type,
            T value,
            CallbackInfoReturnable<Boolean> cir
    ) {
        Entity entity = (Entity) (Object) this;

        if (entity instanceof SnifferVariantState snifferVariantState) {
            if (type == SRComponents.SNIFFER_VARIANT) {
                snifferVariantState.snifferReimagined$setVariant(
                        castComponentValue(SRComponents.SNIFFER_VARIANT, value)
                );
            }
        }
    }
}
