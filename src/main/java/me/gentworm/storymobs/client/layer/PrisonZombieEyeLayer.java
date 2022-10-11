package me.gentworm.storymobs.client.layer;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.model.PrisonZombieModel;
import me.gentworm.storymobs.entity.PrisonZombieEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PrisonZombieEyeLayer<T extends PrisonZombieEntity> extends EyesLayer<T, PrisonZombieModel<T>> {
	private static final RenderType RENDER_TYPE = RenderType
			.eyes(new ResourceLocation(StoryMobs.MODID, "textures/entity/prison_zombie/prison_zombie_eye_glow.png"));

	public PrisonZombieEyeLayer(RenderLayerParent<T, PrisonZombieModel<T>> p_i50939_1_) {
		super(p_i50939_1_);
	}

	public RenderType renderType() {
		return RENDER_TYPE;
	}
}
