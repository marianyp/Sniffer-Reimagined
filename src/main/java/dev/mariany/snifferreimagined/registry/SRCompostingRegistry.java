package dev.mariany.snifferreimagined.registry;

import dev.mariany.snifferreimagined.SnifferReimagined;
import dev.mariany.snifferreimagined.item.SRItems;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;

public class SRCompostingRegistry {
    public static void register() {
        SnifferReimagined.LOGGER.info("Registering Composting Chances for " + SnifferReimagined.MOD_ID);
        CompostingChanceRegistry.INSTANCE.add(SRItems.WORM, 1F);
    }
}
