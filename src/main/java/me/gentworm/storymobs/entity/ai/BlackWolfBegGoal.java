package me.gentworm.storymobs.entity.ai;

import java.util.EnumSet;

import me.gentworm.storymobs.entity.BlackWolfEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public class BlackWolfBegGoal extends Goal {
  private final BlackWolfEntity wolf;
  
  private Player player;
  
  private final Level world;
  
  private final float minPlayerDistance;
  
  private int timeoutCounter;
  
  private final TargetingConditions playerPredicate;
  
  public BlackWolfBegGoal(BlackWolfEntity p_i1617_1_, float p_i1617_2_) {
    this.wolf = p_i1617_1_;
    this.world = p_i1617_1_.level;
    this.minPlayerDistance = p_i1617_2_;
    this.playerPredicate = (new TargetingConditions()).range(p_i1617_2_).allowInvulnerable().allowSameTeam().allowNonAttackable();
    setFlags(EnumSet.of(Goal.Flag.LOOK));
  }
  
  public boolean canUse() {
    this.player = this.world.getNearestPlayer(this.playerPredicate, (LivingEntity)this.wolf);
    if (this.player == null)
      return false; 
    return hasTemptationItemInHand(this.player);
  }
  
  public boolean canContinueToUse() {
    if (!this.player.isAlive())
      return false; 
    if (this.wolf.distanceToSqr((Entity)this.player) > (this.minPlayerDistance * this.minPlayerDistance))
      return false; 
    return (this.timeoutCounter > 0 && hasTemptationItemInHand(this.player));
  }
  
  public void start() {
    this.wolf.setIsInterested(true);
    this.timeoutCounter = 40 + this.wolf.getRandom().nextInt(40);
  }
  
  public void stop() {
    this.wolf.setIsInterested(false);
    this.player = null;
  }
  
  public void tick() {
    this.wolf.getLookControl().setLookAt(this.player.getX(), this.player.getEyeY(), this.player.getZ(), 10.0F, this.wolf.getMaxHeadXRot());
    this.timeoutCounter--;
  }
  
  private boolean hasTemptationItemInHand(Player p_75382_1_) {
    for (InteractionHand lvt_5_1_ : InteractionHand.values()) {
      ItemStack lvt_6_1_ = p_75382_1_.getItemInHand(lvt_5_1_);
      if (this.wolf.isTame() && lvt_6_1_.getItem() == Items.BONE)
        return true; 
      if (this.wolf.isFood(lvt_6_1_))
        return true; 
    } 
    return false;
  }
}
