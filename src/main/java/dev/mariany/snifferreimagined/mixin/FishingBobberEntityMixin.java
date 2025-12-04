package dev.mariany.snifferreimagined.mixin;

import dev.mariany.snifferreimagined.component.BaitComponent;
import dev.mariany.snifferreimagined.component.SRComponents;
import dev.mariany.snifferreimagined.entity.BaitState;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin implements BaitState {
    @Shadow
    @Final
    private static TrackedData<Boolean> CAUGHT_FISH;

    @Shadow
    private boolean caughtFish;

    @Shadow
    private int waitCountdown;

    @Unique
    private ItemStack bait = ItemStack.EMPTY;

    @Override
    public void snifferReimagined$setBait(ItemStack bait) {
        this.bait = bait;
    }

    @Inject(method = "writeCustomData", at = @At(value = "TAIL"))
    protected void injectWriteCustomData(WriteView view, CallbackInfo ci) {
        view.put(BaitState.KEY, ItemStack.CODEC, this.bait);
    }

    @Inject(method = "readCustomData", at = @At(value = "TAIL"))
    protected void injectReadCustomData(ReadView view, CallbackInfo ci) {
        this.snifferReimagined$setBait(view.read(BaitState.KEY, ItemStack.CODEC).orElse(ItemStack.EMPTY));
    }

    @Inject(method = "onTrackedDataSet", at = @At(value = "HEAD"))
    public void onTrackedDataSet(TrackedData<?> data, CallbackInfo ci) {
        FishingBobberEntity fishingBobber = (FishingBobberEntity) (Object) this;

        if (CAUGHT_FISH.equals(data)) {
            if (!this.caughtFish && fishingBobber.getDataTracker().get(CAUGHT_FISH)) {
                this.bait = ItemStack.EMPTY;
            }
        }
    }

    @Inject(
            method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/FishingBobberEntity;discard()V")
    )
    public void use(ItemStack usedItem, CallbackInfoReturnable<Integer> cir) {
        if(!this.bait.isEmpty() && !usedItem.contains(SRComponents.BAIT)) {
            usedItem.set(SRComponents.BAIT, new BaitComponent(this.bait));
        }
    }

    @Inject(method = "tickFishingLogic", at = @At(value = "TAIL"))
    private void tickFishingLogic(BlockPos pos, CallbackInfo ci) {
        if (this.waitCountdown < 0) {
            this.waitCountdown = 1;
        }
    }
}
