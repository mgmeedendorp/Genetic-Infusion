package seremis.geninfusion.soul.allele;

import seremis.geninfusion.soul.Allele;
import net.minecraft.nbt.NBTTagCompound;

public class AlleleInteger extends Allele {

    public int value;
    
    public AlleleInteger(boolean isDominant, int value) {
        super(isDominant, EnumAlleleType.INTEGER);
        this.value = value;
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
