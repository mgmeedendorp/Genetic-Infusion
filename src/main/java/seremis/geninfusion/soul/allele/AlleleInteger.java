package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.soul.Allele;

public class AlleleInteger extends Allele {

    public int value;

    public AlleleInteger(boolean isDominant, int value) {
        super(isDominant, EnumAlleleType.INTEGER);
        this.value = value;
    }

    public AlleleInteger(Object... args) {
        super(args);
        value = (Integer) args[1];
    }

    public AlleleInteger(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("value", value);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = compound.getInteger("value");
    }
}
