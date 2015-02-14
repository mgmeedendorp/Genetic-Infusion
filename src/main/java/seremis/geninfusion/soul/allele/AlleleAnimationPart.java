package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.api.soul.util.animation.AnimationPart;
import seremis.geninfusion.api.soul.util.animation.AnimationPartWave;
import seremis.geninfusion.soul.Allele;

public class AlleleAnimationPart extends Allele {

    public AnimationPart value;

    public AlleleAnimationPart(boolean isDominant, AnimationPart value) {
        super(isDominant, EnumAlleleType.ANIMATION_PART);
        this.value = value;
    }

    public AlleleAnimationPart(Object... args) {
        super(args);
        value = (AnimationPart) args[1];
    }

    public AlleleAnimationPart(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        value.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        //TODO new animation instance
        value = new AnimationPartWave(compound);
    }

}
