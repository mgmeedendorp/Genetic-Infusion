package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIRunAroundLikeCrazy extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_AI_RUN_AROUND_LIKE_CRAZY_INDEX)
    addControlledGene(Genes.GENE_AI_RUN_AROUND_LIKE_CRAZY_MOVE_SPEED)
}
