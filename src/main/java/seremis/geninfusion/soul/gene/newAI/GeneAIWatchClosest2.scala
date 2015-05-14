package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIWatchClosest2 extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_2_INDEX)
    addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_2_TARGET)
    addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_2_RANGE)
    addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_2_CHANCE)
}
