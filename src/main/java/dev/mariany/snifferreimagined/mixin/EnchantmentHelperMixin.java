package dev.mariany.snifferreimagined.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.mariany.snifferreimagined.component.BaitComponent;
import dev.mariany.snifferreimagined.component.SRComponents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @WrapOperation(
            method = "getFishingTimeReduction",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/commons/lang3/mutable/MutableFloat;floatValue()F"
            )
    )
    private static float getFishingTimeReduction(
            MutableFloat mutableFloat,
            Operation<Float> original,
            @Local(index = 0, argsOnly = true) ServerWorld world,
            @Local(index = 1, argsOnly = true) ItemStack stack,
            @Local(index = 2, argsOnly = true) Entity user
    ) {
        float fishingTime = original.call(mutableFloat);

        BaitComponent baitComponent = stack.get(SRComponents.BAIT);

        if (baitComponent != null) {
            ItemStack baitStack = baitComponent.getStack();
            Integer fishingTimeReduction = baitStack.get(SRComponents.FISHING_SECONDS_REDUCTION);

            if(fishingTimeReduction != null) {
                fishingTime += fishingTimeReduction;
            }
        }

        return fishingTime;
    }
}
