package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.soul.Allele;

public class AlleleStringArray extends Allele {

    public String[] value;

    public AlleleStringArray(boolean isDominant, String[] value) {
        super(isDominant, EnumAlleleType.STRING_ARRAY);
        this.value = value;
    }

    public AlleleStringArray(Object... args) {
        super(args);
        value = new String[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            value[i - 1] = (String) args[i];
        }
        type = EnumAlleleType.STRING_ARRAY;
    }

    public AlleleStringArray(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("length", value.length);
        for(int i = 0; i < value.length; i++) {
            compound.setString("value" + i, value[i]);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = new String[compound.getInteger("length")];
        for(int i = 0; i < value.length; i++) {
            value[i] = compound.getString("value" + i);
        }
    }

}