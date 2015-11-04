package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIFollowOwner extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIFollowOwnerIndex)
    addControlledGene(Genes.GeneAIFollowOwnerMoveSpeed)
    addControlledGene(Genes.GeneAIFollowOwnerMaxDistance)
    addControlledGene(Genes.GeneAIFollowOwnerMinDistance)
}
