package me.gentworm.storymobs.config.entity;

import java.util.ArrayList;
import java.util.List;

import me.gentworm.storymobs.config.SpawnConfigs;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.ForgeConfigSpec;

public class IcySpiderSpawnConfig {

	public IcySpiderSpawnConfig(ForgeConfigSpec.Builder builder) {

		builder.comment("Icy Spider Configurations").push("icy_spider");

		builder.pop();
	}

	//TODO: Find biome replacements
	@SuppressWarnings("unchecked")
	public static List<SpawnConfigs> getDefaultSpawns() {
		List<SpawnConfigs> spawns = new ArrayList<SpawnConfigs>();
		spawns.add(new SpawnConfigs(24, 1, 35, Biomes.ICE_SPIKES, Biomes.SNOWY_BEACH, Biomes.SNOWY_MOUNTAINS,
				Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA,
				Biomes.FROZEN_RIVER, Biomes.FROZEN_OCEAN));
		return spawns;
	}

}
