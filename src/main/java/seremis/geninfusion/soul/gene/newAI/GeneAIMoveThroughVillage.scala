package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIMoveThroughVillage extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_INDEX)
    addControlledGene(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED)
    addControlledGene(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL)
}
