package me.gentworm.storymobs.entity;

import java.util.EnumSet;
import java.util.Random;

import me.gentworm.storymobs.entity.projectile.CustomFireballEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GiantGhastEntity extends FlyingEntity implements IMob {
	private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(GiantGhastEntity.class,
			DataSerializers.BOOLEAN);

	private int explosionStrength = 1;

	public GiantGhastEntity(EntityType<? extends GiantGhastEntity> p_i50206_1_, World p_i50206_2_) {
		super(p_i50206_1_, p_i50206_2_);
		this.experienceValue = 5;
		this.moveController = new MoveHelperController(this);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void registerGoals() {
		this.goalSelector.addGoal(5, new RandomFlyGoal(this));
		this.goalSelector.addGoal(7, new LookAroundGoal(this));
		this.goalSelector.addGoal(7, new FireballAttackGoal(this));
		this.targetSelector.addGoal(1, (Goal) new NearestAttackableTargetGoal((MobEntity) this, PlayerEntity.class, 10,
				true, false, p_213812_1_ -> (Math.abs(((Entity) p_213812_1_).getPosY() - getPosY()) <= 4.0D)));
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isAttacking() {
		return ((Boolean) this.dataManager.get(ATTACKING)).booleanValue();
	}

	public void setAttacking(boolean p_175454_1_) {
		this.dataManager.set(ATTACKING, Boolean.valueOf(p_175454_1_));
	}

	public int getFireballStrength() {
		return this.explosionStrength;
	}

	protected boolean isDespawnPeaceful() {
		return true;
	}

	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
		if (isInvulnerableTo(p_70097_1_))
			return false;
		if (p_70097_1_.getImmediateSource() instanceof CustomFireballEntity
				&& p_70097_1_.getTrueSource() instanceof PlayerEntity) {
			super.attackEntityFrom(p_70097_1_, 1000.0F);
			return true;
		}
		return super.attackEntityFrom(p_70097_1_, p_70097_2_);
	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(ATTACKING, Boolean.valueOf(false));
	}

	public static AttributeModifierMap.MutableAttribute getAttributes() {
		return MobEntity.func_233666_p_();
	}

	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_GHAST_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.ENTITY_GHAST_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_GHAST_DEATH;
	}

	protected float getSoundVolume() {
		return 5.0F;
	}
	
	public int getMaxSpawnedInChunk() {
		return 1;
	}

	public void writeAdditional(CompoundNBT p_213281_1_) {
		super.writeAdditional(p_213281_1_);
		p_213281_1_.putInt("ExplosionPower", this.explosionStrength);
	}

	public void readAdditional(CompoundNBT p_70037_1_) {
		super.readAdditional(p_70037_1_);
		if (p_70037_1_.contains("ExplosionPower", 99))
			this.explosionStrength = p_70037_1_.getInt("ExplosionPower");
	}

	static class MoveHelperController extends MovementController {
		private final GiantGhastEntity parentEntity;

		private int courseChangeCooldown;

		public MoveHelperController(GiantGhastEntity p_i45838_1_) {
			super((MobEntity) p_i45838_1_);
			this.parentEntity = p_i45838_1_;
		}

		public void tick() {
			if (this.action != MovementController.Action.MOVE_TO)
				return;
			if (this.courseChangeCooldown-- <= 0) {
				this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
				Vector3d lvt_1_1_ = new Vector3d(this.posX - this.parentEntity.getPosX(),
						this.posY - this.parentEntity.getPosY(), this.posZ - this.parentEntity.getPosZ());
				double lvt_2_1_ = lvt_1_1_.length();
				lvt_1_1_ = lvt_1_1_.normalize();
				if (func_220673_a(lvt_1_1_, MathHelper.ceil(lvt_2_1_))) {
					this.parentEntity.setMotion(this.parentEntity.getMotion().add(lvt_1_1_.scale(0.1D)));
				} else {
					this.action = MovementController.Action.WAIT;
				}
			}
		}

		private boolean func_220673_a(Vector3d p_220673_1_, int p_220673_2_) {
			AxisAlignedBB lvt_3_1_ = this.parentEntity.getBoundingBox();
			for (int lvt_4_1_ = 1; lvt_4_1_ < p_220673_2_; lvt_4_1_++) {
				lvt_3_1_ = lvt_3_1_.offset(p_220673_1_);
				if (!this.parentEntity.world.hasNoCollisions((Entity) this.parentEntity, lvt_3_1_))
					return false;
			}
			return true;
		}
	}

	static class RandomFlyGoal extends Goal {
		private final GiantGhastEntity parentEntity;

		public RandomFlyGoal(GiantGhastEntity p_i45836_1_) {
			this.parentEntity = p_i45836_1_;
			setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean shouldExecute() {
			MovementController lvt_1_1_ = this.parentEntity.getMoveHelper();
			if (!lvt_1_1_.isUpdating())
				return true;
			double lvt_2_1_ = lvt_1_1_.getX() - this.parentEntity.getPosX();
			double lvt_4_1_ = lvt_1_1_.getY() - this.parentEntity.getPosY();
			double lvt_6_1_ = lvt_1_1_.getZ() - this.parentEntity.getPosZ();
			double lvt_8_1_ = lvt_2_1_ * lvt_2_1_ + lvt_4_1_ * lvt_4_1_ + lvt_6_1_ * lvt_6_1_;
			if (lvt_8_1_ < 1.0D || lvt_8_1_ > 3600.0D)
				return true;
			return false;
		}

		public boolean shouldContinueExecuting() {
			return false;
		}

		public void startExecuting() {
			Random lvt_1_1_ = this.parentEntity.getRNG();
			double lvt_2_1_ = this.parentEntity.getPosX() + ((lvt_1_1_.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double lvt_4_1_ = this.parentEntity.getPosY() + ((lvt_1_1_.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double lvt_6_1_ = this.parentEntity.getPosZ() + ((lvt_1_1_.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.parentEntity.getMoveHelper().setMoveTo(lvt_2_1_, lvt_4_1_, lvt_6_1_, 1.0D);
		}
	}

	static class LookAroundGoal extends Goal {
		private final GiantGhastEntity parentEntity;

		public LookAroundGoal(GiantGhastEntity p_i45839_1_) {
			this.parentEntity = p_i45839_1_;
			setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		public boolean shouldExecute() {
			return true;
		}

		public void tick() {
			if (this.parentEntity.getAttackTarget() == null) {
				Vector3d lvt_1_1_ = this.parentEntity.getMotion();
				this.parentEntity.rotationYaw = -((float) MathHelper.atan2(lvt_1_1_.x, lvt_1_1_.z)) * 57.295776F;
				this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
			} else {
				LivingEntity lvt_1_2_ = this.parentEntity.getAttackTarget();
				if (lvt_1_2_.getDistanceSq((Entity) this.parentEntity) < 4094.0D) {
					double lvt_4_1_ = lvt_1_2_.getPosX() - this.parentEntity.getPosX();
					double lvt_6_1_ = lvt_1_2_.getPosZ() - this.parentEntity.getPosZ();
					this.parentEntity.rotationYaw = -((float) MathHelper.atan2(lvt_4_1_, lvt_6_1_)) * 57.295776F;
					this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
				}
			}
		}
	}

	static class FireballAttackGoal extends Goal {
		private final GiantGhastEntity parentEntity;

		public int attackTimer;

		public FireballAttackGoal(GiantGhastEntity p_i45837_1_) {
			this.parentEntity = p_i45837_1_;
		}

		public boolean shouldExecute() {
			return (this.parentEntity.getAttackTarget() != null);
		}

		public void startExecuting() {
			this.attackTimer = 0;
		}

		public void resetTask() {
			this.parentEntity.setAttacking(false);
		}

		public void tick() {
			LivingEntity lvt_1_1_ = this.parentEntity.getAttackTarget();
			if (lvt_1_1_.getDistanceSq((Entity) this.parentEntity) < 4094.0D
					&& this.parentEntity.canEntityBeSeen((Entity) lvt_1_1_)) {
				World lvt_4_1_ = this.parentEntity.world;
				this.attackTimer++;
				if (this.attackTimer == 10 && !this.parentEntity.isSilent())
					lvt_4_1_.playEvent(null, 1015, this.parentEntity.getPosition(), 0);
				if (this.attackTimer == 20) {
					Vector3d lvt_7_1_ = this.parentEntity.getLook(1.0F);
					double lvt_8_1_ = lvt_1_1_.getPosX() - this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D;
					double lvt_10_1_ = lvt_1_1_.getPosYHeight(0.5D) - 0.5D + this.parentEntity.getPosYHeight(0.5D);
					double lvt_12_1_ = lvt_1_1_.getPosZ() - this.parentEntity.getPosZ() + lvt_7_1_.z * 4.0D;
					if (!this.parentEntity.isSilent())
						lvt_4_1_.playEvent(null, 1016, this.parentEntity.getPosition(), 0);
					me.gentworm.storymobs.entity.projectile.CustomFireballEntity fire1 = new me.gentworm.storymobs.entity.projectile.CustomFireballEntity(
							lvt_4_1_, (LivingEntity) this.parentEntity, lvt_8_1_, lvt_10_1_, lvt_12_1_);
					me.gentworm.storymobs.entity.projectile.CustomFireballEntity fire2 = new me.gentworm.storymobs.entity.projectile.CustomFireballEntity(
							lvt_4_1_, (LivingEntity) this.parentEntity, lvt_8_1_, lvt_10_1_, lvt_12_1_);
					me.gentworm.storymobs.entity.projectile.CustomFireballEntity fire3 = new me.gentworm.storymobs.entity.projectile.CustomFireballEntity(
							lvt_4_1_, (LivingEntity) this.parentEntity, lvt_8_1_, lvt_10_1_, lvt_12_1_);
					me.gentworm.storymobs.entity.projectile.CustomFireballEntity fire4 = new me.gentworm.storymobs.entity.projectile.CustomFireballEntity(
							lvt_4_1_, (LivingEntity) this.parentEntity, lvt_8_1_, lvt_10_1_, lvt_12_1_);
					me.gentworm.storymobs.entity.projectile.CustomFireballEntity fire5 = new me.gentworm.storymobs.entity.projectile.CustomFireballEntity(
							lvt_4_1_, (LivingEntity) this.parentEntity, lvt_8_1_, lvt_10_1_, lvt_12_1_);
					me.gentworm.storymobs.entity.projectile.CustomFireballEntity fire6 = new me.gentworm.storymobs.entity.projectile.CustomFireballEntity(
							lvt_4_1_, (LivingEntity) this.parentEntity, lvt_8_1_, lvt_10_1_, lvt_12_1_);
					me.gentworm.storymobs.entity.projectile.CustomFireballEntity fire7 = new me.gentworm.storymobs.entity.projectile.CustomFireballEntity(
							lvt_4_1_, (LivingEntity) this.parentEntity, lvt_8_1_, lvt_10_1_, lvt_12_1_);
					me.gentworm.storymobs.entity.projectile.CustomFireballEntity fire8 = new me.gentworm.storymobs.entity.projectile.CustomFireballEntity(
							lvt_4_1_, (LivingEntity) this.parentEntity, lvt_8_1_, lvt_10_1_, lvt_12_1_);
					me.gentworm.storymobs.entity.projectile.CustomFireballEntity fire9 = new me.gentworm.storymobs.entity.projectile.CustomFireballEntity(
							lvt_4_1_, (LivingEntity) this.parentEntity, lvt_8_1_, lvt_10_1_, lvt_12_1_);
					me.gentworm.storymobs.entity.projectile.CustomFireballEntity fire10 = new me.gentworm.storymobs.entity.projectile.CustomFireballEntity(
							lvt_4_1_, (LivingEntity) this.parentEntity, lvt_8_1_, lvt_10_1_, lvt_12_1_);

					fire1.explosionPower = this.parentEntity.getFireballStrength();
					fire1.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D,
							this.parentEntity.getPosYHeight(0.5D) + 0.5D, fire1.getPosZ() + lvt_7_1_.z * 4.0D);
					lvt_4_1_.addEntity((Entity) fire1);

					fire2.explosionPower = this.parentEntity.getFireballStrength();
					fire2.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D,
							this.parentEntity.getPosYHeight(0.5D) + 0.5D, fire2.getPosZ() + lvt_7_1_.z * 4.0D);
					lvt_4_1_.addEntity((Entity) fire2);

					fire3.explosionPower = this.parentEntity.getFireballStrength();
					fire3.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D,
							this.parentEntity.getPosYHeight(0.5D) + 0.5D, fire3.getPosZ() + lvt_7_1_.z * 4.0D);
					lvt_4_1_.addEntity((Entity) fire3);

					fire4.explosionPower = this.parentEntity.getFireballStrength();
					fire4.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D,
							this.parentEntity.getPosYHeight(0.5D) + 0.5D, fire4.getPosZ() + lvt_7_1_.z * 4.0D);
					lvt_4_1_.addEntity((Entity) fire4);

					fire5.explosionPower = this.parentEntity.getFireballStrength();
					fire5.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D,
							this.parentEntity.getPosYHeight(0.5D) + 0.5D, fire5.getPosZ() + lvt_7_1_.z * 4.0D);
					lvt_4_1_.addEntity((Entity) fire5);

					fire6.explosionPower = this.parentEntity.getFireballStrength();
					fire6.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D,
							this.parentEntity.getPosYHeight(0.5D) + 0.5D, fire6.getPosZ() + lvt_7_1_.z * 4.0D);
					lvt_4_1_.addEntity((Entity) fire6);

					fire7.explosionPower = this.parentEntity.getFireballStrength();
					fire7.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D,
							this.parentEntity.getPosYHeight(0.5D) + 0.5D, fire7.getPosZ() + lvt_7_1_.z * 4.0D);
					lvt_4_1_.addEntity((Entity) fire7);

					fire8.explosionPower = this.parentEntity.getFireballStrength();
					fire8.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D,
							this.parentEntity.getPosYHeight(0.5D) + 0.5D, fire8.getPosZ() + lvt_7_1_.z * 4.0D);
					lvt_4_1_.addEntity((Entity) fire8);

					fire9.explosionPower = this.parentEntity.getFireballStrength();
					fire9.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D,
							this.parentEntity.getPosYHeight(0.5D) + 0.5D, fire9.getPosZ() + lvt_7_1_.z * 4.0D);
					lvt_4_1_.addEntity((Entity) fire9);

					fire10.explosionPower = this.parentEntity.getFireballStrength();
					fire10.setPosition(this.parentEntity.getPosX() + lvt_7_1_.x * 4.0D,
							this.parentEntity.getPosYHeight(0.5D) + 0.5D, fire10.getPosZ() + lvt_7_1_.z * 4.0D);
					lvt_4_1_.addEntity((Entity) fire10);
					this.attackTimer = -40;
				}
			} else if (this.attackTimer > 0) {
				this.attackTimer--;
			}
			this.parentEntity.setAttacking((this.attackTimer > 10));
		}
	}

	protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
		return 2.6F;
	}
}
