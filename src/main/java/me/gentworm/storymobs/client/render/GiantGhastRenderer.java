package me.gentworm.storymobs.client.render;

import com.mojang.blaze3d.vertex.PoseStack;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.model.GiantGhastModel;
import me.gentworm.storymobs.entity.GiantGhastEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GiantGhastRenderer extends MobRenderer<GiantGhastEntity, GiantGhastModel> {
	private static final ResourceLocation GIANT_GHAST_TEXTURES = new ResourceLocation(StoryMobs.MODID,
			"textures/entity/giant_ghast/giant_ghast.png");

	public GiantGhastRenderer(EntityRenderDispatcher p_i46174_1_) {
		super(p_i46174_1_, new GiantGhastModel(), 1.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(GiantGhastEntity p_110775_1_) {
		return GIANT_GHAST_TEXTURES;
	}

	@Override
	@SuppressWarnings("unused")
	protected void scale(GiantGhastEntity p_225620_1_, PoseStack p_225620_2_, float p_225620_3_) {
		float lvt_4_1_ = 1.0F;
		float lvt_5_1_ = 4.5F;
		float lvt_6_1_ = 4.5F;
		p_225620_2_.scale(4.5F, 4.5F, 4.5F);
	}
}
