package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIOpenDoor extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_OPEN_DOOR_INDEX)
    addControlledGene(Genes.GENE_AI_OPEN_DOOR_CLOSE_DOOR)
}
