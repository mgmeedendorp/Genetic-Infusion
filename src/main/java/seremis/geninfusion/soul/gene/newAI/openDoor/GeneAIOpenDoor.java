package seremis.geninfusion.soul.gene.newAI.openDoor;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIOpenDoor extends MasterGene {

    public GeneAIOpenDoor() {
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_OPEN_DOOR_CLOSE_DOOR));
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
