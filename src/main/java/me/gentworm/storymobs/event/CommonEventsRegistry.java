package me.gentworm.storymobs.event;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StoryMobs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEventsRegistry {

	@SubscribeEvent
	public static void onEntityRegistry(RegistryEvent.Register<EntityType<?>> event) {
		EntityInit.registerEntityAttributes();
	}
}
