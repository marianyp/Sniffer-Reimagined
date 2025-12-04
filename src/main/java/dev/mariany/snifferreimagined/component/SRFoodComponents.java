package dev.mariany.snifferreimagined.component;

import net.minecraft.component.type.FoodComponent;

public class SRFoodComponents {
    public static final FoodComponent WORM = new FoodComponent.Builder()
            .nutrition(2)
            .saturationModifier(0.8F)
            .build();
}
