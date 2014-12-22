package seremis.geninfusion.soul.gene.newAI.moveTowardsTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIMoveTowardsTarget extends MasterGene {

    public GeneAIMoveTowardsTarget() {
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_MOVE_TOWARDS_TARGET_MAX_DISTANCE));
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_MOVE_TOWARDS_TARGET_MOVE_SPEED));
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
