package seremis.geninfusion.soul.gene.newAI.runAroundLikeCrazy;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIRunAroundLikeCrazy extends MasterGene {

    public GeneAIRunAroundLikeCrazy() {
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_RUN_AROUND_LIKE_CRAZY_MOVE_SPEED));
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
