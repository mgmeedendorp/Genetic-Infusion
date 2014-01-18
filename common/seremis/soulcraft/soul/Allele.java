package seremis.soulcraft.soul;

import net.minecraft.nbt.NBTTagCompound;

public class Allele implements IAllele {

    public boolean isDominant;
    public String name;
    
    public Allele(boolean isDominant, String name) {
        this.isDominant = isDominant;
        this.name = name;
    }
    
    public Allele(NBTTagCompound compound) {
        readFromNBT(compound);
    }
    
    @Override
    public boolean isDominant() {
        return isDominant;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("isDominant", isDominant);
        compound.setString("name", name);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        isDominant = compound.getBoolean("isDominant");
        name = compound.getString("name");
    }
}
