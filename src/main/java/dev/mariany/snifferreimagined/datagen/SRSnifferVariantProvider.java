package dev.mariany.snifferreimagined.datagen;

import dev.mariany.snifferreimagined.registry.SRRegistryKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class SRSnifferVariantProvider extends FabricDynamicRegistryProvider {
    public SRSnifferVariantProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
    ) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getOrThrow(SRRegistryKeys.SNIFFER_VARIANT));
    }

    @Override
    public String getName() {
        return "Sniffer Reimagined Sniffer Variant Provider";
    }
}
