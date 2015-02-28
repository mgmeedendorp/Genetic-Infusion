package seremis.geninfusion.soul.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.util.GIRandomPositionGenerator;

public class EntityAIMoveTowardsTargetCustom extends EntityAIBase {

    public IEntitySoulCustom entity;
    public EntityLiving living;

    public EntityLivingBase targetEntity;
    public Vec3 target;
    public double moveSpeed;
    public float maxDistance;

    public EntityAIMoveTowardsTargetCustom(IEntitySoulCustom entity, double moveSpeed, float maxDistance) {
        this.entity = entity;
        this.moveSpeed = moveSpeed;
        this.maxDistance = maxDistance;
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        targetEntity = this.living.getAttackTarget();

        if(targetEntity != null && targetEntity.getDistanceSqToEntity(living) <= (double) (maxDistance * maxDistance)) {
            target = GIRandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 16, 7, Vec3.createVectorHelper(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ));

            if(target != null) {
                return true;
            }
        }
        return false;
    }

    public boolean continueExecuting() {
        return !this.living.getNavigator().noPath() && this.targetEntity.isEntityAlive() && this.targetEntity.getDistanceSqToEntity(this.living) < (double) (this.maxDistance * this.maxDistance);
    }

    public void resetTask() {
        this.targetEntity = null;
    }

    public void startExecuting() {
        this.living.getNavigator().tryMoveToXYZ(target.xCoord, target.yCoord, target.zCoord, this.moveSpeed);
    }
}