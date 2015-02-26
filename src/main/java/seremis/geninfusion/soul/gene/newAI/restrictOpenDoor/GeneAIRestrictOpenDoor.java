package seremis.geninfusion.soul.gene.newAI.restrictOpenDoor;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIRestrictOpenDoor extends MasterGene {

    public GeneAIRestrictOpenDoor() {
        addControlledGene(Genes.GENE_AI_RESTRICT_OPEN_DOOR_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
