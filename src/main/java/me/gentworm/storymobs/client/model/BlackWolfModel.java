package me.gentworm.storymobs.client.model;

import com.google.common.collect.ImmutableList;

import me.gentworm.storymobs.entity.BlackWolfEntity;
import net.minecraft.client.renderer.entity.model.TintedAgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlackWolfModel<T extends BlackWolfEntity> extends TintedAgeableModel<T> {
	private final ModelRenderer head;

	private final ModelRenderer headChild;

	private final ModelRenderer body;

	private final ModelRenderer legBackRight;

	private final ModelRenderer legBackLeft;

	private final ModelRenderer legFrontRight;

	private final ModelRenderer legFrontLeft;

	private final ModelRenderer tail;

	private final ModelRenderer tailChild;

	private final ModelRenderer mane;

	public BlackWolfModel() {
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(-1.0F, 13.5F, -7.0F);
		this.headChild = new ModelRenderer(this, 0, 0);
		this.headChild.addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, 0.0F);
		this.head.addChild(this.headChild);
		this.body = new ModelRenderer(this, 18, 14);
		this.body.addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, 0.0F);
		this.body.setRotationPoint(0.0F, 14.0F, 2.0F);
		this.mane = new ModelRenderer(this, 21, 0);
		this.mane.addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, 0.0F);
		this.mane.setRotationPoint(-1.0F, 14.0F, 2.0F);
		this.legBackRight = new ModelRenderer(this, 0, 18);
		this.legBackRight.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.legBackRight.setRotationPoint(-2.5F, 16.0F, 7.0F);
		this.legBackLeft = new ModelRenderer(this, 0, 18);
		this.legBackLeft.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.legBackLeft.setRotationPoint(0.5F, 16.0F, 7.0F);
		this.legFrontRight = new ModelRenderer(this, 0, 18);
		this.legFrontRight.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.legFrontRight.setRotationPoint(-2.5F, 16.0F, -4.0F);
		this.legFrontLeft = new ModelRenderer(this, 0, 18);
		this.legFrontLeft.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.legFrontLeft.setRotationPoint(0.5F, 16.0F, -4.0F);
		this.tail = new ModelRenderer(this, 9, 18);
		this.tail.setRotationPoint(-1.0F, 12.0F, 8.0F);
		this.tailChild = new ModelRenderer(this, 9, 18);
		this.tailChild.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.tail.addChild(this.tailChild);
		this.headChild.setTextureOffset(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F);
		this.headChild.setTextureOffset(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F);
		this.headChild.setTextureOffset(0, 10).addBox(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F, 0.0F);
	}

	protected Iterable<ModelRenderer> getHeadParts() {
		return (Iterable<ModelRenderer>) ImmutableList.of(this.head);
	}

	protected Iterable<ModelRenderer> getBodyParts() {
		return (Iterable<ModelRenderer>) ImmutableList.of(this.body, this.legBackRight, this.legBackLeft,
				this.legFrontRight, this.legFrontLeft, this.tail, this.mane);
	}

	public void setLivingAnimations(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
		if (p_212843_1_.func_233678_J__()) {
			this.tail.rotateAngleY = 0.0F;
		} else {
			this.tail.rotateAngleY = MathHelper.cos(p_212843_2_ * 0.6662F) * 1.4F * p_212843_3_;
		}
		if (p_212843_1_.isEntitySleeping()) {
			this.mane.setRotationPoint(-1.0F, 16.0F, -3.0F);
			this.mane.rotateAngleX = 1.2566371F;
			this.mane.rotateAngleY = 0.0F;
			this.body.setRotationPoint(0.0F, 18.0F, 0.0F);
			this.body.rotateAngleX = 0.7853982F;
			this.tail.setRotationPoint(-1.0F, 21.0F, 6.0F);
			this.legBackRight.setRotationPoint(-2.5F, 22.7F, 2.0F);
			this.legBackRight.rotateAngleX = 4.712389F;
			this.legBackLeft.setRotationPoint(0.5F, 22.7F, 2.0F);
			this.legBackLeft.rotateAngleX = 4.712389F;
			this.legFrontRight.rotateAngleX = 5.811947F;
			this.legFrontRight.setRotationPoint(-2.49F, 17.0F, -4.0F);
			this.legFrontLeft.rotateAngleX = 5.811947F;
			this.legFrontLeft.setRotationPoint(0.51F, 17.0F, -4.0F);
		} else {
			this.body.setRotationPoint(0.0F, 14.0F, 2.0F);
			this.body.rotateAngleX = 1.5707964F;
			this.mane.setRotationPoint(-1.0F, 14.0F, -3.0F);
			this.mane.rotateAngleX = this.body.rotateAngleX;
			this.tail.setRotationPoint(-1.0F, 12.0F, 8.0F);
			this.legBackRight.setRotationPoint(-2.5F, 16.0F, 7.0F);
			this.legBackLeft.setRotationPoint(0.5F, 16.0F, 7.0F);
			this.legFrontRight.setRotationPoint(-2.5F, 16.0F, -4.0F);
			this.legFrontLeft.setRotationPoint(0.5F, 16.0F, -4.0F);
			this.legBackRight.rotateAngleX = MathHelper.cos(p_212843_2_ * 0.6662F) * 1.4F * p_212843_3_;
			this.legBackLeft.rotateAngleX = MathHelper.cos(p_212843_2_ * 0.6662F + 3.1415927F) * 1.4F * p_212843_3_;
			this.legFrontRight.rotateAngleX = MathHelper.cos(p_212843_2_ * 0.6662F + 3.1415927F) * 1.4F * p_212843_3_;
			this.legFrontLeft.rotateAngleX = MathHelper.cos(p_212843_2_ * 0.6662F) * 1.4F * p_212843_3_;
		}
		this.headChild.rotateAngleZ = p_212843_1_.getInterestedAngle(p_212843_4_)
				+ p_212843_1_.getShakeAngle(p_212843_4_, 0.0F);
		this.mane.rotateAngleZ = p_212843_1_.getShakeAngle(p_212843_4_, -0.08F);
		this.body.rotateAngleZ = p_212843_1_.getShakeAngle(p_212843_4_, -0.16F);
		this.tailChild.rotateAngleZ = p_212843_1_.getShakeAngle(p_212843_4_, -0.2F);
	}

	public void setRotationAngles(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {
		this.head.rotateAngleX = p_225597_6_ * 0.017453292F;
		this.head.rotateAngleY = p_225597_5_ * 0.017453292F;
		this.tail.rotateAngleX = p_225597_4_;
	}
}
