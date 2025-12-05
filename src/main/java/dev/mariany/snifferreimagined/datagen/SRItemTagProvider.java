package dev.mariany.snifferreimagined.datagen;

import dev.mariany.snifferreimagined.item.SRItems;
import dev.mariany.snifferreimagined.tag.SRTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class SRItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public SRItemTagProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
    ) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        valueLookupBuilder(ItemTags.PIGLIN_LOVED).add(SRItems.GOLD_ROOT);
        valueLookupBuilder(ItemTags.SNIFFER_FOOD).add(SRItems.WORM);
        valueLookupBuilder(ItemTags.CHICKEN_FOOD).add(SRItems.WORM);
        valueLookupBuilder(ItemTags.FROG_FOOD).add(SRItems.WORM);
        valueLookupBuilder(ItemTags.AXOLOTL_FOOD).add(SRItems.WORM);
        valueLookupBuilder(ItemTags.TURTLE_FOOD).add(SRItems.WORM);
        valueLookupBuilder(SRTags.Items.SNIFFER_MUSHROOMS).add(Items.RED_MUSHROOM).add(Items.BROWN_MUSHROOM);
        valueLookupBuilder(SRTags.Items.SNIFFER_NETHER_MUSHROOMS).add(Items.CRIMSON_FUNGUS).add(Items.WARPED_FUNGUS);
    }
}
