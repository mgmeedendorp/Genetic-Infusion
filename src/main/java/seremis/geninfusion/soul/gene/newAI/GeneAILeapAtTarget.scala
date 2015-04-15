package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAILeapAtTarget extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_AI_LEAP_AT_TARGET_INDEX)
    addControlledGene(Genes.GENE_AI_LEAP_AT_TARGET_MOTION_Y)
}
