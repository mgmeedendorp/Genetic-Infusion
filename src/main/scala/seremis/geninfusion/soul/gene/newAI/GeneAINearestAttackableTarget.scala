package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAINearestAttackableTarget extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAINearestAttackableTargetIndex)
    addControlledGene(Genes.GeneAINearestAttackableTargetTarget)
    addControlledGene(Genes.GeneAINearestAttackableTargetTargetChance)
    addControlledGene(Genes.GeneAINearestAttackableTargetVisible)
    addControlledGene(Genes.GeneAINearestAttackableTargetNearbyOnly)
    addControlledGene(Genes.GeneAINearestAttackableTargetEntitySelector)
}
