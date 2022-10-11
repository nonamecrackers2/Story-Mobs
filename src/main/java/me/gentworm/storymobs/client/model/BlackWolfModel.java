package me.gentworm.storymobs.client.model;

import com.google.common.collect.ImmutableList;

import me.gentworm.storymobs.entity.BlackWolfEntity;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlackWolfModel<T extends BlackWolfEntity> extends ColorableAgeableListModel<T> {
	private final ModelPart head;

	private final ModelPart headChild;

	private final ModelPart body;

	private final ModelPart legBackRight;

	private final ModelPart legBackLeft;

	private final ModelPart legFrontRight;

	private final ModelPart legFrontLeft;

	private final ModelPart tail;

	private final ModelPart tailChild;

	private final ModelPart mane;

	public BlackWolfModel() {
		this.head = new ModelPart(this, 0, 0);
		this.head.setPos(-1.0F, 13.5F, -7.0F);
		this.headChild = new ModelPart(this, 0, 0);
		this.headChild.addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, 0.0F);
		this.head.addChild(this.headChild);
		this.body = new ModelPart(this, 18, 14);
		this.body.addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, 0.0F);
		this.body.setPos(0.0F, 14.0F, 2.0F);
		this.mane = new ModelPart(this, 21, 0);
		this.mane.addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, 0.0F);
		this.mane.setPos(-1.0F, 14.0F, 2.0F);
		this.legBackRight = new ModelPart(this, 0, 18);
		this.legBackRight.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.legBackRight.setPos(-2.5F, 16.0F, 7.0F);
		this.legBackLeft = new ModelPart(this, 0, 18);
		this.legBackLeft.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.legBackLeft.setPos(0.5F, 16.0F, 7.0F);
		this.legFrontRight = new ModelPart(this, 0, 18);
		this.legFrontRight.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.legFrontRight.setPos(-2.5F, 16.0F, -4.0F);
		this.legFrontLeft = new ModelPart(this, 0, 18);
		this.legFrontLeft.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.legFrontLeft.setPos(0.5F, 16.0F, -4.0F);
		this.tail = new ModelPart(this, 9, 18);
		this.tail.setPos(-1.0F, 12.0F, 8.0F);
		this.tailChild = new ModelPart(this, 9, 18);
		this.tailChild.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
		this.tail.addChild(this.tailChild);
		this.headChild.texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F);
		this.headChild.texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F);
		this.headChild.texOffs(0, 10).addBox(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F, 0.0F);
	}

	protected Iterable<ModelPart> headParts() {
		return (Iterable<ModelPart>) ImmutableList.of(this.head);
	}

	protected Iterable<ModelPart> bodyParts() {
		return (Iterable<ModelPart>) ImmutableList.of(this.body, this.legBackRight, this.legBackLeft,
				this.legFrontRight, this.legFrontLeft, this.tail, this.mane);
	}

	public void prepareMobModel(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
		if (p_212843_1_.isAngry()) {
			this.tail.yRot = 0.0F;
		} else {
			this.tail.yRot = Mth.cos(p_212843_2_ * 0.6662F) * 1.4F * p_212843_3_;
		}
		if (p_212843_1_.isInSittingPose()) {
			this.mane.setPos(-1.0F, 16.0F, -3.0F);
			this.mane.xRot = 1.2566371F;
			this.mane.yRot = 0.0F;
			this.body.setPos(0.0F, 18.0F, 0.0F);
			this.body.xRot = 0.7853982F;
			this.tail.setPos(-1.0F, 21.0F, 6.0F);
			this.legBackRight.setPos(-2.5F, 22.7F, 2.0F);
			this.legBackRight.xRot = 4.712389F;
			this.legBackLeft.setPos(0.5F, 22.7F, 2.0F);
			this.legBackLeft.xRot = 4.712389F;
			this.legFrontRight.xRot = 5.811947F;
			this.legFrontRight.setPos(-2.49F, 17.0F, -4.0F);
			this.legFrontLeft.xRot = 5.811947F;
			this.legFrontLeft.setPos(0.51F, 17.0F, -4.0F);
		} else {
			this.body.setPos(0.0F, 14.0F, 2.0F);
			this.body.xRot = 1.5707964F;
			this.mane.setPos(-1.0F, 14.0F, -3.0F);
			this.mane.xRot = this.body.xRot;
			this.tail.setPos(-1.0F, 12.0F, 8.0F);
			this.legBackRight.setPos(-2.5F, 16.0F, 7.0F);
			this.legBackLeft.setPos(0.5F, 16.0F, 7.0F);
			this.legFrontRight.setPos(-2.5F, 16.0F, -4.0F);
			this.legFrontLeft.setPos(0.5F, 16.0F, -4.0F);
			this.legBackRight.xRot = Mth.cos(p_212843_2_ * 0.6662F) * 1.4F * p_212843_3_;
			this.legBackLeft.xRot = Mth.cos(p_212843_2_ * 0.6662F + 3.1415927F) * 1.4F * p_212843_3_;
			this.legFrontRight.xRot = Mth.cos(p_212843_2_ * 0.6662F + 3.1415927F) * 1.4F * p_212843_3_;
			this.legFrontLeft.xRot = Mth.cos(p_212843_2_ * 0.6662F) * 1.4F * p_212843_3_;
		}
		this.headChild.zRot = p_212843_1_.getHeadRollAngle(p_212843_4_)
				+ p_212843_1_.getBodyRollAngle(p_212843_4_, 0.0F);
		this.mane.zRot = p_212843_1_.getBodyRollAngle(p_212843_4_, -0.08F);
		this.body.zRot = p_212843_1_.getBodyRollAngle(p_212843_4_, -0.16F);
		this.tailChild.zRot = p_212843_1_.getBodyRollAngle(p_212843_4_, -0.2F);
	}

	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {
		this.head.xRot = p_225597_6_ * 0.017453292F;
		this.head.yRot = p_225597_5_ * 0.017453292F;
		this.tail.xRot = p_225597_4_;
	}
}
