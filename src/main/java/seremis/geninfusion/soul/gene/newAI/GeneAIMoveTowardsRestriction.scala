package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIMoveTowardsRestriction extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_INDEX)
    addControlledGene(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED)
}
