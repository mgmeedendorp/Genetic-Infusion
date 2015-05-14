package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIMoveIndoors extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_MOVE_INDOORS_INDEX)
}
