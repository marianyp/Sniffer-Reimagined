package dev.mariany.snifferreimagined.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.SnifferEggBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SnifferEggBlock.class)
public class SnifferEggBlockMixin {
    @WrapOperation(
            method = "scheduledTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            )
    )
    public boolean wrapScheduledTick(ServerWorld world, Entity entity, Operation<Boolean> original) {
        if (entity instanceof SnifferEntity snifferEntity) {
            BlockPos pos = snifferEntity.getBlockPos();
            LocalDifficulty localDifficulty = world.getLocalDifficulty(pos);
            snifferEntity.initialize(world, localDifficulty, SpawnReason.BREEDING, null);
        }

        return original.call(world, entity);
    }
}
