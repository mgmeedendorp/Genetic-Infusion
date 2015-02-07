package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.soul.Allele;

public class AlleleDouble extends Allele {

    public double value;

    public AlleleDouble(boolean isDominant, double value) {
        super(isDominant, EnumAlleleType.DOUBLE);
        this.value = value;
    }

    public AlleleDouble(Object... args) {
        super(args);
        value = (Double) args[1];
    }

    public AlleleDouble(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setDouble("value", value);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = compound.getDouble("value");
    }

}
