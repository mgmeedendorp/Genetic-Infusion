package seremis.geninfusion.soul

import net.minecraft.entity.EntityLiving
import net.minecraft.item.ItemStack
import seremis.geninfusion.api.soul._
import seremis.geninfusion.api.soul.util.ModelPart

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

    override def registerGene(name: String, alleleType: EnumAlleleType): IGene = registerGene(name, new Gene(alleleType))

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

    override def getSoulFor(entity: EntityLiving): ISoul = {
        if (entity.isInstanceOf[IEntitySoulCustom]) {
            return entity.asInstanceOf[IEntitySoulCustom].getSoul
        } else if (entity != null) {
            return SoulHelper.standardSoulRegistry.getSoulForEntity(entity)
        }
        null
    }

    override def getChromosomeFor(entity: EntityLiving, name: String): IChromosome = {
        getSoulFor(entity).getChromosomes(getGeneId(name))
    }

    override def getChromosomeFor(entity: IEntitySoulCustom, name: String): IChromosome = {
        getChromosomeFor(entity.asInstanceOf[EntityLiving], name)
    }

    override def getActiveFor(entity: EntityLiving, name: String): IAllele = {
        if (getChromosomeFor(entity, name) != null) getChromosomeFor(entity, name).getActive else null
    }

    override def getActiveFor(entity: IEntitySoulCustom, name: String): IAllele = {
        if (getChromosomeFor(entity, name) != null) getChromosomeFor(entity, name).getActive else null
    }

    override def getValueBoolean(entity: IEntitySoulCustom, name: String): Boolean = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Boolean]
    }

    override def getValueInteger(entity: IEntitySoulCustom, name: String): Int = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Integer]
    }

    override def getValueFloat(entity: IEntitySoulCustom, name: String): Float = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Float]
    }

    override def getValueDouble(entity: IEntitySoulCustom, name: String): Double = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Double]
    }

    override def getValueString(entity: IEntitySoulCustom, name: String): String = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[String]
    }

    override def getValueItemStack(entity: IEntitySoulCustom, name: String): ItemStack = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[ItemStack]
    }

    override def getValueModelPart(entity: IEntitySoulCustom, name: String): ModelPart = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[ModelPart]
    }

    override def getValueClass(entity: IEntitySoulCustom, name: String): Class[_] = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Class[_]]
    }

    override def getValueBooleanArray(entity: IEntitySoulCustom, name: String): Array[Boolean] = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Array[Boolean]]
    }

    override def getValueIntegerArray(entity: IEntitySoulCustom, name: String): Array[Int] = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Array[Int]]
    }

    override def getValueFloatArray(entity: IEntitySoulCustom, name: String): Array[Float] = {
        for(gene <- getGenes) {
            val name = getGeneName(gene)

            println(name + " " + getActiveFor(entity, name).getAlleleData.getClass + " " + getActiveFor(entity, name).getAlleleData + " " + gene.getAlleleType)
        }

        println(getActiveFor(entity, name).getAlleleData.getClass + " " + getActiveFor(entity, name).getAlleleData)
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Array[Float]]
    }

    override def getValueDoubleArray(entity: IEntitySoulCustom, name: String): Array[Double] = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Array[Double]]
    }

    override def getValueStringArray(entity: IEntitySoulCustom, name: String): Array[String] = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Array[String]]
    }

    override def getValueItemStackArray(entity: IEntitySoulCustom, name: String): Array[ItemStack] = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Array[ItemStack]]
    }

    override def getValueModelPartArray(entity: IEntitySoulCustom, name: String): Array[ModelPart] = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Array[ModelPart]]
    }

    override def getValueClassArray(entity: IEntitySoulCustom, name: String): Array[Class[_]] = {
        getActiveFor(entity, name).getAlleleData.asInstanceOf[Array[Class[_]]]
    }

    override def getControlledGenes(masterGeneName: String): List[String] = {
        getGene(masterGeneName).asInstanceOf[IMasterGene].getControlledGenes
    }
}