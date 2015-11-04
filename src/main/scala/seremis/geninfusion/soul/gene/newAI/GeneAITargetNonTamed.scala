package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAITargetNonTamed extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAITargetNonTamedIndex)
    addControlledGene(Genes.GeneAITargetNonTamedTarget)
    addControlledGene(Genes.GeneAITargetNonTamedTargetChance)
    addControlledGene(Genes.GeneAITargetNonTamedVisible)
}
