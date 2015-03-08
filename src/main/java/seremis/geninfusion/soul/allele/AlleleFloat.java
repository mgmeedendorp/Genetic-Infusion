package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.soul.Allele;

public class AlleleFloat extends Allele {

    public float value;

    public AlleleFloat(boolean isDominant, float value) {
        super(isDominant, EnumAlleleType.FLOAT);
        this.value = value;
    }

    public AlleleFloat(Object... args) {
        super(args);
        value = (Float) args[1];
        type = EnumAlleleType.FLOAT;
    }

    public AlleleFloat(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setFloat("value", value);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = compound.getFloat("value");
    }
}
