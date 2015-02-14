package seremis.geninfusion.api.soul.util.animation;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.util.INBTTagable;

import java.util.Random;

public abstract class AnimationPart implements INBTTagable {
    public int maxTime;

    public boolean loop;

    public int animTick;

    public ModelPart modelPart;

    public AnimationPart() {}

    public AnimationPart(NBTTagCompound compound) {
        readFromNBT(compound);
    }

    /**
     * Interpolates animation between the start and end AnimationState, at time position with a max of maxPosition.
     *
     * @return The interpolated AnimationState.
     */
    public AnimationState interpolate(AnimationState start, AnimationState end, float position, int maxPosition) {
        AnimationState state = new AnimationState();

        state.rotateAngleX = ((start.rotateAngleX - end.rotateAngleX)/maxPosition)*position;
        state.rotateAngleY = ((start.rotateAngleY - end.rotateAngleY)/maxPosition)*position;
        state.rotateAngleZ = ((start.rotateAngleZ - end.rotateAngleZ)/maxPosition)*position;

        state.rotationPointX = ((start.rotationPointX - end.rotationPointX)/maxPosition)*position;
        state.rotationPointY = ((start.rotationPointY - end.rotationPointY)/maxPosition)*position;
        state.rotationPointZ = ((start.rotationPointZ - end.rotationPointZ)/maxPosition)*position;

        return state;
    }

    /**
     * Set the ModelPart this IAnimationPart animates.
     * @return this IAnimationPart
     */
    public AnimationPart setAnimationModel(ModelPart part) {
        modelPart = part;
        return this;
    }

    /**
     * Execute the animation for the passed entity.
     */
    public abstract void animate(IEntitySoulCustom entity);

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("maxTime", maxTime);
        compound.setBoolean("loop", loop);
        compound.setInteger("animTick", animTick);

        if(modelPart != null) {
            NBTTagCompound partCompound = new NBTTagCompound();
            modelPart.writeToNBT(partCompound);
            compound.setTag("modelPart", partCompound);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        maxTime = compound.getInteger("maxTime");
        loop = compound.getBoolean("loop");
        animTick = compound.getInteger("animTick");

        if(compound.hasKey("modelPart")) {
            modelPart = new ModelPart(compound.getCompoundTag("modelPart"));
        }
    }

    protected Random rand = new Random();

    public abstract AnimationPart mutate();

    /**
     * Gets if this animation loops continuously or executes once.
     */
    public boolean doesLoop() {
        return loop;
    }

    /**
     * The duration of this animation in ticks.
     */
    public int getDuration() {
        return maxTime;
    }

    /**
     * Returns the model this IAnimationPart animates.
     */
    public ModelPart getAnimationModel() {
        return modelPart;
    }

    public static AnimationPart[] bipedLegLeftWalkAnimationParts() {
        return new AnimationPart[] {new AnimationPartWave(true, 0.9F, 100, 0.0F, 0.0F, 0, "legLeftWalkPeriodFactor", "legLeftWalkAmplitudeFactor", null, null)};
    }

    public static AnimationPart[] bipedLegRightWalkAnimationParts() {
        return new AnimationPart[] {new AnimationPartWave(true, 0.9F, 100, 0.0F, (float) Math.PI, 0, "legRightWalkPeriodFactor", "legRightWalkAmplitudeFactor", null, null)};
    }
}
