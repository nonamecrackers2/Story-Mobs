package me.gentworm.storymobs.world;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.init.StoryMobsStructures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

public class ConfiguredStructures {
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_PRISON = StoryMobsStructures.PRISON.get()
			.configured(FeatureConfiguration.NONE);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_EVERSOURCE_STRUCTURE = StoryMobsStructures.EVERSOURCE_STRUCTURE
			.get().configured(FeatureConfiguration.NONE);

	public static void registerConfiguredStructures() {
		Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(StoryMobs.MODID, "configured_prison"), CONFIGURED_PRISON);

		Registry.register(registry, new ResourceLocation(StoryMobs.MODID, "configured_eversource_structure"),
				CONFIGURED_EVERSOURCE_STRUCTURE);

		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(StoryMobsStructures.PRISON.get(), CONFIGURED_PRISON);

		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(StoryMobsStructures.EVERSOURCE_STRUCTURE.get(),
				CONFIGURED_EVERSOURCE_STRUCTURE);

	}
}