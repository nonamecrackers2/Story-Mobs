package me.gentworm.storymobs.event;

import java.util.List;
import java.util.Map;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.config.EntitySpawnBiomeConfig;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StoryMobs.MODID)
public class BiomeLoadingEventHandler {

	private static Map<ResourceLocation, Map<MobCategory, List<SpawnerData>>> biomeSpawnMap;

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		if (biomeSpawnMap == null) {
			biomeSpawnMap = EntitySpawnBiomeConfig.getBiomeSpawnMap();
		}
		if (biomeSpawnMap.containsKey(event.getName())) {
			Map<MobCategory, List<SpawnerData>> classificationMap = biomeSpawnMap.get(event.getName());
			for (Map.Entry<MobCategory, List<SpawnerData>> entry : classificationMap.entrySet()) {
				MobCategory classification = entry.getKey();
				for (SpawnerData spawner : entry.getValue()) {
					event.getSpawns().getSpawner(classification).add(spawner);
				}
			}
		}
	}
}
