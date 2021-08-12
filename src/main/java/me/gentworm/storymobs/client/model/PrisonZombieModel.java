package me.gentworm.storymobs.client.model;

import me.gentworm.storymobs.entity.PrisonZombieEntity;
import net.minecraft.client.renderer.entity.model.AbstractZombieModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PrisonZombieModel<T extends PrisonZombieEntity> extends AbstractZombieModel<T> {
	public PrisonZombieModel(float p_i1168_1_, boolean p_i1168_2_) {
		this(p_i1168_1_, 0.0F, 64, p_i1168_2_ ? 32 : 64);
	}

	protected PrisonZombieModel(float p_i48914_1_, float p_i48914_2_, int p_i48914_3_, int p_i48914_4_) {
		super(p_i48914_1_, p_i48914_2_, p_i48914_3_, p_i48914_4_);
	}

	public boolean isAggressive(T p_212850_1_) {
		return p_212850_1_.isAggressive();
	}
}
