package me.gentworm.storymobs.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class StoryMobsGeneralConfig {

	public static ConfigValue<Boolean> shouldSpawnPrison;

	StoryMobsGeneralConfig(ForgeConfigSpec.Builder builder) {
		builder.push("world");
		
		builder.comment("If the world should spawn a prison dungeon structure, only true/false values");
		shouldSpawnPrison = builder.define("shouldSpawnPrisonStructure", true);
	}
}
