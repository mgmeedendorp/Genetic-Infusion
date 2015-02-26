package seremis.geninfusion.soul.gene.newAI.watchClosest2;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIWatchClosest2 extends MasterGene {

    public GeneAIWatchClosest2() {
        addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_2_INDEX);
        addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_2_TARGET);
        addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_2_RANGE);
        addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_2_CHANCE);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
