package seremis.geninfusion.soul.gene.newAI.play;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIPlay extends MasterGene {

    public GeneAIPlay() {
        addControlledGene(Genes.GENE_AI_PLAY_INDEX);
        addControlledGene(Genes.GENE_AI_PLAY_MOVE_SPEED);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
