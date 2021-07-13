package me.gentworm.storymobs.entity.ai;

import java.util.EnumSet;

import me.gentworm.storymobs.entity.CreederEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class CreederBulgeGoal extends Goal {
	private final CreederEntity swellingCreeder;
	private LivingEntity creeperAttackTarget;

	public CreederBulgeGoal(CreederEntity creederEntity) {
		this.swellingCreeder = creederEntity;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	public boolean shouldExecute() {
		LivingEntity livingentity = this.swellingCreeder.getAttackTarget();
		return this.swellingCreeder.getCreeperState() > 0
				|| livingentity != null && this.swellingCreeder.getDistanceSq(livingentity) < 9.0D;
	}

	public void startExecuting() {
		this.swellingCreeder.getNavigator().clearPath();
		this.creeperAttackTarget = this.swellingCreeder.getAttackTarget();
	}

	public void resetTask() {
		this.creeperAttackTarget = null;
	}

	public void tick() {
		if (this.creeperAttackTarget == null) {
			this.swellingCreeder.setCreeperState(-1);
		} else if (this.swellingCreeder.getDistanceSq(this.creeperAttackTarget) > 49.0D) {
			this.swellingCreeder.setCreeperState(-1);
		} else if (!this.swellingCreeder.getEntitySenses().canSee(this.creeperAttackTarget)) {
			this.swellingCreeder.setCreeperState(-1);
		} else {
			this.swellingCreeder.setCreeperState(1);
		}
	}
}
