package me.gentworm.storymobs.client.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IcySpiderModel<T extends Entity> extends ListModel<T> {
	private final ModelPart spiderHead;

	private final ModelPart spiderNeck;

	private final ModelPart spiderBody;

	private final ModelPart spiderLeg1;

	private final ModelPart spiderLeg2;

	private final ModelPart spiderLeg3;

	private final ModelPart spiderLeg4;

	private final ModelPart spiderLeg5;

	private final ModelPart spiderLeg6;

	private final ModelPart spiderLeg7;

	private final ModelPart spiderLeg8;

	@SuppressWarnings("unused")
	public IcySpiderModel() {
		float lvt_1_1_ = 0.0F;
		int lvt_2_1_ = 15;
		this.spiderHead = new ModelPart(this, 32, 4);
		this.spiderHead.addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, 0.0F);
		this.spiderHead.setPos(0.0F, 15.0F, -3.0F);
		this.spiderNeck = new ModelPart(this, 0, 0);
		this.spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F);
		this.spiderNeck.setPos(0.0F, 15.0F, 0.0F);
		this.spiderBody = new ModelPart(this, 0, 12);
		this.spiderBody.addBox(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F, 0.0F);
		this.spiderBody.setPos(0.0F, 15.0F, 9.0F);
		this.spiderLeg1 = new ModelPart(this, 18, 0);
		this.spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg1.setPos(-4.0F, 15.0F, 2.0F);
		this.spiderLeg2 = new ModelPart(this, 18, 0);
		this.spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg2.setPos(4.0F, 15.0F, 2.0F);
		this.spiderLeg3 = new ModelPart(this, 18, 0);
		this.spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg3.setPos(-4.0F, 15.0F, 1.0F);
		this.spiderLeg4 = new ModelPart(this, 18, 0);
		this.spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg4.setPos(4.0F, 15.0F, 1.0F);
		this.spiderLeg5 = new ModelPart(this, 18, 0);
		this.spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg5.setPos(-4.0F, 15.0F, 0.0F);
		this.spiderLeg6 = new ModelPart(this, 18, 0);
		this.spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg6.setPos(4.0F, 15.0F, 0.0F);
		this.spiderLeg7 = new ModelPart(this, 18, 0);
		this.spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg7.setPos(-4.0F, 15.0F, -1.0F);
		this.spiderLeg8 = new ModelPart(this, 18, 0);
		this.spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 0.0F);
		this.spiderLeg8.setPos(4.0F, 15.0F, -1.0F);
	}

	public Iterable<ModelPart> parts() {
		return (Iterable<ModelPart>) ImmutableList.of(this.spiderHead, this.spiderNeck, this.spiderBody,
				this.spiderLeg1, this.spiderLeg2, this.spiderLeg3, this.spiderLeg4, this.spiderLeg5, this.spiderLeg6,
				this.spiderLeg7, this.spiderLeg8);
	}

	@SuppressWarnings("unused")
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {
		this.spiderHead.yRot = p_225597_5_ * 0.017453292F;
		this.spiderHead.xRot = p_225597_6_ * 0.017453292F;
		float lvt_7_1_ = 0.7853982F;
		this.spiderLeg1.zRot = -0.7853982F;
		this.spiderLeg2.zRot = 0.7853982F;
		this.spiderLeg3.zRot = -0.58119464F;
		this.spiderLeg4.zRot = 0.58119464F;
		this.spiderLeg5.zRot = -0.58119464F;
		this.spiderLeg6.zRot = 0.58119464F;
		this.spiderLeg7.zRot = -0.7853982F;
		this.spiderLeg8.zRot = 0.7853982F;
		float lvt_8_1_ = -0.0F;
		float lvt_9_1_ = 0.3926991F;
		this.spiderLeg1.yRot = 0.7853982F;
		this.spiderLeg2.yRot = -0.7853982F;
		this.spiderLeg3.yRot = 0.3926991F;
		this.spiderLeg4.yRot = -0.3926991F;
		this.spiderLeg5.yRot = -0.3926991F;
		this.spiderLeg6.yRot = 0.3926991F;
		this.spiderLeg7.yRot = -0.7853982F;
		this.spiderLeg8.yRot = 0.7853982F;
		float lvt_10_1_ = -(Mth.cos(p_225597_2_ * 0.6662F * 2.0F + 0.0F) * 0.4F) * p_225597_3_;
		float lvt_11_1_ = -(Mth.cos(p_225597_2_ * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * p_225597_3_;
		float lvt_12_1_ = -(Mth.cos(p_225597_2_ * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * p_225597_3_;
		float lvt_13_1_ = -(Mth.cos(p_225597_2_ * 0.6662F * 2.0F + 4.712389F) * 0.4F) * p_225597_3_;
		float lvt_14_1_ = Math.abs(Mth.sin(p_225597_2_ * 0.6662F + 0.0F) * 0.4F) * p_225597_3_;
		float lvt_15_1_ = Math.abs(Mth.sin(p_225597_2_ * 0.6662F + 3.1415927F) * 0.4F) * p_225597_3_;
		float lvt_16_1_ = Math.abs(Mth.sin(p_225597_2_ * 0.6662F + 1.5707964F) * 0.4F) * p_225597_3_;
		float lvt_17_1_ = Math.abs(Mth.sin(p_225597_2_ * 0.6662F + 4.712389F) * 0.4F) * p_225597_3_;
		this.spiderLeg1.yRot += lvt_10_1_;
		this.spiderLeg2.yRot += -lvt_10_1_;
		this.spiderLeg3.yRot += lvt_11_1_;
		this.spiderLeg4.yRot += -lvt_11_1_;
		this.spiderLeg5.yRot += lvt_12_1_;
		this.spiderLeg6.yRot += -lvt_12_1_;
		this.spiderLeg7.yRot += lvt_13_1_;
		this.spiderLeg8.yRot += -lvt_13_1_;
		this.spiderLeg1.zRot += lvt_14_1_;
		this.spiderLeg2.zRot += -lvt_14_1_;
		this.spiderLeg3.zRot += lvt_15_1_;
		this.spiderLeg4.zRot += -lvt_15_1_;
		this.spiderLeg5.zRot += lvt_16_1_;
		this.spiderLeg6.zRot += -lvt_16_1_;
		this.spiderLeg7.zRot += lvt_17_1_;
		this.spiderLeg8.zRot += -lvt_17_1_;
	}
}
