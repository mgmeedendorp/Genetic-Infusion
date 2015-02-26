package seremis.geninfusion.soul.gene.newAI.sit;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAISit extends MasterGene {

    public GeneAISit() {
        addControlledGene(Genes.GENE_AI_SIT_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
