package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.soul.Allele;

public class AlleleIntArray extends Allele {

    public int[] value;

    public AlleleIntArray(boolean isDominant, int[] value) {
        super(isDominant, EnumAlleleType.INT_ARRAY);
        this.value = value;
    }

    public AlleleIntArray(Object... args) {
        super(args);
        value = new int[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            value[i - 1] = (Integer) args[i];
        }
    }

    public AlleleIntArray(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = compound.getIntArray("value");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setIntArray("value", value);
    }

}
