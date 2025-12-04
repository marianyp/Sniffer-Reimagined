package dev.mariany.snifferreimagined.entity.passive;

import dev.mariany.snifferreimagined.SnifferReimagined;
import dev.mariany.snifferreimagined.loot.SRLootTables;
import dev.mariany.snifferreimagined.registry.SRRegistryKeys;
import dev.mariany.snifferreimagined.tag.SRTags;
import net.minecraft.block.Block;
import net.minecraft.entity.spawn.BiomeSpawnCondition;
import net.minecraft.entity.spawn.SpawnConditionSelectors;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.AssetInfo;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface SnifferVariants {
    RegistryKey<SnifferVariant> DEFAULT = of(SnifferReimagined.id("default"));
    RegistryKey<SnifferVariant> COLD = of(SnifferReimagined.id("cold"));
    RegistryKey<SnifferVariant> CHERRY = of(SnifferReimagined.id("cherry"));
    RegistryKey<SnifferVariant> CRIMSON = of(SnifferReimagined.id("crimson"));
    RegistryKey<SnifferVariant> WARPED = of(SnifferReimagined.id("warped"));

    private static RegistryKey<SnifferVariant> of(Identifier id) {
        return RegistryKey.of(SRRegistryKeys.SNIFFER_VARIANT, id);
    }

    static void bootstrap(Registerable<SnifferVariant> registry) {
        register(
                registry,
                DEFAULT,
                Identifier.ofVanilla("entity/sniffer/sniffer"),
                SpawnConditionSelectors.createFallback(0),
                LootTables.SNIFFER_DIGGING_GAMEPLAY,
                BlockTags.SNIFFER_DIGGABLE_BLOCK
        );

        register(
                registry,
                COLD,
                "entity/sniffer/cold_sniffer",
                SRTags.Biomes.SPAWNS_ICE_VARIANT_SNIFFERS,
                LootTables.SNIFFER_DIGGING_GAMEPLAY,
                BlockTags.SNIFFER_DIGGABLE_BLOCK
        );

        register(
                registry,
                CHERRY,
                "entity/sniffer/cherry_sniffer",
                SRTags.Biomes.SPAWNS_CHERRY_VARIANT_SNIFFERS,
                LootTables.SNIFFER_DIGGING_GAMEPLAY,
                BlockTags.SNIFFER_DIGGABLE_BLOCK
        );

        register(
                registry,
                CRIMSON,
                "entity/sniffer/crimson_sniffer",
                SRTags.Biomes.SPAWNS_CRIMSON_VARIANT_SNIFFERS,
                SRLootTables.NETHER_SNIFFER_DIGGING_GAMEPLAY,
                SRTags.Blocks.SNIFFER_NETHER_DIGGABLE_BLOCKS
        );

        register(
                registry,
                WARPED,
                "entity/sniffer/warped_sniffer",
                SRTags.Biomes.SPAWNS_WARPED_VARIANT_SNIFFERS,
                SRLootTables.NETHER_SNIFFER_DIGGING_GAMEPLAY,
                SRTags.Blocks.SNIFFER_NETHER_DIGGABLE_BLOCKS,
                2
        );
    }

    private static void register(
            Registerable<SnifferVariant> registry,
            RegistryKey<SnifferVariant> key,
            String assetId,
            TagKey<Biome> requiredBiomes,
            @Nullable RegistryKey<LootTable> lootTable,
            @Nullable TagKey<Block> diggableBlocks
    ) {
        register(
                registry,
                key,
                assetId,
                requiredBiomes,
                lootTable,
                diggableBlocks,
                1
        );
    }

    private static void register(
            Registerable<SnifferVariant> registry,
            RegistryKey<SnifferVariant> key,
            String assetId,
            TagKey<Biome> requiredBiomes,
            @Nullable RegistryKey<LootTable> lootTable,
            @Nullable TagKey<Block> diggableBlocks,
            int priority
    ) {
        RegistryEntryList<Biome> registryEntryList = registry.getRegistryLookup(RegistryKeys.BIOME)
                                                             .getOrThrow(requiredBiomes);

        register(
                registry,
                key,
                assetId,
                SpawnConditionSelectors.createSingle(new BiomeSpawnCondition(registryEntryList), priority),
                lootTable,
                diggableBlocks
        );
    }

    private static void register(
            Registerable<SnifferVariant> registry,
            RegistryKey<SnifferVariant> key,
            String assetId,
            SpawnConditionSelectors spawnConditions,
            @Nullable RegistryKey<LootTable> lootTable,
            @Nullable TagKey<Block> diggableBlocks
    ) {
        register(registry, key, SnifferReimagined.id(assetId), spawnConditions, lootTable, diggableBlocks);
    }

    private static void register(
            Registerable<SnifferVariant> registry,
            RegistryKey<SnifferVariant> key,
            Identifier assetId,
            SpawnConditionSelectors spawnConditions,
            @Nullable RegistryKey<LootTable> lootTable,
            @Nullable TagKey<Block> diggableBlocks
    ) {
        registry.register(
                key,
                new SnifferVariant(
                        new AssetInfo(assetId),
                        spawnConditions,
                        Optional.ofNullable(lootTable),
                        Optional.ofNullable(diggableBlocks)
                )
        );
    }
}
