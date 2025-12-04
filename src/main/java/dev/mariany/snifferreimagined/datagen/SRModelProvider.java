package dev.mariany.snifferreimagined.datagen;

import dev.mariany.snifferreimagined.block.SRBlocks;
import dev.mariany.snifferreimagined.item.SRItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;

public class SRModelProvider extends FabricModelProvider {
    public SRModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(SRItems.GOLD_ROOT, Models.GENERATED);
        itemModelGenerator.register(SRItems.IRON_ROOT, Models.GENERATED);
        itemModelGenerator.register(SRItems.WORM, Models.GENERATED);
        itemModelGenerator.register(SRItems.SKELETON_SKULL_FRAGMENT, Models.GENERATED);
        itemModelGenerator.register(SRItems.WITHER_SKELETON_SKULL_FRAGMENT, Models.GENERATED);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        this.registerFarmland(blockStateModelGenerator, SRBlocks.ENRICHED_FARMLAND);
    }

    private void registerFarmland(BlockStateModelGenerator blockStateModelGenerator, Block farmland) {
        TextureMap textureMap = new TextureMap().put(TextureKey.DIRT, TextureMap.getId(farmland))
                                                .put(TextureKey.TOP, TextureMap.getSubId(farmland, "_top"));

        TextureMap moistTextureMap = new TextureMap()
                .put(TextureKey.DIRT, TextureMap.getId(farmland))
                .put(TextureKey.TOP, TextureMap.getSubId(farmland, "_moist_top"));

        WeightedVariant weightedVariant =
                BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_FARMLAND.upload(
                        farmland,
                        textureMap,
                        blockStateModelGenerator.modelCollector
                ));

        WeightedVariant moistWeightedVariant = BlockStateModelGenerator.createWeightedVariant(
                Models.TEMPLATE_FARMLAND.upload(
                        TextureMap.getSubId(farmland, "_moist"),
                        moistTextureMap,
                        blockStateModelGenerator.modelCollector
                )
        );

        blockStateModelGenerator.blockStateCollector
                .accept(
                        VariantsBlockModelDefinitionCreator.of(farmland)
                                                           .with(
                                                                   BlockStateModelGenerator.createValueFencedModelMap(
                                                                           Properties.MOISTURE,
                                                                           7,
                                                                           moistWeightedVariant,
                                                                           weightedVariant
                                                                   )
                                                           )
                );
    }
}
