package me.gentworm.storymobs.entity;

import java.util.Collection;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class CreederEntity extends CreeperEntity {

	private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(CreederEntity.class,
			DataSerializers.BYTE);

	private int timeSinceIgnited;
	private double explosionRadius = 7.5D;
	private int fuseTime = 28;

	public CreederEntity(EntityType<? extends CreederEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 0.35D));
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 1.7F;
	}

	@Override
	protected PathNavigator createNavigator(World worldIn) {
		return new ClimberPathNavigator(this, worldIn);
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(CLIMBING, (byte) 0);
	}

	public void tick() {
		if (!this.world.isRemote) {
			this.setBesideClimbableBlock(this.collidedHorizontally);
		}
		if (this.isAlive()) {
			if (this.hasIgnited()) {
				this.setCreeperState(1);
			}

			int i = this.getCreeperState();
			if (i > 0 && this.timeSinceIgnited == 0) {
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
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

	public static boolean canCreederSpawn(EntityType<CreederEntity> animal, IWorld world, SpawnReason reason,
			BlockPos pos, Random random) {
		BlockState state = world.getBlockState(pos.down());
		return (state.isIn(Blocks.RED_SAND) || state.isIn(Blocks.GRASS_BLOCK) || state.isIn(Blocks.RED_TERRACOTTA)
				|| state.isIn(Blocks.ORANGE_TERRACOTTA) || state.isIn(Blocks.TERRACOTTA)
				|| state.isIn(Blocks.YELLOW_TERRACOTTA) || state.isIn(Blocks.WHITE_TERRACOTTA));
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}

	public static AttributeModifierMap.MutableAttribute getAttributes() {
		return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.31D)
				.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.40D)
				.createMutableAttribute(Attributes.MAX_HEALTH, 30.0D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D);
	}

	public boolean isOnLadder() {
		return this.isBesideClimbableBlock();
	}

	public boolean isBesideClimbableBlock() {
		return (this.dataManager.get(CLIMBING) & 1) != 0;
	}

	public void setBesideClimbableBlock(boolean climbing) {
		byte b0 = this.dataManager.get(CLIMBING);
		if (climbing) {
			b0 = (byte) (b0 | 1);
		} else {
			b0 = (byte) (b0 & -2);
		}

		this.dataManager.set(CLIMBING, b0);
	}

	private void spawnLingeringCloud() {
		Collection<EffectInstance> collection = this.getActivePotionEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(),
					this.getPosY(), this.getPosZ());
			areaeffectcloudentity.setRadius(2.5F);
			areaeffectcloudentity.setRadiusOnUse(-0.5F);
			areaeffectcloudentity.setWaitTime(10);
			areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
			areaeffectcloudentity
					.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());

			for (EffectInstance effectinstance : collection) {
				areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
			}

			this.world.addEntity(areaeffectcloudentity);
		}

	}

	private void explode() {
		if (!this.world.isRemote) {
			Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world,
					this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
			float f = this.isCharged() ? 2.0F : 1.0F;
			this.dead = true;
			this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(),
					(float) this.explosionRadius * f, explosion$mode);
			this.remove();
			this.spawnLingeringCloud();
		}
	}
}
