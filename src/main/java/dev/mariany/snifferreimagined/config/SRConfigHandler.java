package dev.mariany.snifferreimagined.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.mariany.snifferreimagined.SnifferReimagined;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SRConfigHandler {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/" + SnifferReimagined.MOD_ID + ".json5");
    private static SRConfig config = new SRConfig();

    public static SRConfig getConfig() {
        return config;
    }

    public static void loadConfig() {
        SnifferReimagined.LOGGER.info("Loading config for " + SnifferReimagined.MOD_ID);

        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                config = GSON.fromJson(reader, SRConfig.class);
            } catch (IOException error) {
                SnifferReimagined.LOGGER.error("Failed to load config: {}", error.getMessage());
            }
        }

        saveConfig();
    }

    private static void saveConfig() {
        try {
            if (CONFIG_FILE.getParentFile().mkdirs()) {
                SnifferReimagined.LOGGER.info("Creating parent directory for {} config", SnifferReimagined.MOD_ID);
            }

            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException error) {
            SnifferReimagined.LOGGER.error("Failed to save config: {}", error.getMessage());
        }
    }
}
