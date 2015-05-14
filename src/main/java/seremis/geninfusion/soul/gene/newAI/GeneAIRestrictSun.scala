package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIRestrictSun extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GENE_AI_RESTRICT_SUN_INDEX)
}
