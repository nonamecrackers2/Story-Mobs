package me.gentworm.storymobs.world.structure;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.entity.PrisonZombieEntity;
import me.gentworm.storymobs.init.EntityInit;
import me.gentworm.storymobs.init.StoryMobsStructures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import net.minecraft.world.level.levelgen.feature.StructureFeature.StructureStartFactory;

public class PrisonStructure extends StructureFeature<NoneFeatureConfiguration> {

	private static final List<MobSpawnSettings.SpawnerData> PRISON_ENEMIES = ImmutableList
			.of(new MobSpawnSettings.SpawnerData(EntityInit.PRISON_ZOMBIE_ENTITY.get(), 1, 1, 1));

	public PrisonStructure(Codec<NoneFeatureConfiguration> p_i51440_1_) {
		super(p_i51440_1_);
	}

	@Override
	public List<MobSpawnSettings.SpawnerData> getDefaultSpawnList() {
		return PRISON_ENEMIES;
	}

	@Override
	public String getFeatureName() {
		return StoryMobs.MODID + ":prison";
	}

	protected boolean isFeatureChunk(ChunkGenerator p_230363_1_, BiomeSource p_230363_2_, long p_230363_3_,
			WorldgenRandom p_230363_5_, int p_230363_6_, int p_230363_7_, Biome p_230363_8_, ChunkPos p_230363_9_,
			NoneFeatureConfiguration p_230363_10_) {
		int i = p_230363_6_ >> 4;
		int j = p_230363_7_ >> 4;
		p_230363_5_.setSeed((long) (i ^ j << 4) ^ p_230363_3_);
		p_230363_5_.nextInt();
		for (int k = p_230363_6_ - 10; k <= p_230363_6_ + 10; ++k) {
			for (int l = p_230363_7_ - 10; l <= p_230363_7_ + 10; ++l) {
				ChunkPos chunkpos = StructureFeature.VILLAGE.getPotentialFeatureChunk(
						p_230363_1_.getSettings().getConfig(StructureFeature.VILLAGE), p_230363_3_, p_230363_5_, k, l);
				if (k == chunkpos.x && l == chunkpos.z) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	@Override
	public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
		return PrisonStructure.Start::new;
	}

	public static class Start extends StructureStart<NoneFeatureConfiguration> {
		public Start(StructureFeature<NoneFeatureConfiguration> structureIn, int chunkX, int chunkZ,
				BoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		@Override
		public void generatePieces(RegistryAccess dynamicRegistryManager, ChunkGenerator generator,
				StructureManager templateManagerIn, int chunkX, int chunkZ, Biome biome, NoneFeatureConfiguration config) {
			Rotation rotation = Rotation.values()[this.random.nextInt(Rotation.values().length)];
			int x = (chunkX << 4) + 7;
			int z = (chunkZ << 4) + 7;
			int surfaceY = Math.max(
					generator.getFirstOccupiedHeight(x + 12, z + 12, Heightmap.Types.WORLD_SURFACE_WG) - 1,
					generator.getSpawnHeight() - 1);
			BlockPos blockpos = new BlockPos(x, surfaceY, z);
			Piece.start(templateManagerIn, blockpos, rotation, this.pieces, this.random);
			this.calculateBoundingBox();
		}
	}

	public static class Piece extends TemplateStructurePiece {
		private ResourceLocation resourceLocation;
		private Rotation rotation;

		public Piece(StructureManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos,
				Rotation rotationIn) {
			super(StoryMobsStructures.PRISON_PIECE_TYPE, 0);
			this.resourceLocation = resourceLocationIn;
			this.templatePosition = pos;
			this.rotation = rotationIn;
			this.setupPiece(templateManagerIn);
		}

		public Piece(StructureManager templateManagerIn, CompoundTag tagCompound) {
			super(StoryMobsStructures.PRISON_PIECE_TYPE, tagCompound);
			this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
			this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
			this.setupPiece(templateManagerIn);
		}

		public static void start(StructureManager templateManager, BlockPos pos, Rotation rotation,
				List<StructurePiece> pieceList, Random random) {
			int x = pos.getX();
			int z = pos.getZ();
			BlockPos rotationOffSet = new BlockPos(0, 0, 0).rotate(rotation);
			BlockPos blockpos = rotationOffSet.offset(x, pos.getY(), z);
			pieceList.add(new Piece(templateManager, new ResourceLocation(StoryMobs.MODID, "prison/cell"), blockpos,
					rotation));
		}

		private void setupPiece(StructureManager templateManager) {
			StructureTemplate template = templateManager.getOrCreate(this.resourceLocation);
			StructurePlaceSettings placementsettings = (new StructurePlaceSettings()).setRotation(this.rotation)
					.setMirror(Mirror.NONE);
			this.setup(template, this.templatePosition, placementsettings);
		}

		@Override
		protected void addAdditionalSaveData(CompoundTag tagCompound) {
			super.addAdditionalSaveData(tagCompound);
			tagCompound.putString("Template", this.resourceLocation.toString());
			tagCompound.putString("Rot", this.rotation.name());
		}

		@Override
		public boolean postProcess(WorldGenLevel seedReader, StructureFeatureManager structureManager,
				ChunkGenerator chunkGenerator, Random randomIn, BoundingBox structureBoundingBoxIn,
				ChunkPos chunkPos, BlockPos pos) {
			StructurePlaceSettings placementsettings = (new StructurePlaceSettings()).setRotation(this.rotation)
					.setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
			BlockPos blockpos = BlockPos.ZERO;
			this.templatePosition.offset(StructureTemplate.calculateRelativePosition(placementsettings,
					new BlockPos(-blockpos.getX(), 0, -blockpos.getZ())));
			return super.postProcess(seedReader, structureManager, chunkGenerator, randomIn, structureBoundingBoxIn,
					chunkPos, pos);
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor worldIn, Random rand,
				BoundingBox sbb) {
			
			PrisonZombieEntity entity = EntityInit.PRISON_ZOMBIE_ENTITY.get().create(worldIn.getLevel());

			if ("chest".equals(function)) {
				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
				BlockEntity tileentity = worldIn.getBlockEntity(pos.below());
				if (tileentity instanceof ChestBlockEntity) {
					((ChestBlockEntity) tileentity).setLootTable(BuiltInLootTables.SIMPLE_DUNGEON, rand.nextLong());
				}
			}
			worldIn.addFreshEntity(entity);
		}
	}
}
