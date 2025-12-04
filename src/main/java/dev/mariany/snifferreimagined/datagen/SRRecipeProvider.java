package dev.mariany.snifferreimagined.datagen;

import dev.mariany.snifferreimagined.item.SRItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class SRRecipeProvider extends FabricRecipeProvider {
    public SRRecipeProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
    ) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(
            RegistryWrapper.WrapperLookup wrapperLookup,
            RecipeExporter recipeExporter
    ) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                this.offerSmeltingAndBlasting(SRItems.IRON_ROOT, Items.IRON_INGOT);
                this.offerSmeltingAndBlasting(SRItems.GOLD_ROOT, Items.GOLD_INGOT);

                this.offerCompactingRecipe(
                        RecipeCategory.MISC,
                        Items.SKELETON_SKULL,
                        SRItems.SKELETON_SKULL_FRAGMENT
                );

                this.offerCompactingRecipe(
                        RecipeCategory.MISC,
                        Items.WITHER_SKELETON_SKULL,
                        SRItems.WITHER_SKELETON_SKULL_FRAGMENT
                );
            }

            private void offerSmeltingAndBlasting(ItemConvertible input, ItemConvertible output) {
                Item outputItem = output.asItem();

                RegistryEntry.Reference<Item> outputReference = outputItem.getRegistryEntry();
                Optional<RegistryKey<Item>> optionalReferenceKey = outputReference.getKey();

                if (optionalReferenceKey.isPresent()) {
                    Identifier outputId = optionalReferenceKey.get().getValue();
                    String group = outputId.getPath();

                    this.offerSmelting(
                            List.of(input),
                            RecipeCategory.MISC,
                            output,
                            0.1F,
                            200,
                            group
                    );

                    this.offerBlasting(
                            List.of(input),
                            RecipeCategory.MISC,
                            output,
                            0.1F,
                            100,
                            group
                    );
                }
            }
        };
    }

    @Override
    public String getName() {
        return "Sniffer Reimagined Recipe Provider";
    }
}
