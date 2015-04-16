package seremis.geninfusion.soul;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;

public class Chromosome implements IChromosome {

    public IAllele<?> allele1;
    public IAllele<?> allele2;

    public Chromosome(IAllele<?> allele1, IAllele<?> allele2) {
        this.allele1 = allele1;
        this.allele2 = allele2;
    }

    public Chromosome(IAllele<?> allele) {
        this.allele1 = allele;
        this.allele2 = allele;
    }

    public Chromosome(NBTTagCompound compound) {
        readFromNBT(compound);
    }

    @Override
    public IAllele<?> getActive() {
        if(allele1.isDominant()) {
            return allele1;
        }
        if(allele2.isDominant()) {
            return allele2;
        }
        return allele1;
    }

    @Override
    public IAllele<?> getRecessive() {
        if(!allele1.isDominant()) {
            return allele1;
        }
        if(!allele2.isDominant()) {
            return allele2;
        }
        return allele2;
    }

    @Override
    public IAllele<?> getPrimary() {
        return allele1;
    }

    @Override
    public IAllele<?> getSecondary() {
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
        NBTTagList list = compound.getTagList("alleles", Constants.NBT.TAG_COMPOUND);

        allele1 = Allele.fromNBT(list.getCompoundTagAt(0));
        allele2 = Allele.fromNBT(list.getCompoundTagAt(1));
    }

    @Override
    public String toString() {
        return "Chromosome:[allele1: " + allele1 + ", allele2: " + allele2 + "]";
    }
}
