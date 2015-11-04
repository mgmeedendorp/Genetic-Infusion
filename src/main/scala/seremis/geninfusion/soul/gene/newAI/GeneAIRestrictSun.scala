package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIRestrictSun extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAIRestrictSunIndex)
}
