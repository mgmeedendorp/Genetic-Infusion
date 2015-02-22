package seremis.geninfusion.soul.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.util.GIRandomPositionGenerator;

public class EntityAIMoveTowardsRestrictionCustom extends EntityAIBase {

    public IEntitySoulCustom entity;
    public EntityLiving living;

    private Vec3 target;
    private double movementSpeed;

    public EntityAIMoveTowardsRestrictionCustom(IEntitySoulCustom entity, double moveSpeed) {
        this.entity = entity;
        this.movementSpeed = moveSpeed;
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        if(!entity.isWithinHomeDistanceCurrentPosition()) {
            ChunkCoordinates chunkcoordinates = this.entity.getHomePosition();
            target = GIRandomPositionGenerator.findRandomTargetBlockTowards(entity, 16, 7, Vec3.createVectorHelper((double) chunkcoordinates.posX, (double) chunkcoordinates.posY, (double) chunkcoordinates.posZ));

            if(target != null) {
                return true;
            }
        }
        return false;
    }

    public boolean continueExecuting() {
        return !this.living.getNavigator().noPath();
    }

    public void startExecuting() {
        this.living.getNavigator().tryMoveToXYZ(target.xCoord, target.yCoord, target.zCoord, this.movementSpeed);
    }

}
