package me.gentworm.storymobs.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import me.gentworm.storymobs.entity.GiantGhastEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


public class GiantGhastModel extends EntityModel<GiantGhastEntity> {
	private final ModelPart body;
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart tentacle1;
	private final ModelPart tentacle2;
	private final ModelPart tentacle3;
	private final ModelPart tentacle4;
	private final ModelPart tentacle5;
	private final ModelPart tentacle6;
	private final ModelPart tentacle7;
	private final ModelPart tentacle8;
	private final ModelPart tentacle9;

	public GiantGhastModel() {
		texWidth = 64;
		texHeight = 32;

		body = new ModelPart(this);
		body.setPos(0.0F, 4.0F, 0.0F);
		body.texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
		body.texOffs(51, 2).addBox(-3.0F, -8.5F, -2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(51, 2).addBox(-3.0F, -8.5F, 3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(51, 2).addBox(1.0F, -8.5F, 3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(51, 2).addBox(1.0F, -8.5F, -2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 3).addBox(-1.0F, -8.5F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 3).addBox(0.5F, -8.5F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 3).addBox(-3.0F, -8.5F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 3).addBox(-3.0F, -8.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 3).addBox(-3.0F, -8.5F, 1.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 3).addBox(-3.0F, -8.5F, 2.25F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 3).addBox(-1.5F, -8.5F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 3).addBox(0.5F, -8.5F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 3).addBox(2.0F, -8.5F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 3).addBox(2.0F, -8.5F, 1.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 3).addBox(2.0F, -8.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.texOffs(53, 3).addBox(2.0F, -8.5F, -1.25F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		bone = new ModelPart(this);
		bone.setPos(-7.0F, -3.0F, -3.0F);
		body.addChild(bone);
		setRotationAngle(bone, 0.0F, 1.5708F, 0.0F);
		bone.texOffs(0, 0).addBox(-8.0F, -8.0F, -7.0F, 16.0F, 16.0F, 16.0F, -4.0F, false);

		bone2 = new ModelPart(this);
		bone2.setPos(12.0F, 7.0F, -9.0F);
		body.addChild(bone2);
		setRotationAngle(bone2, 0.0F, -1.5708F, 0.0F);
		bone2.texOffs(0, 0).addBox(0.0F, -16.0F, 0.0F, 16.0F, 16.0F, 16.0F, -2.0F, false);

		tentacle1 = new ModelPart(this);
		tentacle1.setPos(3.7F, 11.0F, -5.0F);
		tentacle1.texOffs(0, 0).addBox(-8.4F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

		tentacle2 = new ModelPart(this);
		tentacle2.setPos(-1.3F, 11.0F, -5.0F);
		tentacle2.texOffs(0, 0).addBox(6.6F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F, false);

		tentacle3 = new ModelPart(this);
		tentacle3.setPos(-6.3F, 11.0F, -5.0F);
		tentacle3.texOffs(0, 0).addBox(6.6F, 0.0F, -1.0F, 2.0F, 13.0F, 2.0F, 0.0F, false);

		tentacle4 = new ModelPart(this);
		tentacle4.setPos(6.3F, 11.0F, 0.0F);
		tentacle4.texOffs(0, 0).addBox(-13.6F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);

		tentacle5 = new ModelPart(this);
		tentacle5.setPos(1.3F, 11.0F, 0.0F);
		tentacle5.texOffs(0, 0).addBox(-3.6F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);

		tentacle6 = new ModelPart(this);
		tentacle6.setPos(-3.7F, 11.0F, 0.0F);
		tentacle6.texOffs(0, 0).addBox(6.4F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

		tentacle7 = new ModelPart(this);
		tentacle7.setPos(3.7F, 11.0F, 5.0F);
		tentacle7.texOffs(0, 0).addBox(-8.4F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		tentacle8 = new ModelPart(this);
		tentacle8.setPos(-1.3F, 11.0F, 5.0F);
		tentacle8.texOffs(0, 0).addBox(6.6F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

		tentacle9 = new ModelPart(this);
		tentacle9.setPos(-6.3F, 11.0F, 5.0F);
		tentacle9.texOffs(0, 0).addBox(6.6F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(GiantGhastEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
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

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}