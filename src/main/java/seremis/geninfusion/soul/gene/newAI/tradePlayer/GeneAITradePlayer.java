package seremis.geninfusion.soul.gene.newAI.tradePlayer;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAITradePlayer extends MasterGene {

    public GeneAITradePlayer() {
        addControlledGene(Genes.GENE_AI_TRADE_PLAYER_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
