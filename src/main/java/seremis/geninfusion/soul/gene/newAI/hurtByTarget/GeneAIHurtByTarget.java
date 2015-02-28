package seremis.geninfusion.soul.gene.newAI.hurtByTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIHurtByTarget extends MasterGene {

    public GeneAIHurtByTarget() {
        addControlledGene(Genes.GENE_AI_HURT_BY_TARGET_INDEX);
        addControlledGene(Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
