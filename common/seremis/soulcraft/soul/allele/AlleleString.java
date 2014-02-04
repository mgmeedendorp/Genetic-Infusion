package seremis.soulcraft.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.soulcraft.soul.Allele;

public class AlleleString extends Allele {

    public String value;
    
    public AlleleString(boolean isDominant, String value) {
        super(isDominant, EnumAlleleType.STRING);
        this.value = value;
    }
    
    public AlleleString(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("value", value);
    }
    
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = compound.getString("value");
    }
}
