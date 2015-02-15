package seremis.geninfusion.api.soul.util.animation;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.util.INBTTagable;

import java.util.Random;

public abstract class AnimationPart implements INBTTagable {
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
    public void animate(IEntitySoulCustom entity) {
        if(modelPart == null) {
            throw new NullPointerException("modelPart should not be null at the start of animation! Animation: " + this + ", Entity: " + entity);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        if(modelPart != null) {
            NBTTagCompound partCompound = new NBTTagCompound();
            modelPart.writeToNBT(partCompound);
            compound.setTag("modelPart", partCompound);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if(compound.hasKey("modelPart")) {
            modelPart = new ModelPart(compound.getCompoundTag("modelPart"));
        }
    }

    protected Random rand = new Random();

    /**
     * Mutates this AnimationPart.
     */
    public abstract AnimationPart mutate();

    /**
     * Returns the model this IAnimationPart animates.
     */
    public ModelPart getAnimationModel() {
        return modelPart;
    }

    public static AnimationPart[] bipedLegLeftWalkAnimationParts() {
        return new AnimationPart[] {new AnimationPartWave(0, "legLeftWalkPeriodFactor", "legLeftWalkAmplitudeFactor", null, "legLeftWalkOffsetHorFactor")};
    }

    public static AnimationPart[] bipedLegRightWalkAnimationParts() {
        return new AnimationPart[] {new AnimationPartWave(0, "legRightWalkPeriodFactor", "legRightWalkAmplitudeFactor", null, "legRightWalkOffsetHorFactor")};
    }
}
