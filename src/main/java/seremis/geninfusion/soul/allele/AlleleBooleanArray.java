package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.soul.Allele;

public class AlleleBooleanArray extends Allele {

    public boolean[] value;

    public AlleleBooleanArray(boolean isDominant, boolean[] value) {
        super(isDominant, EnumAlleleType.BOOLEAN_ARRAY);
        this.value = value;
    }

    public AlleleBooleanArray(Object... args) {
        super(args);
        value = new boolean[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            value[i - 1] = (Boolean) args[i];
        }
        type = EnumAlleleType.BOOLEAN_ARRAY;
    }

    public AlleleBooleanArray(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("length", value.length);
        for(int i = 0; i < value.length; i++) {
            compound.setBoolean("value" + i, value[i]);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = new boolean[compound.getInteger("length")];
        for(int i = 0; i < value.length; i++) {
            value[i] = compound.getBoolean("value" + i);
        }
    }
}