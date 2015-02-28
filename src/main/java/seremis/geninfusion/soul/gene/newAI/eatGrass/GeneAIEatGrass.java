package seremis.geninfusion.soul.gene.newAI.eatGrass;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIEatGrass extends MasterGene {

    public GeneAIEatGrass() {
        addControlledGene(Genes.GENE_AI_EAT_GRASS_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
