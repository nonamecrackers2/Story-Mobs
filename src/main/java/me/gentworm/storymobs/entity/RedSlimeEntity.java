package me.gentworm.storymobs.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.world.World;

public class RedSlimeEntity extends SlimeEntity {

	public RedSlimeEntity(EntityType<? extends RedSlimeEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public static AttributeModifierMap.MutableAttribute getAttributes() {
		return SpiderEntity.func_234305_eI_().createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D);
	}

}
