package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIHurtByTarget extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_HURT_BY_TARGET_INDEX)
    addControlledGene(Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP)
}
