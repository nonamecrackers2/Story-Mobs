package me.gentworm.storymobs.client.render;

import com.mojang.blaze3d.vertex.PoseStack;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.layer.RedSlimeGlowLayer;
import me.gentworm.storymobs.client.model.RedSlimeModel;
import me.gentworm.storymobs.entity.RedSlimeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RedSlimeRenderer extends MobRenderer<RedSlimeEntity, RedSlimeModel<RedSlimeEntity>> {
	private static final ResourceLocation RED_SLIME_TEXTURES = new ResourceLocation(StoryMobs.MODID,
			"textures/entity/red_slime/red_slime.png");

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RedSlimeRenderer(EntityRenderDispatcher p_i47193_1_) {
		super(p_i47193_1_, new RedSlimeModel(16), 0.25F);
		addLayer((RenderLayer<RedSlimeEntity, RedSlimeModel<RedSlimeEntity>>) new RedSlimeGlowLayer(this));
		addLayer((RenderLayer<RedSlimeEntity, RedSlimeModel<RedSlimeEntity>>) new SlimeOuterLayer(this));
	}

	public void render(RedSlimeEntity p_225623_1_, float p_225623_2_, float p_225623_3_, PoseStack p_225623_4_,
			MultiBufferSource p_225623_5_, int p_225623_6_) {
		this.shadowRadius = 0.25F * p_225623_1_.getSlimeSize();
		super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
	}

	protected void scale(RedSlimeEntity p_225620_1_, PoseStack p_225620_2_, float p_225620_3_) {
		p_225620_2_.scale(0.999F, 0.999F, 0.999F);
		p_225620_2_.translate(0.0D, 0.0010000000474974513D, 0.0D);
		float lvt_5_1_ = p_225620_1_.getSlimeSize();
		float lvt_6_1_ = Mth.lerp(p_225620_3_, p_225620_1_.prevSquishFactor, p_225620_1_.squishFactor)
				/ (lvt_5_1_ * 0.5F + 1.0F);
		float lvt_7_1_ = 1.0F / (lvt_6_1_ + 1.0F);
		p_225620_2_.scale(lvt_7_1_ * lvt_5_1_, 1.0F / lvt_7_1_ * lvt_5_1_, lvt_7_1_ * lvt_5_1_);
	}

	public ResourceLocation getTextureLocation(RedSlimeEntity p_110775_1_) {
		return RED_SLIME_TEXTURES;
	}
}
