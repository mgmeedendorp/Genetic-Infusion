package seremis.geninfusion.soul.gene.newAI.lookAtVillager;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAILookAtVillager extends MasterGene {

    public GeneAILookAtVillager() {
        addControlledGene(Genes.GENE_AI_LOOK_AT_VILLAGER_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
