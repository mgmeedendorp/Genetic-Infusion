package seremis.soulcraft.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.soulcraft.soul.Allele;

public class AlleleFloatArray extends Allele {

    public float[] value;
    
    public AlleleFloatArray(boolean isDominant, float[] value) {
        super(isDominant, EnumAlleleType.FLOAT_ARRAY);
        this.value = value;
    }
    
    public AlleleFloatArray(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("length", value.length);
        for(int i = 0; i < value.length; i++) {
            compound.setFloat("value" + i, value[i]);
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = new float[compound.getInteger("length")];
        for(int i = 0; i < value.length; i++) {
            value[i] = compound.getFloat("value" + i);
        }
    }
}
