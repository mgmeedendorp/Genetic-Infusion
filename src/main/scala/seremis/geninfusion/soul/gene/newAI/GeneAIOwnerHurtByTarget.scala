package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIOwnerHurtByTarget extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIOwnerHurtByTargetIndex)
}
