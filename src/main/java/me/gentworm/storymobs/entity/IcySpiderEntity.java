package me.gentworm.storymobs.entity;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

//Spider that likes to attack and give slowness, lives only in cold biomes
public class IcySpiderEntity extends MonsterEntity {

	public IcySpiderEntity(EntityType<? extends MonsterEntity> p_i48553_1_, World p_i48553_2_) {
		super(p_i48553_1_, p_i48553_2_);
	}

	public static AttributeModifierMap.MutableAttribute getAttributes() {
		return MonsterEntity.registerAttributes().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.28D)
				.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.20D)
				.createMutableAttribute(Attributes.MAX_HEALTH, 15.0D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 10.0D);
	}

	public static boolean canIcySpiderSpawn(EntityType<IcySpiderEntity> animal, IWorld world, SpawnReason reason,
			BlockPos pos, Random random) {
		BlockState state = world.getBlockState(pos.down());
		return (state.isIn(Blocks.RED_SAND) || (state.isIn(Blocks.ICE)) || (state.isIn(Blocks.BLUE_ICE))
				|| (state.isIn(Blocks.FROSTED_ICE)) || (state.isIn(Blocks.PACKED_ICE)) || (state.isIn(Blocks.SNOW))
				|| (state.isIn(Blocks.SNOW_BLOCK)) || (state.isIn(Blocks.DIRT)) || (state.isIn(Blocks.GRASS_BLOCK)));
	}

}
