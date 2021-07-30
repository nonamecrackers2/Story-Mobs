package me.gentworm.storymobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.gentworm.storymobs.client.RenderHandler;
import me.gentworm.storymobs.config.ConfigBuilder;
import me.gentworm.storymobs.config.ConfigGenerator;
import me.gentworm.storymobs.event.CommonEventsRegistry;
import me.gentworm.storymobs.init.EntityInit;
import me.gentworm.storymobs.init.ItemInit;
import me.gentworm.storymobs.world.StoryMobsStructures;
import me.gentworm.storymobs.world.modification.EversourceStructureModification;
import me.gentworm.storymobs.world.modification.PrisonModification;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StoryMobs.MODID)
public class StoryMobs {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "storymobs";

	public StoryMobs() {
		ModLoadingContext.get().registerConfig(Type.COMMON, ConfigGenerator.spec, "storymobs/storymobs.toml");

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ConfigBuilder.setup();
		// Register general, miscellaneous things
		EntityInit.ENTITY_TYPES.register(modEventBus);
		ItemInit.ITEMS.register(modEventBus);
		StoryMobsStructures.REGISTER.register(modEventBus);

		modEventBus.addListener(CommonEventsRegistry::setup);

		// Client event wrap-up in the main class
		modEventBus.addListener(this::clientSetup);

		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		// Prison structure
		forgeBus.addListener(EventPriority.NORMAL, PrisonModification::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.HIGH, PrisonModification::biomeModification);

		// Eversource structure
		forgeBus.addListener(EventPriority.NORMAL, EversourceStructureModification::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.HIGH, EversourceStructureModification::biomeModification);

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void clientSetup(final FMLClientSetupEvent event) {
		RenderHandler.registerEntityRenders();
	}

	public static ResourceLocation getResources(String path) {
		return new ResourceLocation(MODID, path);
	}
}
