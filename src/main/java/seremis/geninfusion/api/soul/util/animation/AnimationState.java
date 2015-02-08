package seremis.geninfusion.api.soul.util.animation;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.util.INBTTagable;

import java.util.Random;

public class AnimationState implements INBTTagable {

    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;

    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;

    public AnimationState() {
        this(0, 0, 0, 0, 0, 0);
    }

    public AnimationState(ModelPart part) {
        this(part.rotationPointX, part.rotationPointY, part.rotationPointZ, part.rotateAngleX, part.rotateAngleY, part.rotateAngleZ);
    }

    public AnimationState(NBTTagCompound compound) {
        readFromNBT(compound);
    }

    public AnimationState(float rotationPointX, float rotationPointY, float rotationPointZ, float rotateAngleX, float rotateAngleY, float rotateAngleZ) {
        this.rotationPointX = rotationPointX;
        this.rotationPointY = rotationPointY;
        this.rotationPointZ = rotationPointZ;
        this.rotateAngleX = rotateAngleX;
        this.rotateAngleY = rotateAngleY;
        this.rotateAngleZ = rotateAngleZ;
    }

    public void setToModelPart(ModelPart part) {
        part.rotationPointX = rotationPointX;
        part.rotationPointY = rotationPointY;
        part.rotationPointZ = rotationPointZ;

        part.rotateAngleX = rotateAngleX;
        part.rotateAngleY = rotateAngleY;
        part.rotateAngleZ = rotateAngleZ;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setFloat("rotationPointX", rotationPointX);
        compound.setFloat("rotationPointY", rotationPointY);
        compound.setFloat("rotationPointZ", rotationPointZ);
        compound.setFloat("rotateAngleX", rotateAngleX);
        compound.setFloat("rotateAngleY", rotateAngleY);
        compound.setFloat("rotateAngleZ", rotateAngleZ);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        rotationPointX = compound.getFloat("rotationPointX");
        rotationPointY = compound.getFloat("rotationPointY");
        rotationPointZ = compound.getFloat("rotationPointZ");
        rotateAngleX = compound.getFloat("rotateAngleX");
        rotateAngleY = compound.getFloat("rotateAngleY");
        rotateAngleZ = compound.getFloat("rotateAngleZ");
    }

    private Random rand = new Random();

    public AnimationState mutate() {
        if(rand.nextBoolean()) {
            rotationPointX = (float) (rotationPointX * ((rand.nextFloat() * 2 * 0.1) + 0.9));
            rotationPointY = (float) (rotationPointY * ((rand.nextFloat() * 2 * 0.1) + 0.9));
            rotationPointZ = (float) (rotationPointZ * ((rand.nextFloat() * 2 * 0.1) + 0.9));
        } else {
            rotateAngleX = (float) (rotateAngleX * ((rand.nextFloat() * 2 * 0.1) + 0.9));
            rotateAngleY = (float) (rotateAngleY * ((rand.nextFloat() * 2 * 0.1) + 0.9));
            rotateAngleZ = (float) (rotateAngleZ * ((rand.nextFloat() * 2 * 0.1) + 0.9));
        }

        return this;
    }
}
