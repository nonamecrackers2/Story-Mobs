package me.gentworm.storymobs.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

//A zombie that's just a little more powerful and doesn't burn in the day
public class PrisonZombieEntity extends ZombieEntity {

	public PrisonZombieEntity(EntityType<? extends PrisonZombieEntity> p_i50204_1_, World p_i50204_2_) {
		super(p_i50204_1_, p_i50204_2_);
	}

	@Override
	public boolean shouldBurnInDay() {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.ENTITY_ZOMBIE_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ZOMBIE_DEATH;
	}

	@Override
	public SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}

	@Override
	public boolean attackEntityAsMob(Entity p_70652_1_) {
		boolean lvt_2_1_ = super.attackEntityAsMob(p_70652_1_);
		if (lvt_2_1_ && getHeldItemMainhand().isEmpty() && p_70652_1_ instanceof LivingEntity) {
			float lvt_3_1_ = this.world.getDifficultyForLocation(getPosition()).getAdditionalDifficulty();
			((LivingEntity) p_70652_1_).addPotionEffect(new EffectInstance(Effects.NAUSEA, 200 * (int) lvt_3_1_));
		}
		return lvt_2_1_;
	}

	@Override
	public boolean shouldDrown() {
		return true;
	}
}
