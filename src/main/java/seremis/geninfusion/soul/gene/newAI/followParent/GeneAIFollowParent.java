package seremis.geninfusion.soul.gene.newAI.followParent;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIFollowParent extends MasterGene {

    public GeneAIFollowParent() {
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_FOLLOW_PARENT_MOVE_SPEED));
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
