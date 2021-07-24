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
	private final ModelRenderer head;
	private final ModelRenderer bill;
	private final ModelRenderer chin;
	private final ModelRenderer body;
	private final ModelRenderer rotation;
	private final ModelRenderer right_leg;
	private final ModelRenderer left_leg;
	private final ModelRenderer right_wing;
	private final ModelRenderer left_wing;

	public EversourceModel() {
		textureWidth = 64;
		textureHeight = 32;

		this.head = new ModelRenderer(this);
		this.head.setRotationPoint(0.0F, 15.0F, -4.0F);
		this.head.setTextureOffset(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F, false);
		this.head.setTextureOffset(42, 0).addBox(-2.5F, -8.5F, -2.5F, 5.0F, 3.0F, 4.0F, -0.5F, false);

		this.bill = new ModelRenderer(this);
		this.bill.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.addChild(bill);
		this.bill.setTextureOffset(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);

		this.chin = new ModelRenderer(this);
		this.chin.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.addChild(chin);
		this.chin.setTextureOffset(14, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 16.0F, 0.0F);
		this.body.setTextureOffset(0, 0).addBox(-0.5F, 0.5F, -4.1F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		this.rotation = new ModelRenderer(this);
		this.rotation.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addChild(rotation);
		this.setRotationAngle(rotation, 1.5708F, 0.0F, 0.0F);
		this.rotation.setTextureOffset(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);

		this.right_leg = new ModelRenderer(this);
		this.right_leg.setRotationPoint(-1.5F, 19.0F, 1.0F);
		this.right_leg.setTextureOffset(26, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);

		this.left_leg = new ModelRenderer(this);
		this.left_leg.setRotationPoint(1.5F, 19.0F, 1.0F);
		left_leg.setTextureOffset(26, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);

		this.right_wing = new ModelRenderer(this);
		this.right_wing.setRotationPoint(-3.0F, 13.0F, 0.0F);
		this.right_wing.setTextureOffset(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);

		this.left_wing = new ModelRenderer(this);
		this.left_wing.setRotationPoint(3.0F, 13.0F, 0.0F);
		this.left_wing.setTextureOffset(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
		left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
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
		this.chin.rotateAngleX = this.head.rotateAngleX;
		this.chin.rotateAngleY = this.head.rotateAngleY;
		this.body.rotateAngleX = 1.5707964F;
		this.right_leg.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
		this.left_leg.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + 3.1415927F) * 1.4F * p_225597_3_;
		this.right_wing.rotateAngleZ = p_225597_4_;
		this.left_wing.rotateAngleZ = -p_225597_4_;
	}
}