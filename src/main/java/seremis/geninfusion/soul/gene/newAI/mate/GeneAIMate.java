package seremis.geninfusion.soul.gene.newAI.mate;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIMate extends MasterGene {

    public GeneAIMate() {
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_MATE_MOVE_SPEED));
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
