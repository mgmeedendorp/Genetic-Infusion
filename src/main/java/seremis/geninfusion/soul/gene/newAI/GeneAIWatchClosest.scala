package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIWatchClosest extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_INDEX)
    addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_TARGET)
    addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_RANGE)
    addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_CHANCE)
}
