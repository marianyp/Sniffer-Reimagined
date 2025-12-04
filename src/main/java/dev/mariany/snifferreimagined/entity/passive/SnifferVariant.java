package dev.mariany.snifferreimagined.entity.passive;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.mariany.snifferreimagined.registry.SRRegistryKeys;
import net.minecraft.block.Block;
import net.minecraft.entity.VariantSelectorProvider;
import net.minecraft.entity.spawn.SpawnCondition;
import net.minecraft.entity.spawn.SpawnConditionSelectors;
import net.minecraft.entity.spawn.SpawnContext;
import net.minecraft.loot.LootTable;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.AssetInfo;

import java.util.List;
import java.util.Optional;

public record SnifferVariant(
        AssetInfo assetInfo,
        SpawnConditionSelectors spawnConditions,
        Optional<RegistryKey<LootTable>> lootTable,
        Optional<TagKey<Block>> diggableBlocks
) implements
        VariantSelectorProvider<SpawnContext, SpawnCondition> {
    public static final Codec<SnifferVariant> CODEC = RecordCodecBuilder.create(
            instance -> instance
                    .group(
                            AssetInfo.MAP_CODEC.forGetter(SnifferVariant::assetInfo),
                            SpawnConditionSelectors.CODEC.fieldOf("spawn_conditions")
                                                         .forGetter(SnifferVariant::spawnConditions),
                            LootTable.TABLE_KEY
                                    .optionalFieldOf("loot_table")
                                    .forGetter(SnifferVariant::lootTable),
                            TagKey.codec(RegistryKeys.BLOCK)
                                  .optionalFieldOf("diggable_blocks")
                                  .forGetter(SnifferVariant::diggableBlocks)
                    )
                    .apply(instance, SnifferVariant::new)
    );

    public static final Codec<SnifferVariant> NETWORK_CODEC = RecordCodecBuilder.create(
            instance -> instance
                    .group(AssetInfo.MAP_CODEC.forGetter(SnifferVariant::assetInfo))
                    .apply(instance, SnifferVariant::new)
    );

    public static final Codec<RegistryEntry<SnifferVariant>> ENTRY_CODEC =
            RegistryFixedCodec.of(SRRegistryKeys.SNIFFER_VARIANT);

    public static final PacketCodec<RegistryByteBuf, RegistryEntry<SnifferVariant>> PACKET_CODEC =
            PacketCodecs.registryEntry(SRRegistryKeys.SNIFFER_VARIANT);

    private SnifferVariant(AssetInfo assetInfo) {
        this(assetInfo, SpawnConditionSelectors.EMPTY, Optional.empty(), Optional.empty());
    }

    @Override
    public List<Selector<SpawnContext, SpawnCondition>> getSelectors() {
        return this.spawnConditions.selectors();
    }
}
