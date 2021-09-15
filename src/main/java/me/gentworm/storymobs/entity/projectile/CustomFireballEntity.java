package me.gentworm.storymobs.entity.projectile;

import me.gentworm.storymobs.entity.GiantGhastEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

/**
 * Code from:
 * https://github.com/TeamTwilight/twilightforest/blob/1.16.x/src/main/java/twilightforest/entity/boss/UrGhastFireballEntity.java
 * 
 * @apiNote Twilight Forest
 *
 */

public class CustomFireballEntity extends FireballEntity {

	public CustomFireballEntity(World world, GiantGhastEntity entityTFTowerBoss, double x, double y, double z) {
		super(world, entityTFTowerBoss, x, y, z);
	}

	// [VanillaCopy] super, edits noted
	@Override
	protected void onImpact(RayTraceResult result) {
		// TF - don't collide with other fireballs
		if (result instanceof EntityRayTraceResult) {
			if (!this.world.isRemote
					&& !(((EntityRayTraceResult) result).getEntity() instanceof DamagingProjectileEntity)) {
				if (((EntityRayTraceResult) result).getEntity() != null) {
					// TF - up damage by 10
					((EntityRayTraceResult) result).getEntity()
							.attackEntityFrom(DamageSource.func_233547_a_(this, this.func_234616_v_()), 16.0F);
					this.applyEnchantments((LivingEntity) this.func_234616_v_(),
							((EntityRayTraceResult) result).getEntity());
				}

				boolean flag = ForgeEventFactory.getMobGriefingEvent(this.world, this.func_234616_v_());
				this.world.createExplosion(null, this.getPosX(), this.getPosY(), this.getPosZ(), this.explosionPower,
						flag, flag ? Explosion.Mode.BREAK : Explosion.Mode.NONE);
				this.remove();
			}
		}
	}

	@Override
	public void shoot(double x, double y, double z, float scale, float dist) {
		Vector3d vec3d = (new Vector3d(x, y, z)).normalize().add(this.rand.nextGaussian() * 0.0075F * dist,
				this.rand.nextGaussian() * 0.0075F * dist, this.rand.nextGaussian() * 0.0075F * dist).scale(scale);
		this.setMotion(vec3d);
		float f = MathHelper.sqrt(horizontalMag(vec3d));
		this.rotationYaw = (float) (MathHelper.atan2(vec3d.x, z) * (180F / (float) Math.PI));
		this.rotationPitch = (float) (MathHelper.atan2(vec3d.y, f) * (180F / (float) Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
	}
}