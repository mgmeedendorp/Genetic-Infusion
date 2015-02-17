package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.api.soul.util.animation.AnimationPart;
import seremis.geninfusion.api.soul.util.animation.AnimationPartWave;
import seremis.geninfusion.soul.Allele;

public class AlleleAnimationPartArray extends Allele {

    public AnimationPart[] value;

    public AlleleAnimationPartArray(boolean isDominant, AnimationPart[] value) {
        super(isDominant, EnumAlleleType.ANIMATION_PART_ARRAY);
        this.value = value;
    }

    public AlleleAnimationPartArray(Object... args) {
        super(args);
        value = new AnimationPart[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            value[i - 1] = (AnimationPart) args[i];
        }
    }

    public AlleleAnimationPartArray(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("length", value.length);
        for(int i = 0; i < value.length; i++) {
            NBTTagCompound compound1 = new NBTTagCompound();
            value[i].writeToNBT(compound1);
            compound.setTag("value" + i, compound1);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = new AnimationPart[compound.getInteger("length")];
        for(int i = 0; i < value.length; i++) {
            NBTTagCompound compound1 = compound.getCompoundTag("value"+i);
            value[i] = AnimationPart.createFromNBT(compound1);
        }
    }
}
