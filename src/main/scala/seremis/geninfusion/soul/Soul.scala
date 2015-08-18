package seremis.geninfusion.soul

import net.minecraft.entity.{EntityLiving, EntityList}
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import seremis.geninfusion.api.soul.{IGene, SoulHelper, IChromosome, ISoul}
import seremis.geninfusion.api.util.{AncestryNodeBranch, AncestryNodeRoot, AncestryNode}

import scala.collection.immutable.HashMap
import scala.collection.mutable.ListBuffer
import scala.util.Random

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

        fixGenomeErrors()

        compound
    }

    override def toString: String = {
        "Soul:[name: " + name + ", " + ancestry.toString + ", chromosomes:" + chromosomes.mkString(", ") + "]"
    }

    def fixGenomeErrors() {
        if(!isGenomeFixed(chromosomes)) {
            val genes = SoulHelper.geneRegistry.getGenes

            var index = 0

            val fixedChromosomes = new Array[IChromosome](genes.length)

            for((current, loaded) <- genes.map(g => SoulHelper.geneRegistry.getGeneName(g).get) zip chromosomes.map(c => c.getGeneName)) {
                if(current != loaded) {
                    var foundIndex: Option[Int] = None

                    for(i <- index until chromosomes.length if current == chromosomes(i).getGeneName) {
                        foundIndex = Some(i)
                    }

                    if(foundIndex.nonEmpty) {
                        fixedChromosomes(index) = chromosomes(foundIndex.get)
                    } else {
                        fixedChromosomes(index) = getNewInheritedChromosome(genes(index))
                    }
                } else {
                    fixedChromosomes(index) = chromosomes(index)
                }
                index += 1
            }

            if(fixedChromosomes.length > chromosomes.length) {
                for(i <- chromosomes.length - 1 until fixedChromosomes.length) {
                    fixedChromosomes(chromosomes.length - 1 + i) = getNewInheritedChromosome(genes(chromosomes.length - 1 + i))
                }
            }

            chromosomes = fixedChromosomes
        }
    }

    def isGenomeFixed(genome: Array[IChromosome]): Boolean = {
        val genes = SoulHelper.geneRegistry.getGenes

        if(genes.length != genome.length) {
            return false
        } else if(genes.length == genome.length) {
            for((current, loaded) <- genes.map(g => SoulHelper.geneRegistry.getGeneName(g).get) zip chromosomes.map(c => c.getGeneName)) {
                if(current != loaded) {
                    return false
                }
            }
        }

        true
    }

    def getNewInheritedChromosome(gene: IGene): IChromosome = ancestry.getIChromosomeFromGene(gene)
}