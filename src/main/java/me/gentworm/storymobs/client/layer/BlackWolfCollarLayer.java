package me.gentworm.storymobs.client.layer;

import com.mojang.blaze3d.matrix.MatrixStack;

import me.gentworm.storymobs.client.model.BlackWolfModel;
import me.gentworm.storymobs.entity.BlackWolfEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlackWolfCollarLayer extends LayerRenderer<BlackWolfEntity, BlackWolfModel<BlackWolfEntity>> {
	private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");

	public BlackWolfCollarLayer(IEntityRenderer<BlackWolfEntity, BlackWolfModel<BlackWolfEntity>> p_i50914_1_) {
		super(p_i50914_1_);
	}

	public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_,
			BlackWolfEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_,
			float p_225628_9_, float p_225628_10_) {
		if (!p_225628_4_.isTamed() || p_225628_4_.isInvisible())
			return;
		float[] lvt_11_1_ = p_225628_4_.getCollarColor().getColorComponentValues();
		renderCutoutModel((EntityModel<BlackWolfEntity>) getEntityModel(), WOLF_COLLAR, p_225628_1_, p_225628_2_,
				p_225628_3_, p_225628_4_, lvt_11_1_[0], lvt_11_1_[1], lvt_11_1_[2]);
	}
}
