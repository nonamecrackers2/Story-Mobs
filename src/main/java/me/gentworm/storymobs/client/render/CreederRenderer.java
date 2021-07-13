package me.gentworm.storymobs.client.render;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.model.CreederModel;
import me.gentworm.storymobs.entity.CreederEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreederRenderer extends MobRenderer<CreederEntity, CreederModel> {

	protected static final ResourceLocation TEXTURE = new ResourceLocation(StoryMobs.MODID,
			"textures/entity/creeder/creeder.png");

	public CreederRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new CreederModel(), 0.35F);
	}

	@Override
	public ResourceLocation getEntityTexture(CreederEntity entity) {
		return TEXTURE;
	}

}