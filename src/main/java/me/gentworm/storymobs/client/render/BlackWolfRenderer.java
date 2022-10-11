package me.gentworm.storymobs.client.render;

import com.mojang.blaze3d.vertex.PoseStack;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.layer.BlackWolfCollarLayer;
import me.gentworm.storymobs.client.model.BlackWolfModel;
import me.gentworm.storymobs.entity.BlackWolfEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlackWolfRenderer extends MobRenderer<BlackWolfEntity, BlackWolfModel<BlackWolfEntity>> {
	private static final ResourceLocation BLACK_WOLF_TEXTURES = new ResourceLocation(StoryMobs.MODID,
			"textures/entity/black_wolf/black_wolf.png");

	private static final ResourceLocation TAMED_WOLF_TEXTURES = new ResourceLocation(StoryMobs.MODID,
			"textures/entity/black_wolf/black_wolf_tamed.png");

	private static final ResourceLocation ANGRY_WOLF_TEXTURES = new ResourceLocation(StoryMobs.MODID,
			"textures/entity/black_wolf/black_wolf_angry.png");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BlackWolfRenderer(EntityRenderDispatcher p_i47187_1_) {
		super(p_i47187_1_, new BlackWolfModel(), 0.5F);
		addLayer(new BlackWolfCollarLayer(this));
	}

	protected float getBob(BlackWolfEntity p_77044_1_, float p_77044_2_) {
		return p_77044_1_.getTailAngle();
	}

	public void render(BlackWolfEntity p_225623_1_, float p_225623_2_, float p_225623_3_, PoseStack p_225623_4_,
			MultiBufferSource p_225623_5_, int p_225623_6_) {
		if (p_225623_1_.isWet()) {
			float lvt_7_1_ = p_225623_1_.getWetShade(p_225623_3_);
			this.model.setColor(lvt_7_1_, lvt_7_1_, lvt_7_1_);
		}
		super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
		if (p_225623_1_.isWet())
			this.model.setColor(1.0F, 1.0F, 1.0F);
	}

	public ResourceLocation getTextureLocation(BlackWolfEntity p_110775_1_) {
		if (p_110775_1_.isTame())
			return TAMED_WOLF_TEXTURES;
		if (p_110775_1_.isAngry())
			return ANGRY_WOLF_TEXTURES;
		return BLACK_WOLF_TEXTURES;
	}
}
