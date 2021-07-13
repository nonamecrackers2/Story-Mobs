package me.gentworm.storymobs.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import me.gentworm.storymobs.entity.CreederEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports

public class CreederModel extends EntityModel<CreederEntity> {
	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer leg1;
	private final ModelRenderer leg2;
	private final ModelRenderer leg3;
	private final ModelRenderer leg4;
	private final ModelRenderer leg5;
	private final ModelRenderer leg6;

	public CreederModel() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 6.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 6.0F, 0.0F);
		body.setTextureOffset(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(-4.0F, 17.0F, 1.0F);
		setRotationAngle(leg1, 0.0F, 0.4363F, -0.3054F);
		leg1.setTextureOffset(16, 34).addBox(-22.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, true);

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(4.0F, 17.0F, 1.0F);
		setRotationAngle(leg2, 0.0F, -0.4363F, 0.3054F);
		leg2.setTextureOffset(16, 34).addBox(0.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, false);

		leg3 = new ModelRenderer(this);
		leg3.setRotationPoint(-4.0F, 17.0F, 0.0F);
		setRotationAngle(leg3, 0.0F, 0.0F, -0.3054F);
		leg3.setTextureOffset(16, 34).addBox(-22.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, true);

		leg4 = new ModelRenderer(this);
		leg4.setRotationPoint(4.0F, 17.0F, 0.0F);
		setRotationAngle(leg4, 0.0F, 0.0F, 0.3054F);
		leg4.setTextureOffset(16, 34).addBox(0.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, false);

		leg5 = new ModelRenderer(this);
		leg5.setRotationPoint(-4.0F, 17.0F, -1.0F);
		setRotationAngle(leg5, 0.0F, -0.4363F, -0.3054F);
		leg5.setTextureOffset(16, 34).addBox(-22.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, true);

		leg6 = new ModelRenderer(this);
		leg6.setRotationPoint(4.0F, 17.0F, -1.0F);
		setRotationAngle(leg6, 0.0F, 0.4363F, 0.3054F);
		leg6.setTextureOffset(16, 34).addBox(0.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(CreederEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		// previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		leg1.render(matrixStack, buffer, packedLight, packedOverlay);
		leg2.render(matrixStack, buffer, packedLight, packedOverlay);
		leg3.render(matrixStack, buffer, packedLight, packedOverlay);
		leg4.render(matrixStack, buffer, packedLight, packedOverlay);
		leg5.render(matrixStack, buffer, packedLight, packedOverlay);
		leg6.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}