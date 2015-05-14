package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIFleeSun extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_FLEE_SUN_INDEX)
    addControlledGene(Genes.GENE_AI_FLEE_SUN_MOVE_SPEED)
}
