package dev.mariany.snifferreimagined.datagen;

import dev.mariany.snifferreimagined.block.SRBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class SRBlockLootTableProvider extends FabricBlockLootTableProvider {
    public SRBlockLootTableProvider(
            FabricDataOutput dataOutput,
            CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup
    ) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.addDrop(SRBlocks.ENRICHED_FARMLAND, Blocks.DIRT);
    }
}
