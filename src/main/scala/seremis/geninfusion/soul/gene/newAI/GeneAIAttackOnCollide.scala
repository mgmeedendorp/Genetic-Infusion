package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIAttackOnCollide extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIAttackOnCollideIndex)
    addControlledGene(Genes.GeneAIAttackOnCollideTarget)
    addControlledGene(Genes.GeneAIAttackOnCollideMoveSpeed)
    addControlledGene(Genes.GeneAIAttackOnCollideLongMemory)
}
