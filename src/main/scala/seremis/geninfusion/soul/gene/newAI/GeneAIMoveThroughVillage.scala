package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIMoveThroughVillage extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIMoveThroughVillageIndex)
    addControlledGene(Genes.GeneAIMoveThroughVillageMoveSpeed)
    addControlledGene(Genes.GeneAIMoveThroughVillageIsNocturnal)
}
