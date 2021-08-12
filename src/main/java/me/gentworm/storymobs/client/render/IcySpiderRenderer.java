package me.gentworm.storymobs.client.render;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.layer.IcySpiderEyeLayer;
import me.gentworm.storymobs.client.model.IcySpiderModel;
import me.gentworm.storymobs.entity.IcySpiderEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IcySpiderRenderer<T extends IcySpiderEntity> extends MobRenderer<T, IcySpiderModel<T>> {
	private static final ResourceLocation ICY_SPIDER_TEXTURES = new ResourceLocation(StoryMobs.MODID,
			"textures/entity/icy_spider/icy_spider.png");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public IcySpiderRenderer(EntityRendererManager p_i46139_1_) {
		super(p_i46139_1_, new IcySpiderModel(), 0.8F);
		addLayer((LayerRenderer<T, IcySpiderModel<T>>) new IcySpiderEyeLayer(this));
	}

	@Override
	protected float getDeathMaxRotation(T p_77037_1_) {
		return 180.0F;
	}

	@Override
	public ResourceLocation getEntityTexture(T p_110775_1_) {
		return ICY_SPIDER_TEXTURES;
	}
}
