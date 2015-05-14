package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIMoveTowardsTarget extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_MOVE_TOWARDS_TARGET_INDEX)
    addControlledGene(Genes.GENE_AI_MOVE_TOWARDS_TARGET_MAX_DISTANCE)
    addControlledGene(Genes.GENE_AI_MOVE_TOWARDS_TARGET_MOVE_SPEED)
}
