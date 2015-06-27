package seremis.geninfusion.soul

import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import seremis.geninfusion.api.soul.{IChromosome, ISoul}

class Soul(var chromosomes: Array[IChromosome]) extends ISoul {

    def this(compound: NBTTagCompound) {
        this(null.asInstanceOf[Array[IChromosome]])
        readFromNBT(compound)
    }

    override def getChromosomes: Array[IChromosome] = chromosomes

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setInteger("genomeLength", chromosomes.length)

        val tagList = new NBTTagList()

        for (chromosome <- chromosomes if chromosome != null) {
            val compound1 = new NBTTagCompound()
            chromosome.writeToNBT(compound1)
            tagList.appendTag(compound1)
        }
        compound.setTag("chromosomes", tagList)
        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        chromosomes = Array.ofDim[IChromosome](compound.getInteger("genomeLength"))

        val tagList = compound.getTag("chromosomes").asInstanceOf[NBTTagList]

        for (i <- 0 until tagList.tagCount()) {
            val compound1 = tagList.getCompoundTagAt(i)
            chromosomes(i) = new Chromosome(compound1)
        }
        compound
    }

    override def toString: String = {
        "Soul:[chromosomes:" + chromosomes.mkString(", ") + "]"
    }
}