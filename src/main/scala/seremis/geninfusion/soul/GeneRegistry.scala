package seremis.geninfusion.soul

import net.minecraft.entity.EntityLiving
import seremis.geninfusion.api.soul._

import scala.collection.immutable.HashMap
import scala.collection.mutable.LinkedHashMap
import scala.collection.mutable.ListBuffer

class GeneRegistry extends IGeneRegistry {

    var genes: LinkedHashMap[String, IGene] = LinkedHashMap()
    var genesInv: LinkedHashMap[IGene, String] = LinkedHashMap()
    var ids: LinkedHashMap[IGene, Int] = LinkedHashMap()
    var idsInv: LinkedHashMap[Int, IGene] = LinkedHashMap()

    var masterGenes: LinkedHashMap[String, IMasterGene] = LinkedHashMap()

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
        getGene(name).foreach(g => customInheritance += g)
    }

    override def registerCustomInheritance(gene: IGene) {
        customInheritance += gene
    }

    override def useNormalInheritance(gene: IGene): Boolean = !customInheritance.contains(gene)

    override def useNormalInheritance(name: String): Boolean = getGene(name).exists(g => useNormalInheritance(g))

    override def getCustomInheritanceGenes: List[IGene] = customInheritance.toList

    override def getGene(name: String): Option[IGene] = genes.get(name)

    override def getGeneName(gene: IGene): Option[String] = genesInv.get(gene)

    override def getGeneId(name: String): Option[Int] = if(getGene(name).nonEmpty) getGeneId(getGene(name).get) else None

    override def getGeneId(gene: IGene): Option[Int] = ids.get(gene)

    override def getGenes: List[IGene] = genes.values.toList

    override def getGene(id: Int): Option[IGene] = idsInv.get(id)

    override def getSoulFor(entity: EntityLiving): Option[ISoul] = {
        if (entity.isInstanceOf[IEntitySoulCustom]) {
            return Some(entity.asInstanceOf[IEntitySoulCustom].getSoul_I)
        } else if (entity != null) {
            return SoulHelper.standardSoulRegistry.getSoulForEntity(entity)
        }
        None
    }

    override def getChromosomeFor(entity: EntityLiving, name: String): Option[IChromosome] = {
        getSoulFor(entity).foreach(s => getGeneId(name).foreach(g => return Some(s.getChromosomes(g))))
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

    override def changeAlleleValue[T](entity: IEntitySoulCustom, name: String, value: T, changeActiveGene: Boolean) {
        if(getGene(name).exists(g => g.isChangeable)) {
            if(changeActiveGene) {
                getActiveFor(entity, name).foreach(a => a.setAlleleData(value))
            } else {
                getChromosomeFor(entity, name).map(c => c.getRecessive).foreach(a => a.setAlleleData(value))
            }
        }
    }

    override def getControlledGenes(masterGeneName: String): List[String] = {
        getGene(masterGeneName).asInstanceOf[IMasterGene].getControlledGenes
    }
}