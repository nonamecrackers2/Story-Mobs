package me.gentworm.storymobs.init;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.entity.CreederEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			StoryMobs.MODID);
	
	private static final Properties spawn_egg_props = new Item.Properties().group(MainItemGroup.STORY_MOBS_ITEM_GROUP);
	
	private static final EntityType<CreederEntity> creeder = createStandardEntityType("creeder", CreederEntity::new,
			EntityClassification.MONSTER, 0.8F, 1.7F);
	
	
	public static final RegistryObject<EntityType<CreederEntity>> CREEDER_ENTITY = ENTITY_TYPES.register("creeder",
			() -> creeder);
	
	public static final RegistryObject<Item> CREEDER_SPAWN_EGG = ItemInit.ITEMS.register("creeder_spawn_egg",
			() -> new SpawnEggItem(creeder, 0x322828, 0xF30909, spawn_egg_props));
	
	
	private static <T extends Entity> EntityType<T> createStandardEntityType(String entity_name,
			EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
		return EntityType.Builder.create(factory, classification).size(width, height)
				.build(StoryMobs.MODID + ":" + entity_name);
	}
	
	public static void registerEntityAttributes() {
		GlobalEntityTypeAttributes.put(CREEDER_ENTITY.get(), CreederEntity.getAttributes().create());
	}
	
	public static void registerEntitySpawnPlacements() {
		EntitySpawnPlacementRegistry.register(EntityInit.CREEDER_ENTITY.get(), PlacementType.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CreederEntity::canCreederSpawn);
	}
}
