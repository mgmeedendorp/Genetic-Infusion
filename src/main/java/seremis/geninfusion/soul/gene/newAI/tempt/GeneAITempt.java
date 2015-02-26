package seremis.geninfusion.soul.gene.newAI.tempt;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAITempt extends MasterGene {

    public GeneAITempt() {
        addControlledGene(Genes.GENE_AI_TEMPT_INDEX);
        addControlledGene(Genes.GENE_AI_TEMPT_MOVE_SPEED);
        addControlledGene(Genes.GENE_AI_TEMPT_ITEM);
        addControlledGene(Genes.GENE_AI_TEMPT_SCARED_BY_PLAYER);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
