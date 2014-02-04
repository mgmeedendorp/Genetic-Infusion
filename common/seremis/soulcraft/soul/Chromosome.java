package seremis.soulcraft.soul;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

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
        NBTTagList list = new NBTTagList();
        
        NBTTagCompound compound1 = new NBTTagCompound();
        allele1.writeToNBT(compound1);
        NBTTagCompound compound2 = new NBTTagCompound();
        allele2.writeToNBT(compound2);
        
        list.appendTag(compound1);
        list.appendTag(compound2);
        
        compound.setTag("alleles", list);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList list = (NBTTagList) compound.getTag("alleles");
        
        allele1 = Allele.readAlleleFromNBT((NBTTagCompound) list.tagAt(0));
        allele2 = Allele.readAlleleFromNBT((NBTTagCompound) list.tagAt(0));
    }
}
