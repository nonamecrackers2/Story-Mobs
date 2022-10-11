package me.gentworm.storymobs.client.render;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.model.EversourceModel;
import me.gentworm.storymobs.entity.EversourceEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EversourceRenderer extends MobRenderer<EversourceEntity, EversourceModel<EversourceEntity>> {
	private static final ResourceLocation EVERSOURCE_TEXTURE = new ResourceLocation(StoryMobs.MODID,
			"textures/entity/eversource/eversource.png");

	public EversourceRenderer(EntityRenderDispatcher manager) {
		super(manager, new EversourceModel<>(), 0.3F);
	}

	@Override
	public ResourceLocation getTextureLocation(EversourceEntity entity) {
		return EVERSOURCE_TEXTURE;
	}

	@Override
	protected float getBob(EversourceEntity p_77044_1_, float p_77044_2_) {
		float lvt_3_1_ = Mth.lerp(p_77044_2_, p_77044_1_.oFlap, p_77044_1_.wingRotation);
		float lvt_4_1_ = Mth.lerp(p_77044_2_, p_77044_1_.oFlapSpeed, p_77044_1_.destPos);
		return (Mth.sin(lvt_3_1_) + 1.0F) * lvt_4_1_;
	}
}
