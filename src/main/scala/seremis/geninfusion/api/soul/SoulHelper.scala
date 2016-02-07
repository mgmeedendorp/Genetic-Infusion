package seremis.geninfusion.api.soul

import java.util.Random

import net.minecraft.client.model.ModelBase
import seremis.geninfusion.api.util.AncestryNodeBranch

/**
 * The access point for several registries needed for souls.
 *
 * @author Seremis
 */
object SoulHelper {
    var geneRegistry: IGeneRegistry = null

    var standardSoulRegistry: IStandardSoulRegistry = null

    var traitRegistry: ITraitRegistry = null

    var instanceHelper: IInstanceHelper = null

    var animationRegistry: IAnimationRegistry = null

    var alleleTypeRegistry: IAlleleTypeRegistry = null

    var entityMethodRegistry: IEntityMethodRegistry = null

    //TODO remove this
    var entityModel: ModelBase = null

    /**
     * Creates offspring from two parent souls
     *
     * @param parent1 A parent soul
     * @param parent2 A parent soul
     * @return The offspring
     */
    //TODO move this to a non-api class
    def produceOffspring(parent1: ISoul, parent2: ISoul): Option[ISoul] = {
        val chromosomes1 = parent1.getChromosomes
        val chromosomes2 = parent2.getChromosomes

        val offspring = Array.ofDim[IChromosome](chromosomes1.length)

        for(i <- chromosomes1.indices) {
            val gene = geneRegistry.getGene(i)
            if(gene.exists(g => geneRegistry.useNormalInheritance(g))) {
                offspring(i) = gene.get.inherit(chromosomes1(i), chromosomes2(i))
                if(rand.nextInt(200) == 0) {
                    offspring(i) = gene.get.mutate(offspring(i))
                }
            }
        }

        for(i <- geneRegistry.getCustomInheritanceGenes.indices) {
            val gene = geneRegistry.getCustomInheritanceGenes(i)
            val geneId = geneRegistry.getGeneId(gene)
            geneId.foreach(g => offspring(g) = gene.advancedInherit(chromosomes1, chromosomes2, offspring))
            if(rand.nextInt(200) == 0) {
                geneId.foreach(g => offspring(g) = gene.mutate(offspring(g)))
            }
        }

        instanceHelper.getISoulInstance(offspring, None, AncestryNodeBranch(parent1.getAncestryNode, parent2.getAncestryNode))
    }

    private val rand = new Random
}
