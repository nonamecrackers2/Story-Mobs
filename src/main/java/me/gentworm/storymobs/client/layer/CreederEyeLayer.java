package me.gentworm.storymobs.client.layer;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.model.CreederModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreederEyeLayer<T extends Entity, M extends CreederModel<T>> extends EyesLayer<T, M> {
	private static final RenderType RENDER_TYPE = RenderType
			.eyes(new ResourceLocation(StoryMobs.MODID, "textures/entity/creeder/creeder_eye_glow.png"));

	public CreederEyeLayer(RenderLayerParent<T, M> p_i50921_1_) {
		super(p_i50921_1_);
	}

	public RenderType renderType() {
		return RENDER_TYPE;
	}
}
