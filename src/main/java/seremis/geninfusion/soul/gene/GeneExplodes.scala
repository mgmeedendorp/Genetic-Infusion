package seremis.geninfusion.soul.gene

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneExplodes extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_EXPLOSION_RADIUS)
    addControlledGene(Genes.GENE_FUSE_TIME)
    addControlledGene(Genes.GENE_CAN_BE_CHARGED)
    addControlledGene(Genes.GENE_FLINT_AND_STEEL_IGNITE)
}
