package dev.mariany.snifferreimagined.item;

import dev.mariany.snifferreimagined.SnifferReimagined;
import dev.mariany.snifferreimagined.component.SRComponents;
import dev.mariany.snifferreimagined.component.SRConsumableComponents;
import dev.mariany.snifferreimagined.component.SRFoodComponents;
import dev.mariany.snifferreimagined.config.SRConfigHandler;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public class SRItems {
    public static final Item GOLD_ROOT = register(
            "gold_root"
    );

    public static final Item IRON_ROOT = register(
            "iron_root"
    );

    public static final Item SKELETON_SKULL_FRAGMENT = register(
            "skeleton_skull_fragment"
    );

    public static final Item WITHER_SKELETON_SKULL_FRAGMENT = register(
            "wither_skeleton_skull_fragment"
    );

    public static final Item WORM = register(
            "worm",
            WormItem::new,
            new Item.Settings()
                    .component(
                            SRComponents.FISHING_SECONDS_REDUCTION,
                            SRConfigHandler.getConfig().wormReducedFishingSeconds
                    )
                    .food(SRFoodComponents.WORM, SRConsumableComponents.WORM)
    );

    private static Item register(String name) {
        return register(name, Item::new);
    }

    private static Item register(String name, Function<Item.Settings, Item> factory) {
        return register(name, factory, new Item.Settings());
    }

    private static Item register(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, SnifferReimagined.id(name));
        return Items.register(itemKey, factory, settings);
    }

    public static void bootstrap() {
        SnifferReimagined.LOGGER.info("Registering Items for " + SnifferReimagined.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addBefore(Items.BONE, SKELETON_SKULL_FRAGMENT);
            entries.addAfter(SKELETON_SKULL_FRAGMENT, WITHER_SKELETON_SKULL_FRAGMENT);

            entries.addAfter(Items.SPIDER_EYE, WORM);

            entries.addBefore(Items.RAW_IRON, IRON_ROOT);
            entries.addAfter(IRON_ROOT, GOLD_ROOT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(Items.SPIDER_EYE, WORM);
        });
    }
}
