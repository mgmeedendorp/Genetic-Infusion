package seremis.geninfusion.soul.gene

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneUseOldAI extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_CEASE_AI_MOVEMENT)
}
