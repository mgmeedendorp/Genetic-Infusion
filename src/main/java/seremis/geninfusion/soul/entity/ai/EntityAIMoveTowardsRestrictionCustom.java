package seremis.geninfusion.soul.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.util.GIRandomPositionGenerator;

public class EntityAIMoveTowardsRestrictionCustom extends EntityAIBase {

    public IEntitySoulCustom entity;
    public EntityLiving entityLiving;
    public Vec3 target;
    public double movementSpeed;

    public EntityAIMoveTowardsRestrictionCustom(IEntitySoulCustom entity, double moveSpeed) {
        this.entity = entity;
        this.movementSpeed = moveSpeed;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if(this.entity.isWithinHomeDistanceCurrentPosition()) {
            return false;
        } else {
            ChunkCoordinates chunkCoord = this.entity.getHomePosition();
            Vec3 vec3 = GIRandomPositionGenerator.findRandomTargetBlockTowards(entity, 16, 7, Vec3.createVectorHelper((double) chunkCoord.posX, (double) chunkCoord.posY, (double) chunkCoord.posZ));

            if(vec3 == null) {
                return false;
            } else {
                target = vec3;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting() {
        return !this.entityLiving.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        if(target != null)
            this.entityLiving.getNavigator().tryMoveToXYZ(target.xCoord, target.yCoord, target.zCoord, this.movementSpeed);
    }

}
