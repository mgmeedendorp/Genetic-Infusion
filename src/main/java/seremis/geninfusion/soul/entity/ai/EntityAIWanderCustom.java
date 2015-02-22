package seremis.geninfusion.soul.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.util.GIRandomPositionGenerator;

public class EntityAIWanderCustom extends EntityAIBase {

    public IEntitySoulCustom entity;
    public EntityLiving living;

    public Vec3 targetPosition;
    public double moveSpeed;

    public EntityAIWanderCustom(IEntitySoulCustom entity, double moveSpeed) {
        this.entity = entity;
        this.living = (EntityLiving) entity;
        this.moveSpeed = moveSpeed;
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        if(living.getAge() < 100 && living.getRNG().nextInt(120) == 0) {
            targetPosition = GIRandomPositionGenerator.findRandomTarget(entity, 10, 7);
        }
        return targetPosition != null;
    }

    public boolean continueExecuting() {
        return !living.getNavigator().noPath();
    }

    public void startExecuting() {
        living.getNavigator().tryMoveToXYZ(targetPosition.xCoord, targetPosition.yCoord, targetPosition.zCoord, this.moveSpeed);
    }

}
