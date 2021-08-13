package me.gentworm.storymobs.event;

import java.util.List;
import java.util.Map;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.config.EntitySpawnBiomeConfig;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StoryMobs.MODID)
public class BiomeLoadingEventHandler {

	private static Map<ResourceLocation, Map<EntityClassification, List<Spawners>>> biomeSpawnMap;

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		if (biomeSpawnMap == null) {
			biomeSpawnMap = EntitySpawnBiomeConfig.getBiomeSpawnMap();
		}
		if (biomeSpawnMap.containsKey(event.getName())) {
			Map<EntityClassification, List<Spawners>> classificationMap = biomeSpawnMap.get(event.getName());
			for (Map.Entry<EntityClassification, List<Spawners>> entry : classificationMap.entrySet()) {
				EntityClassification classification = entry.getKey();
				for (Spawners spawner : entry.getValue()) {
					event.getSpawns().getSpawner(classification).add(spawner);
				}
			}
		}
	}
}
