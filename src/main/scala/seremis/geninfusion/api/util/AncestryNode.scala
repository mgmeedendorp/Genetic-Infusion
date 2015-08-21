package seremis.geninfusion.api.util

import net.minecraft.entity.{EntityLiving, EntityList}
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}
import seremis.geninfusion.api.soul.{ISoul, SoulHelper, IChromosome, IGene}
import seremis.geninfusion.soul.Chromosome
import seremis.geninfusion.util.{GenomeHelper, INBTTagable}

import scala.collection.immutable.HashMap
import scala.util.Random

abstract class AncestryNode extends INBTTagable {
    var chromosomes: Array[IChromosome] = null

    var child: Option[AncestryNode] = None

    def getIChromosomeFromGene(gene: IGene): IChromosome

    def getUniqueAncestorRoots: Array[AncestryNodeRoot]
    def getAncestorRoots: Array[AncestryNodeRoot]

    def getAncestors: Option[(AncestryNode, AncestryNode)]

    def setChild(ancestryNode: AncestryNode) = child = Some(ancestryNode)
    def getChild: Option[AncestryNode] = child

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setInteger("genomeLength", chromosomes.length)

        val tagList = new NBTTagList()

        for (chromosome <- chromosomes) {
            tagList.appendTag(chromosome.writeToNBT(new NBTTagCompound))
        }
        compound.setTag("chromosomes", tagList)

        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        chromosomes = Array.ofDim[IChromosome](compound.getInteger("genomeLength"))

        val tagList = compound.getTag("chromosomes").asInstanceOf[NBTTagList]

        for (i <- 0 until tagList.tagCount()) {
            chromosomes(i) = new Chromosome(tagList.getCompoundTagAt(i))
        }

        chromosomes = GenomeHelper.fixGenomeErrors(this, chromosomes)

        compound
    }
}

object AncestryNode {
    def fromNBT(compound: NBTTagCompound): AncestryNode = {
        var element: AncestryNode = null

        compound.getByte("elementId") match {
            case 0 => element = AncestryNodeRoot("")
            case 1 => element = AncestryNodeBranch(null, null)
        }
        element.readFromNBT(compound)

        element.getAncestors.foreach(a => {a._1.setChild(element); a._2.setChild(element)})

        element
    }
}

case class AncestryNodeRoot(var name: String) extends AncestryNode {

    var cachedChromosomes: HashMap[IGene, IChromosome] = HashMap()

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setString("name", name)
        compound.setByte("elementId", 0)

        super.writeToNBT(compound)
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        name = compound.getString("name")

        super.readFromNBT(compound)
    }

    override def toString: String = {
        "AncestryNodeRoot[name: " + name + "]"
    }

    override def getAncestorRoots: Array[AncestryNodeRoot] = Array(this)
    override def getUniqueAncestorRoots: Array[AncestryNodeRoot] = Array(this)

    override def getAncestors = None

    override def getIChromosomeFromGene(gene: IGene): IChromosome = {
        if(!cachedChromosomes.contains(gene)) {
            val entity = EntityList.createEntityByName(name, null).asInstanceOf[EntityLiving]

            cachedChromosomes += (gene -> SoulHelper.standardSoulRegistry.getStandardSoulForEntity(entity).get.getChromosomeFromGene(entity, SoulHelper.geneRegistry.getGeneName(gene).get))
        }
        cachedChromosomes.get(gene).get
    }
}

case class AncestryNodeBranch(var ancestor1: AncestryNode, var ancestor2: AncestryNode) extends AncestryNode {

    var cachedChromosomes: HashMap[IGene, IChromosome] = HashMap()

    if(ancestor1 != null && ancestor2 != null) {
        ancestor1.setChild(this)
        ancestor2.setChild(this)
    }

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setTag("ancestor1", ancestor1.writeToNBT(new NBTTagCompound))
        compound.setTag("ancestor2", ancestor2.writeToNBT(new NBTTagCompound))
        compound.setByte("elementId", 1)

        super.writeToNBT(compound)
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        ancestor1 = AncestryNode.fromNBT(compound.getCompoundTag("ancestor1"))
        ancestor2 = AncestryNode.fromNBT(compound.getCompoundTag("ancestor2"))

        super.readFromNBT(compound)
    }

    override def toString: String = {
        "AncestryNodeBranch[ancestor1: " + ancestor1 + ", ancestor2: " + ancestor2 + "]"
    }

    override def getAncestorRoots: Array[AncestryNodeRoot] = ancestor1.getAncestorRoots ++ ancestor2.getAncestorRoots
    override def getUniqueAncestorRoots: Array[AncestryNodeRoot] = getAncestorRoots.distinct

    override def getAncestors = Some(ancestor1 -> ancestor2)

    override def getIChromosomeFromGene(gene: IGene): IChromosome = {
        if(!cachedChromosomes.contains(gene)) {
            if(SoulHelper.geneRegistry.useNormalInheritance(gene)) {
                var chromosome = gene.inherit(ancestor1.getIChromosomeFromGene(gene), ancestor1.getIChromosomeFromGene(gene))

                if(new Random().nextInt(200) == 0) {
                    chromosome = gene.mutate(chromosome)
                }

                cachedChromosomes += (gene -> chromosome)
            } else {
                var chromosome = gene.advancedInherit(ancestor1.chromosomes, ancestor2.chromosomes, chromosomes)

                if(new Random().nextInt(200) == 0) {
                    chromosome = gene.mutate(chromosome)
                }

                cachedChromosomes += (gene -> chromosome)
            }
        }
        cachedChromosomes.get(gene).get
    }
}