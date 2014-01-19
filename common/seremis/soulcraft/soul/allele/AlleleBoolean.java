package seremis.soulcraft.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.soulcraft.soul.Allele;

public class AlleleBoolean extends Allele {

    public boolean value;
    
    public AlleleBoolean(boolean isDominant, boolean value) {
        super(isDominant);
        this.value = value;
    }
    
    public AlleleBoolean(NBTTagCompound compound) {
        super(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("value", value);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        value = compound.getBoolean("value");
    }
}
