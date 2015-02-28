package seremis.geninfusion.soul.gene.newAI.swimming;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAISwimming extends MasterGene {

    public GeneAISwimming() {
        addControlledGene(Genes.GENE_AI_SWIMMING);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
