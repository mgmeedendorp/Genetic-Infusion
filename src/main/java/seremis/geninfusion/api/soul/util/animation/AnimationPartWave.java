package seremis.geninfusion.api.soul.util.animation;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import seremis.geninfusion.api.soul.IEntitySoulCustom;

/**
 * Animation with a sine or cosine curve for rotation. Mostly used for arm and leg animation.
 */
public class AnimationPartWave extends AnimationPart {

    public float amplitude;
    public float offsetVert;
    public float offsetHor;

    public String periodFactor;
    public String amplitudeFactor;
    public String offsetVertFactor;
    public String offsetHorFactor;

    private float lastPeriodFactor = 1.0f;

    /**
     * The rotation axis this AnimationPart animates, 0 for x, 1 for y, 2 for z, 3 for x & y, 4 for x & z, 5 for y & z, 6 for every axis.
     */
    public short axis;

    public AnimationPartWave(boolean loop, float amplitude, int period, float offsetVert, float offsetHor, int axis) {
        this.loop = loop;
        this.amplitude = amplitude;
        this.offsetVert = offsetVert;
        this.offsetHor = (float) ((offsetHor/(2*Math.PI))*period);
        this.axis = (short) axis;
        this.maxTime = period;
    }

    /**
     * A helper class for animations.
     * @param loop True if this animation loops.
     * @param amplitude The amplitude (rotation angle in radians) of the wave function.
     * @param period The period of the wave function in ticks.
     * @param offsetVert The vertical offset of the wave function.
     * @param offsetHor the horizontal offset of the wave function.
     * @param axis The rotation axis this AnimationPart animates, 0 for x, 1 for y, 2 for z, 3 for x & y, 4 for x & z, 5 for y & z, 6 for every axis
     * @param periodFactor The period will get multiplied by the value of the variable with this String as it's name. If null it defaults to 1.0F.
     * @param amplitudeFactor The amplitude will get multiplied by the value of the variable with this String as it's name. If null it defaults to 1.0F.
     * @param offsetVertFactor The offsetVert will get multiplied by the value of the variable with this String as it's name. If null it defaults to 1.0F.
     * @param offsetHorFactor The offsetHor will get multiplied by the value of the variable with this String as it's name. If null it defaults to 1.0F.
     */
    public AnimationPartWave(boolean loop, float amplitude, int period, float offsetVert, float offsetHor, int axis, String periodFactor, String amplitudeFactor, String offsetVertFactor, String offsetHorFactor) {
        this(loop, amplitude, period, offsetVert, offsetHor, axis);
        this.periodFactor = periodFactor;
        this.amplitudeFactor = amplitudeFactor;
        this.offsetVertFactor = offsetVertFactor;
        this.offsetHorFactor = offsetHorFactor;
    }

    public AnimationPartWave(NBTTagCompound compound) {
        readFromNBT(compound);
    }

    @Override
    public void animate(IEntitySoulCustom entity) {
        if(animTick < maxTime*(periodFactor != null ? entity.getFloat(periodFactor) : 1.0F)) {
            if(modelPart == null) {
                throw new NullPointerException("modelPart should not be null at the start of animation! Animation: " + this + ", Entity: " + entity);
            } else {
                getStateAtTime(entity, animTick++).setRotationToModelPart(modelPart);
            }
        } else if(loop) animTick = 0;
    }

    public AnimationState getStateAtTime(IEntitySoulCustom entity, int time) {
        AnimationState state = new AnimationState();

        time = 1;

        float offsetHorFactor = this.offsetHorFactor != null ? entity.getFloat(this.offsetHorFactor) : 1.0F;
        float offsetVertFactor = this.offsetVertFactor != null ? entity.getFloat(this.offsetVertFactor) : 1.0F;
        float amplitudeFactor = this.amplitudeFactor != null ? entity.getFloat(this.amplitudeFactor) : 1.0F;
        float periodFactor = this.periodFactor != null ? entity.getFloat(this.periodFactor) : 1.0F;

        switch(axis) {
            case 0:
                state.rotateAngleX = MathHelper.sin((float) ((time - offsetHor * periodFactor * offsetHorFactor) * (2*Math.PI/(maxTime*periodFactor)))) * (amplitude*amplitudeFactor) + (offsetVert *offsetVertFactor);
                break;
            case 1:
                state.rotateAngleY = MathHelper.sin((float) ((time - offsetHor * periodFactor * offsetHorFactor) * (2*Math.PI/(maxTime*periodFactor)))) * (amplitude*amplitudeFactor) + (offsetVert *offsetVertFactor);
                break;
            case 2:
                state.rotateAngleZ = MathHelper.sin((float) ((time - offsetHor * periodFactor * offsetHorFactor) * (2*Math.PI/(maxTime*periodFactor)))) * (amplitude*amplitudeFactor) + (offsetVert *offsetVertFactor);
                break;
            case 3:
                state.rotateAngleX = MathHelper.sin((float) ((time - offsetHor * periodFactor * offsetHorFactor) * (2*Math.PI/(maxTime*periodFactor)))) * (amplitude*amplitudeFactor) + (offsetVert *offsetVertFactor);
                state.rotateAngleY = MathHelper.sin((float) ((time - offsetHor * periodFactor * offsetHorFactor) * (2*Math.PI/(maxTime*periodFactor)))) * (amplitude*amplitudeFactor) + (offsetVert *offsetVertFactor);
                break;
            case 4:
                state.rotateAngleX = MathHelper.sin((float) ((time - offsetHor * periodFactor * offsetHorFactor) * (2*Math.PI/(maxTime*periodFactor)))) * (amplitude*amplitudeFactor) + (offsetVert *offsetVertFactor);
                state.rotateAngleZ = MathHelper.sin((float) ((time - offsetHor * periodFactor * offsetHorFactor) * (2*Math.PI/(maxTime*periodFactor)))) * (amplitude*amplitudeFactor) + (offsetVert *offsetVertFactor);
                break;
            case 5:
                state.rotateAngleY = MathHelper.sin((float) ((time - offsetHor * periodFactor * offsetHorFactor) * (2*Math.PI/(maxTime*periodFactor)))) * (amplitude*amplitudeFactor) + (offsetVert *offsetVertFactor);
                state.rotateAngleZ = MathHelper.sin((float) ((time - offsetHor * periodFactor * offsetHorFactor) * (2*Math.PI/(maxTime*periodFactor)))) * (amplitude*amplitudeFactor) + (offsetVert *offsetVertFactor);
                break;
            case 6:
                state.rotateAngleX = MathHelper.sin((float) ((time - offsetHor * periodFactor * offsetHorFactor) * (2*Math.PI/(maxTime*periodFactor)))) * (amplitude*amplitudeFactor) + (offsetVert *offsetVertFactor);
                state.rotateAngleY = MathHelper.sin((float) ((time - offsetHor * periodFactor * offsetHorFactor) * (2*Math.PI/(maxTime*periodFactor)))) * (amplitude*amplitudeFactor) + (offsetVert *offsetVertFactor);
                state.rotateAngleZ = MathHelper.sin((float) ((time - offsetHor * periodFactor * offsetHorFactor) * (2*Math.PI/(maxTime*periodFactor)))) * (amplitude*amplitudeFactor) + (offsetVert *offsetVertFactor);
                break;
        }

        lastPeriodFactor = periodFactor;

        return state;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setFloat("amplitude", amplitude);
        compound.setFloat("offsetVert", offsetVert);
        compound.setFloat("offsetHor", offsetHor);
        compound.setShort("axis", axis);

        if(amplitudeFactor != null) compound.setString("amplitudeFactor", amplitudeFactor);
        if(periodFactor != null) compound.setString("periodFactor", periodFactor);
        if(offsetVertFactor != null) compound.setString("offsetVertFactor", offsetVertFactor);
        if(offsetHorFactor != null) compound.setString("offsetHorFactor", offsetHorFactor);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        amplitude = compound.getFloat("amplitude");
        offsetVert = compound.getFloat("offsetVert");
        offsetHor = compound.getFloat("offsetHor");
        axis = compound.getShort("axis");

        if(compound.hasKey("amplitudeFactor")) amplitudeFactor = compound.getString("amplitudeFactor");
        if(compound.hasKey("periodFactor")) periodFactor = compound.getString("periodFactor");
        if(compound.hasKey("offsetVertFactor")) offsetVertFactor = compound.getString("offsetVertFactor");
        if(compound.hasKey("offsetHorFactor")) offsetHorFactor = compound.getString("offsetHorFactor");
    }

    @Override
    public int getDuration() {
        return (int) (maxTime*lastPeriodFactor);
    }

    @Override
    public AnimationPart mutate() {
        return this;
    }
}