package me.gentworm.storymobs.entity;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;

public class RedSlimeEntity extends Mob implements Enemy {
	private static final EntityDataAccessor<Integer> SLIME_SIZE = SynchedEntityData.defineId(RedSlimeEntity.class,
			EntityDataSerializers.INT);
	public float squishAmount;
	public float squishFactor;
	public float prevSquishFactor;
	private boolean wasOnGround;

	public RedSlimeEntity(EntityType<? extends RedSlimeEntity> type, Level worldIn) {
		super(type, worldIn);
		this.moveControl = new RedSlimeEntity.MoveHelperController(this);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(1, new RedSlimeEntity.FloatGoal(this));
		this.goalSelector.addGoal(2, new RedSlimeEntity.AttackGoal(this));
		this.goalSelector.addGoal(3, new RedSlimeEntity.FaceRandomGoal(this));
		this.goalSelector.addGoal(5, new RedSlimeEntity.HopGoal(this));
		this.targetSelector.addGoal(1,
				new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_213811_1_) -> {
					return Math.abs(p_213811_1_.getY() - this.getY()) <= 4.0D;
				}));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SLIME_SIZE, 1);
	}

	protected void setSlimeSize(int size, boolean resetHealth) {
		this.entityData.set(SLIME_SIZE, size);
		this.reapplyPosition();
		this.refreshDimensions();
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double) (size * size));
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) (0.2F + 0.1F * (float) size));
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double) size);
		if (resetHealth) {
			this.setHealth(this.getMaxHealth());
		}

		this.xpReward = size;
	}

	/**
	 * Returns the size of the slime.
	 */
	public int getSlimeSize() {
		return this.entityData.get(SLIME_SIZE);
	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Size", this.getSlimeSize() - 1);
		compound.putBoolean("wasOnGround", this.wasOnGround);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditionalSaveData(CompoundTag compound) {
		int i = compound.getInt("Size");
		if (i < 0) {
			i = 0;
		}

		this.setSlimeSize(i + 1, false);
		super.readAdditionalSaveData(compound);
		this.wasOnGround = compound.getBoolean("wasOnGround");
	}

	public boolean isSmallSlime() {
		return this.getSlimeSize() <= 1;
	}

	protected boolean shouldDespawnInPeaceful() {
		return this.getSlimeSize() > 0;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void tick() {
		this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
		this.prevSquishFactor = this.squishFactor;
		super.tick();
		if (this.onGround && !this.wasOnGround) {
			int i = this.getSlimeSize();

			if (spawnCustomParticles())
				i = 0; // don't spawn particles if it's handled by the implementation itself
			for (int j = 0; j < i * 8; ++j) {
				float f = this.random.nextFloat() * ((float) Math.PI * 2F);
				float f1 = this.random.nextFloat() * 0.5F + 0.5F;
				float f2 = Mth.sin(f) * (float) i * 0.5F * f1;
				float f3 = Mth.cos(f) * (float) i * 0.5F * f1;
	            this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Blocks.REDSTONE_BLOCK)), this.getX() + (double) f2, this.getY(), this.getZ() + (double) f3, 0.0D, 0.0D, 0.0D);

			}

			this.playSound(this.getSquishSound(), this.getSoundVolume(),
					((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			this.squishAmount = -0.5F;
		} else if (!this.onGround && this.wasOnGround) {
			this.squishAmount = 1.0F;
		}

		this.wasOnGround = this.onGround;
		this.alterSquishAmount();
	}

	protected void alterSquishAmount() {
		this.squishAmount *= 0.6F;
	}

	protected int getJumpDelay() {
		return this.random.nextInt(20) + 10;
	}

	public void refreshDimensions() {
		double d0 = this.getX();
		double d1 = this.getY();
		double d2 = this.getZ();
		super.refreshDimensions();
		this.setPos(d0, d1, d2);
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		if (SLIME_SIZE.equals(key)) {
			this.refreshDimensions();
			this.yRot = this.yHeadRot;
			this.yBodyRot = this.yHeadRot;
			if (this.isInWater() && this.random.nextInt(20) == 0) {
				this.doWaterSplashEffect();
			}
		}

		super.onSyncedDataUpdated(key);
	}

	@SuppressWarnings({ "unchecked" })
	public EntityType<? extends RedSlimeEntity> getType() {
		return (EntityType<? extends RedSlimeEntity>) super.getType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void remove(boolean keepData) {
		int i = this.getSlimeSize();
		if (!this.level.isClientSide && i > 1 && this.isDeadOrDying() && !this.removed) {
			Component itextcomponent = this.getCustomName();
			boolean flag = this.isNoAi();
			float f = (float) i / 4.0F;
			int j = i / 2;
			int k = 2 + this.random.nextInt(3);

			for (int l = 0; l < k; ++l) {
				float f1 = ((float) (l % 2) - 0.5F) * f;
				float f2 = ((float) (l / 2) - 0.5F) * f;
				RedSlimeEntity slimeentity = this.getType().create(this.level);
				if (this.isPersistenceRequired()) {
					slimeentity.setPersistenceRequired();
				}

				slimeentity.setCustomName(itextcomponent);
				slimeentity.setNoAi(flag);
				slimeentity.setInvulnerable(this.isInvulnerable());
				slimeentity.setSlimeSize(j, true);
				slimeentity.moveTo(this.getX() + (double) f1, this.getY() + 0.5D,
						this.getZ() + (double) f2, this.random.nextFloat() * 360.0F, 0.0F);
				this.level.addFreshEntity(slimeentity);
			}
		}

		super.remove(keepData);
	}

	/**
	 * Applies a velocity to the entities, to push them away from eachother.
	 */
	public void push(Entity entityIn) {
		super.push(entityIn);
		if (entityIn instanceof IronGolem && this.canDamagePlayer()) {
			this.dealDamage((LivingEntity) entityIn);
		}

	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	public void playerTouch(Player entityIn) {
		if (this.canDamagePlayer()) {
			this.dealDamage(entityIn);
		}

	}

	protected void dealDamage(LivingEntity entityIn) {
		if (this.isAlive()) {
			int i = this.getSlimeSize();
			if (this.distanceToSqr(entityIn) < 0.6D * (double) i * 0.6D * (double) i && this.canSee(entityIn)
					&& entityIn.hurt(DamageSource.mobAttack(this), this.getAttackDamage())) {
				this.playSound(SoundEvents.SLIME_ATTACK, 1.0F,
						(this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
				this.doEnchantDamageEffects(this, entityIn);
			}
		}

	}

	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 0.625F * sizeIn.height;
	}

	/**
	 * Indicates weather the slime is able to damage the player (based upon the
	 * slime's size)
	 */
	protected boolean canDamagePlayer() {
		return !this.isSmallSlime() && this.isEffectiveAi();
	}

	protected float getAttackDamage() {
		return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return this.isSmallSlime() ? SoundEvents.SLIME_HURT_SMALL : SoundEvents.SLIME_HURT;
	}

	protected SoundEvent getDeathSound() {
		return this.isSmallSlime() ? SoundEvents.SLIME_DEATH_SMALL : SoundEvents.SLIME_DEATH;
	}

	protected SoundEvent getSquishSound() {
		return this.isSmallSlime() ? SoundEvents.SLIME_SQUISH_SMALL : SoundEvents.SLIME_SQUISH;
	}

	protected ResourceLocation getDefaultLootTable() {
		return this.getSlimeSize() == 1 ? this.getType().getDefaultLootTable() : BuiltInLootTables.EMPTY;
	}
	
	public static AttributeSupplier.Builder getAttributes() {
		return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 6.0D)
				.add(Attributes.FOLLOW_RANGE, 22.0D);
	}

	protected float getSoundVolume() {
		return 0.4F * (float) this.getSlimeSize();
	}

	/**
	 * The speed it takes to move the entityliving's rotationPitch through the
	 * faceEntity method. This is only currently use in wolves.
	 */
	public int getMaxHeadXRot() {
		return 0;
	}

	/**
	 * Returns true if the slime makes a sound when it jumps (based upon the slime's
	 * size)
	 */
	protected boolean makesSoundOnJump() {
		return this.getSlimeSize() > 0;
	}

	/**
	 * Causes this entity to do an upwards motion (jumping).
	 */
	protected void jumpFromGround() {
		Vec3 vector3d = this.getDeltaMovement();
		this.setDeltaMovement(vector3d.x, (double) this.getJumpPower(), vector3d.z);
		this.hasImpulse = true;
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason,
			@Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		int i = this.random.nextInt(3);
		if (i < 2 && this.random.nextFloat() < 0.5F * difficultyIn.getSpecialMultiplier()) {
			++i;
		}

		int j = 1 << i;
		this.setSlimeSize(j, true);
		return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	private float getSoundPitch() {
		float f = this.isSmallSlime() ? 1.4F : 0.8F;
		return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * f;
	}

	protected SoundEvent getJumpSound() {
		return this.isSmallSlime() ? SoundEvents.SLIME_JUMP_SMALL : SoundEvents.SLIME_JUMP;
	}

	public EntityDimensions getDimensions(Pose poseIn) {
		return super.getDimensions(poseIn).scale(0.255F * (float) this.getSlimeSize());
	}

	protected boolean spawnCustomParticles() {
		return false;
	}

	static class AttackGoal extends Goal {
		private final RedSlimeEntity slime;
		private int growTieredTimer;

		public AttackGoal(RedSlimeEntity slimeIn) {
			this.slime = slimeIn;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		public boolean canUse() {
			LivingEntity livingentity = this.slime.getTarget();
			if (livingentity == null) {
				return false;
			} else if (!livingentity.isAlive()) {
				return false;
			} else {
				return livingentity instanceof Player && ((Player) livingentity).abilities.invulnerable
						? false
						: this.slime.getMoveControl() instanceof RedSlimeEntity.MoveHelperController;
			}
		}

		public void start() {
			this.growTieredTimer = 300;
			super.start();
		}

		public boolean canContinueToUse() {
			LivingEntity livingentity = this.slime.getTarget();
			if (livingentity == null) {
				return false;
			} else if (!livingentity.isAlive()) {
				return false;
			} else if (livingentity instanceof Player && ((Player) livingentity).abilities.invulnerable) {
				return false;
			} else {
				return --this.growTieredTimer > 0;
			}
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			this.slime.lookAt(this.slime.getTarget(), 10.0F, 10.0F);
			((RedSlimeEntity.MoveHelperController) this.slime.getMoveControl()).setDirection(this.slime.yRot,
					this.slime.canDamagePlayer());
		}
	}

	static class FaceRandomGoal extends Goal {
		private final RedSlimeEntity slime;
		private float chosenDegrees;
		private int nextRandomizeTime;

		public FaceRandomGoal(RedSlimeEntity slimeIn) {
			this.slime = slimeIn;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state
		 * necessary for execution in this method as well.
		 */
		public boolean canUse() {
			return this.slime.getTarget() == null
					&& (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava()
							|| this.slime.hasEffect(MobEffects.LEVITATION))
					&& this.slime.getMoveControl() instanceof RedSlimeEntity.MoveHelperController;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			if (--this.nextRandomizeTime <= 0) {
				this.nextRandomizeTime = 40 + this.slime.getRandom().nextInt(60);
				this.chosenDegrees = (float) this.slime.getRandom().nextInt(360);
			}

			((RedSlimeEntity.MoveHelperController) this.slime.getMoveControl()).setDirection(this.chosenDegrees, false);
		}
	}

	static class FloatGoal extends Goal {
		private final RedSlimeEntity slime;

		public FloatGoal(RedSlimeEntity slimeIn) {
			this.slime = slimeIn;
			this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
			slimeIn.getNavigation().setCanFloat(true);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state
		 * necessary for execution in this method as well.
		 */
		public boolean canUse() {
			return (this.slime.isInWater() || this.slime.isInLava())
					&& this.slime.getMoveControl() instanceof RedSlimeEntity.MoveHelperController;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			if (this.slime.getRandom().nextFloat() < 0.8F) {
				this.slime.getJumpControl().jump();
			}

			((RedSlimeEntity.MoveHelperController) this.slime.getMoveControl()).setSpeed(1.2D);
		}
	}

	static class HopGoal extends Goal {
		private final RedSlimeEntity slime;

		public HopGoal(RedSlimeEntity slimeIn) {
			this.slime = slimeIn;
			this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state
		 * necessary for execution in this method as well.
		 */
		public boolean canUse() {
			return !this.slime.isPassenger();
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			((RedSlimeEntity.MoveHelperController) this.slime.getMoveControl()).setSpeed(1.0D);
		}
	}

	static class MoveHelperController extends MoveControl {
		private float yRot;
		private int jumpDelay;
		private final RedSlimeEntity slime;
		private boolean isAggressive;

		public MoveHelperController(RedSlimeEntity slimeIn) {
			super(slimeIn);
			this.slime = slimeIn;
			this.yRot = 180.0F * slimeIn.yRot / (float) Math.PI;
		}

		public void setDirection(float yRotIn, boolean aggressive) {
			this.yRot = yRotIn;
			this.isAggressive = aggressive;
		}

		public void setSpeed(double speedIn) {
			this.speedModifier = speedIn;
			this.operation = MoveControl.Operation.MOVE_TO;
		}

		public void tick() {
			this.mob.yRot = this.rotlerp(this.mob.yRot, this.yRot, 90.0F);
			this.mob.yHeadRot = this.mob.yRot;
			this.mob.yBodyRot = this.mob.yRot;
			if (this.operation != MoveControl.Operation.MOVE_TO) {
				this.mob.setZza(0.0F);
			} else {
				this.operation = MoveControl.Operation.WAIT;
				if (this.mob.isOnGround()) {
					this.mob.setSpeed(
							(float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
					if (this.jumpDelay-- <= 0) {
						this.jumpDelay = this.slime.getJumpDelay();
						if (this.isAggressive) {
							this.jumpDelay /= 3;
						}

						this.slime.getJumpControl().jump();
						if (this.slime.makesSoundOnJump()) {
							this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(),
									this.slime.getSoundPitch());
						}
					} else {
						this.slime.xxa = 0.0F;
						this.slime.zza = 0.0F;
						this.mob.setSpeed(0.0F);
					}
				} else {
					this.mob.setSpeed(
							(float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
				}

			}
		}
	}

	public static boolean canRedSlimeSpawn(EntityType<RedSlimeEntity> p_223366_0_, LevelAccessor p_223366_1_,
			MobSpawnType reason, BlockPos p_223366_3_, Random randomIn) {
		if (p_223366_1_.getDifficulty() != Difficulty.PEACEFUL) {
			if (Objects.equals(p_223366_1_.getBiomeName(p_223366_3_), Optional.of(Biomes.SWAMP))
					&& p_223366_3_.getY() > 50 && p_223366_3_.getY() < 70 && randomIn.nextFloat() < 0.5F
					&& randomIn.nextFloat() < p_223366_1_.getMoonBrightness()
					&& p_223366_1_.getMaxLocalRawBrightness(p_223366_3_) <= randomIn.nextInt(8)) {
				return checkMobSpawnRules(p_223366_0_, p_223366_1_, reason, p_223366_3_, randomIn);
			}

			if (!(p_223366_1_ instanceof WorldGenLevel)) {
				return false;
			}

			ChunkPos chunkpos = new ChunkPos(p_223366_3_);
			boolean flag = WorldgenRandom
					.seedSlimeChunk(chunkpos.x, chunkpos.z, ((WorldGenLevel) p_223366_1_).getSeed(), 987234911L)
					.nextInt(10) == 0;
			if (randomIn.nextInt(10) == 0 && flag && p_223366_3_.getY() < 40) {
				return checkMobSpawnRules(p_223366_0_, p_223366_1_, reason, p_223366_3_, randomIn);
			}
		}

		return false;
	}
}
