package me.gentworm.storymobs.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports

@OnlyIn(Dist.CLIENT)
public class CreederModel<T extends Entity> extends SegmentedModel<T> {
	private final ModelRenderer main;
	private final ModelRenderer leg2;
	private final ModelRenderer leg4;
	private final ModelRenderer leg6;
	private final ModelRenderer leg5;
	private final ModelRenderer head;
	private final ModelRenderer leg3;
	private final ModelRenderer leg1;
	private final ModelRenderer body;

	public CreederModel() {
		textureWidth = 64;
		textureHeight = 64;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 10.3F, 0.0F);

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(4.0F, 1.0F, 1.0F);
		main.addChild(leg2);
		setRotationAngle(leg2, 0.0F, -0.4363F, 0.3054F);
		leg2.setTextureOffset(16, 34).addBox(0.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, false);

		leg4 = new ModelRenderer(this);
		leg4.setRotationPoint(4.0F, 1.0F, 0.0F);
		main.addChild(leg4);
		setRotationAngle(leg4, 0.0F, 0.0F, 0.3054F);
		leg4.setTextureOffset(16, 34).addBox(0.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, false);

		leg6 = new ModelRenderer(this);
		leg6.setRotationPoint(4.0F, 1.0F, -1.0F);
		main.addChild(leg6);
		setRotationAngle(leg6, 0.0F, 0.4363F, 0.3054F);
		leg6.setTextureOffset(16, 34).addBox(0.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, false);

		leg5 = new ModelRenderer(this);
		leg5.setRotationPoint(-4.0F, 1.0F, -1.0F);
		main.addChild(leg5);
		setRotationAngle(leg5, 0.0F, -0.4363F, -0.3054F);
		leg5.setTextureOffset(16, 34).addBox(-22.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, true);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -10.0F, 0.0F);
		main.addChild(head);
		head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		leg3 = new ModelRenderer(this);
		leg3.setRotationPoint(-4.0F, 1.0F, 0.0F);
		main.addChild(leg3);
		setRotationAngle(leg3, 0.0F, 0.0F, -0.3054F);
		leg3.setTextureOffset(16, 34).addBox(-22.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, true);

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(-4.0F, 1.0F, 1.0F);
		main.addChild(leg1);
		setRotationAngle(leg1, 0.0F, 0.4363F, -0.3054F);
		leg1.setTextureOffset(16, 34).addBox(-22.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, true);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -10.0F, 0.0F);
		main.addChild(body);
		body.setTextureOffset(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return (Iterable<ModelRenderer>) ImmutableList.of(this.head, this.body, this.leg1, this.leg2, this.leg3,
				this.leg4, this.leg5, this.leg6);
	}

	@Override
	public void setRotationAngles(T arg0, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
		this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
		this.leg1.rotateAngleZ = (-(float) Math.PI / 4F);
		this.leg2.rotateAngleZ = ((float) Math.PI / 4F);
		this.leg3.rotateAngleZ = -0.58119464F;
		this.leg4.rotateAngleZ = 0.58119464F;
		this.leg5.rotateAngleZ = -0.58119464F;
		this.leg6.rotateAngleZ = 0.58119464F;
		this.leg1.rotateAngleY = ((float) Math.PI / 4F);
		this.leg2.rotateAngleY = (-(float) Math.PI / 4F);
		this.leg3.rotateAngleY = ((float) Math.PI / 8F);
		this.leg4.rotateAngleY = (-(float) Math.PI / 8F);
		this.leg5.rotateAngleY = (-(float) Math.PI / 8F);
		this.leg6.rotateAngleY = ((float) Math.PI / 8F);
		float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
		float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * limbSwingAmount;
		float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI * 1.5F));
		float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
		float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * limbSwingAmount;
		float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float) Math.PI * 1.5F)) * 0.4F);
		this.leg1.rotateAngleY += f3;
		this.leg2.rotateAngleY += -f3;
		this.leg3.rotateAngleY += f4;
		this.leg4.rotateAngleY += -f4;
		this.leg5.rotateAngleY += f5;
		this.leg6.rotateAngleY += -f5;
		this.leg1.rotateAngleZ += f7;
		this.leg2.rotateAngleZ += -f7;
		this.leg3.rotateAngleZ += f8;
		this.leg4.rotateAngleZ += -f8;
		this.leg5.rotateAngleZ += f9;
		this.leg6.rotateAngleZ += -f9;
	}
}