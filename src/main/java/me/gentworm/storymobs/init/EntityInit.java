package me.gentworm.storymobs.init;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.entity.CreederEntity;
import me.gentworm.storymobs.entity.EversourceEntity;
import me.gentworm.storymobs.entity.IcySpiderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			StoryMobs.MODID);

	// Create Entity Types
	public static final EntityType<CreederEntity> creeder = createStandardEntityType("creeder", CreederEntity::new,
			EntityClassification.MONSTER, 0.5F, 1.93F);
	public static final EntityType<EversourceEntity> eversource = createStandardEntityType("eversource",
			EversourceEntity::new, EntityClassification.AMBIENT, 0.6F, 1.0F);
	public static final EntityType<IcySpiderEntity> icy_spider = createStandardEntityType("icy_spider",
			IcySpiderEntity::new, EntityClassification.MONSTER, 1.4F, 0.9F);
	// Register Entities
	public static final RegistryObject<EntityType<CreederEntity>> CREEDER_ENTITY = ENTITY_TYPES.register("creeder",
			() -> creeder);
	public static final RegistryObject<EntityType<EversourceEntity>> EVERSOURCE_ENTITY = ENTITY_TYPES
			.register("eversource", () -> eversource);
	public static final RegistryObject<EntityType<IcySpiderEntity>> ICY_SPIDER_ENTITY = ENTITY_TYPES
			.register("icy_spider", () -> icy_spider);

	// Special method to register entities
	private static <T extends Entity> EntityType<T> createStandardEntityType(String entity_name,
			EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
		return EntityType.Builder.create(factory, classification).size(width, height)
				.build(StoryMobs.MODID + ":" + entity_name);
	}

	// Register Attributes
	public static void registerEntityAttributes() {
		GlobalEntityTypeAttributes.put(CREEDER_ENTITY.get(), CreederEntity.getAttributes().create());
		GlobalEntityTypeAttributes.put(EVERSOURCE_ENTITY.get(), EversourceEntity.getAttributes().create());
		GlobalEntityTypeAttributes.put(ICY_SPIDER_ENTITY.get(), IcySpiderEntity.getAttributes().create());
	}

	// Spawn placements
	public static void registerEntitySpawnPlacements() {
		EntitySpawnPlacementRegistry.register(CREEDER_ENTITY.get(), PlacementType.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CreederEntity::canCreederSpawn);
		EntitySpawnPlacementRegistry.register(ICY_SPIDER_ENTITY.get(), PlacementType.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IcySpiderEntity::canIcySpiderSpawn);
	}
}
