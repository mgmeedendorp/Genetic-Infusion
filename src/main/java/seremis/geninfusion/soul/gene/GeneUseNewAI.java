package seremis.geninfusion.soul.gene;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.MasterGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

/**
 * @author Seremis
 */
public class GeneUseNewAI extends MasterGene {

    public GeneUseNewAI() {
        addControlledGene(Genes.GENE_AI_ARROW_ATTACK);
        addControlledGene(Genes.GENE_AI_ATTACK_ON_COLLIDE);
        addControlledGene(Genes.GENE_AI_AVOID_ENTITY);
        addControlledGene(Genes.GENE_AI_BEG);
        addControlledGene(Genes.GENE_AI_BREAK_DOOR);
        addControlledGene(Genes.GENE_AI_CONTROLLED_BY_PLAYER);
        addControlledGene(Genes.GENE_AI_CREEPER_SWELL);
        addControlledGene(Genes.GENE_AI_DEFEND_VILLAGE);
        addControlledGene(Genes.GENE_AI_EAT_GRASS);
        addControlledGene(Genes.GENE_AI_FLEE_SUN);
        addControlledGene(Genes.GENE_AI_FOLLOW_GOLEM);
        addControlledGene(Genes.GENE_AI_FOLLOW_OWNER);
        addControlledGene(Genes.GENE_AI_FOLLOW_PARENT);
        addControlledGene(Genes.GENE_AI_HURT_BY_TARGET);
        addControlledGene(Genes.GENE_AI_LEAP_AT_TARGET);
        addControlledGene(Genes.GENE_AI_LOOK_AT_TRADE_PLAYER);
        addControlledGene(Genes.GENE_AI_LOOK_AT_VILLAGER);
        addControlledGene(Genes.GENE_AI_LOOK_IDLE);
        addControlledGene(Genes.GENE_AI_MATE);
        addControlledGene(Genes.GENE_AI_MOVE_INDOORS);
        addControlledGene(Genes.GENE_AI_MOVE_THROUGH_VILLAGE);
        addControlledGene(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION);
        addControlledGene(Genes.GENE_AI_MOVE_TOWARDS_TARGET);
        addControlledGene(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET);
        addControlledGene(Genes.GENE_AI_OCELOT_ATTACK);
        addControlledGene(Genes.GENE_AI_OCELOT_SIT);
        addControlledGene(Genes.GENE_AI_OPEN_DOOR);
        addControlledGene(Genes.GENE_AI_OWNER_HURT_BY_TARGET);
        addControlledGene(Genes.GENE_AI_OWNER_HURT_TARGET);
        addControlledGene(Genes.GENE_AI_PANIC);
        addControlledGene(Genes.GENE_AI_PLAY);
        addControlledGene(Genes.GENE_AI_RESTRICT_OPEN_DOOR);
        addControlledGene(Genes.GENE_AI_RESTRICT_SUN);
        addControlledGene(Genes.GENE_AI_RUN_AROUND_LIKE_CRAZY);
        addControlledGene(Genes.GENE_AI_SIT);
        addControlledGene(Genes.GENE_AI_SWIMMING);
        addControlledGene(Genes.GENE_AI_TARGET_NON_TAMED);
        addControlledGene(Genes.GENE_AI_TEMPT);
        addControlledGene(Genes.GENE_AI_TRADE_PLAYER);
        addControlledGene(Genes.GENE_AI_VILLAGER_MATE);
        addControlledGene(Genes.GENE_AI_WANDER);
        addControlledGene(Genes.GENE_AI_WATCH_CLOSEST);
        addControlledGene(Genes.GENE_AI_WATCH_CLOSEST_2);

        addControlledGene(Genes.GENE_AI_MODIFIER_ATTACK);
        addControlledGene(Genes.GENE_AI_MODIFIER_DO_USELESS_THINGS);
        addControlledGene(Genes.GENE_AI_MODIFIER_HELP_OWNER);
        addControlledGene(Genes.GENE_AI_MODIFIER_MATE);
        addControlledGene(Genes.GENE_AI_MODIFIER_RUN);
        addControlledGene(Genes.GENE_AI_MODIFIER_SURVIVE);
        addControlledGene(Genes.GENE_AI_MODIFIER_TRADE);
    }

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
