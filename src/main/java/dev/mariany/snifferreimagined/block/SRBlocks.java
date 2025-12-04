package dev.mariany.snifferreimagined.block;

import dev.mariany.snifferreimagined.SnifferReimagined;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Function;

public class SRBlocks {
    public static final Block ENRICHED_FARMLAND = register(
            "enriched_farmland",
            EnrichedFarmlandBlock::new,
            AbstractBlock.Settings.create()
                                  .mapColor(MapColor.DIRT_BROWN)
                                  .ticksRandomly()
                                  .strength(0.6F)
                                  .sounds(BlockSoundGroup.GRAVEL)
                                  .blockVision(Blocks::always)
                                  .suffocates(Blocks::always)
    );

    public static Block register(
            String name,
            Function<AbstractBlock.Settings, Block> factory,
            AbstractBlock.Settings settings
    ) {
        final RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, SnifferReimagined.id(name));
        final Block block = Blocks.register(registryKey, factory, settings);
        Items.register(block);

        return block;
    }

    public static void bootstrap() {
        SnifferReimagined.LOGGER.info("Registering Blocks for " + SnifferReimagined.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Items.FARMLAND, ENRICHED_FARMLAND);
        });
    }
}
