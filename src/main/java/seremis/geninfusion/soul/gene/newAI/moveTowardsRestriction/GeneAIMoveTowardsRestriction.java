package seremis.geninfusion.soul.gene.newAI.moveTowardsRestriction;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIMoveTowardsRestriction extends MasterGene {

    public GeneAIMoveTowardsRestriction() {
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED));
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
