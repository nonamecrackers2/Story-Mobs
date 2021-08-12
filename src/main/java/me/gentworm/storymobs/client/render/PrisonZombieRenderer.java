package me.gentworm.storymobs.client.render;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.layer.PrisonZombieEyeLayer;
import me.gentworm.storymobs.client.model.PrisonZombieModel;
import me.gentworm.storymobs.entity.PrisonZombieEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PrisonZombieRenderer<T extends PrisonZombieEntity> extends MobRenderer<T, PrisonZombieModel<T>> {

	private static final ResourceLocation PRISON_ZOMBIE_TEXTURE = new ResourceLocation(StoryMobs.MODID,
			"textures/entity/prison_zombie/prison_zombie.png");

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PrisonZombieRenderer(EntityRendererManager p_i50961_1_) {
		super(p_i50961_1_, new PrisonZombieModel(0.4F, false), 0.4F);
		addLayer((LayerRenderer<T, PrisonZombieModel<T>>) new PrisonZombieEyeLayer(this));
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return PRISON_ZOMBIE_TEXTURE;
	}
}
