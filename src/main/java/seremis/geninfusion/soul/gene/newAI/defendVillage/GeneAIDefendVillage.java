package seremis.geninfusion.soul.gene.newAI.defendVillage;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIDefendVillage extends MasterGene {

    public GeneAIDefendVillage() {
        addControlledGene(Genes.GENE_AI_DEFEND_VILLAGE_INDEX);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
