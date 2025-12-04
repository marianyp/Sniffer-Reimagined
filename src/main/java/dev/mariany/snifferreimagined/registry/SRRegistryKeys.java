package dev.mariany.snifferreimagined.registry;

import dev.mariany.snifferreimagined.entity.passive.SnifferVariant;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class SRRegistryKeys {
    public static final RegistryKey<Registry<SnifferVariant>> SNIFFER_VARIANT = of("sniffer_variant");

    private static <T> RegistryKey<Registry<T>> of(String id) {
        return RegistryKey.ofRegistry(Identifier.ofVanilla(id));
    }
}
