package seremis.geninfusion.soul.gene

import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneDropsItemWhenKilledBySpecificEntity extends MasterGene(EnumAlleleType.BOOLEAN) {
    addControlledGene(Genes.GENE_KILLED_BY_SPECIFIC_ENTITY_DROPS)
    addControlledGene(Genes.GENE_KILLED_BY_SPECIFIC_ENTITY_ENTITY)
}
