package seremis.geninfusion.soul.gene.newAI.nearestAttackableTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class GeneAINearestAttackableTarget extends MasterGene {

    public GeneAINearestAttackableTarget() {
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET));
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE));
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE));
        addControlledGene(SoulHelper.geneRegistry.getGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY));
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
