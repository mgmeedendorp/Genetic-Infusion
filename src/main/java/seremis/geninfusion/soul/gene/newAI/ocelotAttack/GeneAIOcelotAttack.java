package seremis.geninfusion.soul.gene.newAI.ocelotAttack;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIOcelotAttack extends MasterGene {

    public GeneAIOcelotAttack() {
        addControlledGene(Genes.GENE_AI_OCELOT_ATTACK_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
