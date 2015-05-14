package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIWander extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_WANDER_INDEX)
    addControlledGene(Genes.GENE_AI_WANDER_MOVE_SPEED)
}
