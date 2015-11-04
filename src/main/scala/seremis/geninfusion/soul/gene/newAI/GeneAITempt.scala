package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAITempt extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAITemptIndex)
    addControlledGene(Genes.GeneAITemptMoveSpeed)
    addControlledGene(Genes.GeneAITemptItem)
    addControlledGene(Genes.GeneAITemptScaredByPlayer)
}
