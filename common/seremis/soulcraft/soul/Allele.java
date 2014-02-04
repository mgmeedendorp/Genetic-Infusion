package seremis.soulcraft.soul;

import java.lang.reflect.Constructor;

import net.minecraft.nbt.NBTTagCompound;
import seremis.soulcraft.soul.allele.EnumAlleleType;

public class Allele implements IAllele {

    public boolean isDominant;
    public EnumAlleleType type;
    
    public Allele(boolean isDominant, EnumAlleleType type) {
        this.isDominant = isDominant;
        this.type = type;
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
            Object object = ctor.newInstance(new Object[] { compound });
            
            return (IAllele) object;
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
