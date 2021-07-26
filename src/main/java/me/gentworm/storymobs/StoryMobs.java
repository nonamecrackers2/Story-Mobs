package me.gentworm.storymobs;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap;

import me.gentworm.storymobs.client.RenderHandler;
import me.gentworm.storymobs.config.ConfigBuilder;
import me.gentworm.storymobs.config.ConfigGenerator;
import me.gentworm.storymobs.init.EntityInit;
import me.gentworm.storymobs.init.ItemInit;
import me.gentworm.storymobs.world.ConfiguredStructures;
import me.gentworm.storymobs.world.StoryMobsStructures;
import me.gentworm.storymobs.world.modification.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StoryMobs.MODID)
public class StoryMobs{

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "storymobs";

	public StoryMobs() {
		ModLoadingContext.get().registerConfig(Type.COMMON, ConfigGenerator.spec, "storymobs/storymobs.toml");

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ConfigBuilder.setup();
		// Register general, miscellaneous things
		EntityInit.ENTITY_TYPES.register(modEventBus);
		ItemInit.ITEMS.register(modEventBus);
		StoryMobsStructures.REGISTER.register(modEventBus);

		modEventBus.addListener(this::setup);

		// Client event wrap-up in the main class
		modEventBus.addListener(this::clientSetup);

		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		// World generation like structures, etc
		forgeBus.addListener(EventPriority.NORMAL, PrisonModification::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.HIGH, PrisonModification::biomeModification);
		forgeBus.addListener(EventPriority.NORMAL, EversourceStructureModification::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.HIGH, EversourceStructureModification::biomeModification);

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void setup(final FMLCommonSetupEvent event) {
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
							DimensionStructuresSettings.field_236191_b_.get(StoryMobsStructures.EVERSOURCE_STRUCTURE.get()));
					settings.getValue().getStructures().field_236193_d_  = tempMap;
				} else {
					structureMap.put(StoryMobsStructures.PRISON.get(),
							DimensionStructuresSettings.field_236191_b_.get(StoryMobsStructures.PRISON.get()));
					structureMap.put(StoryMobsStructures.EVERSOURCE_STRUCTURE.get(),
							DimensionStructuresSettings.field_236191_b_.get(StoryMobsStructures.EVERSOURCE_STRUCTURE.get()));
				}
			});
		});
	}

	public void clientSetup(final FMLClientSetupEvent event) {
		RenderHandler.registerEntityRenders();
	}

	public static ResourceLocation getResources(String path) {
		return new ResourceLocation(MODID, path);
	}
}
