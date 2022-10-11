package me.gentworm.storymobs.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;

//A zombie that's just a little more powerful and doesn't burn in the day
public class PrisonZombieEntity extends Zombie {

	public PrisonZombieEntity(EntityType<? extends PrisonZombieEntity> p_i50204_1_, Level p_i50204_2_) {
		super(p_i50204_1_, p_i50204_2_);
	}

	@Override
	public boolean isSunSensitive() {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.ZOMBIE_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.ZOMBIE_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.ZOMBIE_DEATH;
	}

	@Override
	public SoundEvent getStepSound() {
		return SoundEvents.ZOMBIE_STEP;
	}

	@Override
	public boolean doHurtTarget(Entity p_70652_1_) {
		boolean lvt_2_1_ = super.doHurtTarget(p_70652_1_);
		if (lvt_2_1_ && getMainHandItem().isEmpty() && p_70652_1_ instanceof LivingEntity) {
			float lvt_3_1_ = this.level.getCurrentDifficultyAt(blockPosition()).getEffectiveDifficulty();
			((LivingEntity) p_70652_1_).addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200 * (int) lvt_3_1_));
		}
		return lvt_2_1_;
	}

	@Override
	public boolean convertsInWater() {
		return true;
	}
}
