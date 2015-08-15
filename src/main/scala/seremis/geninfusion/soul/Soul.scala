package seremis.geninfusion.soul

import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import seremis.geninfusion.api.soul.{IChromosome, ISoul}
import seremis.geninfusion.api.util.AncestryNode

class Soul(var chromosomes: Array[IChromosome], var name: Option[String] = None, var ancestry: AncestryNode) extends ISoul {

    def this(chromosomes: Array[IChromosome], ancestry: AncestryNode) {
        this(chromosomes, None, ancestry)
    }
    
    def this(compound: NBTTagCompound) {
        this(null.asInstanceOf[Array[IChromosome]], None, null.asInstanceOf[AncestryNode])
        readFromNBT(compound)
    }

    override def getChromosomes: Array[IChromosome] = chromosomes
    override def getName: Option[String] = name
    override def getAncestryNode: AncestryNode = ancestry

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setInteger("genomeLength", chromosomes.length)

        val tagList = new NBTTagList()

        for (chromosome <- chromosomes if chromosome != null) {
            val compound1 = new NBTTagCompound()
            chromosome.writeToNBT(compound1)
            tagList.appendTag(compound1)
        }
        compound.setTag("chromosomes", tagList)

        name.foreach(n => compound.setString("soulName", n))
        
        compound.setTag("soulAncestry", ancestry.writeToNBT(new NBTTagCompound))

        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        chromosomes = Array.ofDim[IChromosome](compound.getInteger("genomeLength"))

        val tagList = compound.getTag("chromosomes").asInstanceOf[NBTTagList]

        for (i <- 0 until tagList.tagCount()) {
            val compound1 = tagList.getCompoundTagAt(i)
            chromosomes(i) = new Chromosome(compound1)
        }

        if(compound.hasKey("soulName")) {
            name = Some(compound.getString("soulName"))
        }

        ancestry = AncestryNode.fromNBT(compound.getCompoundTag("soulAncestry"))
        
        compound
    }

    override def toString: String = {
        "Soul:[name: " + name + ", " + ancestry.toString + ", chromosomes:" + chromosomes.mkString(", ") + "]"
    }
}