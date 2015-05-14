package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAINearestAttackableTarget extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX)
    addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET)
    addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE)
    addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE)
    addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY)
    addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR)
}
