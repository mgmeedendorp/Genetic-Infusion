package seremis.geninfusion.api.soul

import net.minecraft.entity.EntityLiving

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

    def getValueFromAllele[T](entity: IEntitySoulCustom, name: String): T

    def getControlledGenes(masterGeneName: String): List[String]
}
