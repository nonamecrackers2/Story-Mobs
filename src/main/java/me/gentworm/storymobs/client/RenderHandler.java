package me.gentworm.storymobs.client;

import me.gentworm.storymobs.client.render.CreederRenderer;
import me.gentworm.storymobs.client.render.EversourceRenderer;
import me.gentworm.storymobs.client.render.IcySpiderRenderer;
import me.gentworm.storymobs.client.render.PrisonZombieRenderer;
import me.gentworm.storymobs.client.render.BlackWolfRenderer;
import me.gentworm.storymobs.client.render.RedSlimeRenderer;
import me.gentworm.storymobs.client.render.GiantGhastRenderer;
import me.gentworm.storymobs.init.EntityInit;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {
	public static void registerEntityRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.CREEDER_ENTITY.get(), CreederRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.EVERSOURCE_ENTITY.get(), EversourceRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.ICY_SPIDER_ENTITY.get(), IcySpiderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.PRISON_ZOMBIE_ENTITY.get(), PrisonZombieRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.BLACK_WOLF_ENTITY.get(), BlackWolfRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.RED_SLIME_ENTITY.get(), RedSlimeRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.GIANT_GHAST_ENTITY.get(), GiantGhastRenderer::new);
	}
}