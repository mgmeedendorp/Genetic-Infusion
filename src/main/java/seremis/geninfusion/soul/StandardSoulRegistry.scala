package seremis.geninfusion.soul

import net.minecraft.entity.EntityLiving
import seremis.geninfusion.api.soul._

import scala.collection.mutable.ListBuffer

object StandardSoulRegistry extends IStandardSoulRegistry {

    var standardSouls: ListBuffer[IStandardSoul] = ListBuffer()

    override def register(standard: IStandardSoul) {
        standardSouls += standard
    }

    override def getSoulForEntity(entity: EntityLiving): ISoul = {
        val chromosomes = new Array[IChromosome](SoulHelper.geneRegistry.getGenes.size)
        for(i <- 0 until chromosomes.length) {
            val gene = SoulHelper.geneRegistry.getGene(i)
            val name = SoulHelper.geneRegistry.getGeneName(gene)
            chromosomes(i) = getStandardSoulForEntity(entity).getChromosomeFromGene(entity, name)
            if(chromosomes(i) == null) {
                throw new NullPointerException("There seems to be a Gene: (" + name + ") without an associated Chromosome for Entity: (" + entity + ").")
            } else if(!EnumAlleleType.getForClass(gene.possibleAlleles()).equals(EnumAlleleType.getForClass(chromosomes(i).getPrimary().getClass()))) {
                throw new ClassCastException("Someone associated a Gene: (" + name + ") with an Allele (" + chromosomes(i).getPrimary() + ") that isn't allowed for this gene. It should be: " + gene.possibleAlleles())
            } else if(!EnumAlleleType.getForClass(gene.possibleAlleles()).equals(EnumAlleleType.getForClass(chromosomes(i).getPrimary().getClass()))) {
                throw new ClassCastException("Someone associated a Gene: (" + name + ") with an Allele (" + chromosomes(i).getSecondary() + ") that isn't allowed for this gene. It should be: " + gene.possibleAlleles())
            }
        }
        new Soul(chromosomes)
    }

    override def getStandardSoulForEntity(entity: EntityLiving): IStandardSoul = {
        standardSouls.foreach(soul => if(soul.isStandardSoulForEntity(entity)) return soul)
        null
    }
}
