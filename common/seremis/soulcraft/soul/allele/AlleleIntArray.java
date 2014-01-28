package seremis.soulcraft.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.soulcraft.soul.Allele;

public class AlleleIntArray extends Allele {

    public int[] value;
    
    public AlleleIntArray(boolean isDominant, int[] value) {
        super(isDominant);
        this.value = value;
    }
    
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = compound.getIntArray("value");
    }

    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setIntArray("value", value);
    }
}
