package me.gentworm.storymobs.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.init.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EversourceEntity extends AnimalEntity {
	private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(
			new IItemProvider[] { Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS });

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

	List<Item> eggItemsList = Arrays.asList(eggItems);

	public float wingRotation;

	public float destPos;

	public float oFlapSpeed;

	public float oFlap;

	public float wingRotDelta = 1.0F;

	public int timeUntilNextEgg;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EversourceEntity(EntityType<? extends EversourceEntity> p_i50282_1_, World p_i50282_2_) {
		super((EntityType) p_i50282_1_, p_i50282_2_);
		this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
		setPathPriority(PathNodeType.WATER, 0.0F);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, (Goal) new SwimGoal((MobEntity) this));
		this.goalSelector.addGoal(1, (Goal) new PanicGoal((CreatureEntity) this, 1.4D));
		this.goalSelector.addGoal(2, (Goal) new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, (Goal) new TemptGoal((CreatureEntity) this, 1.0D, false, TEMPTATION_ITEMS));
		this.goalSelector.addGoal(4, (Goal) new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, (Goal) new WaterAvoidingRandomWalkingGoal((CreatureEntity) this, 1.0D));
		this.goalSelector.addGoal(6, (Goal) new LookAtGoal((MobEntity) this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, (Goal) new LookRandomlyGoal((MobEntity) this));
	}

	protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
		return (p_213348_2_.height * 0.92F);
	}

	public static AttributeModifierMap.MutableAttribute getAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 6.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	public void livingTick() {
		super.livingTick();
		this.oFlap = this.wingRotation;
		this.oFlapSpeed = this.destPos;
		this.destPos = (float) (this.destPos + (this.onGround ? -1 : 4) * 0.3D);
		this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
		if (!this.onGround && this.wingRotDelta < 1.0F)
			this.wingRotDelta = 1.0F;
		this.wingRotDelta = (float) (this.wingRotDelta * 0.9D);
		Vector3d lvt_1_1_ = getMotion();
		if (!this.onGround && lvt_1_1_.y < 0.0D)
			setMotion(lvt_1_1_.mul(1.0D, 0.6D, 1.0D));
		this.wingRotation += this.wingRotDelta * 2.0F;
		if (!this.world.isRemote && isAlive() && (--this.timeUntilNextEgg == 0)) {
			playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F,
					(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			Collections.shuffle(eggItemsList);

			Random rand = new Random();

			StoryMobs.LOGGER.info("The eversource has layed a spawn egg");
			int topNumber = 61;
			int nextInteger = rand.nextInt(topNumber);
			entityDropItem(eggItems[nextInteger]);
		}
	}

	public boolean onLivingFall(float p_225503_1_, float p_225503_2_) {
		return false;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_CHICKEN_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.ENTITY_CHICKEN_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_CHICKEN_DEATH;
	}

	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
	}

	@Override
	public AgeableEntity func_241840_a(ServerWorld arg0, AgeableEntity arg1) {
		return null;
	}

	public boolean isBreedingItem(ItemStack p_70877_1_) {
		return TEMPTATION_ITEMS.test(p_70877_1_);
	}

	protected int getExperiencePoints(PlayerEntity p_70693_1_) {
		return 2;
	}

	public void readAdditional(CompoundNBT p_70037_1_) {
		super.readAdditional(p_70037_1_);
		if (p_70037_1_.contains("EggLayTime"))
			this.timeUntilNextEgg = p_70037_1_.getInt("EggLayTime");
	}

	public void writeAdditional(CompoundNBT p_213281_1_) {
		super.writeAdditional(p_213281_1_);
		p_213281_1_.putInt("EggLayTime", this.timeUntilNextEgg);
	}

	public boolean canDespawn(double distanceFromPlayer) {
		return false;
	}
}
