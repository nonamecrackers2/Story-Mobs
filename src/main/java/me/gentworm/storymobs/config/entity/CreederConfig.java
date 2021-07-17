package me.gentworm.storymobs.config.entity;

import java.util.ArrayList;
import java.util.List;

import me.gentworm.storymobs.config.SpawnConfigs;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.ForgeConfigSpec;

public class CreederConfig {

	public CreederConfig(ForgeConfigSpec.Builder builder) {

		builder.comment("Creeder Configurations").push("creeder");

		builder.pop();
	}

	@SuppressWarnings("unchecked")
	public static List<SpawnConfigs> getDefaultSpawns() {
		List<SpawnConfigs> spawns = new ArrayList<SpawnConfigs>();
		spawns.add(new SpawnConfigs(27, 1, 35, Biomes.BADLANDS, Biomes.BADLANDS_PLATEAU, Biomes.ERODED_BADLANDS,
				Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU,
				Biomes.WOODED_BADLANDS_PLATEAU));
		return spawns;
	}

}
