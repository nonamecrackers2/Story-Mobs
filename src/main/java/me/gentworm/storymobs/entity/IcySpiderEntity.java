package me.gentworm.storymobs.entity;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class IcySpiderEntity extends SpiderEntity {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public IcySpiderEntity(EntityType<? extends IcySpiderEntity> p_i50214_1_, World p_i50214_2_) {
		super((EntityType) p_i50214_1_, p_i50214_2_);
	}

	public static AttributeModifierMap.MutableAttribute getAttributes() {
		return SpiderEntity.func_234305_eI_().createMutableAttribute(Attributes.MAX_HEALTH, 15.0D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D);
	}

	public boolean attackEntityAsMob(Entity p_70652_1_) {
		if (super.attackEntityAsMob(p_70652_1_)) {
			int lvt_2_1_ = 15;

			if (lvt_2_1_ > 0 && p_70652_1_ instanceof LivingEntity) {
				((LivingEntity) p_70652_1_).addPotionEffect(new EffectInstance(Effects.SLOWNESS, lvt_2_1_ * 30, 3));
			}
		}
		return true;
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_,
			SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
		return p_213386_4_;
	}

	@Override
	protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
		return 0.50F;
	}
	
	public static boolean canIcySpiderSpawn(EntityType<IcySpiderEntity> animal, IWorld world, SpawnReason reason,
			BlockPos pos, Random random) {
		BlockState state = world.getBlockState(pos.down());
		return (state.isIn(Blocks.RED_SAND) || (state.isIn(Blocks.ICE)) || (state.isIn(Blocks.BLUE_ICE))
				|| (state.isIn(Blocks.FROSTED_ICE)) || (state.isIn(Blocks.PACKED_ICE)) || (state.isIn(Blocks.SNOW))
				|| (state.isIn(Blocks.SNOW_BLOCK)) || (state.isIn(Blocks.DIRT)) || (state.isIn(Blocks.GRASS_BLOCK)));
	}
}
