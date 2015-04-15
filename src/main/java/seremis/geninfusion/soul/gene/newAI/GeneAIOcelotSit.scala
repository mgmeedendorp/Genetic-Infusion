package seremis.geninfusion.soul.gene.newAI

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneAIOcelotSit extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_AI_OCELOT_SIT_INDEX)
    addControlledGene(Genes.GENE_AI_OCELOT_SIT_MOVE_SPEED)
}
