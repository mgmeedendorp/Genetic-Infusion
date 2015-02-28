package seremis.geninfusion.api.soul.util.animation;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.util.INBTTagable;

import java.lang.reflect.Constructor;
import java.util.Random;

public abstract class AnimationPart implements INBTTagable {

    public EnumAnimationPartType type;
    public ModelPart modelPart;

    public boolean immutable;

    protected Random rand = new Random();

    public AnimationPart(EnumAnimationPartType type) {
        this.type = type;
    }

    public AnimationPart(NBTTagCompound compound) {
        readFromNBT(compound);
    }

    /**
     * Set the ModelPart this IAnimationPart animates.
     *
     * @return this IAnimationPart
     */
    public AnimationPart setAnimationModel(ModelPart part) {
        modelPart = part;
        return this;
    }

    /**
     * Execute the animation for the passed entity.
     */
    public void animate(IEntitySoulCustom entity) {
        if(modelPart == null) {
            throw new NullPointerException("modelPart should not be null at the start of animation! Animation: " + this + ", Entity: " + entity);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("type", type.ordinal());

        if(modelPart != null) {
            NBTTagCompound partCompound = new NBTTagCompound();
            modelPart.writeToNBT(partCompound);
            compound.setTag("modelPart", partCompound);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        type = EnumAnimationPartType.values()[compound.getInteger("type")];

        if(compound.hasKey("modelPart")) {
            modelPart = new ModelPart(compound.getCompoundTag("modelPart"));
        }
    }

    /**
     * Mutates this AnimationPart. This should take immutability into account.
     */
    public abstract AnimationPart mutate();

    /**
     * Returns the model this IAnimationPart animates.
     */
    public ModelPart getAnimationModel() {
        return modelPart;
    }

    /**
     * Make this model unaffected by mutations.
     */
    public AnimationPart setImmutable() {
        immutable = true;
        return this;
    }


    public static AnimationPart createFromNBT(NBTTagCompound compound) {
        EnumAnimationPartType type = EnumAnimationPartType.values()[compound.getInteger("type")];

        try {
            Constructor<?> ctor = type.clazz.getConstructor(NBTTagCompound.class);
            Object object = ctor.newInstance(compound);

            return (AnimationPart) object;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static AnimationPart[] bipedLegLeftWaveAnimationParts() {
        return new AnimationPart[]{new AnimationPartWave(0, "legLeftWavePeriod", "legLeftWaveAmplitude", "legLeftWaveOffsetVert", "legLeftWaveOffsetHor")};
    }

    public static AnimationPart[] bipedLegRightWaveAnimationParts() {
        return new AnimationPart[]{new AnimationPartWave(0, "legRightWavePeriod", "legRightWaveAmplitude", "legRightWaveOffsetVert", "legRightWaveOffsetHor")};
    }

    public static AnimationPart[] bipedLegLeftLinearAnimationParts() {
        return new AnimationPart[]{new AnimationPartLinear("legLeftLinearRotateAngleX", "legLeftLinearRotateAngleY", "legLeftLinearRotateAngleZ", "legLeftLinearRotationPointX", "legLeftLinearRotationPointY", "legLeftLinearRotationPointZ")};
    }

    public static AnimationPart[] bipedLegRightLinearAnimationParts() {
        return new AnimationPart[]{new AnimationPartLinear("legRightLinearRotateAngleX", "legRightLinearRotateAngleY", "legRightLinearRotateAngleZ", "legRightLinearRotationPointX", "legRightLinearRotationPointY", "legRightLinearRotationPointZ")};
    }

    public static AnimationPart[] bipedArmLeftWaveAnimationParts() {
        return new AnimationPart[]{new AnimationPartWave(0, "armLeftWalkPeriod", "armLeftWalkAmplitude", null, "armLeftWalkOffsetHor")};
    }

    public static AnimationPart[] bipedArmRightWaveAnimationParts() {
        return new AnimationPart[]{new AnimationPartWave(0, "armRightWalkPeriod", "armRightWalkAmplitude", null, "armRightWalkOffsetHor")};
    }

    public static AnimationPart[] bipedArmLeftLinearAnimationParts() {
        return new AnimationPart[]{new AnimationPartLinear("armLeftLinearRotateAngleX", "armLeftLinearRotateAngleY", "armLeftLinearRotateAngleZ", "armLeftLinearRotationPointX", "armLeftLinearRotationPointY", "armLeftLinearRotationPointZ")};
    }

    public static AnimationPart[] bipedArmRightLinearAnimationParts() {
        return new AnimationPart[]{new AnimationPartLinear("armRightLinearRotateAngleX", "armRightLinearRotateAngleY", "armRightLinearRotateAngleZ", "armRightLinearRotationPointX", "armRightLinearRotationPointY", "armRightLinearRotationPointZ")};
    }

    public static AnimationPart[] bipedHeadLinearAnimationParts() {
        return new AnimationPart[]{new AnimationPartLinear("headLinearRotateAngleX", "headLinearRotateAngleY", "headLinearRotateAngleZ", "headLinearRotationPointX", "headLinearRotationPointY", "headLinearRotationPointZ").setImmutable(), new AnimationPartLinear("headLinearRotateAngleX", "headLinearRotateAngleY", "headLinearRotateAngleZ", "headLinearRotationPointX", "headLinearRotationPointY", "headLinearRotationPointZ").setImmutable()};
    }

    public static AnimationPart[] bipedBodyLinearAnimationParts() {
        return new AnimationPart[]{new AnimationPartLinear("bodyLinearRotateAngleX", "bodyLinearRotateAngleY", "bodyLinearRotateAngleZ", "bodyLinearRotationPointX", "bodyLinearRotationPointY", "bodyLinearRotationPointZ").setImmutable()};
    }
}
