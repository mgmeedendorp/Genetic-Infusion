package seremis.geninfusion.soul.gene.newAI.followOwner;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIFollowOwner extends MasterGene {

    public GeneAIFollowOwner() {
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_FOLLOW_OWNER_MOVE_SPEED));
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_FOLLOW_OWNER_MAX_DISTANCE));
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_FOLLOW_OWNER_MIN_DISTANCE));
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}