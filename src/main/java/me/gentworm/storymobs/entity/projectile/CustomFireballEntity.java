package me.gentworm.storymobs.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class CustomFireballEntity extends AbstractFireballEntity {
	public int explosionPower = 1;

	public CustomFireballEntity(World p_i1769_1_, LivingEntity p_i1769_2_, double p_i1769_3_, double p_i1769_5_,
			double p_i1769_7_) {
		super(EntityType.FIREBALL, p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_, p_i1769_1_);
	}

	@Override
	protected void onImpact(RayTraceResult p_70227_1_) {
		super.onImpact(p_70227_1_);
		if (!this.world.isRemote) {
			boolean flag = ForgeEventFactory.getMobGriefingEvent(this.world, func_234616_v_());
			this.world.createExplosion((Entity) null, getPosX(), getPosY(), getPosZ(), this.explosionPower, flag,
					flag ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
		}
	}

	@Override
	protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
		super.onEntityHit(p_213868_1_);
		if (!this.world.isRemote) {
			Entity entity = p_213868_1_.getEntity();
			Entity entity1 = func_234616_v_();
			entity.attackEntityFrom(DamageSource.func_233547_a_(this, entity1), 6.0F);
			if (entity instanceof CustomFireballEntity || entity1 instanceof CustomFireballEntity)
				return;
			if (entity1 instanceof LivingEntity)
				applyEnchantments((LivingEntity) entity1, entity);
		}
	}

	public void writeAdditional(CompoundNBT p_213281_1_) {
		super.writeAdditional(p_213281_1_);
		p_213281_1_.putInt("ExplosionPower", this.explosionPower);
	}

	public void readAdditional(CompoundNBT p_70037_1_) {
		super.readAdditional(p_70037_1_);
		if (p_70037_1_.contains("ExplosionPower", 99))
			this.explosionPower = p_70037_1_.getInt("ExplosionPower");
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
}
