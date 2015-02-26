package seremis.geninfusion.soul.gene.newAI.breakDoor;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIBreakDoor extends MasterGene {

    public GeneAIBreakDoor() {
        addControlledGene(Genes.GENE_AI_BREAK_DOOR_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
