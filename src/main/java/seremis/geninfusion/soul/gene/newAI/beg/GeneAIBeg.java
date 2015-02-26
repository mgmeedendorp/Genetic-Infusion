package seremis.geninfusion.soul.gene.newAI.beg;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIBeg extends MasterGene {

    public GeneAIBeg() {
        addControlledGene(Genes.GENE_AI_BEG_INDEX);
        addControlledGene(Genes.GENE_AI_BEG_RANGE);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
