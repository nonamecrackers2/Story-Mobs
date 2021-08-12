package me.gentworm.storymobs.client.layer;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.model.IcySpiderModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IcySpiderEyeLayer<T extends Entity, M extends IcySpiderModel<T>> extends AbstractEyesLayer<T, M> {

	private static final RenderType RENDER_TYPE = RenderType
			.getEyes(new ResourceLocation(StoryMobs.MODID, "textures/entity/icy_spider/icy_spider_eye_glow.png"));

	public IcySpiderEyeLayer(IEntityRenderer<T, M> p_i226039_1_) {
		super(p_i226039_1_);
	}

	@Override
	public RenderType getRenderType() {
		return RENDER_TYPE;
	}

}
