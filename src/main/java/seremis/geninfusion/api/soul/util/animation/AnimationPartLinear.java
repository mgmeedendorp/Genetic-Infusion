package seremis.geninfusion.api.soul.util.animation;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.IEntitySoulCustom;

/**
 * An AnimationPart that doesn't do any calculations with the numbers plugged in, it directly applies them to the ModelPart.
 */
public class AnimationPartLinear extends AnimationPart {

    public String rotateAngleX;
    public String rotateAngleY;
    public String rotateAngleZ;
    public String rotationPointX;
    public String rotationPointY;
    public String rotationPointZ;

    /**
     * These variables should be modified when a method mutates.
     */
    public float rotateAngleXModifier = 1.0F;
    public float rotateAngleYModifier = 1.0F;
    public float rotateAngleZModifier = 1.0F;
    public float rotationPointXModifier = 1.0F;
    public float rotationPointYModifier = 1.0F;
    public float rotationPointZModifier = 1.0F;

    public AnimationPartLinear(String rotateAngleX, String rotateAngleY, String rotateAngleZ, String rotationPointX, String rotationPointY, String rotationPointZ) {
        super(EnumAnimationPartType.ANIMATION_PART_LINEAR);
        this.rotateAngleX = rotateAngleX;
        this.rotateAngleY = rotateAngleY;
        this.rotateAngleZ = rotateAngleZ;
        this.rotationPointX = rotationPointX;
        this.rotationPointY = rotationPointY;
        this.rotationPointZ = rotationPointZ;
    }

    public AnimationPartLinear(NBTTagCompound compound) {
        super(EnumAnimationPartType.ANIMATION_PART_LINEAR);
        readFromNBT(compound);
    }

    @Override
    public void animate(IEntitySoulCustom entity) {
        super.animate(entity);

        modelPart.rotateAngleX = entity.getFloat(rotateAngleX);
        modelPart.rotateAngleY = entity.getFloat(rotateAngleY);
        modelPart.rotateAngleZ = entity.getFloat(rotateAngleZ);
        modelPart.rotationPointX = entity.getFloat(rotationPointX);
        modelPart.rotationPointY = entity.getFloat(rotationPointY);
        modelPart.rotationPointZ = entity.getFloat(rotationPointZ);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        if(rotateAngleX != null) compound.setString("rotateAngleX", rotateAngleX);
        if(rotateAngleY != null) compound.setString("rotateAngleY", rotateAngleY);
        if(rotateAngleZ != null) compound.setString("rotateAngleZ", rotateAngleZ);

        if(rotationPointX != null) compound.setString("rotationPointX", rotationPointX);
        if(rotationPointY != null) compound.setString("rotationPointY", rotationPointY);
        if(rotationPointZ != null) compound.setString("rotationPointZ", rotationPointZ);

        if(rotateAngleXModifier != 1.0F) compound.setFloat("rotateAngleXModifier", rotateAngleXModifier);
        if(rotateAngleYModifier != 1.0F) compound.setFloat("rotateAngleYModifier", rotateAngleYModifier);
        if(rotateAngleZModifier != 1.0F) compound.setFloat("rotateAngleZModifier", rotateAngleZModifier);

        if(rotationPointXModifier != 1.0F) compound.setFloat("rotationPointXModifier", rotationPointXModifier);
        if(rotationPointYModifier != 1.0F) compound.setFloat("rotationPointYModifier", rotationPointYModifier);
        if(rotationPointZModifier != 1.0F) compound.setFloat("rotationPointZModifier", rotationPointZModifier);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if(compound.hasKey("rotateAngleX")) rotateAngleX = compound.getString("rotateAngleX");
        if(compound.hasKey("rotateAngleY")) rotateAngleY = compound.getString("rotateAngleY");
        if(compound.hasKey("rotateAngleZ")) rotateAngleZ = compound.getString("rotateAngleZ");

        if(compound.hasKey("rotationPointX")) rotationPointX = compound.getString("rotationPointX");
        if(compound.hasKey("rotationPointY")) rotationPointY = compound.getString("rotationPointY");
        if(compound.hasKey("rotationPointZ")) rotationPointZ = compound.getString("rotationPointZ");

        if(compound.hasKey("rotateAngleXModifier")) rotateAngleXModifier = compound.getFloat("rotateAngleXModifier");
        if(compound.hasKey("rotateAngleYModifier")) rotateAngleYModifier = compound.getFloat("rotateAngleYModifier");
        if(compound.hasKey("rotateAngleZModifier")) rotateAngleZModifier = compound.getFloat("rotateAngleZModifier");

        if(compound.hasKey("rotationPointXModifier")) rotationPointXModifier = compound.getFloat("rotationPointXModifier");
        if(compound.hasKey("rotationPointYModifier")) rotationPointYModifier = compound.getFloat("rotationPointYModifier");
        if(compound.hasKey("rotationPointZModifier")) rotationPointZModifier = compound.getFloat("rotationPointZModifier");
    }

    @Override
    public AnimationPart mutate() {
        if(!immutable) {
            rotateAngleXModifier = (float) (rotateAngleXModifier * ((rand.nextFloat() * 2 * 0.5) + 0.5));
            rotateAngleYModifier = (float) (rotateAngleYModifier * ((rand.nextFloat() * 2 * 0.5) + 0.5));
            rotateAngleZModifier = (float) (rotateAngleZModifier * ((rand.nextFloat() * 2 * 0.5) + 0.5));
            rotationPointXModifier = (float) (rotationPointXModifier * ((rand.nextFloat() * 2 * 0.5) + 0.5));
            rotationPointYModifier = (float) (rotationPointYModifier * ((rand.nextFloat() * 2 * 0.5) + 0.5));
            rotationPointZModifier = (float) (rotationPointZModifier * ((rand.nextFloat() * 2 * 0.5) + 0.5));
        }

        return this;
    }
}
