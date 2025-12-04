package dev.mariany.snifferreimagined;

import dev.mariany.snifferreimagined.datagen.*;
import dev.mariany.snifferreimagined.entity.passive.SnifferVariants;
import dev.mariany.snifferreimagined.registry.SRRegistryKeys;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;

public class SnifferReimaginedDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(SRBiomeTagProvider::new);
		pack.addProvider(SRBlockLootTableProvider::new);
		pack.addProvider(SRBlockTagProvider::new);
		pack.addProvider(SRItemTagProvider::new);
		pack.addProvider(SRModelProvider::new);
		pack.addProvider(SRRecipeProvider::new);
		pack.addProvider(SRSnifferVariantProvider::new);
		pack.addProvider(SRGiftLootTableProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(SRRegistryKeys.SNIFFER_VARIANT, SnifferVariants::bootstrap);
	}
}
