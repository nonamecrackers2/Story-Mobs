package me.gentworm.storymobs.client.layer;

import me.gentworm.storymobs.StoryMobs;
import me.gentworm.storymobs.client.model.PrisonZombieModel;
import me.gentworm.storymobs.entity.PrisonZombieEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PrisonZombieEyeLayer<T extends PrisonZombieEntity> extends AbstractEyesLayer<T, PrisonZombieModel<T>> {
	private static final RenderType RENDER_TYPE = RenderType
			.getEyes(new ResourceLocation(StoryMobs.MODID, "textures/entity/prison_zombie/prison_zombie_eye_glow.png"));

	public PrisonZombieEyeLayer(IEntityRenderer<T, PrisonZombieModel<T>> p_i50939_1_) {
		super(p_i50939_1_);
	}

	public RenderType getRenderType() {
		return RENDER_TYPE;
	}
}
