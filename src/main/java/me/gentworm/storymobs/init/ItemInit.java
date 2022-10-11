package me.gentworm.storymobs.init;

import me.gentworm.storymobs.StoryMobs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final Properties SPAWN_EGG_PROPERTIES = new Item.Properties()
			.tab(StoryMobsItemGroup.STORY_MOBS_ITEM_GROUP);

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, StoryMobs.MODID);

	public static final RegistryObject<Item> STORY_MOBS_LOGO = ItemInit.ITEMS.register("storymobs_logo",
			() -> new Item(new Item.Properties().tab(null)));

	// Spawn eggs
	public static final RegistryObject<Item> CREEDER_SPAWN_EGG = ITEMS.register("creeder_spawn_egg",
			() -> new SpawnEggItem(EntityInit.creeder, 0x322828, 0xF30909, SPAWN_EGG_PROPERTIES));

	public static final RegistryObject<Item> EVERSOURCE_SPAWN_EGG = ITEMS.register("eversource_spawn_egg",
			() -> new SpawnEggItem(EntityInit.eversource, 0xDEDEDE, 0xD76C2B, SPAWN_EGG_PROPERTIES));

	public static final RegistryObject<Item> ICY_SPIDER_SPAWN_EGG = ITEMS.register("icy_spider_spawn_egg",
			() -> new SpawnEggItem(EntityInit.icy_spider, 0x14CCEB, 0xE9FCFF, SPAWN_EGG_PROPERTIES));

	public static final RegistryObject<Item> PRISON_ZOMBIE_SPAWN_EGG = ITEMS.register("prison_zombie_spawn_egg",
			() -> new SpawnEggItem(EntityInit.prison_zombie, 0x009B12, 0xFFD310, SPAWN_EGG_PROPERTIES));

	public static final RegistryObject<SpawnEggItem> BLACK_WOLF_SPAWN_EGG = ITEMS.register("black_wolf_spawn_egg",
			() -> new SpawnEggItem(EntityInit.black_wolf, 0x161616, 0xf9f9f9, SPAWN_EGG_PROPERTIES));
	
	public static final RegistryObject<SpawnEggItem> RED_SLIME_SPAWN_EGG = ITEMS.register("red_slime_spawn_egg",
			() -> new SpawnEggItem(EntityInit.red_slime, 0xbc0000, 0x2b2121, SPAWN_EGG_PROPERTIES));

}
