package me.gentworm.storymobs.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.World;

public class BlackWolfEntity extends WolfEntity {

	public BlackWolfEntity(EntityType<? extends BlackWolfEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public static AttributeModifierMap.MutableAttribute func_234233_eS_() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, (double) 0.35F)
				.createMutableAttribute(Attributes.MAX_HEALTH, 16.0D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0F);
	}

}
