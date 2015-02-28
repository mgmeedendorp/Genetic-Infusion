package seremis.geninfusion.soul.gene.newAI.restrictSun;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIRestrictSun extends MasterGene {

    public GeneAIRestrictSun() {
        addControlledGene(Genes.GENE_AI_RESTRICT_SUN_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
