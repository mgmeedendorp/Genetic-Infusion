package seremis.geninfusion.soul.gene.newAI.fleeSun;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIFleeSun extends MasterGene {

    public GeneAIFleeSun() {
        addControlledGene(Genes.GENE_AI_FLEE_SUN_INDEX);
        addControlledGene(Genes.GENE_AI_FLEE_SUN_MOVE_SPEED);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
