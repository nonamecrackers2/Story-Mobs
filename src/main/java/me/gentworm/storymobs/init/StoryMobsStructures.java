package me.gentworm.storymobs.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.world.structure.EversourceStructure;
import me.gentworm.storymobs.world.structure.PrisonStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = StoryMobs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StoryMobsStructures {
	public static IStructurePieceType PRISON_PIECE_TYPE = PrisonStructure.Piece::new;
	public static IStructurePieceType EVERSOURCE_PIECE_TYPE = EversourceStructure.Piece::new;

	public static final DeferredRegister<Structure<?>> REGISTER = DeferredRegister
			.create(ForgeRegistries.STRUCTURE_FEATURES, StoryMobs.MODID);

	public static final RegistryObject<Structure<NoFeatureConfig>> PRISON = REGISTER.register("prison",
			() -> (new PrisonStructure(NoFeatureConfig.field_236558_a_)));

	public static final RegistryObject<Structure<NoFeatureConfig>> EVERSOURCE_STRUCTURE = REGISTER
			.register("eversource_structure", () -> (new EversourceStructure(NoFeatureConfig.field_236558_a_)));

	public static void setupStructures() {
		// Set transformSurroundingLand to true to take the surrounding land
		// Set to false for surrounding land to not have an impact
		setupMapSpacingAndLand(PRISON.get(), new StructureSeparationSettings(80, 50, 2538959), true);
		setupMapSpacingAndLand(EVERSOURCE_STRUCTURE.get(), new StructureSeparationSettings(560, 555, 4857626), false);
	}

	public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure,
			StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
		Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

		if (transformSurroundingLand) {
			Structure.field_236384_t_ = ImmutableList.<Structure<?>>builder().addAll(Structure.field_236384_t_)
					.add(structure).build();
		}
		DimensionStructuresSettings.field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
				.putAll(DimensionStructuresSettings.field_236191_b_).put(structure, structureSeparationSettings)
				.build();
	}

	public static void registerStructurePieces() {
		Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(StoryMobs.MODID, "prison"), PRISON_PIECE_TYPE);

		Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(StoryMobs.MODID, "eversource_structure"),
				EVERSOURCE_PIECE_TYPE);
	}
}