package seremis.geninfusion.soul

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.{IChromosome, ISoul}
import seremis.geninfusion.api.util.AncestryNode
import seremis.geninfusion.util.GenomeHelper

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
        name.foreach(n => compound.setString("soulName", n))
        
        compound.setTag("soulAncestry", ancestry.writeToNBT(new NBTTagCompound))

        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        if(compound.hasKey("soulName")) {
            name = Some(compound.getString("soulName"))
        }

        ancestry = AncestryNode.fromNBT(compound.getCompoundTag("soulAncestry"))
        chromosomes = ancestry.chromosomes

        chromosomes = GenomeHelper.fixGenomeErrors(this)

        compound
    }

    override def toString: String = {
        "Soul:[name: " + name + ", " + ancestry.toString + ", chromosomes:" + chromosomes.mkString(", ") + "]"
    }
}