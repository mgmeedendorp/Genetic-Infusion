package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIFollowOwner extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_AI_FOLLOW_OWNER_INDEX)
    addControlledGene(Genes.GENE_AI_FOLLOW_OWNER_MOVE_SPEED)
    addControlledGene(Genes.GENE_AI_FOLLOW_OWNER_MAX_DISTANCE)
    addControlledGene(Genes.GENE_AI_FOLLOW_OWNER_MIN_DISTANCE)
}
