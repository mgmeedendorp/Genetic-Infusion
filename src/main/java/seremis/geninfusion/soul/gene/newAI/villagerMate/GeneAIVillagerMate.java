package seremis.geninfusion.soul.gene.newAI.villagerMate;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIVillagerMate extends MasterGene {

    public GeneAIVillagerMate() {
        addControlledGene(Genes.GENE_AI_VILLAGER_MATE_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
