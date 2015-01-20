package seremis.geninfusion.soul.standardSoul;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IStandardSoul;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.*;

/**
 * @author Seremis
 */
public class StandardSoul implements IStandardSoul {

    @Override
    public IChromosome getChromosomeFromGene(String gene) {
        if(gene.equals(Genes.GENE_ATTACK_DAMAGE)) {
            return new Chromosome(new AlleleFloat(true, 0F));
        } else if(gene.equals(Genes.GENE_BURNS_IN_DAYLIGHT)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_CEASE_AI_MOVEMENT)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_CHILDREN_BURN_IN_DAYLIGHT)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_CREATURE_ATTRIBUTE)) {
            return new Chromosome(new AlleleInteger(true, EnumCreatureAttribute.UNDEFINED.ordinal()));
        } else if(gene.equals(Genes.GENE_DEATH_SOUND)) {
            return new Chromosome(new AlleleString(false, "game.hostile.die"));
        } else if(gene.equals(Genes.GENE_DROWNS_IN_AIR)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_DROWNS_IN_WATER)) {
            return new Chromosome(new AlleleBoolean(false, true));
        } else if(gene.equals(Genes.GENE_EQUIPMENT_DROP_CHANCES)) {
            return new Chromosome(new AlleleFloatArray(true, new float[]{0.085F, 0.085F, 0.085F, 0.085F, 0.085F}));
        } else if(gene.equals(Genes.GENE_EXPERIENCE_VALUE)) {
            return new Chromosome(new AlleleInteger(true, 5));
        } else if(gene.equals(Genes.GENE_FOLLOW_RANGE)) {
            return new Chromosome(new AlleleFloat(true, 16));
        } else if(gene.equals(Genes.GENE_HURT_SOUND)) {
            return new Chromosome(new AlleleString(true, "mob.hostile.hurt"));
        } else if(gene.equals(Genes.GENE_IMMUNE_TO_FIRE)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_INVULNERABLE)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_IS_CREATURE)) {
            return new Chromosome(new AlleleBoolean(false, false));
        } else if(gene.equals(Genes.GENE_ITEM_DROPS)) {
            return new Chromosome(new AlleleInventory(true, new ItemStack[]{}));
        } else if(gene.equals(Genes.GENE_KNOCKBACK_RESISTANCE)) {
            return new Chromosome(new AlleleFloat(true, 0));
        } else if(gene.equals(Genes.GENE_LIVING_SOUND)) {
            return new Chromosome(new AlleleString(false, null));
        } else if(gene.equals(Genes.GENE_MAX_HEALTH)) {
            return new Chromosome(new AlleleFloat(true, 20));
        } else if(gene.equals(Genes.GENE_MAX_HURT_RESISTANT_TIME)) {
            return new Chromosome(new AlleleInteger(true, 20));
        } else if(gene.equals(Genes.GENE_MOVEMENT_SPEED)) {
            return new Chromosome(new AlleleFloat(true, 0.2F));
        } else if(gene.equals(Genes.GENE_PICKS_UP_ITEMS)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_PORTAL_COOLDOWN)) {
            return new Chromosome(new AlleleInteger(true, 900));
        } else if(gene.equals(Genes.GENE_RARE_ITEM_DROP_CHANCES)) {
            return new Chromosome(new AlleleFloatArray(true, new float[]{0.33F, 0.33F, 0.33F}));
        } else if(gene.equals(Genes.GENE_RARE_ITEM_DROPS)) {
            return new Chromosome(new AlleleInventory(true, new ItemStack[]{}));
        } else if(gene.equals(Genes.GENE_SET_ON_FIRE_FROM_ATTACK)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_SHOULD_DESPAWN)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_SOUND_VOLUME)) {
            return new Chromosome(new AlleleFloat(true, 1F));
        } else if(gene.equals(Genes.GENE_SPLASH_SOUND)) {
            return new Chromosome(new AlleleString(true, "game.neutral.swim.splash"));
        } else if(gene.equals(Genes.GENE_SWIM_SOUND)) {
            return new Chromosome(new AlleleString(true, "game.neutral.swim"));
        } else if(gene.equals(Genes.GENE_TALK_INTERVAL)) {
            return new Chromosome(new AlleleInteger(true, 80));
        } else if(gene.equals(Genes.GENE_TELEPORT_TIME_IN_PORTAL)) {
            return new Chromosome(new AlleleInteger(true, 0));
        } else if(gene.equals(Genes.GENE_WALK_SOUND)) {
            return new Chromosome(new AlleleString(true, "mob.neutral.step"));
        } else if(gene.equals(Genes.GENE_USE_NEW_AI)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_USE_OLD_AI)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_VERTICAL_FACE_SPEED)) {
            return new Chromosome(new AlleleInteger(true, 40));
        } else if(gene.equals(Genes.GENE_IS_CREATURE)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_AI_ARROW_ATTACK)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME)) {
            return new Chromosome(new AlleleInteger(false, 0));
        } else if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME)) {
            return new Chromosome(new AlleleInteger(false, 0));
        } else if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER)) {
            return new Chromosome(new AlleleFloat(false, 1.0F));
        } else if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY)) {
            return new Chromosome(new AlleleBoolean(false, false));
        } else if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_TARGET)) {
            return new Chromosome(new AlleleString(false, EntityPlayer.class));
        } else if(gene.equals(Genes.GENE_AI_AVOID_ENTITY)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_AVOID_ENTITY_FAR_SPEED)) {
            return new Chromosome(new AlleleBoolean(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_AVOID_ENTITY_NEAR_SPEED)) {
            return new Chromosome(new AlleleBoolean(false, 1.2D));
        } else if(gene.equals(Genes.GENE_AI_AVOID_ENTITY_RANGE)) {
            return new Chromosome(new AlleleFloat(false, 6.0F));
        } else if(gene.equals(Genes.GENE_AI_AVOID_ENTITY_TARGET)) {
            return new Chromosome(new AlleleString(false, EntityCreeper.class));
        }
        return null;
    }
}
