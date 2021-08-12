package me.gentworm.storymobs.client.layer;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.model.RedSlimeModel;
import me.gentworm.storymobs.entity.RedSlimeEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("rawtypes")
@OnlyIn(Dist.CLIENT)
public class RedSlimeGlowLayer<T extends RedSlimeEntity> extends AbstractEyesLayer {
	private static final RenderType RENDER_TYPE = RenderType
			.getEyes(new ResourceLocation(StoryMobs.MODID, "textures/entity/red_slime/red_slime_glow.png"));

	@SuppressWarnings("unchecked")
	public RedSlimeGlowLayer(IEntityRenderer<T, RedSlimeModel<T>> p_i50939_1_) {
		super(p_i50939_1_);
	}

	public RenderType getRenderType() {
		return RENDER_TYPE;
	}
}
