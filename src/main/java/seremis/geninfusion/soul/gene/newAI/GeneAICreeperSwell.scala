package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

//If this is true, the entity can explode!
class GeneAICreeperSwell extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_AI_CREEPER_SWELL_INDEX)
}