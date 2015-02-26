package seremis.geninfusion.soul.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.StringUtils;
import seremis.geninfusion.api.soul.IEntitySoulCustom;

public abstract class EntityAITargetCustom extends EntityAIBase {

    public IEntitySoulCustom entity;
    public EntityLiving living;

    public boolean shouldCheckSight;
    public boolean nearbyOnly;
    public int targetSearchStatus;
    public int targetSearchDelay;
    public int targetUnseenTicks;

    public EntityAITargetCustom(IEntitySoulCustom entity, boolean targetVisible) {
        this(entity, targetVisible, false);
    }

    public EntityAITargetCustom(IEntitySoulCustom entity, boolean targetVisible, boolean nearbyOnly) {
        this.entity = entity;
        this.living = (EntityLiving) entity;
        this.shouldCheckSight = targetVisible;
        this.nearbyOnly = nearbyOnly;
    }

    public boolean continueExecuting() {
        EntityLivingBase attackTarget = living.getAttackTarget();

        if(attackTarget != null && attackTarget.isEntityAlive()) {
            double distance = getFollowRange();

            if(living.getDistanceSqToEntity(attackTarget) <= distance * distance) {
                if(shouldCheckSight) {
                    if(living.getEntitySenses().canSee(attackTarget)) {
                        targetUnseenTicks = 0;
                    } else if(++targetUnseenTicks > 60) {
                        return false;
                    }
                }
                return !(attackTarget instanceof EntityPlayerMP) || !((EntityPlayerMP) attackTarget).theItemInWorldManager.isCreative();
            }
        }
        return false;
    }

    public double getFollowRange() {
        IAttributeInstance iattributeinstance = this.living.getEntityAttribute(SharedMonsterAttributes.followRange);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
    }

    public void startExecuting() {
        this.targetSearchStatus = 0;
        this.targetSearchDelay = 0;
        this.targetUnseenTicks = 0;
    }

    public void resetTask() {
        this.living.setAttackTarget(null);
    }

    protected boolean isSuitableTarget(EntityLivingBase target, boolean targetInvincible) {
        if(target != null && target != living && target.isEntityAlive() && living.canAttackClass(target.getClass()) && !isTargetOwnerOrPet(target)) {
            if(!(target instanceof EntityPlayer && targetInvincible && ((EntityPlayer) target).capabilities.disableDamage)) {
                if(entity.isWithinHomeDistance(MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ))) {
                    if(shouldCheckSight && !living.getEntitySenses().canSee(target)) {
                        return false;
                    } else {
                        if(nearbyOnly) {
                            if(--targetSearchDelay <= 0) {
                                targetSearchStatus = 0;
                            }

                            if(targetSearchStatus == 0) {
                                targetSearchStatus = canAttack(target) ? 1 : 2;
                            }

                            if(targetSearchStatus == 2) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isTargetOwnerOrPet(EntityLivingBase target) {
        return ((living instanceof IEntityOwnable) && StringUtils.isNotEmpty(((IEntityOwnable) living).func_152113_b())) && (target == ((IEntityOwnable) living).getOwner() || target instanceof IEntityOwnable && ((IEntityOwnable) living).func_152113_b().equals(((IEntityOwnable) target).func_152113_b()));
    }

    private boolean canAttack(EntityLivingBase target) {
        targetSearchDelay = 10 + living.getRNG().nextInt(5);
        PathEntity path = living.getNavigator().getPathToEntityLiving(target);

        if(path != null) {
            PathPoint goal = path.getFinalPathPoint();

            if(goal != null) {
                int dX = goal.xCoord - MathHelper.floor_double(target.posX);
                int dZ = goal.xCoord - MathHelper.floor_double(target.posZ);

                return (double) (dX * dX + dZ * dZ) <= 2.25D;
            }
        }
        return false;
    }

}
