package seremis.soulcraft.soul.allele;

import seremis.soulcraft.soul.Allele;
import net.minecraft.nbt.NBTTagCompound;

public class AlleleIntArray extends Allele {

    public int[] value;
    
    public AlleleIntArray(boolean isDominant, int[] value) {
        super(isDominant, EnumAlleleType.INT_ARRAY);
        this.value = value;
    }
    
    public AlleleIntArray(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }
    
    @Override
	public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = compound.getIntArray("value");
    }

    @Override
	public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setIntArray("value", value);
    }
}
