package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAITargetNonTamed extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_TARGET_NON_TAMED_INDEX)
    addControlledGene(Genes.GENE_AI_TARGET_NON_TAMED_TARGET)
    addControlledGene(Genes.GENE_AI_TARGET_NON_TAMED_TARGET_CHANCE)
    addControlledGene(Genes.GENE_AI_TARGET_NON_TAMED_VISIBLE)
}
