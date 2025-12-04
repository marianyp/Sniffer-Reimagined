package dev.mariany.snifferreimagined.registry;

import dev.mariany.snifferreimagined.SnifferReimagined;
import dev.mariany.snifferreimagined.entity.passive.SnifferVariant;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.registry.entry.RegistryEntry;

public class SRTrackedDataHandlerRegistry {
    public static final TrackedDataHandler<RegistryEntry<SnifferVariant>> SNIFFER_VARIANT = TrackedDataHandler.create(
            SnifferVariant.PACKET_CODEC
    );

    public static void register() {
        SnifferReimagined.LOGGER.info("Registering Tracked Data Handlers for " + SnifferReimagined.MOD_ID);
        FabricTrackedDataRegistry.register(SnifferReimagined.id("sniffer_variant"), SNIFFER_VARIANT);
    }
}
