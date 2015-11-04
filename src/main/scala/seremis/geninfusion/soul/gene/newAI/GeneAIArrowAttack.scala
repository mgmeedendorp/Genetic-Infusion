package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIArrowAttack extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIArrowAttackIndex)
    addControlledGene(Genes.GeneAIArrowAttackMaxRangedAttackTime)
    addControlledGene(Genes.GeneAIArrowAttackMinRangedAttackTime)
    addControlledGene(Genes.GeneAIArrowAttackMoveSpeed)
    addControlledGene(Genes.GeneAIArrowAttackRangedAttackTimeModifier)
}
