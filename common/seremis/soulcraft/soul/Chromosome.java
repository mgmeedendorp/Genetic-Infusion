package seremis.soulcraft.soul;

import net.minecraft.nbt.NBTTagCompound;

public class Chromosome implements IChromosome {

    public IAllele allele1;
    public IAllele allele2;
    
    public Chromosome(IAllele allele1, IAllele allele2) {
        this.allele1 = allele1;
        this.allele2 = allele2;
    }
    
    public Chromosome(NBTTagCompound compound) {
        readFromNBT(compound);
    }
    
    @Override
    public IAllele getActive() {
        if(allele1.isDominant()) {
            return allele1;
        }
        if(allele2.isDominant()) {
            return allele2;
        }
        return allele1;
    }
    @Override
    public IAllele getRecessive() {
        if(!allele1.isDominant()) {
            return allele1;
        }
        if(!allele2.isDominant()) {
            return allele2;
        }
        return allele2;
    }
    
    @Override
    public IAllele getPrimary() {
        return allele1;
    }
    @Override
    public IAllele getSecondary() {
        return allele2;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        allele1.writeToNBT(compound);
        allele2.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        allele1 = new Allele(compound);
        allele2 = new Allele(compound);
    }
}
