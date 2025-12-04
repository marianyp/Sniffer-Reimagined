package dev.mariany.snifferreimagined.datagen;

import dev.mariany.snifferreimagined.item.SRItems;
import dev.mariany.snifferreimagined.loot.SRLootTables;
import dev.mariany.snifferreimagined.tag.SRTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.TagEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class SRGiftLootTableProvider extends SimpleFabricLootTableProvider {
    public SRGiftLootTableProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup
    ) {
        super(output, registryLookup, LootContextTypes.GIFT);
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        lootTableBiConsumer.accept(
                SRLootTables.NETHER_SNIFFER_DIGGING_GAMEPLAY,
                LootTable.builder()
                         .pool(
                                 LootPool.builder()
                                         .rolls(ConstantLootNumberProvider.create(1F))
                                         .with(
                                                 TagEntry.expandBuilder(SRTags.Items.SNIFFER_NETHER_MUSHROOMS)
                                                         .apply(
                                                                 SetCountLootFunction.builder(
                                                                         UniformLootNumberProvider.create(1, 4)
                                                                 )
                                                         )
                                         )
                                         .with(
                                                 ItemEntry.builder(SRItems.GOLD_ROOT).apply(
                                                         SetCountLootFunction.builder(
                                                                 UniformLootNumberProvider.create(1, 6)
                                                         )
                                                 )
                                         )
                                         .with(ItemEntry.builder(SRItems.SKELETON_SKULL_FRAGMENT))
                                         .with(ItemEntry.builder(SRItems.WITHER_SKELETON_SKULL_FRAGMENT))
                                         .with(ItemEntry.builder(Items.NETHER_WART))
                                         .with(ItemEntry.builder(Items.BLAZE_ROD))
                                         .with(ItemEntry.builder(Items.GHAST_TEAR))
                         )
        );
    }

    @Override
    public String getName() {
        return "Sniffer Reimagined Gift Loot Table Provider";
    }
}
