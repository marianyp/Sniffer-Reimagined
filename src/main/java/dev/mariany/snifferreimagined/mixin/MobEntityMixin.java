package dev.mariany.snifferreimagined.mixin;

import dev.mariany.snifferreimagined.entity.passive.SnifferVariantState;
import dev.mariany.snifferreimagined.registry.SRRegistryKeys;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.Variants;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.spawn.SpawnContext;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    @Inject(method = "initialize", at = @At(value = "HEAD"))
    public void injectInitialize(
            ServerWorldAccess world,
            LocalDifficulty difficulty,
            SpawnReason spawnReason,
            EntityData entityData,
            CallbackInfoReturnable<EntityData> cir
    ) {
        MobEntity mobEntity = (MobEntity) (Object) this;

        if (mobEntity instanceof SnifferVariantState snifferVariantState) {
            Variants.select(SpawnContext.of(world, mobEntity.getBlockPos()), SRRegistryKeys.SNIFFER_VARIANT)
                    .ifPresent(snifferVariantState::snifferReimagined$setVariant);
        }
    }

    @Inject(method = "writeCustomData", at = @At(value = "TAIL"))
    protected void injectWriteCustomData(WriteView view, CallbackInfo ci) {
        MobEntity mobEntity = (MobEntity) (Object) this;

        if (mobEntity instanceof SnifferVariantState snifferVariantState) {
            Variants.writeData(view, snifferVariantState.snifferReimagined$getVariant());
        }
    }

    @Inject(method = "readCustomData", at = @At(value = "TAIL"))
    protected void injectReadCustomData(ReadView view, CallbackInfo ci) {
        MobEntity mobEntity = (MobEntity) (Object) this;

        if (mobEntity instanceof SnifferVariantState snifferVariantState) {
            Variants.fromData(view, SRRegistryKeys.SNIFFER_VARIANT)
                    .ifPresent(snifferVariantState::snifferReimagined$setVariant);
        }
    }
}
