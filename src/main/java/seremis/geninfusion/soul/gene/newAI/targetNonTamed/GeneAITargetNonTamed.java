package seremis.geninfusion.soul.gene.newAI.targetNonTamed;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAITargetNonTamed extends MasterGene {

    public GeneAITargetNonTamed() {
        addControlledGene(Genes.GENE_AI_TARGET_NON_TAMED_INDEX);
        addControlledGene(Genes.GENE_AI_TARGET_NON_TAMED_TARGET);
        addControlledGene(Genes.GENE_AI_TARGET_NON_TAMED_TARGET_CHANCE);
        addControlledGene(Genes.GENE_AI_TARGET_NON_TAMED_VISIBLE);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
