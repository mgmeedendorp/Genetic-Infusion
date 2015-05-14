package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIPlay extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_PLAY_INDEX)
    addControlledGene(Genes.GENE_AI_PLAY_MOVE_SPEED)
}
