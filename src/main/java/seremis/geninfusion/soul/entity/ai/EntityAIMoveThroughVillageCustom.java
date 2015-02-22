package seremis.geninfusion.soul.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.util.GIRandomPositionGenerator;

import java.util.ArrayList;
import java.util.List;

public class EntityAIMoveThroughVillageCustom extends EntityAIBase {

    public IEntitySoulCustom entity;
    public EntityLiving living;
    public double moveSpeed;
    public PathEntity pathNavigate;
    public VillageDoorInfo doorInfo;
    public boolean isNocturnal;
    public List<VillageDoorInfo> doorList = new ArrayList<VillageDoorInfo>();

    public EntityAIMoveThroughVillageCustom(IEntitySoulCustom entity, double moveSpeed, boolean isNocturnal) {
        this.entity = entity;
        this.living = (EntityLiving) entity;
        this.moveSpeed = moveSpeed;
        this.isNocturnal = isNocturnal;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        this.resizeDoorList();

        if(!(isNocturnal && entity.getWorld().isDaytime())) {
            Village village = entity.getWorld().villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.living.posX), MathHelper.floor_double(this.living.posY), MathHelper.floor_double(this.living.posZ), 0);
            if(village != null) {
                doorInfo = findNearestDoor(village);

                if(doorInfo != null) {
                    boolean breakDoors = living.getNavigator().getCanBreakDoors();
                    living.getNavigator().setBreakDoors(false);
                    pathNavigate = living.getNavigator().getPathToXYZ((double) this.doorInfo.posX, (double) this.doorInfo.posY, (double) this.doorInfo.posZ);
                    living.getNavigator().setBreakDoors(breakDoors);

                    if(pathNavigate != null) {
                        return true;
                    } else {
                        Vec3 walkGoal = GIRandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 10, 7, Vec3.createVectorHelper((double) this.doorInfo.posX, (double) this.doorInfo.posY, (double) this.doorInfo.posZ));

                        if(walkGoal != null) {
                            living.getNavigator().setBreakDoors(false);
                            pathNavigate = living.getNavigator().getPathToXYZ(walkGoal.xCoord, walkGoal.yCoord, walkGoal.zCoord);
                            living.getNavigator().setBreakDoors(breakDoors);
                            return pathNavigate != null;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting() {
        if(this.living.getNavigator().noPath()) {
            return false;
        } else {
            float f = this.living.width + 4.0F;
            return this.living.getDistanceSq((double) this.doorInfo.posX, (double) this.doorInfo.posY, (double) this.doorInfo.posZ) > (double) (f * f);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.living.getNavigator().setPath(this.pathNavigate, this.moveSpeed);
    }

    /**
     * Resets the task
     */
    public void resetTask() {
        if(this.living.getNavigator().noPath() || this.living.getDistanceSq((double) this.doorInfo.posX, (double) this.doorInfo.posY, (double) this.doorInfo.posZ) < 16.0D) {
            this.doorList.add(this.doorInfo);
        }
    }

    private VillageDoorInfo findNearestDoor(Village village) {
        VillageDoorInfo doorInfo = null;
        int nearestDoorDistance = Integer.MAX_VALUE;
        List<VillageDoorInfo> doors = village.getVillageDoorInfoList();

        for(VillageDoorInfo door : doors) {
            int doorDistance = door.getDistanceSquared(MathHelper.floor_double(this.living.posX), MathHelper.floor_double(this.living.posY), MathHelper.floor_double(this.living.posZ));

            if(doorDistance < nearestDoorDistance && !this.doesDoorListContain(door)) {
                doorInfo = door;
                nearestDoorDistance = doorDistance;
            }
        }

        return doorInfo;
    }

    private boolean doesDoorListContain(VillageDoorInfo door) {
        for(VillageDoorInfo doorInfo : doorList) {
            if(door.posX == doorInfo.posX && door.posY == doorInfo.posY && door.posZ == doorInfo.posZ) {
                return true;
            }
        }
        return false;
    }

    private void resizeDoorList() {
        if(this.doorList.size() > 15) {
            this.doorList.remove(0);
        }
    }

}