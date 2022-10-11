package me.gentworm.storymobs.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.util.Mth;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports

public class EversourceModel<T extends Entity> extends AgeableListModel<T> {
	private final ModelPart rotation;
	private final ModelPart head;
	private final ModelPart Crown;
	private final ModelPart bill;
	private final ModelPart chin;
	private final ModelPart body;
	private final ModelPart body_r1;
	private final ModelPart right_wing;
	private final ModelPart left_wing;
	private final ModelPart right_leg;
	private final ModelPart left_leg;

	public EversourceModel() {
		texWidth = 64;
		texHeight = 32;

		rotation = new ModelPart(this);
		rotation.setPos(0.0F, 24.0F, 0.0F);
		

		head = new ModelPart(this);
		head.setPos(0.0F, -9.0F, -4.0F);
		rotation.addChild(head);
		head.texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F, false);

		Crown = new ModelPart(this);
		Crown.setPos(0.25F, -6.25F, -0.5F);
		head.addChild(Crown);
		setRotationAngle(Crown, 0.0F, 0.0F, 0.1745F);
		Crown.texOffs(42, 0).addBox(-2.5F, -1.5F, -2.0F, 5.0F, 3.0F, 4.0F, 0.0F, false);

		bill = new ModelPart(this);
		bill.setPos(0.0F, 0.0F, 0.0F);
		head.addChild(bill);
		bill.texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);

		chin = new ModelPart(this);
		chin.setPos(0.0F, -2.0F, -2.0F);
		head.addChild(chin);
		chin.texOffs(14, 4).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		body = new ModelPart(this);
		body.setPos(0.0F, -8.0F, 0.0F);
		rotation.addChild(body);
		

		body_r1 = new ModelPart(this);
		body_r1.setPos(0.0F, 0.0F, 8.0F);
		body.addChild(body_r1);
		setRotationAngle(body_r1, 1.5708F, 0.0F, 0.0F);
		body_r1.texOffs(0, 9).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);

		right_wing = new ModelPart(this);
		right_wing.setPos(-3.0F, -3.0F, 0.0F);
		body.addChild(right_wing);
		right_wing.texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);

		left_wing = new ModelPart(this);
		left_wing.setPos(3.0F, -3.0F, 0.0F);
		body.addChild(left_wing);
		left_wing.texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);

		right_leg = new ModelPart(this);
		right_leg.setPos(-1.5F, -5.0F, 1.0F);
		rotation.addChild(right_leg);
		right_leg.texOffs(26, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);

		left_leg = new ModelPart(this);
		left_leg.setPos(1.5F, -5.0F, 1.0F);
		rotation.addChild(left_leg);
		left_leg.texOffs(26, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		rotation.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return (Iterable<ModelPart>) ImmutableList.of(this.head, this.bill, this.chin);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return (Iterable<ModelPart>) ImmutableList.of(this.body, this.right_leg, this.left_leg, this.right_wing,
				this.left_wing, this.rotation);
	}

	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {
		this.head.xRot = p_225597_6_ * 0.017453292F;
		this.head.yRot = p_225597_5_ * 0.017453292F;
		this.right_leg.xRot = Mth.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
		this.left_leg.xRot = Mth.cos(p_225597_2_ * 0.6662F + 3.1415927F) * 1.4F * p_225597_3_;
		this.right_wing.zRot = p_225597_4_;
		this.left_wing.zRot = -p_225597_4_;
	}
}