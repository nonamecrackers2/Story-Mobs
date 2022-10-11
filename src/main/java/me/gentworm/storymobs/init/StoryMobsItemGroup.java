package me.gentworm.storymobs.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.core.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class StoryMobsItemGroup {

	public static final CreativeModeTab STORY_MOBS_ITEM_GROUP = new CreativeModeTab("storymobs") {

		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ItemInit.STORY_MOBS_LOGO.get());
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public void fillItemList(NonNullList<ItemStack> items) {
			NonNullList<ItemStack> spawneggsItems = NonNullList.create();
			for (Item item : ForgeRegistries.ITEMS) {
				if (!(item instanceof SpawnEggItem)) {
					item.fillItemCategory(this, items);
				} else {
					if (item.getItemCategory() == this) {
						spawneggsItems.add(new ItemStack(item));
					}
				}
			}
			items.addAll(spawneggsItems);
		}
	};
}
