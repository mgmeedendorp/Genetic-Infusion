package seremis.geninfusion.api.soul

import net.minecraft.entity.EntityLiving

trait IGeneRegistry {

    def registerGene(name: String, gene: IGene): IGene

    def registerGene(name: String, clzz: Class[_]): IGene

    def registerMasterGene(name: String, gene: IMasterGene): IMasterGene

    def registerCustomInheritance(name: String)

    def registerCustomInheritance(gene: IGene)

    def useNormalInheritance(gene: IGene): Boolean

    def useNormalInheritance(name: String): Boolean

    def getCustomInheritanceGenes: List[IGene]

    def getGene(name: String): Option[IGene]

    def getGene(id: Int): Option[IGene]

    def getGeneName(gene: IGene): Option[String]

    def getGeneId(name: String): Option[Int]

    def getGeneId(gene: IGene): Option[Int]

    def getGenes: List[IGene]

    def getSoulFor(entity: EntityLiving): Option[ISoul]

    def getChromosomeFor(entity: EntityLiving, name: String): Option[IChromosome]

    def getChromosomeFor(entity: IEntitySoulCustom, name: String): Option[IChromosome]

    def getActiveFor(entity: EntityLiving, name: String): Option[IAllele]

    def getActiveFor(entity: IEntitySoulCustom, name: String): Option[IAllele]

    def getValueFromAllele[T](entity: IEntitySoulCustom, name: String): T

    /**
     * Change the value of an allele of the gene with the specified name. This is only possible for genes with the flag isChangeable true. (see IGene)
     * @param entity The entity for which to change the allele.
     * @param name The name of the IGene for which to change the allele.
     * @param value The new value of the specified IGene.
     * @param changeActiveGene Whether to change the active or recessive gene.
     */
    def changeAlleleValue[T](entity: IEntitySoulCustom, name: String, value: T, changeActiveGene: Boolean)

    def getControlledGenes(masterGeneName: String): List[String]
}
