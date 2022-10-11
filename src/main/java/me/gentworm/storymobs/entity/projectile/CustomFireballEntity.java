package me.gentworm.storymobs.entity.projectile;

import me.gentworm.storymobs.entity.GiantGhastEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

/**
 * Code from:
 * https://github.com/TeamTwilight/twilightforest/blob/1.16.x/src/main/java/twilightforest/entity/boss/UrGhastFireballEntity.java
 * 
 * @apiNote Twilight Forest
 */

public class CustomFireballEntity extends LargeFireball {

	public CustomFireballEntity(Level world, GiantGhastEntity entityTFTowerBoss, double x, double y, double z) {
		super(world, entityTFTowerBoss, x, y, z);
	}

	// [VanillaCopy] super, edits noted
	@Override
	protected void onHit(HitResult result) {
		// TF - don't collide with other fireballs
		if (!this.level.isClientSide) {
			boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level,
					this.getOwner());
			this.level.explode((Entity) null, this.getX(), this.getY(), this.getZ(),
					(float) this.explosionPower, flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
			this.remove();
		}
		if (result instanceof EntityHitResult) {
			if (!this.level.isClientSide
					&& !(((EntityHitResult) result).getEntity() instanceof AbstractHurtingProjectile)) {
				if (((EntityHitResult) result).getEntity() != null) {
					// TF - up damage by 10
					((EntityHitResult) result).getEntity()
							.hurt(DamageSource.fireball(this, this.getOwner()), 16.0F);
					this.doEnchantDamageEffects((LivingEntity) this.getOwner(),
							((EntityHitResult) result).getEntity());
				}
			}
		}
	}

	@Override
	public void shoot(double x, double y, double z, float scale, float dist) {
		Vec3 vec3d = (new Vec3(x, y, z)).normalize().add(this.random.nextGaussian() * 0.0075F * dist,
				this.random.nextGaussian() * 0.0075F * dist, this.random.nextGaussian() * 0.0075F * dist).scale(scale);
		this.setDeltaMovement(vec3d);
		float f = Mth.sqrt(getHorizontalDistanceSqr(vec3d));
		this.yRot = (float) (Mth.atan2(vec3d.x, z) * (180F / (float) Math.PI));
		this.xRot = (float) (Mth.atan2(vec3d.y, f) * (180F / (float) Math.PI));
		this.yRotO = this.yRot;
		this.xRotO = this.xRot;
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		return false;
	}
}