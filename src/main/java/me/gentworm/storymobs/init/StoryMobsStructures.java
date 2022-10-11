package me.gentworm.storymobs.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.world.structure.EversourceStructure;
import me.gentworm.storymobs.world.structure.PrisonStructure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = StoryMobs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StoryMobsStructures {
	public static StructurePieceType PRISON_PIECE_TYPE = PrisonStructure.Piece::new;
	public static StructurePieceType EVERSOURCE_PIECE_TYPE = EversourceStructure.Piece::new;

	public static final DeferredRegister<StructureFeature<?>> REGISTER = DeferredRegister
			.create(ForgeRegistries.STRUCTURE_FEATURES, StoryMobs.MODID);

	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> PRISON = REGISTER.register("prison",
			() -> (new PrisonStructure(NoneFeatureConfiguration.CODEC)));

	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> EVERSOURCE_STRUCTURE = REGISTER
			.register("eversource_structure", () -> (new EversourceStructure(NoneFeatureConfiguration.CODEC)));

	public static void setupStructures() {
		// Set transformSurroundingLand to true to take the surrounding land
		// Set to false for surrounding land to not have an impact
		setupMapSpacingAndLand(PRISON.get(), new StructureFeatureConfiguration(80, 50, 2538959), true);
		setupMapSpacingAndLand(EVERSOURCE_STRUCTURE.get(), new StructureFeatureConfiguration(560, 555, 4857626), false);
	}

	public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(F structure,
			StructureFeatureConfiguration structureSeparationSettings, boolean transformSurroundingLand) {
		StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

		if (transformSurroundingLand) {
			StructureFeature.NOISE_AFFECTING_FEATURES = ImmutableList.<StructureFeature<?>>builder().addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
					.add(structure).build();
		}
		StructureSettings.DEFAULTS = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
				.putAll(StructureSettings.DEFAULTS).put(structure, structureSeparationSettings)
				.build();
	}

	public static void registerStructurePieces() {
		Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(StoryMobs.MODID, "prison"), PRISON_PIECE_TYPE);

		Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(StoryMobs.MODID, "eversource_structure"),
				EVERSOURCE_PIECE_TYPE);
	}
}