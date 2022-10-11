package me.gentworm.storymobs.config.entity;

import java.util.ArrayList;
import java.util.List;

import me.gentworm.storymobs.config.SpawnConfigs;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.ForgeConfigSpec;

public class BlackWolfSpawnConfig {

	public BlackWolfSpawnConfig(ForgeConfigSpec.Builder builder) {

		builder.comment("Black Wolf Configurations").push("black_wolf");

		builder.pop();
	}

	@SuppressWarnings("unchecked")
	public static List<SpawnConfigs> getDefaultSpawns() {
		List<SpawnConfigs> spawns = new ArrayList<SpawnConfigs>();
		spawns.add(new SpawnConfigs(15, 1, 20, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.TAIGA,
				Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.SNOWY_TAIGA, Biomes.FROZEN_PEAKS));
		return spawns;
	}

}
