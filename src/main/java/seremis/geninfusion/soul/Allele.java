package seremis.geninfusion.soul;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.EnumAlleleType;

import java.lang.reflect.Constructor;

public class Allele implements IAllele {

    public boolean isDominant;
    public EnumAlleleType type;
    
    public Allele(boolean isDominant, EnumAlleleType type) {
        this.isDominant = isDominant;
        this.type = type;
    }

    public Allele(Object... args) {
        isDominant = (Boolean) args[0];
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
        compound.setInteger("type", type.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        isDominant = compound.getBoolean("isDominant");
        type = EnumAlleleType.values()[compound.getInteger("type")];
    }
    
    public static IAllele readAlleleFromNBT(NBTTagCompound compound) {
        EnumAlleleType type = EnumAlleleType.values()[compound.getInteger("type")];
        
        try {
            Constructor<?> ctor = type.clazz.getConstructor(NBTTagCompound.class);
            Object object = ctor.newInstance(compound);
            
            return (IAllele) object;
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public String toString() {
    	return "Allele[type: " + type + ", isDominant: " + isDominant + "]";
    }
}
