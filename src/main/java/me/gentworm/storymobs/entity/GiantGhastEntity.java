package me.gentworm.storymobs.entity;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import me.gentworm.storymobs.entity.projectile.CustomFireballEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GiantGhastEntity extends FlyingEntity implements IMob {
	private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(GiantGhastEntity.class,
			DataSerializers.BOOLEAN);

	private int explosionStrength = 2;

	private final ServerBossInfo bossInfo = new ServerBossInfo(getDisplayName(), BossInfo.Color.RED,
			BossInfo.Overlay.PROGRESS);

	public GiantGhastEntity(EntityType<? extends GiantGhastEntity> p_i50206_1_, World p_i50206_2_) {
		super(p_i50206_1_, p_i50206_2_);
		this.experienceValue = 5;
		this.moveController = new MoveHelperController(this);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void registerGoals() {
		this.goalSelector.addGoal(3, new GiantGhastEntity.FireballBarrageAttackGoal(this));
		this.goalSelector.addGoal(3, new GiantGhastEntity.FireballClusterAttackGoal(this));
		this.goalSelector.addGoal(5, new GiantGhastEntity.RandomFlyGoal(this));
		this.goalSelector.addGoal(7, new GiantGhastEntity.LookAroundGoal(this));
		this.targetSelector.addGoal(1, (Goal) new NearestAttackableTargetGoal((MobEntity) this, PlayerEntity.class, 10,
				true, false, p_213812_1_ -> (Math.abs(((Entity) p_213812_1_).getPosY() - getPosY()) <= 4.0D)));
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isAttacking() {
		return ((Boolean) this.dataManager.get(ATTACKING)).booleanValue();
	}

	@Override
	public void setCustomName(@Nullable ITextComponent name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	public void setAttacking(boolean p_175454_1_) {
		this.dataManager.set(ATTACKING, Boolean.valueOf(p_175454_1_));
	}

	public int getFireballStrength() {
		return this.explosionStrength;
	}

	@Override
	public void addTrackingPlayer(ServerPlayerEntity player) {
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(ServerPlayerEntity player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	protected boolean isDespawnPeaceful() {
		return true;
	}

	@Override
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

	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_GHAST_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.ENTITY_GHAST_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_GHAST_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return 5.0F;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}

	@Override
	public void writeAdditional(CompoundNBT p_213281_1_) {
		super.writeAdditional(p_213281_1_);
		p_213281_1_.putInt("ExplosionPower", this.explosionStrength);
	}

	@Override
	public void readAdditional(CompoundNBT p_70037_1_) {
		super.readAdditional(p_70037_1_);
		if (p_70037_1_.contains("ExplosionPower", 99))
			this.explosionStrength = p_70037_1_.getInt("ExplosionPower");
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
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
		public boolean shouldExecute() {
			return this.parentEntity.getAttackTarget() != null
					&& parentEntity.shouldAttack(parentEntity.getAttackTarget());
		}

		@Override
		public void startExecuting() {
			this.attackTimer = this.prevAttackTimer = 0;
		}

		@Override
		public void resetTask() {
			this.parentEntity.setAttacking(false);
		}

		@Override
		public void tick() {
			LivingEntity entitylivingbase = this.parentEntity.getAttackTarget();

			if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D
					&& this.parentEntity.getEntitySenses().canSee(entitylivingbase)) {
				this.prevAttackTimer = attackTimer;
				++this.attackTimer;

				// TF face our target at all times
				this.parentEntity.getLookController().setLookPositionWithEntity(entitylivingbase, 10F,
						this.parentEntity.getVerticalFaceSpeed());

				if (this.attackTimer == 10) {
					parentEntity.playSound(SoundEvents.ENTITY_GHAST_WARN, 10.0F, parentEntity.getSoundPitch());
				}

				if (this.attackTimer == 20) {
					if (this.parentEntity.shouldAttack(entitylivingbase)) {
						// TF - call custom method
						parentEntity.playSound(SoundEvents.ENTITY_GHAST_SHOOT, 10.0F, parentEntity.getSoundPitch());
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
	public int getTalkInterval() {
		return 160;
	}

	@Override
	public void livingTick() {
		// age when in light, like mobs
		if (!world.isRemote) {
			bossInfo.setPercent(getHealth() / getMaxHealth());
		}
		if (getBrightness() > 0.5F) {
			this.idleTime += 2;
		}

		if (this.rand.nextBoolean()) {
			this.world.addParticle(RedstoneParticleData.REDSTONE_DUST,
					this.getPosX() + (this.rand.nextDouble() - 0.5D) * this.getWidth(),
					this.getPosY() + this.rand.nextDouble() * this.getHeight() - 0.25D,
					this.getPosZ() + (this.rand.nextDouble() - 0.5D) * this.getWidth(), 0, 0, 0);
		}

		super.livingTick();
	}

	protected boolean shouldAttack(LivingEntity living) {
		return true;
	}

	/**
	 * Something is deeply wrong with the calculations based off of this value, so
	 * let's set it high enough that it's ignored.
	 */
	@Override
	public int getVerticalFaceSpeed() {
		return 500;
	}

	protected void spitClusterFireball() {
		Vector3d vec3d = this.getLook(1.0F);
		double d2 = getAttackTarget().getPosX() - (this.getPosX() + vec3d.x * 4.0D);
		double d3 = getAttackTarget().getBoundingBox().minY + getAttackTarget().getHeight() / 2.0F
				- (0.5D + this.getPosY() + this.getHeight() / 2.0F);
		double d4 = getAttackTarget().getPosZ() - (this.getPosZ() + vec3d.z * 4.0D);
		CustomFireballEntity entitylargefireball1 = new CustomFireballEntity(world, this, d2, d3, d4);
		CustomFireballEntity entitylargefireball2 = new CustomFireballEntity(world, this, d2, d3, d4);
		CustomFireballEntity entitylargefireball3 = new CustomFireballEntity(world, this, d2, d3, d4);
		CustomFireballEntity entitylargefireball4 = new CustomFireballEntity(world, this, d2, d3, d4);
		CustomFireballEntity entitylargefireball5 = new CustomFireballEntity(world, this, d2, d3, d4);
		CustomFireballEntity entitylargefireball6 = new CustomFireballEntity(world, this, d2, d3, d4);
		CustomFireballEntity entitylargefireball7 = new CustomFireballEntity(world, this, d2, d3, d4);

		entitylargefireball1.explosionPower = this.getFireballStrength();
		entitylargefireball1.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 1.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball1);

		entitylargefireball2.explosionPower = this.getFireballStrength();
		entitylargefireball2.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 2.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball2);

		entitylargefireball3.explosionPower = this.getFireballStrength();
		entitylargefireball3.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 3.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball3);

		entitylargefireball4.explosionPower = this.getFireballStrength();
		entitylargefireball4.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 4.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball4);

		entitylargefireball5.explosionPower = this.getFireballStrength();
		entitylargefireball5.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 5.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball5);

		entitylargefireball6.explosionPower = this.getFireballStrength();
		entitylargefireball6.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 6.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball6);

		entitylargefireball7.explosionPower = this.getFireballStrength();
		entitylargefireball7.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 7.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball7);

		// when we attack, there is a 1-in-6 chance we decide to stop attacking
		if (rand.nextInt(6) == 0) {
			setAttackTarget(null);
		}
	}

	public void spitBarrageFireball() {
		Vector3d vec3d = this.getLook(1.0F);
		double d2 = getAttackTarget().getPosX() - (this.getPosX() + vec3d.x * 4.0D);
		double d3 = getAttackTarget().getBoundingBox().minY + getAttackTarget().getHeight() / 2.0F
				- (0.5D + this.getPosY() + this.getHeight() / 2.0F);
		double d4 = getAttackTarget().getPosZ() - (this.getPosZ() + vec3d.z * 4.0D);
		CustomFireballEntity entitylargefireball1 = new CustomFireballEntity(world, this, d2, d3, d4);

		entitylargefireball1.explosionPower = this.getFireballStrength();
		entitylargefireball1.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 1.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball1);

		CustomFireballEntity entitylargefireball2 = new CustomFireballEntity(world, this, d2, d3, d4);

		entitylargefireball2.explosionPower = this.getFireballStrength();
		entitylargefireball2.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 1.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball2);

		CustomFireballEntity entitylargefireball3 = new CustomFireballEntity(world, this, d2, d3, d4);

		entitylargefireball3.explosionPower = this.getFireballStrength();
		entitylargefireball3.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 1.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball3);

		CustomFireballEntity entitylargefireball4 = new CustomFireballEntity(world, this, d2, d3, d4);

		entitylargefireball4.explosionPower = this.getFireballStrength();
		entitylargefireball4.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 1.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball4);

		CustomFireballEntity entitylargefireball5 = new CustomFireballEntity(world, this, d2, d3, d4);

		entitylargefireball5.explosionPower = this.getFireballStrength();
		entitylargefireball5.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 1.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball5);

		CustomFireballEntity entitylargefireball6 = new CustomFireballEntity(world, this, d2, d3, d4);

		entitylargefireball6.explosionPower = this.getFireballStrength();
		entitylargefireball6.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 1.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball6);

		CustomFireballEntity entitylargefireball7 = new CustomFireballEntity(world, this, d2, d3, d4);

		entitylargefireball7.explosionPower = this.getFireballStrength();
		entitylargefireball7.setPosition(this.getPosX() + vec3d.x * 4.0D,
				this.getPosY() + this.getHeight() / 1.0F + 0.5D, this.getPosZ() + vec3d.z * 4.0D);
		world.addEntity(entitylargefireball7);

		// when we attack, there is a 1-in-6 chance we decide to stop attacking
		if (rand.nextInt(6) == 0) {
			setAttackTarget(null);
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
		public boolean shouldExecute() {
			return this.ghast.getAttackTarget() != null && ghast.shouldAttack(ghast.getAttackTarget());
		}

		public boolean shouldAttack(LivingEntity entity) {
			return true;
		}

		@Override
		public void tick() {
			LivingEntity livingentity = this.ghast.getAttackTarget();
			if (livingentity.getDistanceSq(this.ghast) < 4096.0D && this.ghast.canEntityBeSeen(livingentity)) {
				World world = this.ghast.world;
				++this.attackTimer;
				if (this.attackTimer == 10 && !this.ghast.isSilent()) {
					world.playEvent((PlayerEntity) null, 1015, this.ghast.getPosition(), 0);
				}

				if (this.attackTimer == 20) {
					Vector3d vector3d = this.ghast.getLook(1.0F);
					double d2 = livingentity.getPosX() - (this.ghast.getPosX() + vector3d.x * 4.0D);
					double d3 = livingentity.getPosYHeight(0.5D) - (0.5D + this.ghast.getPosYHeight(0.5D));
					double d4 = livingentity.getPosZ() - (this.ghast.getPosZ() + vector3d.z * 4.0D);
					if (!this.ghast.isSilent()) {
						world.playEvent((PlayerEntity) null, 1016, this.ghast.getPosition(), 0);
					}

					CustomFireballEntity fireballentity = new CustomFireballEntity(world, this.ghast, d2, d3, d4);
					fireballentity.explosionPower = this.ghast.getFireballStrength();
					fireballentity.setPosition(this.ghast.getPosX() + vector3d.x * 4.0D,
							this.ghast.getPosYHeight(0.5D) + 0.5D, fireballentity.getPosZ() + vector3d.z * 4.0D);
					world.addEntity(fireballentity);
					this.attackTimer = -40;
				}
			} else if (this.attackTimer > 0) {
				--this.attackTimer;
			}

			this.ghast.setAttacking(this.attackTimer > 10);
		}
	}
}
