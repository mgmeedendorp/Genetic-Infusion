package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.soul.Allele;

public class AlleleDoubleArray extends Allele {

    public double[] value;

    public AlleleDoubleArray(boolean isDominant, double[] value) {
        super(isDominant, EnumAlleleType.DOUBLE_ARRAY);
        this.value = value;
    }

    public AlleleDoubleArray(Object... args) {
        super(args);
        value = new double[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            value[i - 1] = (Float) args[i];
        }
    }

    public AlleleDoubleArray(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("length", value.length);
        for(int i = 0; i < value.length; i++) {
            compound.setDouble("value" + i, value[i]);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = new double[compound.getInteger("length")];
        for(int i = 0; i < value.length; i++) {
            value[i] = compound.getDouble("value" + i);
        }
    }

}
