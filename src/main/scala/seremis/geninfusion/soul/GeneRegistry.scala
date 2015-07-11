package seremis.geninfusion.soul

import net.minecraft.entity.EntityLiving
import seremis.geninfusion.api.soul._

import scala.collection.immutable.HashMap
import scala.collection.mutable.ListBuffer

class GeneRegistry extends IGeneRegistry {

    var genes: HashMap[String, IGene] = HashMap()
    var genesInv: HashMap[IGene, String] = HashMap()
    var ids: HashMap[IGene, Int] = HashMap()
    var idsInv: HashMap[Int, IGene] = HashMap()

    var masterGenes: HashMap[String, IMasterGene] = HashMap()

    var customInheritance: ListBuffer[IGene] = ListBuffer()

    override def registerGene(name: String, gene: IGene): IGene = {
        if (!genes.contains(name)) {
            genes += (name -> gene)
            genesInv += (gene -> name)
            ids += (gene -> ids.size)
            idsInv += (idsInv.size -> gene)
        }
        gene
    }

    override def registerGene(name: String, clzz: Class[_]): IGene = registerGene(name, new Gene(clzz))

    override def registerMasterGene(name: String, gene: IMasterGene): IMasterGene = {
        registerGene(name, gene.asInstanceOf[IGene])
        masterGenes += (name -> gene)
        gene
    }

    override def registerCustomInheritance(name: String) {
        customInheritance += getGene(name)
    }

    override def registerCustomInheritance(gene: IGene) {
        customInheritance += gene
    }

    override def useNormalInheritance(gene: IGene): Boolean = !customInheritance.contains(gene)

    override def useNormalInheritance(name: String): Boolean = useNormalInheritance(getGene(name))

    override def getCustomInheritanceGenes: List[IGene] = customInheritance.toList

    override def getGene(name: String): IGene = genes.get(name).get

    override def getGeneName(gene: IGene): String = genesInv.get(gene).get

    override def getGeneId(name: String): Int = getGeneId(getGene(name))

    override def getGeneId(gene: IGene): Int = ids.get(gene).get

    override def getGenes: List[IGene] = genes.values.toList

    override def getGene(id: Int): IGene = idsInv.get(id).get

    override def getSoulFor(entity: EntityLiving): Option[ISoul] = {
        if (entity.isInstanceOf[IEntitySoulCustom]) {
            return Some(entity.asInstanceOf[IEntitySoulCustom].getSoul)
        } else if (entity != null) {
            return SoulHelper.standardSoulRegistry.getSoulForEntity(entity)
        }
        None
    }

    override def getChromosomeFor(entity: EntityLiving, name: String): Option[IChromosome] = {
        getSoulFor(entity).foreach(s => return Some(s.getChromosomes(getGeneId(name))))
        None
    }

    override def getChromosomeFor(entity: IEntitySoulCustom, name: String): Option[IChromosome] = {
        getChromosomeFor(entity.asInstanceOf[EntityLiving], name)
    }

    override def getActiveFor(entity: EntityLiving, name: String): Option[IAllele] = {
        getChromosomeFor(entity, name).foreach(c => return Some(c.getActive))
        None
    }

    override def getActiveFor(entity: IEntitySoulCustom, name: String): Option[IAllele] = {
        getChromosomeFor(entity, name).foreach(c => return Some(c.getActive))
        None
    }

    override def getValueFromAllele[T](entity: IEntitySoulCustom, name: String): T = {
        getActiveFor(entity, name).foreach(a => return a.getAlleleData.asInstanceOf[T])
        null.asInstanceOf[T]
    }

    override def getControlledGenes(masterGeneName: String): List[String] = {
        getGene(masterGeneName).asInstanceOf[IMasterGene].getControlledGenes
    }
}