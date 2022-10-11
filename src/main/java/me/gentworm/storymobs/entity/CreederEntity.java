package me.gentworm.storymobs.entity;

import java.util.Collection;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;


//A very dangerous buffed creeper and spider hybrid!
public class CreederEntity extends Creeper {

	private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(CreederEntity.class,
			EntityDataSerializers.BYTE);

	private int timeSinceIgnited;
	private double explosionRadius = 4.5D;
	private int fuseTime = 27;

	public CreederEntity(EntityType<? extends CreederEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.4F));
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.7F;
	}

	@Override
	protected PathNavigation createNavigation(Level worldIn) {
		return new WallClimberNavigation(this, worldIn);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(CLIMBING, (byte) 0);
	}

	public void tick() {
		if (!this.level.isClientSide) {
			this.setBesideClimbableBlock(this.horizontalCollision);
		}
		if (this.isAlive()) {
			if (this.isIgnited()) {
				this.setSwellDir(1);
			}

			int i = this.getSwellDir();
			if (i > 0 && this.timeSinceIgnited == 0) {
				this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i;
			if (this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;
				this.explode();
			}
		}
		super.tick();
	}

	public static boolean canCreederSpawn(EntityType<CreederEntity> animal, LevelAccessor world, MobSpawnType reason,
			BlockPos pos, Random random) {
		BlockState state = world.getBlockState(pos.below());
		return (state.is(Blocks.RED_SAND) || state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.RED_TERRACOTTA)
				|| state.is(Blocks.ORANGE_TERRACOTTA) || state.is(Blocks.TERRACOTTA)
				|| state.is(Blocks.YELLOW_TERRACOTTA) || state.is(Blocks.WHITE_TERRACOTTA));
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	public static AttributeSupplier.Builder getAttributes() {
		return Monster.createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.35D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.40D)
				.add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.FOLLOW_RANGE, 30.0D);
	}

	public boolean onClimbable() {
		return this.isBesideClimbableBlock();
	}

	public boolean isBesideClimbableBlock() {
		return (this.entityData.get(CLIMBING) & 1) != 0;
	}

	public void setBesideClimbableBlock(boolean climbing) {
		byte b0 = this.entityData.get(CLIMBING);
		if (climbing) {
			b0 = (byte) (b0 | 1);
		} else {
			b0 = (byte) (b0 & -2);
		}

		this.entityData.set(CLIMBING, b0);
	}

	private void spawnLingeringCloud() {
		Collection<MobEffectInstance> collection = this.getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(),
					this.getY(), this.getZ());
			areaeffectcloudentity.setRadius(2.5F);
			areaeffectcloudentity.setRadiusOnUse(-0.5F);
			areaeffectcloudentity.setWaitTime(10);
			areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
			areaeffectcloudentity
					.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());

			for (MobEffectInstance effectinstance : collection) {
				areaeffectcloudentity.addEffect(new MobEffectInstance(effectinstance));
			}

			this.level.addFreshEntity(areaeffectcloudentity);
		}

	}

	private void explode() {
		if (!this.level.isClientSide) {
			Explosion.BlockInteraction explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level,
					this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
			float f = this.isPowered() ? 2.0F : 1.0F;
			this.dead = true;
			this.level.explode(this, this.getX(), this.getY(), this.getZ(),
					(float) this.explosionRadius * f, explosion$mode);
			this.remove();
			this.spawnLingeringCloud();
		}
	}
}
