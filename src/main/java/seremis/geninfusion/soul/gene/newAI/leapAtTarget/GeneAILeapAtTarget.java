package seremis.geninfusion.soul.gene.newAI.leapAtTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAILeapAtTarget extends MasterGene {

    public GeneAILeapAtTarget() {
        addControlledGene(Genes.GENE_AI_LEAP_AT_TARGET_INDEX);
        addControlledGene(Genes.GENE_AI_LEAP_AT_TARGET_MOTION_Y);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
