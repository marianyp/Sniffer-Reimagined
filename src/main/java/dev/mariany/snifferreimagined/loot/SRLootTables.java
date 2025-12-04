package dev.mariany.snifferreimagined.loot;

import dev.mariany.snifferreimagined.SnifferReimagined;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class SRLootTables {
    public static final RegistryKey<LootTable> NETHER_SNIFFER_DIGGING_GAMEPLAY = register("gameplay/sniffer_digging/nether");

    private static RegistryKey<LootTable> register(String id) {
        return RegistryKey.of(RegistryKeys.LOOT_TABLE, SnifferReimagined.id(id));
    }

    public static void bootstrap() {
        SnifferReimagined.LOGGER.info("Registering Loot Tables for " + SnifferReimagined.MOD_ID);
    }
}
