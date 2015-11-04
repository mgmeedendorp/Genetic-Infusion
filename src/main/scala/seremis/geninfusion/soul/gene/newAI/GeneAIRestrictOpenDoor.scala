package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIRestrictOpenDoor extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIRestrictOpenDoorIndex)
}
