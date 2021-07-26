package me.gentworm.storymobs.world;

import me.gentworm.storymobs.StoryMobs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ConfiguredStructures {
    public static StructureFeature<?, ?> CONFIGURED_PRISON = StoryMobsStructures.PRISON.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> CONFIGURED_EVERSOURCE_STRUCTURE = StoryMobsStructures.EVERSOURCE_STRUCTURE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(StoryMobs.MODID, "configured_prison"), CONFIGURED_PRISON);
        Registry.register(registry, new ResourceLocation(StoryMobs.MODID, "configured_eversource_structure"), CONFIGURED_EVERSOURCE_STRUCTURE);


        FlatGenerationSettings.STRUCTURES.put(StoryMobsStructures.PRISON.get(), CONFIGURED_PRISON);
        FlatGenerationSettings.STRUCTURES.put(StoryMobsStructures.EVERSOURCE_STRUCTURE.get(), CONFIGURED_EVERSOURCE_STRUCTURE);

    }
}