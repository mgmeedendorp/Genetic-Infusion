package seremis.geninfusion.soul.gene.newAI.lookIdle;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAILookIdle extends MasterGene {

    public GeneAILookIdle() {
        addControlledGene(Genes.GENE_AI_LOOK_IDLE_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
