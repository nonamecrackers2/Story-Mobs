package me.gentworm.storymobs.event;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.init.EntityInit;
import me.gentworm.storymobs.init.StoryMobsStructures;
import me.gentworm.storymobs.world.ConfiguredStructures;
import me.gentworm.storymobs.world.modification.EversourceStructureModification;
import me.gentworm.storymobs.world.modification.PrisonModification;
import net.minecraft.world.entity.EntityType;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = StoryMobs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEventsRegistry {

	@SubscribeEvent
	public static void onEntityRegistry(RegistryEvent.Register<EntityType<?>> event) {
		EntityInit.registerEntityAttributes();
		EntityInit.registerEntitySpawnPlacements();
	}

	public static void registerStructuresInWorld() {
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		// Prison structure
		forgeBus.addListener(EventPriority.NORMAL, PrisonModification::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.HIGH, PrisonModification::biomeModification);

		// Eversource structure
		forgeBus.addListener(EventPriority.NORMAL, EversourceStructureModification::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.HIGH, EversourceStructureModification::biomeModification);
	}

	// Setup of the structures
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			StoryMobsStructures.setupStructures();
			StoryMobsStructures.registerStructurePieces();
			ConfiguredStructures.registerConfiguredStructures();

			BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
				Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings()
						.structureConfig();

				if (structureMap instanceof ImmutableMap) {
					Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
					tempMap.put(StoryMobsStructures.PRISON.get(),
							StructureSettings.DEFAULTS.get(StoryMobsStructures.PRISON.get()));

					tempMap.put(StoryMobsStructures.EVERSOURCE_STRUCTURE.get(),
							StructureSettings.DEFAULTS
									.get(StoryMobsStructures.EVERSOURCE_STRUCTURE.get()));
					settings.getValue().structureSettings().structureConfig = tempMap;
				} else {
					structureMap.put(StoryMobsStructures.PRISON.get(),
							StructureSettings.DEFAULTS.get(StoryMobsStructures.PRISON.get()));
					structureMap.put(StoryMobsStructures.EVERSOURCE_STRUCTURE.get(),
							StructureSettings.DEFAULTS
									.get(StoryMobsStructures.EVERSOURCE_STRUCTURE.get()));
				}
			});
		});
	}
}
