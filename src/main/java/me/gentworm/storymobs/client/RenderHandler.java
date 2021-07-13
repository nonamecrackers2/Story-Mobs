package me.gentworm.storymobs.client;

import me.gentworm.storymobs.init.EntityInit;
import me.gentworm.storymobs.client.render.CreederRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {
	public static void registerEntityRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.CREEDER_ENTITY.get(), CreederRenderer::new);

	}
}
