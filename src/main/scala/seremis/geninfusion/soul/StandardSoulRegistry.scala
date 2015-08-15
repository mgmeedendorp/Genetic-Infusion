package seremis.geninfusion.soul

import net.minecraft.entity.monster.EntityCreeper
import net.minecraft.entity.{EntityList, EntityLiving}
import net.minecraft.util.{EnumChatFormatting, StatCollector}
import seremis.geninfusion.api.soul._
import seremis.geninfusion.api.util.AncestryNodeRoot

import scala.collection.mutable.ListBuffer

object StandardSoulRegistry extends IStandardSoulRegistry {

    var standardSouls: ListBuffer[IStandardSoul] = ListBuffer()

    override def register(standard: IStandardSoul) {
        standardSouls += standard
    }

    override def getSoulForEntity(entity: EntityLiving): Option[ISoul] = {
        val chromosomes = new Array[IChromosome](SoulHelper.geneRegistry.getGenes.size)
        for(i <- chromosomes.indices) {
            val gene = SoulHelper.geneRegistry.getGene(i)
            val name = SoulHelper.geneRegistry.getGeneName(gene)

            chromosomes(i) = getStandardSoulForEntity(entity).getOrElse(return None).getChromosomeFromGene(entity, name)

            if(chromosomes(i) == null) {
                throw new NullPointerException("There seems to be a Gene: (" + name + ") without an associated Chromosome for Entity: (" + entity + ").")
            } else if(chromosomes(i).getPrimary.getAlleleData != null && !chromosomes(i).getPrimary.getAlleleType.equals(SoulHelper.geneRegistry.getGene(i).getAlleleType)) {
                throw new ClassCastException("Someone associated a Gene: (" + name + ") with an Allele (" + chromosomes(i).getPrimary.getAlleleData.getClass.getName + ") & AlleleType: " + chromosomes(i).getPrimary.getAlleleType + " that isn't allowed for this gene. It should be: " + gene.getAlleleType.getAlleleTypeClass + " & AlleleType: " + SoulHelper.geneRegistry.getGene(i).getAlleleType)
            }
        }
        val name = EntityList.getEntityString(entity)

        Some(new Soul(chromosomes, Some(name), AncestryNodeRoot(name)))
    }

    override def getStandardSoulForEntity(entity: EntityLiving): Option[IStandardSoul] = {
        standardSouls.foreach(soul => if(soul.isStandardSoulForEntity(entity)) return Some(soul))
        None
    }

    override def getStandardSouls = standardSouls.to[Array]
}
