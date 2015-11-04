package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIAvoidEntity extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIAvoidEntityIndex)
    addControlledGene(Genes.GeneAIAvoidEntityTarget)
    addControlledGene(Genes.GeneAIAvoidEntityRange)
    addControlledGene(Genes.GeneAIAvoidEntityFarSpeed)
    addControlledGene(Genes.GeneAIAvoidEntityNearSpeed)
}
