package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAITempt extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_AI_TEMPT_INDEX)
    addControlledGene(Genes.GENE_AI_TEMPT_MOVE_SPEED)
    addControlledGene(Genes.GENE_AI_TEMPT_ITEM)
    addControlledGene(Genes.GENE_AI_TEMPT_SCARED_BY_PLAYER)
}
