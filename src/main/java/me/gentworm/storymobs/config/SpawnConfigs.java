package me.gentworm.storymobs.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;

//Some Code used from other mods
public final class SpawnConfigs {

	public int weight;
	public int minCount;
	public int maxCount;
	public List<String> biomes;

	public SpawnConfigs(int weight, int min, int max, @SuppressWarnings("unchecked") RegistryKey<Biome>... biomes) {
		this.weight = weight;
		this.minCount = min;
		this.maxCount = max;
		this.biomes = new ArrayList<>();
		for (int i = 0; i < biomes.length; i++) {
			this.biomes.add(biomes[i].getLocation().toString());
		}
	}
}
