package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.soul.Allele;

public class AlleleBoolean extends Allele {

    public boolean value;
    
    public AlleleBoolean(boolean isDominant, boolean value) {
        super(isDominant, EnumAlleleType.BOOLEAN);
        this.value = value;
    }
    
    public AlleleBoolean(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("value", value);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = compound.getBoolean("value");
    }
}
