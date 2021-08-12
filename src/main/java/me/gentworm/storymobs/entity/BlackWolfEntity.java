package me.gentworm.storymobs.entity;

import me.gentworm.storymobs.entity.ai.BlackWolfBegGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.World;

public class BlackWolfEntity extends WolfEntity {

	public BlackWolfEntity(EntityType<? extends WolfEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(9, new BlackWolfBegGoal(this, 8.0F));
		super.registerGoals();
	}
}
