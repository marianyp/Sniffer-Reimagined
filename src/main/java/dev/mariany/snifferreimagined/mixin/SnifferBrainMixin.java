package dev.mariany.snifferreimagined.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.mariany.snifferreimagined.config.SRConfigHandler;
import dev.mariany.snifferreimagined.config.SRConfig;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.SnifferBrain;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SnifferBrain.DiggingTask.class)
public class SnifferBrainMixin {
    @WrapOperation(
            method = "finishRunning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/SnifferEntity;J)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ai/brain/Brain;remember(Lnet/minecraft/entity/ai/brain/MemoryModuleType;Ljava/lang/Object;J)V"
            )
    )
    protected <U> void finishRunning(
            Brain<SnifferEntity> brain,
            MemoryModuleType<U> type,
            U value,
            long expiry,
            Operation<Void> original,
            @Local(index = 1, argsOnly = true) ServerWorld world
    ) {
        SRConfig config = SRConfigHandler.getConfig();

        int minimumCooldownTicks = config.snifferMinimumCooldownSeconds * 20;
        int maximumCooldownTicks = config.snifferMaximumCooldownSeconds * 20;

        long cooldown;

        if (minimumCooldownTicks > 0 || maximumCooldownTicks > 0) {
            cooldown = MathHelper.nextBetween(
                    world.getRandom(),
                    Math.max(0, minimumCooldownTicks),
                    Math.max(minimumCooldownTicks, Math.max(0, maximumCooldownTicks))
            );
        } else {
            cooldown = expiry;
        }

        original.call(brain, type, value, cooldown);
    }
}
