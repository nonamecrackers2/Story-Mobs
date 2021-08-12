package me.gentworm.storymobs.entity.ai;

import java.util.EnumSet;

import me.gentworm.storymobs.entity.BlackWolfEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BlackWolfBegGoal extends Goal {
  private final BlackWolfEntity wolf;
  
  private PlayerEntity player;
  
  private final World world;
  
  private final float minPlayerDistance;
  
  private int timeoutCounter;
  
  private final EntityPredicate playerPredicate;
  
  public BlackWolfBegGoal(BlackWolfEntity p_i1617_1_, float p_i1617_2_) {
    this.wolf = p_i1617_1_;
    this.world = p_i1617_1_.world;
    this.minPlayerDistance = p_i1617_2_;
    this.playerPredicate = (new EntityPredicate()).setDistance(p_i1617_2_).allowInvulnerable().allowFriendlyFire().setSkipAttackChecks();
    setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
  }
  
  public boolean shouldExecute() {
    this.player = this.world.getClosestPlayer(this.playerPredicate, (LivingEntity)this.wolf);
    if (this.player == null)
      return false; 
    return hasTemptationItemInHand(this.player);
  }
  
  public boolean shouldContinueExecuting() {
    if (!this.player.isAlive())
      return false; 
    if (this.wolf.getDistanceSq((Entity)this.player) > (this.minPlayerDistance * this.minPlayerDistance))
      return false; 
    return (this.timeoutCounter > 0 && hasTemptationItemInHand(this.player));
  }
  
  public void startExecuting() {
    this.wolf.setBegging(true);
    this.timeoutCounter = 40 + this.wolf.getRNG().nextInt(40);
  }
  
  public void resetTask() {
    this.wolf.setBegging(false);
    this.player = null;
  }
  
  public void tick() {
    this.wolf.getLookController().setLookPosition(this.player.getPosX(), this.player.getPosYEye(), this.player.getPosZ(), 10.0F, this.wolf.getVerticalFaceSpeed());
    this.timeoutCounter--;
  }
  
  private boolean hasTemptationItemInHand(PlayerEntity p_75382_1_) {
    for (Hand lvt_5_1_ : Hand.values()) {
      ItemStack lvt_6_1_ = p_75382_1_.getHeldItem(lvt_5_1_);
      if (this.wolf.isTamed() && lvt_6_1_.getItem() == Items.BONE)
        return true; 
      if (this.wolf.isBreedingItem(lvt_6_1_))
        return true; 
    } 
    return false;
  }
}
