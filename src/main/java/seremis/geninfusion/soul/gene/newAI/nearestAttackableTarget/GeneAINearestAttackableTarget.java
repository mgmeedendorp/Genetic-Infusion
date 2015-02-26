package seremis.geninfusion.soul.gene.newAI.nearestAttackableTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAINearestAttackableTarget extends MasterGene {

    public GeneAINearestAttackableTarget() {
        addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX);
        addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET);
        addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE);
        addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE);
        addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY);
        addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
