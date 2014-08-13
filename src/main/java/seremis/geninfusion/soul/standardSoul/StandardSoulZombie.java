package seremis.geninfusion.soul.standardSoul;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.StandardSoul;
import seremis.geninfusion.soul.allele.*;

/**
 * @author Seremis
 */
public class StandardSoulZombie extends StandardSoul {

    @Override
    public IChromosome getChromosomeFromGene(String gene) {
        if(gene.equals(Genes.GENE_ATTACK_DAMAGE))
            return new Chromosome(new AlleleFloat(true, 3.0F));
        if(gene.equals(Genes.GENE_BURNS_IN_DAYLIGHT))
            return new Chromosome(new AlleleBoolean(false, true));
        if(gene.equals(Genes.GENE_CREATURE_ATTRIBUTE))
            return new Chromosome(new AlleleInteger(false, EnumCreatureAttribute.UNDEAD.ordinal()));
        if(gene.equals(Genes.GENE_DEATH_SOUND))
            return new Chromosome(new AlleleString(true, "mob.zombie.death"));
        if(gene.equals(Genes.GENE_HURT_SOUND))
            return new Chromosome(new AlleleString(false, null));
        if(gene.equals(Genes.GENE_ITEM_DROPS))
            return new Chromosome(new AlleleInventory(false, new ItemStack[] {new ItemStack(Items.rotten_flesh)}));
        if(gene.equals(Genes.GENE_LIVING_SOUND))
            return new Chromosome(new AlleleString(false, "mob.zombie.say"));
        if(gene.equals(Genes.GENE_MOVEMENT_SPEED))
            return new Chromosome(new AlleleFloat(true, 0.23F));
        if(gene.equals(Genes.GENE_PICKS_UP_ITEMS))
            return new Chromosome(new AlleleBoolean(false, true));
        if(gene.equals(Genes.GENE_RARE_ITEM_DROPS))
            return new Chromosome(new AlleleInventory(false, new ItemStack[] {new ItemStack(Items.iron_ingot), new ItemStack(Items.carrot), new ItemStack(Items.potato)}));
        if(gene.equals(Genes.GENE_SET_ON_FIRE_FROM_ATTACK))
            return new Chromosome(new AlleleBoolean(false, true));
        if(gene.equals(Genes.GENE_SPLASH_SOUND))
            return new Chromosome(new AlleleString(false, "game.hostile.swim.splash"));
        if(gene.equals(Genes.GENE_SWIM_SOUND))
            return new Chromosome(new AlleleString(false, "game.hostile.swim"));
        if(gene.equals(Genes.GENE_WALK_SOUND))
            return new Chromosome(new AlleleString(true, "mob.zombie.step"));
        if(gene.equals(Genes.GENE_USE_NEW_AI))
            return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_USE_OLD_AI))
            return new Chromosome(new AlleleBoolean(true, false));

        return super.getChromosomeFromGene(gene);
    }
}
