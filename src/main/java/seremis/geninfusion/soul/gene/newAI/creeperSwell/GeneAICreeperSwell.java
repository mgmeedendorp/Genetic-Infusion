package seremis.geninfusion.soul.gene.newAI.creeperSwell;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAICreeperSwell extends MasterGene {

    public GeneAICreeperSwell() {
        addControlledGene(Genes.GENE_AI_CREEPER_SWELL_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
