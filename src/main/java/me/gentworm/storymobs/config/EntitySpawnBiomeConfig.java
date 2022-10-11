package me.gentworm.storymobs.config;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.config.entity.CreederSpawnConfig;
import me.gentworm.storymobs.config.entity.IcySpiderSpawnConfig;
import me.gentworm.storymobs.config.entity.BlackWolfSpawnConfig;
import me.gentworm.storymobs.config.entity.RedSlimeSpawnConfig;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.registries.ForgeRegistries;

public final class EntitySpawnBiomeConfig {

	private static Map<String, List<SpawnConfigs>> entityBasedSpawnData;
	private static Map<String, Supplier<List<SpawnConfigs>>> entityConfigEntries = getEntityConfigEntries();
	private static Type type = new TypeToken<Map<String, List<SpawnConfigs>>>() {
	}.getType();

	private EntitySpawnBiomeConfig() {
	}

	public static void setToDefault() {
		entityBasedSpawnData = new HashMap<>();
		for (Map.Entry<String, Supplier<List<SpawnConfigs>>> entry : entityConfigEntries.entrySet()) {
			entityBasedSpawnData.put(entry.getKey(), entry.getValue().get());
		}
	}

	public static JsonObject serialize() {
		return new Gson().toJsonTree(entityBasedSpawnData, type).getAsJsonObject();
	}

	public static void deserialize(JsonObject json) {
		entityBasedSpawnData = new Gson().fromJson(json, type);
		for (Iterator<Entry<String, List<SpawnConfigs>>> it = entityBasedSpawnData.entrySet().iterator(); it
				.hasNext();) {
			Entry<String, List<SpawnConfigs>> entry = it.next();
			ResourceLocation name = new ResourceLocation(entry.getKey());
			if (!name.getNamespace().equals(StoryMobs.MODID)) {
				it.remove();
			}
		}

		for (Map.Entry<String, Supplier<List<SpawnConfigs>>> entry : entityConfigEntries.entrySet()) {
			if (!entityBasedSpawnData.containsKey(entry.getKey())) {
				entityBasedSpawnData.put(entry.getKey(), entry.getValue().get());
			}
		}
	}

	public static Map<ResourceLocation, Map<MobCategory, List<SpawnerData>>> getBiomeSpawnMap() {
		Map<ResourceLocation, Map<MobCategory, List<SpawnerData>>> biomeSpawnData = new HashMap<>();
		for (Map.Entry<String, List<SpawnConfigs>> entry : entityBasedSpawnData.entrySet()) {
			ResourceLocation name = new ResourceLocation(entry.getKey());
			EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(name);
			if (entityType == null || !name.getNamespace().equals(StoryMobs.MODID)) {
				continue;
			}
			for (SpawnConfigs spawnData : entry.getValue()) {
				for (String biomeString : spawnData.biomes) {
					ResourceLocation biome = new ResourceLocation(biomeString);
					if (!ForgeRegistries.BIOMES.containsKey(biome)) {
						continue;
					}
					Map<MobCategory, List<SpawnerData>> classificationMap = biomeSpawnData.get(biome);
					if (classificationMap == null) {
						classificationMap = new HashMap<>();
					}
					MobCategory entityClassification = entityType.getCategory();
					List<SpawnerData> spawnersMap = classificationMap.get(entityClassification);
					if (spawnersMap == null) {
						spawnersMap = new ArrayList<>();
					}
					int weight = EntitySpawnBiomeConfig.checkRange(spawnData.weight, 1, Short.MAX_VALUE, 15);
					int minCount = EntitySpawnBiomeConfig.checkRange(spawnData.minCount, 1, Short.MAX_VALUE, 3);
					int maxCount = EntitySpawnBiomeConfig.checkRange(spawnData.maxCount, 1, Short.MAX_VALUE, 5);
					SpawnerData spawners = new SpawnerData(entityType, weight, minCount, maxCount);
					spawnersMap.add(spawners);
					classificationMap.put(entityClassification, spawnersMap);
					biomeSpawnData.put(biome, classificationMap);
				}
			}
		}
		return biomeSpawnData;
	}

	private static Map<String, Supplier<List<SpawnConfigs>>> getEntityConfigEntries() {
		Map<String, Supplier<List<SpawnConfigs>>> names = new HashMap<>();
		names.put("storymobs:creeder", CreederSpawnConfig::getDefaultSpawns);
		names.put("storymobs:icy_spider", IcySpiderSpawnConfig::getDefaultSpawns);
		names.put("storymobs:black_wolf", BlackWolfSpawnConfig::getDefaultSpawns);
		names.put("storymobs:red_slime", RedSlimeSpawnConfig::getDefaultSpawns);
		return names;
	}

	public static int checkRange(int checkMe, int min, int max, int defaultValue) {
		if (checkMe >= min && checkMe <= max) {
			return checkMe;
		}
		return defaultValue;
	}

}
