package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIWatchClosest extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIWatchClosestIndex)
    addControlledGene(Genes.GeneAIWatchClosestTarget)
    addControlledGene(Genes.GeneAIWatchClosestRange)
    addControlledGene(Genes.GeneAIWatchClosestChance)
}
