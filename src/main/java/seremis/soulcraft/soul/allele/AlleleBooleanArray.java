package seremis.soulcraft.soul.allele;

import seremis.soulcraft.soul.Allele;
import net.minecraft.nbt.NBTTagCompound;

public class AlleleBooleanArray extends Allele {

    public boolean[] value;
    
    public AlleleBooleanArray(boolean isDominant, boolean[] value) {
        super(isDominant, EnumAlleleType.BOOLEAN_ARRAY);
        this.value = value;
    }
    
    public AlleleBooleanArray(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("length", value.length);
        for(int i = 0; i < value.length; i++) {
            compound.setBoolean("value" + i, value[i]);
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = new boolean[compound.getInteger("length")];
        for(int i = 0; i < value.length; i++) {
            value[i] = compound.getBoolean("value" + i);
        }
    }
}