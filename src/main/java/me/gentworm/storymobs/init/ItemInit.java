package me.gentworm.storymobs.init;

import me.gentworm.storymobs.StoryMobs;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	private static final Properties spawn_egg_props = new Item.Properties().group(StoryMobsItemGroup.STORY_MOBS_ITEM_GROUP);

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, StoryMobs.MODID);
	
	public static final RegistryObject<Item> STORY_MOBS_LOGO = ItemInit.ITEMS.register("storymobs_logo",
			() -> new Item(new Item.Properties().group(null)));

	// Spawn eggs
	public static final RegistryObject<Item> CREEDER_SPAWN_EGG = ItemInit.ITEMS.register("creeder_spawn_egg",
			() -> new SpawnEggItem(EntityInit.creeder, 0x322828, 0xF30909, spawn_egg_props));

	public static final RegistryObject<Item> EVERSOURCE_SPAWN_EGG = ItemInit.ITEMS.register("eversource_spawn_egg",
			() -> new SpawnEggItem(EntityInit.eversource, 0xDEDEDE, 0xD76C2B, spawn_egg_props));

	public static final RegistryObject<Item> ICY_SPIDER_SPAWN_EGG = ItemInit.ITEMS.register("icy_spider_spawn_egg",
			() -> new SpawnEggItem(EntityInit.icy_spider, 0x14CCEB, 0xE9FCFF, spawn_egg_props));
	
	public static Item createItem(Item item, String id) {
      item.setRegistryName(new ResourceLocation(StoryMobs.MODID, id));
      return item;
  }
}
