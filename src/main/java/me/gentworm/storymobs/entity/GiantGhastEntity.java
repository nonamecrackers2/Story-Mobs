package me.gentworm.storymobs.entity;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import me.gentworm.storymobs.entity.projectile.CustomFireballEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GiantGhastEntity extends FlyingMob implements Enemy {
	private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(GiantGhastEntity.class,
			EntityDataSerializers.BOOLEAN);

	private int explosionStrength = 2;

	private final ServerBossEvent bossInfo = new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.RED,
			BossEvent.BossBarOverlay.PROGRESS);

	public GiantGhastEntity(EntityType<? extends GiantGhastEntity> p_i50206_1_, Level p_i50206_2_) {
		super(p_i50206_1_, p_i50206_2_);
		this.xpReward = 5;
		this.moveControl = new MoveHelperController(this);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void registerGoals() {
		this.goalSelector.addGoal(3, new GiantGhastEntity.FireballBarrageAttackGoal(this));
		this.goalSelector.addGoal(3, new GiantGhastEntity.FireballClusterAttackGoal(this));
		this.goalSelector.addGoal(5, new GiantGhastEntity.RandomFlyGoal(this));
		this.goalSelector.addGoal(7, new GiantGhastEntity.LookAroundGoal(this));
		this.targetSelector.addGoal(1, (Goal) new NearestAttackableTargetGoal((Mob) this, Player.class, 10,
				true, false, p_213812_1_ -> (Math.abs(((Entity) p_213812_1_).getY() - getY()) <= 4.0D)));
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isAttacking() {
		return ((Boolean) this.entityData.get(ATTACKING)).booleanValue();
	}

	@Override
	public void setCustomName(@Nullable Component name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	public void setAttacking(boolean p_175454_1_) {
		this.entityData.set(ATTACKING, Boolean.valueOf(p_175454_1_));
	}

	public int getFireballStrength() {
		return this.explosionStrength;
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
		if (isInvulnerableTo(p_70097_1_))
			return false;
		if (p_70097_1_.getDirectEntity() instanceof CustomFireballEntity
				&& p_70097_1_.getEntity() instanceof Player) {
			super.hurt(p_70097_1_, 1000.0F);
			return true;
		}
		return super.hurt(p_70097_1_, p_70097_2_);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ATTACKING, Boolean.valueOf(false));
	}

	public static AttributeSupplier.Builder getAttributes() {
		return Mob.createMobAttributes();
	}

	@Override
	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.GHAST_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.GHAST_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.GHAST_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return 5.0F;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag p_213281_1_) {
		super.addAdditionalSaveData(p_213281_1_);
		p_213281_1_.putInt("ExplosionPower", this.explosionStrength);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag p_70037_1_) {
		super.readAdditionalSaveData(p_70037_1_);
		if (p_70037_1_.contains("ExplosionPower", 99))
			this.explosionStrength = p_70037_1_.getInt("ExplosionPower");
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
	}

	static class MoveHelperController extends MoveControl {
		private final GiantGhastEntity parentEntity;

		private int courseChangeCooldown;

		public MoveHelperController(GiantGhastEntity p_i45838_1_) {
			super((Mob) p_i45838_1_);
			this.parentEntity = p_i45838_1_;
		}

		public void tick() {
			if (this.operation != MoveControl.Operation.MOVE_TO)
				return;
			if (this.courseChangeCooldown-- <= 0) {
				this.courseChangeCooldown += this.parentEntity.getRandom().nextInt(5) + 2;
				Vec3 lvt_1_1_ = new Vec3(this.wantedX - this.parentEntity.getX(),
						this.wantedY - this.parentEntity.getY(), this.wantedZ - this.parentEntity.getZ());
				double lvt_2_1_ = lvt_1_1_.length();
				lvt_1_1_ = lvt_1_1_.normalize();
				if (canReach(lvt_1_1_, Mth.ceil(lvt_2_1_))) {
					this.parentEntity.setDeltaMovement(this.parentEntity.getDeltaMovement().add(lvt_1_1_.scale(0.1D)));
				} else {
					this.operation = MoveControl.Operation.WAIT;
				}
			}
		}

		private boolean canReach(Vec3 p_220673_1_, int p_220673_2_) {
			AABB lvt_3_1_ = this.parentEntity.getBoundingBox();
			for (int lvt_4_1_ = 1; lvt_4_1_ < p_220673_2_; lvt_4_1_++) {
				lvt_3_1_ = lvt_3_1_.move(p_220673_1_);
				if (!this.parentEntity.level.noCollision((Entity) this.parentEntity, lvt_3_1_))
					return false;
			}
			return true;
		}
	}

	static class RandomFlyGoal extends Goal {
		private final GiantGhastEntity parentEntity;

		public RandomFlyGoal(GiantGhastEntity p_i45836_1_) {
			this.parentEntity = p_i45836_1_;
			setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			MoveControl lvt_1_1_ = this.parentEntity.getMoveControl();
			if (!lvt_1_1_.hasWanted())
				return true;
			double lvt_2_1_ = lvt_1_1_.getWantedX() - this.parentEntity.getX();
			double lvt_4_1_ = lvt_1_1_.getWantedY() - this.parentEntity.getY();
			double lvt_6_1_ = lvt_1_1_.getWantedZ() - this.parentEntity.getZ();
			double lvt_8_1_ = lvt_2_1_ * lvt_2_1_ + lvt_4_1_ * lvt_4_1_ + lvt_6_1_ * lvt_6_1_;
			if (lvt_8_1_ < 1.0D || lvt_8_1_ > 3600.0D)
				return true;
			return false;
		}

		public boolean canContinueToUse() {
			return false;
		}

		public void start() {
			Random lvt_1_1_ = this.parentEntity.getRandom();
			double lvt_2_1_ = this.parentEntity.getX() + ((lvt_1_1_.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double lvt_4_1_ = this.parentEntity.getY() + ((lvt_1_1_.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double lvt_6_1_ = this.parentEntity.getZ() + ((lvt_1_1_.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.parentEntity.getMoveControl().setWantedPosition(lvt_2_1_, lvt_4_1_, lvt_6_1_, 1.0D);
		}
	}

	static class LookAroundGoal extends Goal {
		private final GiantGhastEntity parentEntity;

		public LookAroundGoal(GiantGhastEntity p_i45839_1_) {
			this.parentEntity = p_i45839_1_;
			setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return true;
		}

		public void tick() {
			if (this.parentEntity.getTarget() == null) {
				Vec3 lvt_1_1_ = this.parentEntity.getDeltaMovement();
				this.parentEntity.yRot = -((float) Mth.atan2(lvt_1_1_.x, lvt_1_1_.z)) * 57.295776F;
				this.parentEntity.yBodyRot = this.parentEntity.yRot;
			} else {
				LivingEntity lvt_1_2_ = this.parentEntity.getTarget();
				if (lvt_1_2_.distanceToSqr((Entity) this.parentEntity) < 4094.0D) {
					double lvt_4_1_ = lvt_1_2_.getX() - this.parentEntity.getX();
					double lvt_6_1_ = lvt_1_2_.getZ() - this.parentEntity.getZ();
					this.parentEntity.yRot = -((float) Mth.atan2(lvt_4_1_, lvt_6_1_)) * 57.295776F;
					this.parentEntity.yBodyRot = this.parentEntity.yRot;
				}
			}
		}
	}

	// CODE FROM TWILIGHT FOREST MOD
	// https://github.com/TeamTwilight/twilightforest/blob/1.16.x/src/main/java/twilightforest/entity/CarminiteGhastguardEntity.java
	public static class FireballClusterAttackGoal extends Goal {
		private final GiantGhastEntity parentEntity;
		public int attackTimer;
		public int prevAttackTimer; // TF - add for renderer

		public FireballClusterAttackGoal(GiantGhastEntity ghast) {
			this.parentEntity = ghast;
		}

		@Override
		public boolean canUse() {
			return this.parentEntity.getTarget() != null
					&& parentEntity.shouldAttack(parentEntity.getTarget());
		}

		@Override
		public void start() {
			this.attackTimer = this.prevAttackTimer = 0;
		}

		@Override
		public void stop() {
			this.parentEntity.setAttacking(false);
		}

		@Override
		public void tick() {
			LivingEntity entitylivingbase = this.parentEntity.getTarget();

			if (entitylivingbase.distanceToSqr(this.parentEntity) < 4096.0D
					&& this.parentEntity.getSensing().canSee(entitylivingbase)) {
				this.prevAttackTimer = attackTimer;
				++this.attackTimer;

				// TF face our target at all times
				this.parentEntity.getLookControl().setLookAt(entitylivingbase, 10F,
						this.parentEntity.getMaxHeadXRot());

				if (this.attackTimer == 10) {
					parentEntity.playSound(SoundEvents.GHAST_WARN, 10.0F, parentEntity.getVoicePitch());
				}

				if (this.attackTimer == 20) {
					if (this.parentEntity.shouldAttack(entitylivingbase)) {
						// TF - call custom method
						parentEntity.playSound(SoundEvents.GHAST_SHOOT, 10.0F, parentEntity.getVoicePitch());
						this.parentEntity.spitClusterFireball();
						this.prevAttackTimer = attackTimer;
					}
					this.attackTimer = -40;
				}
			} else if (this.attackTimer > 0) {
				this.prevAttackTimer = attackTimer;
				--this.attackTimer;
			}

			this.parentEntity.setAttacking(this.attackTimer > 10);
		}
	}

	@Override
	public int getAmbientSoundInterval() {
		return 160;
	}

	@Override
	public void aiStep() {
		// age when in light, like mobs
		if (!level.isClientSide) {
			bossInfo.setPercent(getHealth() / getMaxHealth());
		}
		if (getBrightness() > 0.5F) {
			this.noActionTime += 2;
		}

		if (this.random.nextBoolean()) {
			this.level.addParticle(DustParticleOptions.REDSTONE,
					this.getX() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(),
					this.getY() + this.random.nextDouble() * this.getBbHeight() - 0.25D,
					this.getZ() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(), 0, 0, 0);
		}

		super.aiStep();
	}

	protected boolean shouldAttack(LivingEntity living) {
		return true;
	}

	/**
	 * Something is deeply wrong with the calculations based off of this value, so
	 * let's set it high enough that it's ignored.
	 */
	@Override
	public int getMaxHeadXRot() {
		return 500;
	}

	protected void spitClusterFireball() {
		Vec3 vec3d = this.getViewVector(1.0F);
		double d2 = getTarget().getX() - (this.getX() + vec3d.x * 4.0D);
		double d3 = getTarget().getBoundingBox().minY + getTarget().getBbHeight() / 2.0F
				- (0.5D + this.getY() + this.getBbHeight() / 2.0F);
		double d4 = getTarget().getZ() - (this.getZ() + vec3d.z * 4.0D);
		CustomFireballEntity entitylargefireball1 = new CustomFireballEntity(level, this, d2, d3, d4);
		CustomFireballEntity entitylargefireball2 = new CustomFireballEntity(level, this, d2, d3, d4);
		CustomFireballEntity entitylargefireball3 = new CustomFireballEntity(level, this, d2, d3, d4);
		CustomFireballEntity entitylargefireball4 = new CustomFireballEntity(level, this, d2, d3, d4);
		CustomFireballEntity entitylargefireball5 = new CustomFireballEntity(level, this, d2, d3, d4);
		CustomFireballEntity entitylargefireball6 = new CustomFireballEntity(level, this, d2, d3, d4);
		CustomFireballEntity entitylargefireball7 = new CustomFireballEntity(level, this, d2, d3, d4);

		entitylargefireball1.explosionPower = this.getFireballStrength();
		entitylargefireball1.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 1.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball1);

		entitylargefireball2.explosionPower = this.getFireballStrength();
		entitylargefireball2.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 2.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball2);

		entitylargefireball3.explosionPower = this.getFireballStrength();
		entitylargefireball3.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 3.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball3);

		entitylargefireball4.explosionPower = this.getFireballStrength();
		entitylargefireball4.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 4.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball4);

		entitylargefireball5.explosionPower = this.getFireballStrength();
		entitylargefireball5.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 5.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball5);

		entitylargefireball6.explosionPower = this.getFireballStrength();
		entitylargefireball6.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 6.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball6);

		entitylargefireball7.explosionPower = this.getFireballStrength();
		entitylargefireball7.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 7.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball7);

		// when we attack, there is a 1-in-6 chance we decide to stop attacking
		if (random.nextInt(6) == 0) {
			setTarget(null);
		}
	}

	public void spitBarrageFireball() {
		Vec3 vec3d = this.getViewVector(1.0F);
		double d2 = getTarget().getX() - (this.getX() + vec3d.x * 4.0D);
		double d3 = getTarget().getBoundingBox().minY + getTarget().getBbHeight() / 2.0F
				- (0.5D + this.getY() + this.getBbHeight() / 2.0F);
		double d4 = getTarget().getZ() - (this.getZ() + vec3d.z * 4.0D);
		CustomFireballEntity entitylargefireball1 = new CustomFireballEntity(level, this, d2, d3, d4);

		entitylargefireball1.explosionPower = this.getFireballStrength();
		entitylargefireball1.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 1.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball1);

		CustomFireballEntity entitylargefireball2 = new CustomFireballEntity(level, this, d2, d3, d4);

		entitylargefireball2.explosionPower = this.getFireballStrength();
		entitylargefireball2.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 1.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball2);

		CustomFireballEntity entitylargefireball3 = new CustomFireballEntity(level, this, d2, d3, d4);

		entitylargefireball3.explosionPower = this.getFireballStrength();
		entitylargefireball3.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 1.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball3);

		CustomFireballEntity entitylargefireball4 = new CustomFireballEntity(level, this, d2, d3, d4);

		entitylargefireball4.explosionPower = this.getFireballStrength();
		entitylargefireball4.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 1.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball4);

		CustomFireballEntity entitylargefireball5 = new CustomFireballEntity(level, this, d2, d3, d4);

		entitylargefireball5.explosionPower = this.getFireballStrength();
		entitylargefireball5.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 1.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball5);

		CustomFireballEntity entitylargefireball6 = new CustomFireballEntity(level, this, d2, d3, d4);

		entitylargefireball6.explosionPower = this.getFireballStrength();
		entitylargefireball6.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 1.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball6);

		CustomFireballEntity entitylargefireball7 = new CustomFireballEntity(level, this, d2, d3, d4);

		entitylargefireball7.explosionPower = this.getFireballStrength();
		entitylargefireball7.setPos(this.getX() + vec3d.x * 4.0D,
				this.getY() + this.getBbHeight() / 1.0F + 0.5D, this.getZ() + vec3d.z * 4.0D);
		level.addFreshEntity(entitylargefireball7);

		// when we attack, there is a 1-in-6 chance we decide to stop attacking
		if (random.nextInt(6) == 0) {
			setTarget(null);
		}
	}

	static class FireballBarrageAttackGoal extends Goal {
		public int attackTimer;
		public int prevAttackTimer;
		private final GiantGhastEntity ghast;

		FireballBarrageAttackGoal(GiantGhastEntity ghast) {
			this.ghast = ghast;
		}

		@Override
		public boolean canUse() {
			return this.ghast.getTarget() != null && ghast.shouldAttack(ghast.getTarget());
		}

		public boolean shouldAttack(LivingEntity entity) {
			return true;
		}

		@Override
		public void tick() {
			LivingEntity livingentity = this.ghast.getTarget();
			if (livingentity.distanceToSqr(this.ghast) < 4096.0D && this.ghast.canSee(livingentity)) {
				Level world = this.ghast.level;
				++this.attackTimer;
				if (this.attackTimer == 10 && !this.ghast.isSilent()) {
					world.levelEvent((Player) null, 1015, this.ghast.blockPosition(), 0);
				}

				if (this.attackTimer == 20) {
					Vec3 vector3d = this.ghast.getViewVector(1.0F);
					double d2 = livingentity.getX() - (this.ghast.getX() + vector3d.x * 4.0D);
					double d3 = livingentity.getY(0.5D) - (0.5D + this.ghast.getY(0.5D));
					double d4 = livingentity.getZ() - (this.ghast.getZ() + vector3d.z * 4.0D);
					if (!this.ghast.isSilent()) {
						world.levelEvent((Player) null, 1016, this.ghast.blockPosition(), 0);
					}

					CustomFireballEntity fireballentity = new CustomFireballEntity(world, this.ghast, d2, d3, d4);
					fireballentity.explosionPower = this.ghast.getFireballStrength();
					fireballentity.setPos(this.ghast.getX() + vector3d.x * 4.0D,
							this.ghast.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 4.0D);
					world.addFreshEntity(fireballentity);
					this.attackTimer = -40;
				}
			} else if (this.attackTimer > 0) {
				--this.attackTimer;
			}

			this.ghast.setAttacking(this.attackTimer > 10);
		}
	}
}
