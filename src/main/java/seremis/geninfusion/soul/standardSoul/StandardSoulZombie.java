package seremis.geninfusion.soul.standardSoul;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IStandardSoul;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.*;

/**
 * @author Seremis
 */
public class StandardSoulZombie implements IStandardSoul {

    @Override
    public IChromosome getChromosomeFromGene(String gene) {
        if(gene.equals(Genes.GENE_ATTACK_DAMAGE)) {
            return new Chromosome(new AlleleFloat(true, 3.0F), new AlleleFloat(false, 3.0F));
        }
        if(gene.equals(Genes.GENE_BURNS_IN_DAYLIGHT)) {
            return new Chromosome(new AlleleBoolean(true, true), new AlleleBoolean(false, true));
        }
        if(gene.equals(Genes.GENE_CREATURE_ATTRIBUTE)) {
            return new Chromosome(new AlleleInteger(true, EnumCreatureAttribute.UNDEAD.ordinal()), new AlleleInteger(true, EnumCreatureAttribute.UNDEAD.ordinal()));
        }
        if(gene.equals(Genes.GENE_DEATH_SOUND)) {
            return new Chromosome(new AlleleString(true, "mob.zombie.death"), new AlleleString(false, "mob.zombie.death"));
        }
        if(gene.equals(Genes.GENE_DROWNS_IN_AIR)) {
            return new Chromosome(new AlleleBoolean(true, false), new AlleleBoolean(false, false));
        }
        if(gene.equals(Genes.GENE_DROWNS_IN_WATER)) {
            return new Chromosome(new AlleleBoolean(true, true), new AlleleBoolean(false, true));
        }
        if(gene.equals(Genes.GENE_EQUIPMENT_DROP_CHANCES)) {
            float[] array = new float[] {0.085F, 0.085F, 0.085F, 0.085F, 0.085F};
            return new Chromosome(new AlleleFloatArray(true, array), new AlleleFloatArray(false, array));
        }
        if(gene.equals(Genes.GENE_FOLLOW_RANGE)) {
            return new Chromosome(new AlleleFloat(true, 16), new AlleleFloat(false, 16));
        }
        if(gene.equals(Genes.GENE_HURT_SOUND)) {
            return new Chromosome(new AlleleString(true, "mob.zombie.hurt"), new AlleleString(false, "mob.zombie.hurt"));
        }
        if(gene.equals(Genes.GENE_IMMUNE_TO_FIRE)) {
            return new Chromosome(new AlleleBoolean(true, false), new AlleleBoolean(false, false));
        }
        if(gene.equals(Genes.GENE_INVULNERABLE)) {
            return new Chromosome(new AlleleBoolean(true, false), new AlleleBoolean(false, false));
        }
        if(gene.equals(Genes.GENE_ITEM_DROPS)) {
            return new Chromosome(new AlleleInventory(true, new ItemStack[] {new ItemStack(Items.rotten_flesh)}), new AlleleInventory(false, new ItemStack[] {new ItemStack(Items.rotten_flesh)}));
        }
        if(gene.equals(Genes.GENE_KNOCKBACK_RESISTANCE)) {
            return new Chromosome(new AlleleFloat(true, 0), new AlleleFloat(false, 0));
        }
        if(gene.equals(Genes.GENE_LIVING_SOUND)) {
            return new Chromosome(new AlleleString(true, "mob.zombie.say"), new AlleleString(false, "mob.zombie.say"));
        }
        if(gene.equals(Genes.GENE_MAX_HEALTH)) {
            return new Chromosome(new AlleleFloat(true, 20), new AlleleFloat(false, 20));
        }
        if(gene.equals(Genes.GENE_MAX_HURT_RESISTANT_TIME)) {
            return new Chromosome(new AlleleInteger(true, 20), new AlleleInteger(false, 20));
        }
        if(gene.equals(Genes.GENE_MOVEMENT_SPEED)) {
            return new Chromosome(new AlleleFloat(true, 0.23F), new AlleleFloat(false, 0.23F));
        }
        if(gene.equals(Genes.GENE_PICKS_UP_ITEMS)) {
            return new Chromosome(new AlleleBoolean(true, true), new AlleleBoolean(false, true));
        }
        if(gene.equals(Genes.GENE_PORTAL_COOLDOWN)) {
            return new Chromosome(new AlleleInteger(true, 900), new AlleleInteger(false, 900));
        }
        if(gene.equals(Genes.GENE_RARE_ITEM_DROP_CHANCES)) {
            float[] array = new float[] {0.33F, 0.33F, 0.33F};
            return new Chromosome(new AlleleFloatArray(true, array), new AlleleFloatArray(false, array));
        }
        if(gene.equals(Genes.GENE_RARE_ITEM_DROPS)) {
            ItemStack[] array = new ItemStack[] {new ItemStack(Items.iron_ingot), new ItemStack(Items.carrot), new ItemStack(Items.potato)};
            return new Chromosome(new AlleleInventory(true, array), new AlleleInventory(false, array));
        }
        if(gene.equals(Genes.GENE_SET_ON_FIRE_FROM_ATTACK)) {
            return new Chromosome(new AlleleBoolean(true, true), new AlleleBoolean(false, true));
        }
        if(gene.equals(Genes.GENE_SHOULD_DESPAWN)) {
            return new Chromosome(new AlleleBoolean(true, false), new AlleleBoolean(false, false));
        }
        if(gene.equals(Genes.GENE_SOUND_VOLUME)) {
            return new Chromosome(new AlleleFloat(true, 1F), new AlleleFloat(false, 1F));
        }
        if(gene.equals(Genes.GENE_SPLASH_SOUND)) {
            return new Chromosome(new AlleleString(true, "game.hostile.swim.splash"), new AlleleString(false, "game.hostile.swim.splash"));
        }
        if(gene.equals(Genes.GENE_SWIM_SOUND)) {
            return new Chromosome(new AlleleString(true, "game.hostile.swim"), new AlleleString(false, "game.hostile.swim"));
        }
        if(gene.equals(Genes.GENE_TALK_INTERVAL)) {
            return new Chromosome(new AlleleInteger(true, 80), new AlleleInteger(false, 80));
        }
        if(gene.equals(Genes.GENE_TELEPORT_TIME_IN_PORTAL)) {
            return new Chromosome(new AlleleInteger(true, 0), new AlleleInteger(false, 0));
        }
        if(gene.equals(Genes.GENE_WALK_SOUND)) {
            return new Chromosome(new AlleleString(true, "mob.zombie.step"), new AlleleString(false, "mob.zombie.step"));
        }
        return null;
    }
}
