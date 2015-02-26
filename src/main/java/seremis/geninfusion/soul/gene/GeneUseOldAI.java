package seremis.geninfusion.soul.gene;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneUseOldAI extends MasterGene {

    public GeneUseOldAI() {
        addControlledGene(Genes.GENE_CEASE_AI_MOVEMENT);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
