package me.gentworm.storymobs.init;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.entity.BlackWolfEntity;
import me.gentworm.storymobs.entity.CreederEntity;
import me.gentworm.storymobs.entity.EversourceEntity;
import me.gentworm.storymobs.entity.GiantGhastEntity;
import me.gentworm.storymobs.entity.IcySpiderEntity;
import me.gentworm.storymobs.entity.PrisonZombieEntity;
import me.gentworm.storymobs.entity.RedSlimeEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			StoryMobs.MODID);

	// Create Entity Types
	public static final EntityType<CreederEntity> creeder = createStandardEntityType("creeder", CreederEntity::new,
			MobCategory.MONSTER, 0.5F, 1.93F);
	public static final EntityType<EversourceEntity> eversource = createStandardEntityType("eversource",
			EversourceEntity::new, MobCategory.AMBIENT, 0.6F, 1.0F);
	public static final EntityType<IcySpiderEntity> icy_spider = createStandardEntityType("icy_spider",
			IcySpiderEntity::new, MobCategory.MONSTER, 1.4F, 0.9F);
	public static final EntityType<PrisonZombieEntity> prison_zombie = createStandardEntityType("prison_zombie",
			PrisonZombieEntity::new, MobCategory.MONSTER, 0.6F, 1.95F);
	public static final EntityType<BlackWolfEntity> black_wolf = createStandardEntityType("black_wolf",
			BlackWolfEntity::new, MobCategory.CREATURE, 0.6F, 0.85F);
	public static final EntityType<RedSlimeEntity> red_slime = createStandardEntityType("red_slime",
			RedSlimeEntity::new, MobCategory.MONSTER, 2.04F, 2.04F);
	public static final EntityType<GiantGhastEntity> giant_ghast = createStandardEntityType("giant_slime",
			GiantGhastEntity::new, MobCategory.MONSTER, 2.04F, 2.04F);

	// Register Entities
	public static final RegistryObject<EntityType<CreederEntity>> CREEDER_ENTITY = ENTITY_TYPES.register("creeder",
			() -> creeder);
	public static final RegistryObject<EntityType<EversourceEntity>> EVERSOURCE_ENTITY = ENTITY_TYPES
			.register("eversource", () -> eversource);
	public static final RegistryObject<EntityType<IcySpiderEntity>> ICY_SPIDER_ENTITY = ENTITY_TYPES
			.register("icy_spider", () -> icy_spider);
	public static final RegistryObject<EntityType<PrisonZombieEntity>> PRISON_ZOMBIE_ENTITY = ENTITY_TYPES
			.register("prison_zombie", () -> prison_zombie);
	public static final RegistryObject<EntityType<BlackWolfEntity>> BLACK_WOLF_ENTITY = ENTITY_TYPES
			.register("black_wolf", () -> black_wolf);
	public static final RegistryObject<EntityType<RedSlimeEntity>> RED_SLIME_ENTITY = ENTITY_TYPES.register("red_slime",
			() -> red_slime);
	public static final RegistryObject<EntityType<GiantGhastEntity>> GIANT_GHAST_ENTITY = ENTITY_TYPES.register("giant_ghast",
			() -> giant_ghast);

	// Special method to register entities
	private static <T extends Entity> EntityType<T> createStandardEntityType(String entity_name,
			EntityType.EntityFactory<T> factory, MobCategory classification, float width, float height) {
		return EntityType.Builder.of(factory, classification).sized(width, height)
				.build(StoryMobs.MODID + ":" + entity_name);
	}

	// Register Attributes
	public static void registerEntityAttributes() {
		DefaultAttributes.put(CREEDER_ENTITY.get(), CreederEntity.getAttributes().build());
		DefaultAttributes.put(EVERSOURCE_ENTITY.get(), EversourceEntity.getAttributes().build());
		DefaultAttributes.put(ICY_SPIDER_ENTITY.get(), IcySpiderEntity.getAttributes().build());
		DefaultAttributes.put(BLACK_WOLF_ENTITY.get(), BlackWolfEntity.createAttributes().build());
		DefaultAttributes.put(RED_SLIME_ENTITY.get(), RedSlimeEntity.getAttributes().build());
		DefaultAttributes.put(GIANT_GHAST_ENTITY.get(), GiantGhastEntity.getAttributes().build());
	}

	// Spawn placements
	public static void registerEntitySpawnPlacements() {
		SpawnPlacements.register(CREEDER_ENTITY.get(), Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CreederEntity::canCreederSpawn);
		SpawnPlacements.register(ICY_SPIDER_ENTITY.get(), Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IcySpiderEntity::canIcySpiderSpawn);
		SpawnPlacements.register(BLACK_WOLF_ENTITY.get(), Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(RED_SLIME_ENTITY.get(), Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RedSlimeEntity::canRedSlimeSpawn);
		//EntitySpawnPlacementRegistry.register(GIANT_GHAST_ENTITY.get(), PlacementType.NO_RESTRICTIONS,
				//Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GiantGhastEntity::canGiantGhastSpawn);
	}
}
