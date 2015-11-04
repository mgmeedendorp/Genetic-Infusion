package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIRunAroundLikeCrazy extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIRunAroundLikeCrazyIndex)
    addControlledGene(Genes.GeneAIRunAroundLikeCrazyMoveSpeed)
}
