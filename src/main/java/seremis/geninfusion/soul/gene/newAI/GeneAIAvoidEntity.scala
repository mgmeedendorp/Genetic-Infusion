package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIAvoidEntity extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_AVOID_ENTITY_INDEX)
    addControlledGene(Genes.GENE_AI_AVOID_ENTITY_TARGET)
    addControlledGene(Genes.GENE_AI_AVOID_ENTITY_RANGE)
    addControlledGene(Genes.GENE_AI_AVOID_ENTITY_FAR_SPEED)
    addControlledGene(Genes.GENE_AI_AVOID_ENTITY_NEAR_SPEED)
}
