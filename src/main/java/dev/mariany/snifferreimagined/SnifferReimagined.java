package dev.mariany.snifferreimagined;

import dev.mariany.snifferreimagined.block.SRBlocks;
import dev.mariany.snifferreimagined.component.SRComponents;
import dev.mariany.snifferreimagined.config.SRConfigHandler;
import dev.mariany.snifferreimagined.item.SRItems;
import dev.mariany.snifferreimagined.loot.SRLootTableModifiers;
import dev.mariany.snifferreimagined.loot.SRLootTables;
import dev.mariany.snifferreimagined.registry.SRCompostingRegistry;
import dev.mariany.snifferreimagined.registry.SRDynamicRegistries;
import dev.mariany.snifferreimagined.registry.SRTrackedDataHandlerRegistry;
import dev.mariany.snifferreimagined.sound.SRSoundEvents;
import dev.mariany.snifferreimagined.village.SRTradeOffers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnifferReimagined implements ModInitializer {
    public static final String MOD_ID = "snifferreimagined";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String resource) {
        return Identifier.of(MOD_ID, resource);
    }

    @Override
    public void onInitialize() {
        SRConfigHandler.loadConfig();
        SRDynamicRegistries.register();
        SRSoundEvents.bootstrap();
        SRComponents.bootstrap();
        SRItems.bootstrap();
        SRBlocks.bootstrap();
        SRLootTables.bootstrap();
        SRLootTableModifiers.modifyLootTables();
        SRTradeOffers.register();
        SRCompostingRegistry.register();
        SRTrackedDataHandlerRegistry.register();
    }
}