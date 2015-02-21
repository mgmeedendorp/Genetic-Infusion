package seremis.geninfusion.soul.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import seremis.geninfusion.api.soul.IEntitySoulCustom;

public class EntityAIAttackOnCollideCustom extends EntityAIBase {

    World worldObj;
    IEntitySoulCustom attacker;
    EntityLiving attackerLiving;
    int attackTick;
    double speedTowardsTarget;
    boolean longMemory;
    PathEntity entityPathEntity;
    Class classTarget;
    private int attackTimeout;
    private double targetX;
    private double targetY;
    private double targetZ;

    private int failedPathFindingPenalty;

    public EntityAIAttackOnCollideCustom(IEntitySoulCustom entity, Class targetClass, double moveSpeed, boolean longMemory) {
        this(entity, moveSpeed, longMemory); this.classTarget = targetClass;
    }

    public EntityAIAttackOnCollideCustom(IEntitySoulCustom entity, double moveSpeed, boolean longMemory) {
        this.attacker = entity; this.worldObj = entity.getWorld(); this.speedTowardsTarget = moveSpeed;
        this.longMemory = longMemory;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
        EntityLivingBase attackTarget = this.attackerLiving.getAttackTarget();

        if(attackTarget != null && attackTarget.isEntityAlive() && (classTarget == null || classTarget.isAssignableFrom(attackTarget.getClass()))) {
            if(--this.attackTimeout <= 0) {
                this.entityPathEntity = this.attackerLiving.getNavigator().getPathToEntityLiving(attackTarget);
                this.attackTimeout = 4 + this.attackerLiving.getRNG().nextInt(7);
                return this.entityPathEntity != null;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean continueExecuting() {
        EntityLivingBase attackTarget = this.attackerLiving.getAttackTarget();
        return attackTarget != null && (attackTarget.isEntityAlive() && (!this.longMemory ? !this.attackerLiving.getNavigator().noPath() : this.attacker.isWithinHomeDistance(MathHelper.floor_double(attackTarget.posX), MathHelper.floor_double(attackTarget.posY), MathHelper.floor_double(attackTarget.posZ))));
    }

    public void startExecuting() {
        this.attackerLiving.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.attackTimeout = 0;
    }

    public void resetTask() {
        this.attackerLiving.getNavigator().clearPathEntity();
    }

    public void updateTask() {
        EntityLivingBase attackTarget = this.attackerLiving.getAttackTarget();
        this.attackerLiving.getLookHelper().setLookPositionWithEntity(attackTarget, 30.0F, 30.0F);
        double targetDistance = this.attackerLiving.getDistanceSq(attackTarget.posX, attackTarget.boundingBox.minY, attackTarget.posZ);
        double minAttackDistance = (double) (this.attackerLiving.width * 2.0F * this.attackerLiving.width * 2.0F + attackTarget.width);
        --this.attackTimeout;

        if((this.longMemory || this.attackerLiving.getEntitySenses().canSee(attackTarget)) && this.attackTimeout <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || attackTarget.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attackerLiving.getRNG().nextFloat() < 0.05F)) {
            this.targetX = attackTarget.posX;
            this.targetY = attackTarget.boundingBox.minY;
            this.targetZ = attackTarget.posZ;
            this.attackTimeout = failedPathFindingPenalty + 4 + this.attackerLiving.getRNG().nextInt(7);

            if(this.attackerLiving.getNavigator().getPath() != null) {
                PathPoint finalPathPoint = this.attackerLiving.getNavigator().getPath().getFinalPathPoint();
                if(finalPathPoint != null && attackTarget.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1) {
                    failedPathFindingPenalty = 0;
                } else {
                    failedPathFindingPenalty += 10;
                }
            } else {
                failedPathFindingPenalty += 10;
            }

            if(targetDistance > 1024.0D) {
                this.attackTimeout += 10;
            } else if(targetDistance > 256.0D) {
                this.attackTimeout += 5;
            }

            if(!this.attackerLiving.getNavigator().tryMoveToEntityLiving(attackTarget, this.speedTowardsTarget)) {
                this.attackTimeout += 15;
            }
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);

        if(targetDistance <= minAttackDistance && this.attackTick <= 20) {
            this.attackTick = 20;

            if(this.attackerLiving.getHeldItem() != null) {
                this.attackerLiving.swingItem();
            }

            this.attackerLiving.attackEntityAsMob(attackTarget);
        }
    }

}
