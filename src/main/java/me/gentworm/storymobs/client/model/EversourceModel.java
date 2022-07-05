package me.gentworm.storymobs.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports

public class EversourceModel<T extends Entity> extends AgeableModel<T> {
	private final ModelRenderer rotation;
	private final ModelRenderer head;
	private final ModelRenderer Crown;
	private final ModelRenderer bill;
	private final ModelRenderer chin;
	private final ModelRenderer body;
	private final ModelRenderer body_r1;
	private final ModelRenderer right_wing;
	private final ModelRenderer left_wing;
	private final ModelRenderer right_leg;
	private final ModelRenderer left_leg;

	public EversourceModel() {
		textureWidth = 64;
		textureHeight = 32;

		rotation = new ModelRenderer(this);
		rotation.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -9.0F, -4.0F);
		rotation.addChild(head);
		head.setTextureOffset(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F, false);

		Crown = new ModelRenderer(this);
		Crown.setRotationPoint(0.25F, -6.25F, -0.5F);
		head.addChild(Crown);
		setRotationAngle(Crown, 0.0F, 0.0F, 0.1745F);
		Crown.setTextureOffset(42, 0).addBox(-2.5F, -1.5F, -2.0F, 5.0F, 3.0F, 4.0F, 0.0F, false);

		bill = new ModelRenderer(this);
		bill.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(bill);
		bill.setTextureOffset(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);

		chin = new ModelRenderer(this);
		chin.setRotationPoint(0.0F, -2.0F, -2.0F);
		head.addChild(chin);
		chin.setTextureOffset(14, 4).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -8.0F, 0.0F);
		rotation.addChild(body);
		

		body_r1 = new ModelRenderer(this);
		body_r1.setRotationPoint(0.0F, 0.0F, 8.0F);
		body.addChild(body_r1);
		setRotationAngle(body_r1, 1.5708F, 0.0F, 0.0F);
		body_r1.setTextureOffset(0, 9).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);

		right_wing = new ModelRenderer(this);
		right_wing.setRotationPoint(-3.0F, -3.0F, 0.0F);
		body.addChild(right_wing);
		right_wing.setTextureOffset(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);

		left_wing = new ModelRenderer(this);
		left_wing.setRotationPoint(3.0F, -3.0F, 0.0F);
		body.addChild(left_wing);
		left_wing.setTextureOffset(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(-1.5F, -5.0F, 1.0F);
		rotation.addChild(right_leg);
		right_leg.setTextureOffset(26, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);

		left_leg = new ModelRenderer(this);
		left_leg.setRotationPoint(1.5F, -5.0F, 1.0F);
		rotation.addChild(left_leg);
		left_leg.setTextureOffset(26, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		rotation.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return (Iterable<ModelRenderer>) ImmutableList.of(this.head, this.bill, this.chin);
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return (Iterable<ModelRenderer>) ImmutableList.of(this.body, this.right_leg, this.left_leg, this.right_wing,
				this.left_wing, this.rotation);
	}

	@Override
	public void setRotationAngles(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {
		this.head.rotateAngleX = p_225597_6_ * 0.017453292F;
		this.head.rotateAngleY = p_225597_5_ * 0.017453292F;
		this.right_leg.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
		this.left_leg.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + 3.1415927F) * 1.4F * p_225597_3_;
		this.right_wing.rotateAngleZ = p_225597_4_;
		this.left_wing.rotateAngleZ = -p_225597_4_;
	}
}