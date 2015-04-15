package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIArrowAttack extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_AI_ARROW_ATTACK_INDEX)
    addControlledGene(Genes.GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME)
    addControlledGene(Genes.GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME)
    addControlledGene(Genes.GENE_AI_ARROW_ATTACK_MOVE_SPEED)
    addControlledGene(Genes.GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER)
}
