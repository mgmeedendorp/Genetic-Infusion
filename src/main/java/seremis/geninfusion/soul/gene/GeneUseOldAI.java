package seremis.geninfusion.soul.gene;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

/**
 * @author Seremis
 */
public class GeneUseOldAI extends MasterGene {

    public GeneUseOldAI() {
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_CEASE_AI_MOVEMENT));
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
