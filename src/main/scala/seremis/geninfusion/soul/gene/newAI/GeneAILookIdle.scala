package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAILookIdle extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneAILookIdleIndex)
}
