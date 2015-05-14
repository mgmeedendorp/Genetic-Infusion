package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIOwnerHurtTarget extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_OWNER_HURT_TARGET_INDEX)
}
