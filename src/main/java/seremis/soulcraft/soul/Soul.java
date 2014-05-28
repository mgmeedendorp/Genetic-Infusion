package seremis.soulcraft.soul;

import seremis.soulcraft.api.soul.IChromosome;
import seremis.soulcraft.api.soul.ISoul;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class Soul implements ISoul {

    protected IChromosome[] chromosomes;
    
    public Soul(IChromosome[] chromosomes) {
        this.chromosomes = chromosomes;
    }
    
    public Soul(NBTTagCompound compound) {
        readFromNBT(compound);
    }

    @Override
    public IChromosome[] getChromosomes() {
        return chromosomes;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("genomeLength", chromosomes.length);
        
        NBTTagList tagList = new NBTTagList();
        
        for(IChromosome chromosome : chromosomes) {
            if(chromosome != null) {
                NBTTagCompound compound1 = new NBTTagCompound();
                chromosome.writeToNBT(compound1);
                tagList.appendTag(compound1);
            }
        }
        compound.setTag("chromosomes", tagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        chromosomes = new Chromosome[compound.getInteger("genomeLength")];
        
        NBTTagList tagList = compound.getTagList("chromosomes", Constants.NBT.TAG_COMPOUND);
        
        for(int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound compound1 = tagList.getCompoundTagAt(i);
            
            chromosomes[i] = new Chromosome(compound1);
        }
    }
}