package me.gentworm.storymobs.entity;

import java.util.Random;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.init.ItemInit;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.AgableMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.ItemLike;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class EversourceEntity extends Animal {
	private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(
			new ItemLike[] { Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS });

	// Don't let it spawn elder guardians, evokers, or ravagers.
	Item[] eggItems = { ItemInit.EVERSOURCE_SPAWN_EGG.get(), Items.BAT_SPAWN_EGG, Items.BEE_SPAWN_EGG,
			Items.BLAZE_SPAWN_EGG, Items.CAT_SPAWN_EGG, Items.CAVE_SPIDER_SPAWN_EGG, Items.CHICKEN_SPAWN_EGG,
			Items.COD_SPAWN_EGG, Items.COW_SPAWN_EGG, Items.CREEPER_SPAWN_EGG, Items.DOLPHIN_SPAWN_EGG,
			Items.DONKEY_SPAWN_EGG, Items.DROWNED_SPAWN_EGG, Items.ELDER_GUARDIAN_SPAWN_EGG, Items.ENDERMAN_SPAWN_EGG,
			Items.ENDERMITE_SPAWN_EGG, Items.FOX_SPAWN_EGG, Items.GHAST_SPAWN_EGG, Items.HOGLIN_SPAWN_EGG,
			Items.HORSE_SPAWN_EGG, Items.HUSK_SPAWN_EGG, Items.LLAMA_SPAWN_EGG, Items.MAGMA_CUBE_SPAWN_EGG,
			Items.MOOSHROOM_SPAWN_EGG, Items.MULE_SPAWN_EGG, Items.OCELOT_SPAWN_EGG, Items.PANDA_SPAWN_EGG,
			Items.PARROT_SPAWN_EGG, Items.PHANTOM_SPAWN_EGG, Items.PIG_SPAWN_EGG, Items.PIGLIN_SPAWN_EGG,
			Items.PILLAGER_SPAWN_EGG, Items.POLAR_BEAR_SPAWN_EGG, Items.PUFFERFISH_SPAWN_EGG, Items.RABBIT_SPAWN_EGG,
			Items.SALMON_SPAWN_EGG, Items.SHEEP_SPAWN_EGG, Items.SHULKER_SPAWN_EGG, Items.SHULKER_SPAWN_EGG,
			Items.SILVERFISH_SPAWN_EGG, Items.SKELETON_HORSE_SPAWN_EGG, Items.SKELETON_SPAWN_EGG, Items.SLIME_SPAWN_EGG,
			Items.SPIDER_SPAWN_EGG, Items.SQUID_SPAWN_EGG, Items.STRAY_SPAWN_EGG, Items.STRIDER_SPAWN_EGG,
			Items.TRADER_LLAMA_SPAWN_EGG, Items.TROPICAL_FISH_SPAWN_EGG, Items.TURTLE_SPAWN_EGG, Items.VEX_SPAWN_EGG,
			Items.VILLAGER_SPAWN_EGG, Items.VINDICATOR_SPAWN_EGG, Items.WANDERING_TRADER_SPAWN_EGG,
			Items.WITCH_SPAWN_EGG, Items.WITHER_SKELETON_SPAWN_EGG, Items.WOLF_SPAWN_EGG, Items.ZOGLIN_SPAWN_EGG,
			Items.ZOMBIE_HORSE_SPAWN_EGG, Items.ZOMBIE_SPAWN_EGG, Items.ZOMBIE_VILLAGER_SPAWN_EGG,
			Items.ZOMBIFIED_PIGLIN_SPAWN_EGG };

	public float wingRotation;

	public float destPos;

	public float oFlapSpeed;

	public float oFlap;

	public float wingRotDelta = 1.0F;

	public int timeUntilNextEgg;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EversourceEntity(EntityType<? extends EversourceEntity> p_i50282_1_, Level p_i50282_2_) {
		super((EntityType) p_i50282_1_, p_i50282_2_);
		this.timeUntilNextEgg = this.random.nextInt(6000) + 6000;
		setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, (Goal) new FloatGoal((Mob) this));
		this.goalSelector.addGoal(1, (Goal) new PanicGoal((PathfinderMob) this, 1.4D));
		this.goalSelector.addGoal(2, (Goal) new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, (Goal) new TemptGoal((PathfinderMob) this, 1.0D, false, TEMPTATION_ITEMS));
		this.goalSelector.addGoal(4, (Goal) new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, (Goal) new WaterAvoidingRandomStrollGoal((PathfinderMob) this, 1.0D));
		this.goalSelector.addGoal(6, (Goal) new LookAtPlayerGoal((Mob) this, Player.class, 6.0F));
		this.goalSelector.addGoal(7, (Goal) new RandomLookAroundGoal((Mob) this));
	}

	protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
		return (p_213348_2_.height * 0.92F);
	}

	public static AttributeSupplier.Builder getAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	public void aiStep() {
		super.aiStep();
		this.oFlap = this.wingRotation;
		this.oFlapSpeed = this.destPos;
		this.destPos = (float) (this.destPos + (this.onGround ? -1 : 4) * 0.3D);
		this.destPos = Mth.clamp(this.destPos, 0.0F, 1.0F);
		if (!this.onGround && this.wingRotDelta < 1.0F)
			this.wingRotDelta = 1.0F;
		this.wingRotDelta = (float) (this.wingRotDelta * 0.9D);
		Vec3 lvt_1_1_ = getDeltaMovement();
		if (!this.onGround && lvt_1_1_.y < 0.0D)
			setDeltaMovement(lvt_1_1_.multiply(1.0D, 0.6D, 1.0D));
		this.wingRotation += this.wingRotDelta * 2.0F;
		if (!this.level.isClientSide && isAlive() && this.timeUntilNextEgg > 0) {
			this.timeUntilNextEgg--;
			if (this.timeUntilNextEgg == 0) {
				playSound(SoundEvents.CHICKEN_EGG, 1.0F,
						(this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

				Random rand = new Random();

				StoryMobs.LOGGER.info("The eversource has layed a spawn egg");
				int topNumber = 61;
				int nextInteger = rand.nextInt(topNumber);
				spawnAtLocation(eggItems[nextInteger]);
				this.timeUntilNextEgg = this.random.nextInt(6000) + 6000;
			}
		}
	}

	public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
		return false;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.CHICKEN_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.CHICKEN_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.CHICKEN_DEATH;
	}

	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
	}

	@Override
	public AgableMob getBreedOffspring(ServerLevel arg0, AgableMob arg1) {
		return null;
	}

	public boolean isFood(ItemStack p_70877_1_) {
		return TEMPTATION_ITEMS.test(p_70877_1_);
	}

	protected int getExperienceReward(Player p_70693_1_) {
		return 2;
	}

	public void readAdditionalSaveData(CompoundTag p_70037_1_) {
		super.readAdditionalSaveData(p_70037_1_);
		if (p_70037_1_.contains("EggLayTime"))
			this.timeUntilNextEgg = p_70037_1_.getInt("EggLayTime");
	}

	public void addAdditionalSaveData(CompoundTag p_213281_1_) {
		super.addAdditionalSaveData(p_213281_1_);
		p_213281_1_.putInt("EggLayTime", this.timeUntilNextEgg);
	}

	public boolean removeWhenFarAway(double distanceFromPlayer) {
		return false;
	}
}