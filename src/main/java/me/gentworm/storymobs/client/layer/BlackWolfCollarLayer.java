package me.gentworm.storymobs.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.model.BlackWolfModel;
import me.gentworm.storymobs.entity.BlackWolfEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlackWolfCollarLayer extends RenderLayer<BlackWolfEntity, BlackWolfModel<BlackWolfEntity>> {
	private static final ResourceLocation BLACK_WOLF_COLLAR = new ResourceLocation(StoryMobs.MODID, "textures/entity/black_wolf/black_wolf_collar.png");

	public BlackWolfCollarLayer(RenderLayerParent<BlackWolfEntity, BlackWolfModel<BlackWolfEntity>> p_i50914_1_) {
		super(p_i50914_1_);
	}

	public void render(PoseStack p_225628_1_, MultiBufferSource p_225628_2_, int p_225628_3_,
			BlackWolfEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_,
			float p_225628_9_, float p_225628_10_) {
		if (!p_225628_4_.isTame() || p_225628_4_.isInvisible())
			return;
		float[] lvt_11_1_ = p_225628_4_.getCollarColor().getTextureDiffuseColors();
		renderColoredCutoutModel((EntityModel<BlackWolfEntity>) getParentModel(), BLACK_WOLF_COLLAR, p_225628_1_, p_225628_2_,
				p_225628_3_, p_225628_4_, lvt_11_1_[0], lvt_11_1_[1], lvt_11_1_[2]);
	}
}
