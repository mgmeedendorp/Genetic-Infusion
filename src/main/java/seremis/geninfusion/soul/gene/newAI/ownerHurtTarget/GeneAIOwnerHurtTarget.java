package seremis.geninfusion.soul.gene.newAI.ownerHurtTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIOwnerHurtTarget extends MasterGene {

    public GeneAIOwnerHurtTarget() {
        addControlledGene(Genes.GENE_AI_OWNER_HURT_TARGET);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
