package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIAttackOnCollide extends MasterGene(EnumAlleleType.BOOLEAN_ARRAY) {
    addControlledGene(Genes.GENE_AI_ATTACK_ON_COLLIDE_INDEX)
    addControlledGene(Genes.GENE_AI_ATTACK_ON_COLLIDE_TARGET)
    addControlledGene(Genes.GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED)
    addControlledGene(Genes.GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY)
}
