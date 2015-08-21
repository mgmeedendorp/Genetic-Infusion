package seremis.geninfusion.soul

import net.minecraft.entity.{EntityLiving, EntityList}
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import seremis.geninfusion.api.soul.{IGene, SoulHelper, IChromosome, ISoul}
import seremis.geninfusion.api.util.{AncestryNodeBranch, AncestryNodeRoot, AncestryNode}
import seremis.geninfusion.util.GenomeHelper

import scala.collection.immutable.HashMap
import scala.collection.mutable.ListBuffer
import scala.util.Random

class Soul(var chromosomes: Array[IChromosome], var name: Option[String] = None, var ancestry: AncestryNode) extends ISoul {

    if(ancestry != null)
        ancestry.chromosomes = chromosomes

    def this(chromosomes: Array[IChromosome], ancestry: AncestryNode) {
        this(chromosomes, None, ancestry)
    }
    
    def this(compound: NBTTagCompound) {
        this(null.asInstanceOf[Array[IChromosome]], None, null.asInstanceOf[AncestryNode])
        readFromNBT(compound)
        ancestry.chromosomes = chromosomes
    }

    override def getChromosomes: Array[IChromosome] = chromosomes
    override def getName: Option[String] = name
    override def getAncestryNode: AncestryNode = ancestry

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setInteger("genomeLength", chromosomes.length)

        val tagList = new NBTTagList()

        for (chromosome <- chromosomes) {
            tagList.appendTag(chromosome.writeToNBT(new NBTTagCompound))
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
            chromosomes(i) = new Chromosome(tagList.getCompoundTagAt(i))
        }

        if(compound.hasKey("soulName")) {
            name = Some(compound.getString("soulName"))
        }

        ancestry = AncestryNode.fromNBT(compound.getCompoundTag("soulAncestry"))

        chromosomes = GenomeHelper.fixGenomeErrors(this)

        //Write this to nbt to prevent infinitely fixing genome errors.
        writeToNBT(compound)
    }

    override def toString: String = {
        "Soul:[name: " + name + ", " + ancestry.toString + ", chromosomes:" + chromosomes.mkString(", ") + "]"
    }
}