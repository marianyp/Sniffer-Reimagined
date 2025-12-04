package dev.mariany.snifferreimagined.tag;

import dev.mariany.snifferreimagined.SnifferReimagined;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public class SRTags {
    public static final class Biomes {
        public static final TagKey<Biome> SPAWNS_ICE_VARIANT_SNIFFERS = createTag("spawns_ice_variant_sniffers");
        public static final TagKey<Biome> SPAWNS_CHERRY_VARIANT_SNIFFERS = createTag(
                "spawns_cherry_variant_sniffers"
        );
        public static final TagKey<Biome> SPAWNS_CRIMSON_VARIANT_SNIFFERS = createTag(
                "spawns_crimson_variant_sniffers"
        );
        public static final TagKey<Biome> SPAWNS_WARPED_VARIANT_SNIFFERS = createTag(
                "spawns_warped_variant_sniffers"
        );

        private static TagKey<Biome> createTag(String name) {
            return TagKey.of(RegistryKeys.BIOME, SnifferReimagined.id(name));
        }
    }

    public static final class Blocks {
        public static final TagKey<Block> SNIFFER_NETHER_DIGGABLE_BLOCKS = createTag(
                "sniffer_nether_diggable_blocks"
        );

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, SnifferReimagined.id(name));
        }
    }

    public static final class Items {
        public static final TagKey<Item> SNIFFER_MUSHROOMS = createTag("sniffer_mushrooms");
        public static final TagKey<Item> SNIFFER_NETHER_MUSHROOMS = createTag("sniffer_nether_mushrooms");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, SnifferReimagined.id(name));
        }
    }
}
