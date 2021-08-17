package me.gentworm.storymobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.gentworm.storymobs.client.RenderHandler;
import me.gentworm.storymobs.config.ConfigBuilder;
import me.gentworm.storymobs.event.CommonEventsRegistry;
import me.gentworm.storymobs.init.EntityInit;
import me.gentworm.storymobs.init.ItemInit;
import me.gentworm.storymobs.init.StoryMobsStructures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StoryMobs.MODID)
public class StoryMobs {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "storymobs";
	
	public StoryMobs() {

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ConfigBuilder.setup();
		// Register general, miscellaneous things
		EntityInit.ENTITY_TYPES.register(modEventBus);
		ItemInit.ITEMS.register(modEventBus);
		StoryMobsStructures.REGISTER.register(modEventBus);

		modEventBus.addListener(CommonEventsRegistry::setup);

		// Client event wrap-up in the main class
		modEventBus.addListener(this::clientSetup);
		
		CommonEventsRegistry.registerStructuresInWorld();

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void clientSetup(final FMLClientSetupEvent event) {
		RenderHandler.registerEntityRenders();
	}

}
