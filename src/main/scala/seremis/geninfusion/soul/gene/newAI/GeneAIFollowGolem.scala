package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIFollowGolem extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIFollowGolemIndex)
}
