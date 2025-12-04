package dev.mariany.snifferreimagined.component;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

public class SRConsumableComponents {
    public static final ConsumableComponent WORM = ConsumableComponents
            .food()
            .consumeEffect(
                    new ApplyEffectsConsumeEffect(
                            new StatusEffectInstance(
                                    StatusEffects.HUNGER,
                                    100,
                                    0
                            )
                    )
            )
            .build();
}
