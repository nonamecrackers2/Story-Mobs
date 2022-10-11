package me.gentworm.storymobs.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports

@OnlyIn(Dist.CLIENT)
public class CreederModel<T extends Entity> extends ListModel<T> {
	private final ModelPart main;
	private final ModelPart leg2;
	private final ModelPart leg4;
	private final ModelPart leg6;
	private final ModelPart leg5;
	private final ModelPart head;
	private final ModelPart leg3;
	private final ModelPart leg1;
	private final ModelPart body;

	public CreederModel() {
		texWidth = 64;
		texHeight = 64;

		main = new ModelPart(this);
		main.setPos(0.0F, 10.3F, 0.0F);

		leg2 = new ModelPart(this);
		leg2.setPos(4.0F, 1.0F, 1.0F);
		main.addChild(leg2);
		setRotationAngle(leg2, 0.0F, -0.4363F, 0.3054F);
		leg2.texOffs(16, 34).addBox(0.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, false);

		leg4 = new ModelPart(this);
		leg4.setPos(4.0F, 1.0F, 0.0F);
		main.addChild(leg4);
		setRotationAngle(leg4, 0.0F, 0.0F, 0.3054F);
		leg4.texOffs(16, 34).addBox(0.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, false);

		leg6 = new ModelPart(this);
		leg6.setPos(4.0F, 1.0F, -1.0F);
		main.addChild(leg6);
		setRotationAngle(leg6, 0.0F, 0.4363F, 0.3054F);
		leg6.texOffs(16, 34).addBox(0.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, false);

		leg5 = new ModelPart(this);
		leg5.setPos(-4.0F, 1.0F, -1.0F);
		main.addChild(leg5);
		setRotationAngle(leg5, 0.0F, -0.4363F, -0.3054F);
		leg5.texOffs(16, 34).addBox(-22.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, true);

		head = new ModelPart(this);
		head.setPos(0.0F, -10.0F, 0.0F);
		main.addChild(head);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		leg3 = new ModelPart(this);
		leg3.setPos(-4.0F, 1.0F, 0.0F);
		main.addChild(leg3);
		setRotationAngle(leg3, 0.0F, 0.0F, -0.3054F);
		leg3.texOffs(16, 34).addBox(-22.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, true);

		leg1 = new ModelPart(this);
		leg1.setPos(-4.0F, 1.0F, 1.0F);
		main.addChild(leg1);
		setRotationAngle(leg1, 0.0F, 0.4363F, -0.3054F);
		leg1.texOffs(16, 34).addBox(-22.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, 0.0F, true);

		body = new ModelPart(this);
		body.setPos(0.0F, -10.0F, 0.0F);
		main.addChild(body);
		body.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public Iterable<ModelPart> parts() {
		return (Iterable<ModelPart>) ImmutableList.of(this.head, this.body, this.leg1, this.leg2, this.leg3,
				this.leg4, this.leg5, this.leg6);
	}

	@Override
	public void setupAnim(T arg0, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.leg1.zRot = (-(float) Math.PI / 4F);
		this.leg2.zRot = ((float) Math.PI / 4F);
		this.leg3.zRot = -0.58119464F;
		this.leg4.zRot = 0.58119464F;
		this.leg5.zRot = -0.58119464F;
		this.leg6.zRot = 0.58119464F;
		this.leg1.yRot = ((float) Math.PI / 4F);
		this.leg2.yRot = (-(float) Math.PI / 4F);
		this.leg3.yRot = ((float) Math.PI / 8F);
		this.leg4.yRot = (-(float) Math.PI / 8F);
		this.leg5.yRot = (-(float) Math.PI / 8F);
		this.leg6.yRot = ((float) Math.PI / 8F);
		float f3 = -(Mth.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
		float f4 = -(Mth.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * limbSwingAmount;
		float f5 = -(Mth.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		Mth.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI * 1.5F));
		float f7 = Math.abs(Mth.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
		float f8 = Math.abs(Mth.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * limbSwingAmount;
		float f9 = Math.abs(Mth.sin(limbSwing * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		Math.abs(Mth.sin(limbSwing * 0.6662F + ((float) Math.PI * 1.5F)) * 0.4F);
		this.leg1.yRot += f3;
		this.leg2.yRot += -f3;
		this.leg3.yRot += f4;
		this.leg4.yRot += -f4;
		this.leg5.yRot += f5;
		this.leg6.yRot += -f5;
		this.leg1.zRot += f7;
		this.leg2.zRot += -f7;
		this.leg3.zRot += f8;
		this.leg4.zRot += -f8;
		this.leg5.zRot += f9;
		this.leg6.zRot += -f9;
	}
}