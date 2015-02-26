package seremis.geninfusion.soul.gene.newAI.avoidEntity;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIAvoidEntity extends MasterGene {

    public GeneAIAvoidEntity() {
        addControlledGene(Genes.GENE_AI_AVOID_ENTITY_INDEX);
        addControlledGene(Genes.GENE_AI_AVOID_ENTITY_TARGET);
        addControlledGene(Genes.GENE_AI_AVOID_ENTITY_RANGE);
        addControlledGene(Genes.GENE_AI_AVOID_ENTITY_FAR_SPEED);
        addControlledGene(Genes.GENE_AI_AVOID_ENTITY_NEAR_SPEED);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
