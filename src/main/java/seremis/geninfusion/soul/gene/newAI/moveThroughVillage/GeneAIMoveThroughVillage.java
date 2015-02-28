package seremis.geninfusion.soul.gene.newAI.moveThroughVillage;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIMoveThroughVillage extends MasterGene {

    public GeneAIMoveThroughVillage() {
        addControlledGene(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_INDEX);
        addControlledGene(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED);
        addControlledGene(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
