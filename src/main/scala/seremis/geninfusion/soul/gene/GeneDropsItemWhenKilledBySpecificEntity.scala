package seremis.geninfusion.soul.gene

import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.MasterGene

class GeneDropsItemWhenKilledBySpecificEntity extends MasterGene(classOf[Boolean]) {
    addControlledGene(Genes.GeneKilledBySpecificEntityDrops)
    addControlledGene(Genes.GeneKilledBySpecificEntityEntity)
}
