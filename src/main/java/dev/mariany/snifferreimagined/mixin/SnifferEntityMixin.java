package dev.mariany.snifferreimagined.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.mariany.snifferreimagined.entity.passive.SnifferVariant;
import dev.mariany.snifferreimagined.entity.passive.SnifferVariantState;
import dev.mariany.snifferreimagined.entity.passive.SnifferVariants;
import dev.mariany.snifferreimagined.registry.SRTrackedDataHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.Variants;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(SnifferEntity.class)
public abstract class SnifferEntityMixin extends Entity implements SnifferVariantState {
    @Unique
    private static final TrackedData<RegistryEntry<SnifferVariant>> VARIANT = DataTracker.registerData(
            SnifferEntityMixin.class,
            SRTrackedDataHandlerRegistry.SNIFFER_VARIANT
    );

    protected SnifferEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public RegistryEntry<SnifferVariant> snifferReimagined$getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    @Override
    public void snifferReimagined$setVariant(RegistryEntry<SnifferVariant> variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Inject(method = "initDataTracker", at = @At(value = "TAIL"))
    protected void injectInitDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(VARIANT, Variants.getOrDefaultOrThrow(this.getRegistryManager(), SnifferVariants.DEFAULT));
    }

    @WrapOperation(
            method = "createChild",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/EntityType;create(Lnet/minecraft/world/World;Lnet/minecraft/entity/SpawnReason;)Lnet/minecraft/entity/Entity;"
            )
    )
    @Nullable
    public <T extends Entity> T wrapCreateChild(
            EntityType<T> entityType,
            World world,
            SpawnReason reason,
            Operation<T> original,
            @Local(index = 2, argsOnly = true) PassiveEntity parent
    ) {
        T child = original.call(entityType, world, reason);

        if (parent instanceof SnifferVariantState parentVariantState) {
            if (child instanceof SnifferVariantState childVariantState) {
                childVariantState.snifferReimagined$setVariant(parentVariantState.snifferReimagined$getVariant());
            }
        }

        return child;
    }

    @WrapOperation(
            method = "dropSeeds",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/passive/SnifferEntity;forEachGiftedItem(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/registry/RegistryKey;Ljava/util/function/BiConsumer;)Z"
            )
    )
    private boolean injectDropSeeds(
            SnifferEntity snifferEntity,
            ServerWorld serverWorld,
            RegistryKey<?> registryKey,
            BiConsumer<?, ?> biConsumer,
            Operation<Boolean> original
    ) {
        RegistryKey<?> lootTable = registryKey;

        if (snifferEntity instanceof SnifferVariantState snifferVariantState) {
            lootTable = snifferVariantState.snifferReimagined$getVariant()
                                           .value()
                                           .lootTable()
                                           .orElse(LootTables.SNIFFER_DIGGING_GAMEPLAY);
        }

        return original.call(snifferEntity, serverWorld, lootTable, biConsumer);
    }

    @WrapOperation(
            method = "isDiggable",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean wrapIsDiggable(BlockState blockState, TagKey<Block> tagKey, Operation<Boolean> original) {
        SnifferEntity snifferEntity = (SnifferEntity) (Object) this;

        if (snifferEntity instanceof SnifferVariantState snifferVariantState) {
            Optional<TagKey<Block>> optionalDiggableBlocks = snifferVariantState
                    .snifferReimagined$getVariant()
                    .value()
                    .diggableBlocks();

            return original.call(blockState, optionalDiggableBlocks.orElse(BlockTags.SNIFFER_DIGGABLE_BLOCK));
        }

        return original.call(blockState, tagKey);
    }

    @WrapOperation(
            method = "isDiggable",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/stream/Stream;noneMatch(Ljava/util/function/Predicate;)Z"
            )
    )
    private boolean wrapIsDiggableExploredLocations(
            Stream<?> instance,
            Predicate<?> predicate,
            Operation<Boolean> original
    ) {
        return true;
    }
}
