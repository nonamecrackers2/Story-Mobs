package me.gentworm.storymobs.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class MainItemGroup {

	public static final ItemGroup STORY_MOBS_ITEM_GROUP = new ItemGroup("storymobs") {

		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.CREEPER_HEAD);
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public void fill(NonNullList<ItemStack> items) {
			NonNullList<ItemStack> spawneggsItems = NonNullList.create();
			for (Item item : ForgeRegistries.ITEMS) {
				if (!(item instanceof SpawnEggItem)) {
					item.fillItemGroup(this, items);
				} else {
					if (item.getGroup() == this) {
						spawneggsItems.add(new ItemStack(item));
					}
				}
			}
			items.addAll(spawneggsItems);
		}
	};
}
