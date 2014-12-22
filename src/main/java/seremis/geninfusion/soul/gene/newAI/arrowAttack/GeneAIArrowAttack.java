package seremis.geninfusion.soul.gene.newAI.arrowAttack;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.api.soul.IMasterGene;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

import java.util.List;

public class GeneAIArrowAttack extends MasterGene {

    public GeneAIArrowAttack() {
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME));
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME));
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_ARROW_ATTACK_MOVE_SPEED));
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER));
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
