package seremis.geninfusion.api.soul

import net.minecraft.entity.EntityLiving
import net.minecraft.item.ItemStack
import seremis.geninfusion.api.soul.util.ModelPart

trait IGeneRegistry {

    def registerGene(name: String, gene: IGene): IGene

    def registerGene(name: String, alleleType: EnumAlleleType): IGene

    def registerMasterGene(name: String, gene: IMasterGene): IMasterGene

    def registerCustomInheritance(name: String)

    def registerCustomInheritance(gene: IGene)

    def useNormalInheritance(gene: IGene): Boolean

    def useNormalInheritance(name: String): Boolean

    def getCustomInheritanceGenes: List[IGene]

    def getGene(name: String): IGene

    def getGeneName(gene: IGene): String

    def getGeneId(name: String): Int

    def getGeneId(gene: IGene): Int

    def getGenes: List[IGene]

    def getGene(id: Int): IGene

    def getSoulFor(entity: EntityLiving): ISoul

    def getChromosomeFor(entity: EntityLiving, name: String): IChromosome

    def getChromosomeFor(entity: IEntitySoulCustom, name: String): IChromosome

    def getActiveFor(entity: EntityLiving, name: String): IAllele

    def getActiveFor(entity: IEntitySoulCustom, name: String): IAllele

    def getValueBoolean(entity: IEntitySoulCustom, name: String): Boolean

    def getValueInteger(entity: IEntitySoulCustom, name: String): Int

    def getValueFloat(entity: IEntitySoulCustom, name: String): Float

    def getValueDouble(entity: IEntitySoulCustom, name: String): Double

    def getValueString(entity: IEntitySoulCustom, name: String): String

    def getValueItemStack(entity: IEntitySoulCustom, name: String): ItemStack

    def getValueModelPart(entity: IEntitySoulCustom, name: String): ModelPart

    def getValueClass(entity: IEntitySoulCustom, name: String): Class[_]

    def getValueBooleanArray(entity: IEntitySoulCustom, name: String): Array[Boolean]

    def getValueIntegerArray(entity: IEntitySoulCustom, name: String): Array[Int]

    def getValueFloatArray(entity: IEntitySoulCustom, name: String): Array[Float]

    def getValueDoubleArray(entity: IEntitySoulCustom, name: String): Array[Double]

    def getValueStringArray(entity: IEntitySoulCustom, name: String): Array[String]

    def getValueItemStackArray(entity: IEntitySoulCustom, name: String): Array[ItemStack]

    def getValueModelPartArray(entity: IEntitySoulCustom, name: String): Array[ModelPart]

    def getValueClassArray(entity: IEntitySoulCustom, name: String): Array[Class[_]]

    def getControlledGenes(masterGeneName: String): List[String]
}
