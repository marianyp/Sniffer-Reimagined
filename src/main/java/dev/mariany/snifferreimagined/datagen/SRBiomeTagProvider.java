package dev.mariany.snifferreimagined.datagen;

import dev.mariany.snifferreimagined.tag.SRTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.data.DataOutput;
import net.minecraft.data.tag.SimpleTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class SRBiomeTagProvider extends SimpleTagProvider<Biome> {
    public SRBiomeTagProvider(
            DataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
    ) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.builder(SRTags.Biomes.SPAWNS_CHERRY_VARIANT_SNIFFERS)
            .addOptionalTag(ConventionalBiomeTags.PRIMARY_WOOD_TYPE_CHERRY);

        this.builder(SRTags.Biomes.SPAWNS_CRIMSON_VARIANT_SNIFFERS)
            .addOptionalTag(BiomeTags.IS_NETHER);

        this.builder(SRTags.Biomes.SPAWNS_ICE_VARIANT_SNIFFERS)
            .addOptionalTag(ConventionalBiomeTags.IS_AQUATIC_ICY)
            .addOptionalTag(ConventionalBiomeTags.IS_ICY)
            .addOptionalTag(ConventionalBiomeTags.IS_SNOWY);

        this.builder(SRTags.Biomes.SPAWNS_WARPED_VARIANT_SNIFFERS)
            .addOptionalTag(ConventionalBiomeTags.PRIMARY_WOOD_TYPE_WARPED);
    }
}