package me.gentworm.storymobs.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.gentworm.storymobs.StoryMobs;
import net.minecraftforge.fml.loading.FMLPaths;

public abstract class ConfigBuilder {

	public static void setup() {
		EntitySpawnBiomeConfig.setToDefault();
		File dir = FMLPaths.CONFIGDIR.get().resolve(StoryMobs.MODID).toFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File configFile = new File(dir, "mob_spawning_biomes.json");
		if (configFile.exists()) {
			ConfigBuilder.loadConfigFromFile(configFile);
			ConfigBuilder.writeConfigToFile(configFile);
			StoryMobs.LOGGER.debug("Saved the checked/corrected BiomeSpawnConfig");
		} else {
			ConfigBuilder.writeConfigToFile(configFile);
			StoryMobs.LOGGER.debug("No BiomeSpawnConfig was found, created a new one.");
		}

	}

	private static void writeConfigToFile(File file) {
		JsonObject jsonObject = EntitySpawnBiomeConfig.serialize();
		String jsonString = ConfigBuilder.getGson().toJson(jsonObject);
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(jsonString);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadConfigFromFile(File file) {
		JsonObject json = null;
		try {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(file));
			json = jsonElement.getAsJsonObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (json != null) {
			EntitySpawnBiomeConfig.deserialize(json);
			StoryMobs.LOGGER.debug("BiomeSpawnConfig was successfully loaded.");
		} else {
			StoryMobs.LOGGER.debug("Error loading BiomeSpawnConfig, config hasn't been loaded.");
		}
	}

	public static Gson getGson() {
		GsonBuilder gson = new GsonBuilder();
		gson.setPrettyPrinting();
		gson.serializeNulls();
		gson.disableHtmlEscaping();
		return gson.create();
	}

}
