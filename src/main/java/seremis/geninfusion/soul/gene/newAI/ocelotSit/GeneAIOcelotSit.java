package seremis.geninfusion.soul.gene.newAI.ocelotSit;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIOcelotSit extends MasterGene {

    public GeneAIOcelotSit() {
        addControlledGene(Genes.GENE_AI_OCELOT_SIT_INDEX);
        addControlledGene(Genes.GENE_AI_OCELOT_SIT_MOVE_SPEED);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
