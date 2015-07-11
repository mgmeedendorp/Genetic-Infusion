package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class OwnerHurtTarget extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIOwnerHurtTarget)
}
