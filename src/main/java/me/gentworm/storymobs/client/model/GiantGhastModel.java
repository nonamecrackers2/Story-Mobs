package me.gentworm.storymobs.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import me.gentworm.storymobs.entity.GiantGhastEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


public class GiantGhastModel extends EntityModel<GiantGhastEntity> {
	private final ModelRenderer body;
	private final ModelRenderer bone;
	private final ModelRenderer bone2;
	private final ModelRenderer tentacle1;
	private final ModelRenderer tentacle2;
	private final ModelRenderer tentacle3;
	private final ModelRenderer tentacle4;
	private final ModelRenderer tentacle5;
	private final ModelRenderer tentacle6;
	private final ModelRenderer tentacle7;
	private final ModelRenderer tentacle8;
	private final ModelRenderer tentacle9;

	public GiantGhastModel() {
		textureWidth = 64;
		textureHeight = 32;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 4.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
		body.setTextureOffset(51, 2).addBox(-3.0F, -8.5F, -2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(51, 2).addBox(-3.0F, -8.5F, 3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(51, 2).addBox(1.0F, -8.5F, 3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(51, 2).addBox(1.0F, -8.5F, -2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(53, 3).addBox(-1.0F, -8.5F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(53, 3).addBox(0.5F, -8.5F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(53, 3).addBox(-3.0F, -8.5F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(53, 3).addBox(-3.0F, -8.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(53, 3).addBox(-3.0F, -8.5F, 1.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(53, 3).addBox(-3.0F, -8.5F, 2.25F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(53, 3).addBox(-1.5F, -8.5F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(53, 3).addBox(0.5F, -8.5F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(53, 3).addBox(2.0F, -8.5F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(53, 3).addBox(2.0F, -8.5F, 1.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(53, 3).addBox(2.0F, -8.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(53, 3).addBox(2.0F, -8.5F, -1.25F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setRotationPoint(-7.0F, -3.0F, -3.0F);
		body.addChild(bone);
		setRotationAngle(bone, 0.0F, 1.5708F, 0.0F);
		bone.setTextureOffset(0, 0).addBox(-8.0F, -8.0F, -7.0F, 16.0F, 16.0F, 16.0F, -4.0F, false);

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(12.0F, 7.0F, -9.0F);
		body.addChild(bone2);
		setRotationAngle(bone2, 0.0F, -1.5708F, 0.0F);
		bone2.setTextureOffset(0, 0).addBox(0.0F, -16.0F, 0.0F, 16.0F, 16.0F, 16.0F, -2.0F, false);

		tentacle1 = new ModelRenderer(this);
		tentacle1.setRotationPoint(3.7F, 11.0F, -5.0F);
		tentacle1.setTextureOffset(0, 0).addBox(-8.4F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

		tentacle2 = new ModelRenderer(this);
		tentacle2.setRotationPoint(-1.3F, 11.0F, -5.0F);
		tentacle2.setTextureOffset(0, 0).addBox(6.6F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F, false);

		tentacle3 = new ModelRenderer(this);
		tentacle3.setRotationPoint(-6.3F, 11.0F, -5.0F);
		tentacle3.setTextureOffset(0, 0).addBox(6.6F, 0.0F, -1.0F, 2.0F, 13.0F, 2.0F, 0.0F, false);

		tentacle4 = new ModelRenderer(this);
		tentacle4.setRotationPoint(6.3F, 11.0F, 0.0F);
		tentacle4.setTextureOffset(0, 0).addBox(-13.6F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);

		tentacle5 = new ModelRenderer(this);
		tentacle5.setRotationPoint(1.3F, 11.0F, 0.0F);
		tentacle5.setTextureOffset(0, 0).addBox(-3.6F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);

		tentacle6 = new ModelRenderer(this);
		tentacle6.setRotationPoint(-3.7F, 11.0F, 0.0F);
		tentacle6.setTextureOffset(0, 0).addBox(6.4F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

		tentacle7 = new ModelRenderer(this);
		tentacle7.setRotationPoint(3.7F, 11.0F, 5.0F);
		tentacle7.setTextureOffset(0, 0).addBox(-8.4F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		tentacle8 = new ModelRenderer(this);
		tentacle8.setRotationPoint(-1.3F, 11.0F, 5.0F);
		tentacle8.setTextureOffset(0, 0).addBox(6.6F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		tentacle9 = new ModelRenderer(this);
		tentacle9.setRotationPoint(-6.3F, 11.0F, 5.0F);
		tentacle9.setTextureOffset(0, 0).addBox(6.6F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(GiantGhastEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		tentacle1.render(matrixStack, buffer, packedLight, packedOverlay);
		tentacle2.render(matrixStack, buffer, packedLight, packedOverlay);
		tentacle3.render(matrixStack, buffer, packedLight, packedOverlay);
		tentacle4.render(matrixStack, buffer, packedLight, packedOverlay);
		tentacle5.render(matrixStack, buffer, packedLight, packedOverlay);
		tentacle6.render(matrixStack, buffer, packedLight, packedOverlay);
		tentacle7.render(matrixStack, buffer, packedLight, packedOverlay);
		tentacle8.render(matrixStack, buffer, packedLight, packedOverlay);
		tentacle9.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}