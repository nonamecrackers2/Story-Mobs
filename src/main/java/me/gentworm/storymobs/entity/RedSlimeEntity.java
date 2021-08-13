package me.gentworm.storymobs.entity;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;

public class RedSlimeEntity extends SlimeEntity {

	public RedSlimeEntity(EntityType<? extends RedSlimeEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public static AttributeModifierMap.MutableAttribute getAttributes() {
		return SpiderEntity.func_234305_eI_().createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D);
	}

	public static boolean canRedSlimeSpawn(EntityType<RedSlimeEntity> p_223366_0_, IWorld p_223366_1_,
			SpawnReason reason, BlockPos p_223366_3_, Random randomIn) {
		if (p_223366_1_.getDifficulty() != Difficulty.PEACEFUL) {
			if (Objects.equals(p_223366_1_.func_242406_i(p_223366_3_), Optional.of(Biomes.SWAMP))
					&& p_223366_3_.getY() > 50 && p_223366_3_.getY() < 70 && randomIn.nextFloat() < 0.5F
					&& randomIn.nextFloat() < p_223366_1_.getMoonFactor()
					&& p_223366_1_.getLight(p_223366_3_) <= randomIn.nextInt(8)) {
				return canSpawnOn(p_223366_0_, p_223366_1_, reason, p_223366_3_, randomIn);
			}

			if (!(p_223366_1_ instanceof ISeedReader)) {
				return false;
			}

			ChunkPos chunkpos = new ChunkPos(p_223366_3_);
			boolean flag = SharedSeedRandom
					.seedSlimeChunk(chunkpos.x, chunkpos.z, ((ISeedReader) p_223366_1_).getSeed(), 987234911L)
					.nextInt(10) == 0;
			if (randomIn.nextInt(10) == 0 && flag && p_223366_3_.getY() < 40) {
				return canSpawnOn(p_223366_0_, p_223366_1_, reason, p_223366_3_, randomIn);
			}
		}

		return false;
	}

}
