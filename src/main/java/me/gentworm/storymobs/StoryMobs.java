package me.gentworm.storymobs;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;

import me.gentworm.storymobs.client.RenderHandler;
import me.gentworm.storymobs.config.ConfigBuilder;
import me.gentworm.storymobs.config.ConfigGenerator;
import me.gentworm.storymobs.config.StoryMobsGeneralConfig;
import me.gentworm.storymobs.init.EntityInit;
import me.gentworm.storymobs.init.ItemInit;
import me.gentworm.storymobs.world.ConfiguredStructures;
import me.gentworm.storymobs.world.StoryMobsStructures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StoryMobs.MODID)
public class StoryMobs {

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
		
		if (StoryMobsGeneralConfig.shouldSpawnPrison.get() == true) {
			modEventBus.addListener(this::setup);
		}
		// Client event wrap-up in the main class
		modEventBus.addListener(this::clientSetup);

		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		// World generation like structures, etc
		if (StoryMobsGeneralConfig.shouldSpawnPrison.get() == true) {
			forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
			forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
		}
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
					settings.getValue().getStructures().field_236193_d_ = tempMap;
				} else {
					structureMap.put(StoryMobsStructures.PRISON.get(),
							DimensionStructuresSettings.field_236191_b_.get(StoryMobsStructures.PRISON.get()));
				}
			});
		});
	}

	public void biomeModification(final BiomeLoadingEvent event) {
		if (event.getCategory() == Biome.Category.PLAINS) {
			event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_PRISON);
		}
	}

	public void clientSetup(final FMLClientSetupEvent event) {
		RenderHandler.registerEntityRenders();
	}

	public static ResourceLocation getResources(String path) {
		return new ResourceLocation(MODID, path);
	}

	private static Method GETCODEC_METHOD;

	@SuppressWarnings({ "unchecked", "resource" })
	public void addDimensionalSpacing(final WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) event.getWorld();

			try {
				if (GETCODEC_METHOD == null)
					GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "getCodec");
				ResourceLocation cgRL = Registry.CHUNK_GENERATOR_CODEC
						.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD
								.invoke(serverWorld.getChunkProvider().generator));
				if (cgRL != null && cgRL.getNamespace().equals("terraforged"))
					return;
			} catch (Exception e) {
				LOGGER.error("Was unable to check if " + serverWorld.getDimensionKey().getLocation()
						+ " is using Terraforged's ChunkGenerator.");
			}

			if (serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator
					&& serverWorld.getDimensionKey().equals(World.OVERWORLD)) {
				return;
			}

			Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(
					serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
			tempMap.putIfAbsent(StoryMobsStructures.PRISON.get(),
					DimensionStructuresSettings.field_236191_b_.get(StoryMobsStructures.PRISON.get()));
			serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
		}
	}
}
