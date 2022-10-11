package me.gentworm.storymobs.entity;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

public class IcySpiderEntity extends Spider {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public IcySpiderEntity(EntityType<? extends IcySpiderEntity> p_i50214_1_, Level p_i50214_2_) {
		super((EntityType) p_i50214_1_, p_i50214_2_);
	}

	public static AttributeSupplier.Builder getAttributes() {
		return Spider.createAttributes().add(Attributes.MAX_HEALTH, 15.0D)
				.add(Attributes.FOLLOW_RANGE, 20.0D);
	}

	public boolean doHurtTarget(Entity p_70652_1_) {
		if (super.doHurtTarget(p_70652_1_)) {
			int lvt_2_1_ = 15;

			if (lvt_2_1_ > 0 && p_70652_1_ instanceof LivingEntity) {
				((LivingEntity) p_70652_1_).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, lvt_2_1_ * 30, 3));
			}
		}
		return true;
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_,
			MobSpawnType p_213386_3_, @Nullable SpawnGroupData p_213386_4_, @Nullable CompoundTag p_213386_5_) {
		return p_213386_4_;
	}

	@Override
	protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
		return 0.50F;
	}
	
	public static boolean canIcySpiderSpawn(EntityType<IcySpiderEntity> animal, LevelAccessor world, MobSpawnType reason,
			BlockPos pos, Random random) {
		BlockState state = world.getBlockState(pos.below());
		return (state.is(Blocks.RED_SAND) || (state.is(Blocks.ICE)) || (state.is(Blocks.BLUE_ICE))
				|| (state.is(Blocks.FROSTED_ICE)) || (state.is(Blocks.PACKED_ICE)) || (state.is(Blocks.SNOW))
				|| (state.is(Blocks.SNOW_BLOCK)) || (state.is(Blocks.DIRT)) || (state.is(Blocks.GRASS_BLOCK)));
	}
}
