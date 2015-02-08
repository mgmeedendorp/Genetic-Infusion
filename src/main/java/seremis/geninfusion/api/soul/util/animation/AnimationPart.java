package seremis.geninfusion.api.soul.util.animation;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.util.INBTTagable;

import java.util.Random;

public class AnimationPart implements INBTTagable {

    public AnimationState start;
    public AnimationState end;

    public AnimationState[] animationStates;

    public boolean reverseAnimation;
    public int timeFrame;

    public boolean loop;

    public int animTime;

    public ModelPart modelPart;

    public AnimationPart(AnimationState start, AnimationState end, int timeFrame, boolean reverseAnimation) {
        this(start, end, timeFrame, reverseAnimation, false);
    }

    public AnimationPart(AnimationState start, AnimationState end, int timeFrame, boolean reverseAnimation, boolean loop) {
        this.start = start;
        this.end = end;
        this.timeFrame = timeFrame;
        this.reverseAnimation = reverseAnimation;
        this.loop = loop;
        animTime = 0;

        populateAnimationStates(start, end);
    }

    public AnimationPart(NBTTagCompound compound) {
        readFromNBT(compound);
        populateAnimationStates(start, end);
    }

    public void populateAnimationStates(AnimationState start, AnimationState end) {
        animationStates = new AnimationState[timeFrame];

        if(loop) {
            for(int i = 0; i < timeFrame/2; i++) {
                if(reverseAnimation) {
                    animationStates[i] = interpolate(start, end, i, timeFrame / 4);
                } else {
                    animationStates[i] = interpolate(start, end, i, timeFrame/2);
                }
            }
            for(int i = timeFrame/2; i < timeFrame; i++) {
                if(reverseAnimation) {
                    animationStates[i] = interpolate(start, end, i, timeFrame / 4);
                } else {
                    animationStates[i] = interpolate(start, end, i, timeFrame/2);
                }
            }
        } else {
            for(int i = 0; i < timeFrame; i++) {
                if(reverseAnimation) {
                    animationStates[i] = interpolate(start, end, i, timeFrame / 2);
                } else {
                    animationStates[i] = interpolate(start, end, i, timeFrame);
                }
            }
        }
    }

    public AnimationState interpolate(AnimationState start, AnimationState end, float position, int maxPosition) {
        AnimationState state = new AnimationState();

        state.rotateAngleX += (Math.abs(start.rotateAngleX - end.rotateAngleX)/maxPosition)*position;
        state.rotateAngleY += (Math.abs(start.rotateAngleY - end.rotateAngleY)/maxPosition)*position;
        state.rotateAngleZ += (Math.abs(start.rotateAngleZ - end.rotateAngleZ)/maxPosition)*position;

        state.rotationPointX += (Math.abs(start.rotationPointX - end.rotationPointX)/maxPosition)*position;
        state.rotationPointY += (Math.abs(start.rotationPointY - end.rotationPointY)/maxPosition)*position;
        state.rotationPointZ += (Math.abs(start.rotationPointZ - end.rotationPointZ)/maxPosition)*position;

        return state;
    }

    public AnimationPart setModelPartToAnimate(ModelPart part) {
        modelPart = part;
        return this;
    }

    public void animate(IEntitySoulCustom entity) {
        if(animTime < animationStates.length) {
            if(modelPart == null) {
                throw new NullPointerException("modelPart should not be null at the start of animation! Animation: " + this + ", Entity: " + entity);
            }

            animationStates[animTime++].setToModelPart(modelPart);
        } else if(loop) animTime = 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        NBTTagCompound startCompound = new NBTTagCompound();
        start.writeToNBT(startCompound);
        compound.setTag("start", startCompound);

        NBTTagCompound endCompound = new NBTTagCompound();
        end.writeToNBT(endCompound);
        compound.setTag("end", endCompound);

        compound.setBoolean("reverseAnimation", reverseAnimation);
        compound.setInteger("timeFrame", timeFrame);
        compound.setBoolean("loop", loop);
        compound.setInteger("animTime", animTime);

        if(modelPart != null) {
            NBTTagCompound partCompound = new NBTTagCompound();
            modelPart.writeToNBT(partCompound);
            compound.setTag("modelPart", partCompound);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        start = new AnimationState(compound.getCompoundTag("start"));
        end = new AnimationState(compound.getCompoundTag("end"));

        reverseAnimation = compound.getBoolean("reverseAnimation");
        timeFrame = compound.getInteger("timeFrame");
        loop = compound.getBoolean("loop");
        animTime = compound.getInteger("animTime");

        if(compound.hasKey("modelPart")) {
            modelPart = new ModelPart(compound.getCompoundTag("modelPart"));
        }
    }

    private Random rand = new Random();

    public AnimationPart mutate() {
        if(rand.nextBoolean()) {
            start.mutate();
        } else {
            end.mutate();
        }

        return this;
    }

    public static AnimationPart[] bipedHeadWalkAnimationParts() {
        AnimationPart[] parts = new AnimationPart[1];

        AnimationState start = new AnimationState(0.0F, 0.0F, 0.0F, 0F, 0F, 0F);
        AnimationState end = new AnimationState(0.0F, 0.0F, 0.0F, 1.0F, 360F, 0.0F);

        AnimationPart head = new AnimationPart(start, end, 600, true, true);

        parts[0] = head;

        return parts;
    }
}
