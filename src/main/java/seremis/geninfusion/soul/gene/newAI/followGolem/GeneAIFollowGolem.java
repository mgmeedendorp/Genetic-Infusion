package seremis.geninfusion.soul.gene.newAI.followGolem;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIFollowGolem extends MasterGene {

    public GeneAIFollowGolem() {
        addControlledGene(Genes.GENE_AI_FOLLOW_GOLEM_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
