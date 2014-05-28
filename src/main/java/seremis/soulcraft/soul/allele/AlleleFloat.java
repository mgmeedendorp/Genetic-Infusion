package seremis.soulcraft.soul.allele;

import seremis.soulcraft.soul.Allele;
import net.minecraft.nbt.NBTTagCompound;

public class AlleleFloat extends Allele {

    public float value;
    
    public AlleleFloat(boolean isDominant, float value) {
        super(isDominant, EnumAlleleType.FLOAT);
        this.value = value;
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
