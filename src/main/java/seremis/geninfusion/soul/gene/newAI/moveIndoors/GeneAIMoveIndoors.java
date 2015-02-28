package seremis.geninfusion.soul.gene.newAI.moveIndoors;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIMoveIndoors extends MasterGene {

    public GeneAIMoveIndoors() {
        addControlledGene(Genes.GENE_AI_MOVE_INDOORS_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
