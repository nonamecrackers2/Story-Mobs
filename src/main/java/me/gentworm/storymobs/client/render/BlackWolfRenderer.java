package me.gentworm.storymobs.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.layer.BlackWolfCollarLayer;
import me.gentworm.storymobs.client.model.BlackWolfModel;
import me.gentworm.storymobs.entity.BlackWolfEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;
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
	public BlackWolfRenderer(EntityRendererManager p_i47187_1_) {
		super(p_i47187_1_, new BlackWolfModel(), 0.5F);
		addLayer(new BlackWolfCollarLayer(this));
	}

	protected float handleRotationFloat(WolfEntity p_77044_1_, float p_77044_2_) {
		return p_77044_1_.getTailRotation();
	}

	public void render(BlackWolfEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_,
			IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
		if (p_225623_1_.isWolfWet()) {
			float lvt_7_1_ = p_225623_1_.getShadingWhileWet(p_225623_3_);
			this.entityModel.setTint(lvt_7_1_, lvt_7_1_, lvt_7_1_);
		}
		super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
		if (p_225623_1_.isWolfWet())
			this.entityModel.setTint(1.0F, 1.0F, 1.0F);
	}

	public ResourceLocation getEntityTexture(BlackWolfEntity p_110775_1_) {
		if (p_110775_1_.isTamed())
			return TAMED_WOLF_TEXTURES;
		if (p_110775_1_.func_233678_J__())
			return ANGRY_WOLF_TEXTURES;
		return BLACK_WOLF_TEXTURES;
	}
}
