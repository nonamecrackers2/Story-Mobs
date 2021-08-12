package me.gentworm.storymobs.event;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.init.EntityInit;
import me.gentworm.storymobs.world.ConfiguredStructures;
import me.gentworm.storymobs.world.StoryMobsStructures;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent;
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

	//Setup of the structures
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			StoryMobsStructures.setupStructures();
			StoryMobsStructures.registerStructurePieces();
			ConfiguredStructures.registerConfiguredStructures();

			WorldGenRegistries.NOISE_SETTINGS.getEntries().forEach(settings -> {
				Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().getStructures()
						.func_236195_a_();

				if (structureMap instanceof ImmutableMap) {
					Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
					tempMap.put(StoryMobsStructures.PRISON.get(),
							DimensionStructuresSettings.field_236191_b_.get(StoryMobsStructures.PRISON.get()));

					tempMap.put(StoryMobsStructures.EVERSOURCE_STRUCTURE.get(),
							DimensionStructuresSettings.field_236191_b_
									.get(StoryMobsStructures.EVERSOURCE_STRUCTURE.get()));
					settings.getValue().getStructures().field_236193_d_ = tempMap;
				} else {
					structureMap.put(StoryMobsStructures.PRISON.get(),
							DimensionStructuresSettings.field_236191_b_.get(StoryMobsStructures.PRISON.get()));
					structureMap.put(StoryMobsStructures.EVERSOURCE_STRUCTURE.get(),
							DimensionStructuresSettings.field_236191_b_
									.get(StoryMobsStructures.EVERSOURCE_STRUCTURE.get()));
				}
			});
		});
	}
}
