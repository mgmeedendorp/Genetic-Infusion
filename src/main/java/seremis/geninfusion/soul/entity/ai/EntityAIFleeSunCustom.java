package seremis.geninfusion.soul.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import seremis.geninfusion.api.soul.IEntitySoulCustom;

import java.util.Random;

public class EntityAIFleeSunCustom extends EntityAIBase {

    public IEntitySoulCustom entity;
    public EntityLiving living;

    public double shelterX;
    public double shelterY;
    public double shelterZ;
    public double moveSpeed;
    public World world;

    public EntityAIFleeSunCustom(IEntitySoulCustom entity, double moveSpeed) {
        this.entity = entity;
        this.living = (EntityLiving) entity;
        this.moveSpeed = moveSpeed;
        this.world = entity.getWorld();
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        if(!this.world.isDaytime()) {
            return false;
        } else if(!this.living.isBurning()) {
            return false;
        } else if(!this.world.canBlockSeeTheSky(MathHelper.floor_double(living.posX), (int) living.boundingBox.minY, MathHelper.floor_double(living.posZ))) {
            return false;
        } else {
            Vec3 vec3 = this.findPossibleShelter();

            if(vec3 == null) {
                return false;
            } else {
                this.shelterX = vec3.xCoord;
                this.shelterY = vec3.yCoord;
                this.shelterZ = vec3.zCoord;
                return true;
            }
        }
    }

    public boolean continueExecuting() {
        return !this.living.getNavigator().noPath();
    }

    public void startExecuting() {
        this.living.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.moveSpeed);
    }

    private Vec3 findPossibleShelter() {
        Random random = this.entity.getRandom();

        for(int i = 0; i < 10; ++i) {
            int x = MathHelper.floor_double(living.posX + (double) random.nextInt(20) - 10.0D);
            int y = MathHelper.floor_double(living.boundingBox.minY + (double) random.nextInt(6) - 3.0D);
            int z = MathHelper.floor_double(living.posZ + (double) random.nextInt(20) - 10.0D);

            if(!world.canBlockSeeTheSky(x, y, z) && entity.getBlockPathWeight(x, y, z) < 0.0F) {
                return Vec3.createVectorHelper((double) x, (double) y, (double) z);
            }
        }

        return null;
    }

}
