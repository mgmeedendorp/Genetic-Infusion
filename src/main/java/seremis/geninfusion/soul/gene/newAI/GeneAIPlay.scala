package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIPlay extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIPlayIndex)
    addControlledGene(Genes.GeneAIPlayMoveSpeed)
}
