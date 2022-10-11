package me.gentworm.storymobs.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;

public class BlackWolfEntity extends Wolf {

	public BlackWolfEntity(EntityType<? extends BlackWolfEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.35F)
				.add(Attributes.MAX_HEALTH, 16.0D)
				.add(Attributes.ATTACK_DAMAGE, 4.0F);
	}

}
