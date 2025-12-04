package dev.mariany.snifferreimagined.registry;

import dev.mariany.snifferreimagined.SnifferReimagined;
import dev.mariany.snifferreimagined.entity.passive.SnifferVariant;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;

public class SRDynamicRegistries {
    public static void register() {
        SnifferReimagined.LOGGER.info("Registering Dynamic Registries for " + SnifferReimagined.MOD_ID);

        DynamicRegistries.registerSynced(
                SRRegistryKeys.SNIFFER_VARIANT,
                SnifferVariant.CODEC,
                SnifferVariant.NETWORK_CODEC,
                DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY
        );
    }
}
