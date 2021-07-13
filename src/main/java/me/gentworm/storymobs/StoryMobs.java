package me.gentworm.storymobs;

import me.gentworm.storymobs.client.RenderHandler;
import me.gentworm.storymobs.init.EntityInit;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StoryMobs.MODID)
public class StoryMobs {

	public static final String MOD_NAME = "Story Mobs";
	public static final String MODID = "storymobs";
	public static final float MOD_VERSION = 1.0F;

	public StoryMobs() {

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		EntityInit.ENTITY_TYPES.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void clientSetup(final FMLClientSetupEvent event) {
		RenderHandler.registerEntityRenders();
	}

	public static ResourceLocation getResources(String path) {
		return new ResourceLocation(MODID, path);
	}

	public void commonSetup(final FMLCommonSetupEvent event) {
	}
}
