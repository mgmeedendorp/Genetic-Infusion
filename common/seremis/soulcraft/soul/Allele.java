package seremis.soulcraft.soul;

import net.minecraft.nbt.NBTTagCompound;

public class Allele implements IAllele {

    public boolean isDominant;
    
    public Allele(boolean isDominant) {
        this.isDominant = isDominant;
    }
    
    public Allele(NBTTagCompound compound) {
        readFromNBT(compound);
    }
    
    @Override
    public boolean isDominant() {
        return isDominant;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("isDominant", isDominant);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        isDominant = compound.getBoolean("isDominant");
    }
}
