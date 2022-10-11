package me.gentworm.storymobs.config.entity;

import java.util.ArrayList;
import java.util.List;

import me.gentworm.storymobs.config.SpawnConfigs;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.ForgeConfigSpec;

public class RedSlimeSpawnConfig {

	public RedSlimeSpawnConfig(ForgeConfigSpec.Builder builder) {

		builder.comment("Red Slime Configurations").push("red_slime");

		builder.pop();
	}

	@SuppressWarnings("unchecked")
	public static List<SpawnConfigs> getDefaultSpawns() {
		List<SpawnConfigs> spawns = new ArrayList<SpawnConfigs>();
		spawns.add(new SpawnConfigs(15, 1, 20, Biomes.SWAMP, Biomes.SWAMP_HILLS));
		return spawns;
	}

}
