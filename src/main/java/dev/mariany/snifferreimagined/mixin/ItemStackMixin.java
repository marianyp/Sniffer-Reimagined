package dev.mariany.snifferreimagined.mixin;

import dev.mariany.snifferreimagined.component.BaitComponent;
import dev.mariany.snifferreimagined.component.SRComponents;
import dev.mariany.snifferreimagined.sound.SRSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "onStackClicked", at = @At(value = "HEAD"), cancellable = true)
    public void onStackClicked(
            Slot slot,
            ClickType clickType,
            PlayerEntity player,
            CallbackInfoReturnable<Boolean> cir
    ) {
        ItemStack baitStack = (ItemStack) (Object) this;
        ItemStack clickedStack = slot.getStack();

        if (clickedStack.getItem() instanceof FishingRodItem && !clickedStack.contains(SRComponents.BAIT)) {
            if (clickType.equals(ClickType.LEFT)) {
                if (baitStack.contains(SRComponents.FISHING_SECONDS_REDUCTION)) {
                    ItemStack appliedBait = baitStack.copyWithCount(1);

                    baitStack.decrement(1);

                    clickedStack.set(SRComponents.BAIT, new BaitComponent(appliedBait));

                    player.currentScreenHandler.onContentChanged(player.getInventory());

                    player.playSound(SRSoundEvents.ITEM_BAIT_EQUIP, 1F, 1);

                    cir.setReturnValue(true);
                }
            }
        }
    }
}
