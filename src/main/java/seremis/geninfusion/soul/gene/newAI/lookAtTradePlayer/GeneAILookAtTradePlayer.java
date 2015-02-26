package seremis.geninfusion.soul.gene.newAI.lookAtTradePlayer;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAILookAtTradePlayer extends MasterGene {

    public GeneAILookAtTradePlayer() {
        addControlledGene(Genes.GENE_AI_LOOK_AT_TRADE_PLAYER_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
