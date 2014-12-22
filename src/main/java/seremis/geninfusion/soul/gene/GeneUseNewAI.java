package seremis.geninfusion.soul.gene;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

/**
 * @author Seremis
 */
public class GeneUseNewAI extends MasterGene {

    public GeneUseNewAI() {
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_CAN_SWIM));
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
