package seremis.geninfusion.soul.gene.newAI.arrowAttack;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAIArrowAttack extends MasterGene {

    public GeneAIArrowAttack() {
        addControlledGene(Genes.GENE_AI_ARROW_ATTACK_INDEX);
        addControlledGene(Genes.GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME);
        addControlledGene(Genes.GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME);
        addControlledGene(Genes.GENE_AI_ARROW_ATTACK_MOVE_SPEED);
        addControlledGene(Genes.GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
