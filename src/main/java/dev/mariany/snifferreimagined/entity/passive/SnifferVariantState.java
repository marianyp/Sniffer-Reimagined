package dev.mariany.snifferreimagined.entity.passive;

import net.minecraft.registry.entry.RegistryEntry;

public interface SnifferVariantState {
    RegistryEntry<SnifferVariant> snifferReimagined$getVariant();

    void snifferReimagined$setVariant(RegistryEntry<SnifferVariant> variant);
}
