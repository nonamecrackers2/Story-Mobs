package me.gentworm.storymobs.init;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.entity.CreederEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			StoryMobs.MODID);
	
	private static final Properties spawn_egg_props = new Item.Properties().group(MainItemGroup.STORY_MOBS_ITEM_GROUP);
	
	private static final EntityType<CreederEntity> creeder = createStandardEntityType("creeder", CreederEntity::new,
			EntityClassification.MONSTER, 1.0F, 0.7F);
	
	
	public static final RegistryObject<EntityType<CreederEntity>> CREEDER_ENTITY = ENTITY_TYPES.register("creeder",
			() -> creeder);
	
	public static final RegistryObject<Item> SHARK_SPAWN_EGG = ItemInit.ITEMS.register("creeder_spawn_egg",
			() -> new SpawnEggItem(creeder, 0x339BFF, 0x133B60, spawn_egg_props));
	
	
	private static <T extends Entity> EntityType<T> createStandardEntityType(String entity_name,
			EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
		return EntityType.Builder.create(factory, classification).size(width, height)
				.build(StoryMobs.MODID + ":" + entity_name);
	}
	
	public static void registerEntityAttributes() {
		GlobalEntityTypeAttributes.put(EntityInit.CREEDER_ENTITY.get(), CreederEntity.getAttributes().create());

	}
}
