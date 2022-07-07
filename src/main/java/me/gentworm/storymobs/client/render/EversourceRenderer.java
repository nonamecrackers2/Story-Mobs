package me.gentworm.storymobs.client.render;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.model.EversourceModel;
import me.gentworm.storymobs.entity.EversourceEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EversourceRenderer extends MobRenderer<EversourceEntity, EversourceModel<EversourceEntity>> {
	private static final ResourceLocation EVERSOURCE_TEXTURE = new ResourceLocation(StoryMobs.MODID,
			"textures/entity/eversource/eversource.png");

	public EversourceRenderer(EntityRendererManager manager) {
		super(manager, new EversourceModel<>(), 0.3F);
	}

	@Override
	public ResourceLocation getEntityTexture(EversourceEntity entity) {
		return EVERSOURCE_TEXTURE;
	}

	@Override
	protected float handleRotationFloat(EversourceEntity p_77044_1_, float p_77044_2_) {
		float lvt_3_1_ = MathHelper.lerp(p_77044_2_, p_77044_1_.oFlap, p_77044_1_.wingRotation);
		float lvt_4_1_ = MathHelper.lerp(p_77044_2_, p_77044_1_.oFlapSpeed, p_77044_1_.destPos);
		return (MathHelper.sin(lvt_3_1_) + 1.0F) * lvt_4_1_;
	}
}
