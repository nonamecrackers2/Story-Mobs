package me.gentworm.storymobs.client.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IcySpiderModel<T extends Entity> extends SegmentedModel<T> {
	private final ModelRenderer spiderHead;

	private final ModelRenderer spiderNeck;

	private final ModelRenderer spiderBody;

	private final ModelRenderer spiderLeg1;

	private final ModelRenderer spiderLeg2;

	private final ModelRenderer spiderLeg3;

	private final ModelRenderer spiderLeg4;

	private final ModelRenderer spiderLeg5;

	private final ModelRenderer spiderLeg6;

	private final ModelRenderer spiderLeg7;

	private final ModelRenderer spiderLeg8;

	@SuppressWarnings("unused")
	public IcySpiderModel() {
		float lvt_1_1_ = 0.0F;
		int lvt_2_1_ = 15;
		this.spiderHead = new ModelRenderer(this, 32, 4);
		this.spiderHead.addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, 0.0F);
		this.spiderHead.setRotationPoint(0.0F, 15.0F, -3.0F);
		this.spiderNeck = new ModelRenderer(this, 0, 0);
		this.spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F);
		this.spiderNeck.setRotationPoint(0.0F, 15.0F, 0.0F);
		this.spiderBody = new ModelRenderer(this, 0, 12);
		this.spiderBody.addBox(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F, 0.0F);
		this.spiderBody.setRotationPoint(0.0F, 15.0F, 9.0F);
		this.spiderLeg1 = new ModelRenderer(this, 18, 0);
		this.spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg1.setRotationPoint(-4.0F, 15.0F, 2.0F);
		this.spiderLeg2 = new ModelRenderer(this, 18, 0);
		this.spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg2.setRotationPoint(4.0F, 15.0F, 2.0F);
		this.spiderLeg3 = new ModelRenderer(this, 18, 0);
		this.spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg3.setRotationPoint(-4.0F, 15.0F, 1.0F);
		this.spiderLeg4 = new ModelRenderer(this, 18, 0);
		this.spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg4.setRotationPoint(4.0F, 15.0F, 1.0F);
		this.spiderLeg5 = new ModelRenderer(this, 18, 0);
		this.spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg5.setRotationPoint(-4.0F, 15.0F, 0.0F);
		this.spiderLeg6 = new ModelRenderer(this, 18, 0);
		this.spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg6.setRotationPoint(4.0F, 15.0F, 0.0F);
		this.spiderLeg7 = new ModelRenderer(this, 18, 0);
		this.spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg7.setRotationPoint(-4.0F, 15.0F, -1.0F);
		this.spiderLeg8 = new ModelRenderer(this, 18, 0);
		this.spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg8.setRotationPoint(4.0F, 15.0F, -1.0F);
	}

	public Iterable<ModelRenderer> getParts() {
		return (Iterable<ModelRenderer>) ImmutableList.of(this.spiderHead, this.spiderNeck, this.spiderBody,
				this.spiderLeg1, this.spiderLeg2, this.spiderLeg3, this.spiderLeg4, this.spiderLeg5, this.spiderLeg6,
				this.spiderLeg7, this.spiderLeg8);
	}

	@SuppressWarnings("unused")
	public void setRotationAngles(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {
		this.spiderHead.rotateAngleY = p_225597_5_ * 0.017453292F;
		this.spiderHead.rotateAngleX = p_225597_6_ * 0.017453292F;
		float lvt_7_1_ = 0.7853982F;
		this.spiderLeg1.rotateAngleZ = -0.7853982F;
		this.spiderLeg2.rotateAngleZ = 0.7853982F;
		this.spiderLeg3.rotateAngleZ = -0.58119464F;
		this.spiderLeg4.rotateAngleZ = 0.58119464F;
		this.spiderLeg5.rotateAngleZ = -0.58119464F;
		this.spiderLeg6.rotateAngleZ = 0.58119464F;
		this.spiderLeg7.rotateAngleZ = -0.7853982F;
		this.spiderLeg8.rotateAngleZ = 0.7853982F;
		float lvt_8_1_ = -0.0F;
		float lvt_9_1_ = 0.3926991F;
		this.spiderLeg1.rotateAngleY = 0.7853982F;
		this.spiderLeg2.rotateAngleY = -0.7853982F;
		this.spiderLeg3.rotateAngleY = 0.3926991F;
		this.spiderLeg4.rotateAngleY = -0.3926991F;
		this.spiderLeg5.rotateAngleY = -0.3926991F;
		this.spiderLeg6.rotateAngleY = 0.3926991F;
		this.spiderLeg7.rotateAngleY = -0.7853982F;
		this.spiderLeg8.rotateAngleY = 0.7853982F;
		float lvt_10_1_ = -(MathHelper.cos(p_225597_2_ * 0.6662F * 2.0F + 0.0F) * 0.4F) * p_225597_3_;
		float lvt_11_1_ = -(MathHelper.cos(p_225597_2_ * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * p_225597_3_;
		float lvt_12_1_ = -(MathHelper.cos(p_225597_2_ * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * p_225597_3_;
		float lvt_13_1_ = -(MathHelper.cos(p_225597_2_ * 0.6662F * 2.0F + 4.712389F) * 0.4F) * p_225597_3_;
		float lvt_14_1_ = Math.abs(MathHelper.sin(p_225597_2_ * 0.6662F + 0.0F) * 0.4F) * p_225597_3_;
		float lvt_15_1_ = Math.abs(MathHelper.sin(p_225597_2_ * 0.6662F + 3.1415927F) * 0.4F) * p_225597_3_;
		float lvt_16_1_ = Math.abs(MathHelper.sin(p_225597_2_ * 0.6662F + 1.5707964F) * 0.4F) * p_225597_3_;
		float lvt_17_1_ = Math.abs(MathHelper.sin(p_225597_2_ * 0.6662F + 4.712389F) * 0.4F) * p_225597_3_;
		this.spiderLeg1.rotateAngleY += lvt_10_1_;
		this.spiderLeg2.rotateAngleY += -lvt_10_1_;
		this.spiderLeg3.rotateAngleY += lvt_11_1_;
		this.spiderLeg4.rotateAngleY += -lvt_11_1_;
		this.spiderLeg5.rotateAngleY += lvt_12_1_;
		this.spiderLeg6.rotateAngleY += -lvt_12_1_;
		this.spiderLeg7.rotateAngleY += lvt_13_1_;
		this.spiderLeg8.rotateAngleY += -lvt_13_1_;
		this.spiderLeg1.rotateAngleZ += lvt_14_1_;
		this.spiderLeg2.rotateAngleZ += -lvt_14_1_;
		this.spiderLeg3.rotateAngleZ += lvt_15_1_;
		this.spiderLeg4.rotateAngleZ += -lvt_15_1_;
		this.spiderLeg5.rotateAngleZ += lvt_16_1_;
		this.spiderLeg6.rotateAngleZ += -lvt_16_1_;
		this.spiderLeg7.rotateAngleZ += lvt_17_1_;
		this.spiderLeg8.rotateAngleZ += -lvt_17_1_;
	}
}
