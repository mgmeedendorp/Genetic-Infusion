package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIWatchClosest2 extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIWatchClosest2Index)
    addControlledGene(Genes.GeneAIWatchClosest2Target)
    addControlledGene(Genes.GeneAIWatchClosest2Range)
    addControlledGene(Genes.GeneAIWatchClosest2Chance)
}
