package me.gentworm.storymobs.client.render;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.layer.CreederEyeLayer;
import me.gentworm.storymobs.client.model.CreederModel;
import me.gentworm.storymobs.entity.CreederEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreederRenderer<T extends CreederEntity> extends MobRenderer<T, CreederModel<T>> {
	private static final ResourceLocation CREEDER_TEXTURE = new ResourceLocation(
			StoryMobs.MODID, "textures/entity/creeder/creeder.png");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CreederRenderer(EntityRendererManager p_i46139_1_) {
		super(p_i46139_1_, new CreederModel<T>(), 0.8F);
		addLayer((LayerRenderer<T, CreederModel<T>>) new CreederEyeLayer(this));
	}

	protected float getDeathMaxRotation(T p_77037_1_) {
		return 180.0F;
	}

	public ResourceLocation getEntityTexture(T p_110775_1_) {
		return CREEDER_TEXTURE;
	}
}
