package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.soul.Allele;

public class AlleleFloatArray extends Allele {

    public float[] value;

    public AlleleFloatArray(boolean isDominant, float[] value) {
        super(isDominant, EnumAlleleType.FLOAT_ARRAY);
        this.value = value;
    }

    public AlleleFloatArray(Object... args) {
        super(args);
        value = new float[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            value[i - 1] = (Float) args[i];
        }
        type = EnumAlleleType.FLOAT_ARRAY;
    }

    public AlleleFloatArray(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("length", value.length);
        for(int i = 0; i < value.length; i++) {
            compound.setFloat("value" + i, value[i]);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = new float[compound.getInteger("length")];
        for(int i = 0; i < value.length; i++) {
            value[i] = compound.getFloat("value" + i);
        }
    }

}
