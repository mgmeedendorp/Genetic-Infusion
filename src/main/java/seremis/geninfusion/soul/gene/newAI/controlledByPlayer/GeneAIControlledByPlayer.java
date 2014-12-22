package seremis.geninfusion.soul.gene.newAI.controlledByPlayer;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIControlledByPlayer extends MasterGene {

    public GeneAIControlledByPlayer() {
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_CONTROLLED_BY_PLAYER_MAX_SPEED));
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
