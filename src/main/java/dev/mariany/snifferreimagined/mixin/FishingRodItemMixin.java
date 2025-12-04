package dev.mariany.snifferreimagined.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.mariany.snifferreimagined.component.BaitComponent;
import dev.mariany.snifferreimagined.component.SRComponents;
import dev.mariany.snifferreimagined.entity.BaitState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FishingRodItem.class)
public class FishingRodItemMixin {
    @WrapOperation(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/ProjectileEntity;spawn(Lnet/minecraft/entity/projectile/ProjectileEntity;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/projectile/ProjectileEntity;"
            )
    )
    private static <T extends ProjectileEntity> T use(
            T projectile,
            ServerWorld world,
            ItemStack projectileStack,
            Operation<T> original
    ) {
        BaitComponent baitComponent = projectileStack.get(SRComponents.BAIT);

        if (baitComponent != null && projectile instanceof BaitState baitState) {
            baitState.snifferReimagined$setBait(baitComponent.getStack());
            projectileStack.remove(SRComponents.BAIT);
        }

        return original.call(projectile, world, projectileStack);
    }
}
