package me.gentworm.storymobs.config;

import org.apache.logging.log4j.Level;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.config.entity.CreederConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class ConfigGenerator {

	public static final String DISABLE_SPAWNING = "to disable spawning, leave the array SpawnBoimes empty";

	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	public static final StoryMobsGeneralConfig GENERAL_CONFIG = new StoryMobsGeneralConfig(BUILDER);
	public static final CreederConfig CREEDER_CONFIG = new CreederConfig(BUILDER);


	public static final ForgeConfigSpec spec = BUILDER.build();

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {
		if (configEvent.getConfig().getModId() == StoryMobs.MODID) {
			StoryMobs.LOGGER.debug("Loaded config file {}", configEvent.getConfig().getFileName());
		}
	}

	public static boolean checkBiome(String name, Object test) {
		if (ForgeRegistries.BIOMES.containsKey(new ResourceLocation(String.valueOf(test)))) {
			return true;
		}
		StoryMobs.LOGGER.log(Level.INFO,
				"Removing unknown Biome[" + String.valueOf(test) + "] from " + name + "-SpawnBiomes");
		return false;
	}

}
