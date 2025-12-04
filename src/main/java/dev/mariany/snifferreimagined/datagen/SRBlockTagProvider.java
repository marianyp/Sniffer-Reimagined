package dev.mariany.snifferreimagined.datagen;

import dev.mariany.snifferreimagined.block.SRBlocks;
import dev.mariany.snifferreimagined.tag.SRTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class SRBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public SRBlockTagProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
    ) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        valueLookupBuilder(BlockTags.BIG_DRIPLEAF_PLACEABLE).add(SRBlocks.ENRICHED_FARMLAND);
        valueLookupBuilder(BlockTags.SHOVEL_MINEABLE).add(SRBlocks.ENRICHED_FARMLAND);
        valueLookupBuilder(BlockTags.DRY_VEGETATION_MAY_PLACE_ON).add(SRBlocks.ENRICHED_FARMLAND);
        valueLookupBuilder(SRTags.Blocks.SNIFFER_NETHER_DIGGABLE_BLOCKS)
                .addOptionalTag(BlockTags.NYLIUM)
                .add(Blocks.SOUL_SAND, Blocks.SOUL_SOIL);
    }
}
