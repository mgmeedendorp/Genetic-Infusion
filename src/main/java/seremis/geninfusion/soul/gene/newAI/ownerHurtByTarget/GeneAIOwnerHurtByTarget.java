package seremis.geninfusion.soul.gene.newAI.ownerHurtByTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIOwnerHurtByTarget extends MasterGene {

    public GeneAIOwnerHurtByTarget() {
        addControlledGene(Genes.GENE_AI_OWNER_HURT_BY_TARGET_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
