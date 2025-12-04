package dev.mariany.snifferreimagined.component;

import dev.mariany.snifferreimagined.SnifferReimagined;
import dev.mariany.snifferreimagined.entity.passive.SnifferVariant;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class SRComponents {
    public static final ComponentType<BaitComponent> BAIT = register(
            "bait", builder -> builder
                    .codec(BaitComponent.CODEC)
                    .packetCodec(BaitComponent.PACKET_CODEC)
                    .cache()
    );

    public static final ComponentType<Integer> FISHING_SECONDS_REDUCTION = register(
            "fishing_seconds_reduction", builder -> builder
                    .codec(Codecs.NON_NEGATIVE_INT)
                    .packetCodec(PacketCodecs.VAR_INT)
    );

    public static final ComponentType<RegistryEntry<SnifferVariant>> SNIFFER_VARIANT = register(
            "sniffer/variant", builder -> builder
                    .codec(SnifferVariant.ENTRY_CODEC)
                    .packetCodec(SnifferVariant.PACKET_CODEC)
    );

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                SnifferReimagined.id(id),
                builderOperator.apply(ComponentType.builder()).build()
        );
    }

    public static void bootstrap() {
        SnifferReimagined.LOGGER.info("Registering Components for " + SnifferReimagined.MOD_ID);

        ComponentTooltipAppenderRegistry.addAfter(DataComponentTypes.ENCHANTMENTS, BAIT);
    }
}
