package seremis.soulcraft.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.soulcraft.soul.Allele;

public class AlleleInteger extends Allele {

    public int value;
    
    public AlleleInteger(boolean isDominant, int value) {
        super(isDominant);
        this.value = value;
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
