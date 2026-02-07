package dev.mariany.snifferreimagined.loot;

import dev.mariany.snifferreimagined.SnifferReimagined;
import dev.mariany.snifferreimagined.item.SRItems;
import dev.mariany.snifferreimagined.tag.SRTags;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.TagEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class SRLootTableModifiers {
    public static void modifyLootTables() {
        SnifferReimagined.LOGGER.info("Modifying loot tables");

        LootTableEvents.MODIFY.register(
                (
                        key,
                        tableBuilder,
                        source,
                        registries
                ) -> {
                    if (!source.isBuiltin()) {
                        return;
                    }

                    if (key.equals(LootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY)) {
                        tableBuilder.modifyPools(
                                builder -> builder.with(ItemEntry.builder(Items.SNIFFER_EGG))
                        );
                    }

                    if (key.equals(LootTables.SNIFFER_DIGGING_GAMEPLAY)) {
                        tableBuilder.modifyPools(
                                builder -> builder
                                        .with(
                                                ItemEntry.builder(SRItems.IRON_ROOT)
                                                         .apply(
                                                                 SetCountLootFunction.builder(
                                                                         UniformLootNumberProvider.create(1, 3)
                                                                 )
                                                         )
                                        )
                                        .with(
                                                ItemEntry.builder(SRItems.WORM)
                                                         .apply(
                                                                 SetCountLootFunction.builder(
                                                                         UniformLootNumberProvider.create(1, 6)
                                                                 )
                                                         )
                                        )
                                        .with(
                                                TagEntry.expandBuilder(SRTags.Items.SNIFFER_MUSHROOMS)
                                                        .apply(
                                                                SetCountLootFunction.builder(
                                                                        UniformLootNumberProvider.create(1, 4)
                                                                )
                                                        )
                                        )
                        );
                    }
                }
        );
    }
}
