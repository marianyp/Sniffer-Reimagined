package dev.mariany.snifferreimagined.sound;

import dev.mariany.snifferreimagined.SnifferReimagined;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SRSoundEvents {
    public static final SoundEvent ITEM_WORM_USE = register("item.worm.use");
    public static final SoundEvent ITEM_BAIT_EQUIP = register("item.bait.equip");

    private static SoundEvent register(String id) {
        return register(SnifferReimagined.id(id));
    }

    private static SoundEvent register(Identifier id) {
        return register(id, id);
    }

    private static SoundEvent register(Identifier id, Identifier soundId) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
    }

    public static void bootstrap() {
        SnifferReimagined.LOGGER.info("Registering Sounds for " + SnifferReimagined.MOD_ID);
    }
}
