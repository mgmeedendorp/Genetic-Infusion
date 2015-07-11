package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIOpenDoor extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIOpenDoorIndex)
    addControlledGene(Genes.GeneAIOpenDoorCloseDoor)
}
