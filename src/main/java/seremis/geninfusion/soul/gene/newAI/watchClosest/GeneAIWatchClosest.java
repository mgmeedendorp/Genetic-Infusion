package seremis.geninfusion.soul.gene.newAI.watchClosest;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIWatchClosest extends MasterGene {

    public GeneAIWatchClosest() {
        addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_INDEX);
        addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_TARGET);
        addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_RANGE);
        addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_CHANCE);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
