package seremis.geninfusion.soul.gene.newAI.wander;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIWander extends MasterGene {

    public GeneAIWander() {
        addControlledGene(Genes.GENE_AI_WANDER_INDEX);
        addControlledGene(Genes.GENE_AI_WANDER_MOVE_SPEED);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
