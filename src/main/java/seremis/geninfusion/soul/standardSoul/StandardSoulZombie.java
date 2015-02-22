package seremis.geninfusion.soul.standardSoul;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.*;

public class StandardSoulZombie extends StandardSoul {

    @Override
    public IChromosome getChromosomeFromGene(String gene) {
        if(gene.equals(Genes.GENE_ATTACK_DAMAGE)) return new Chromosome(new AlleleFloat(true, 3.0F));
        if(gene.equals(Genes.GENE_BURNS_IN_DAYLIGHT)) return new Chromosome(new AlleleBoolean(false, true));
        if(gene.equals(Genes.GENE_CREATURE_ATTRIBUTE)) return new Chromosome(new AlleleInteger(false, EnumCreatureAttribute.UNDEAD.ordinal()));
        if(gene.equals(Genes.GENE_DEATH_SOUND)) return new Chromosome(new AlleleString(true, "mob.zombie.death"));
        if(gene.equals(Genes.GENE_HURT_SOUND)) return new Chromosome(new AlleleString(false, "mob.zombie.hurt"));
        if(gene.equals(Genes.GENE_IS_CREATURE)) return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_ITEM_DROPS)) return new Chromosome(new AlleleInventory(false, new ItemStack[]{new ItemStack(Items.rotten_flesh)}));
        if(gene.equals(Genes.GENE_LIVING_SOUND)) return new Chromosome(new AlleleString(false, "mob.zombie.say"));
        if(gene.equals(Genes.GENE_MOVEMENT_SPEED)) return new Chromosome(new AlleleDouble(true, 0.23000000417232513D));
        if(gene.equals(Genes.GENE_PICKS_UP_ITEMS)) return new Chromosome(new AlleleBoolean(false, true));
        if(gene.equals(Genes.GENE_RARE_ITEM_DROPS)) return new Chromosome(new AlleleInventory(false, new ItemStack[]{new ItemStack(Items.iron_ingot), new ItemStack(Items.carrot), new ItemStack(Items.potato)}));
        if(gene.equals(Genes.GENE_SET_ON_FIRE_FROM_ATTACK)) return new Chromosome(new AlleleBoolean(false, true));
        if(gene.equals(Genes.GENE_SPLASH_SOUND)) return new Chromosome(new AlleleString(false, "game.hostile.swim.splash"));
        if(gene.equals(Genes.GENE_SWIM_SOUND)) return new Chromosome(new AlleleString(false, "game.hostile.swim"));
        if(gene.equals(Genes.GENE_WALK_SOUND)) return new Chromosome(new AlleleString(true, "mob.zombie.step"));
        if(gene.equals(Genes.GENE_USE_NEW_AI)) return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_USE_OLD_AI)) return new Chromosome(new AlleleBoolean(true, false));

        if(gene.equals(Genes.GENE_AI_SWIMMING)) return new Chromosome(new AlleleBoolean(true, true));

        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE)) return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY)) return new Chromosome(new AlleleBoolean(true, false));
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED)) return new Chromosome(new AlleleDouble(true, 1.0D));
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_TARGET)) return new Chromosome(new AlleleClass(true, EntityPlayer.class));

        if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION)) return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED)) return new Chromosome(new AlleleDouble(false, 1.0D));

        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE)) return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL)) return new Chromosome(new AlleleBoolean(false, true));
        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED)) return new Chromosome(new AlleleDouble(true, 1.0D));

        if(gene.equals(Genes.GENE_AI_WANDER)) return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_WANDER_MOVE_SPEED)) return new Chromosome(new AlleleDouble(true, 1.0D));

        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST)) return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_CHANCE)) return new Chromosome(new AlleleFloat(true, 0.02F));
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_RANGE)) return new Chromosome(new AlleleFloat(true, 8.0F));
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_TARGET)) return new Chromosome(new AlleleClass(true, EntityPlayer.class));

        if(gene.equals(Genes.GENE_AI_LOOK_IDLE)) return new Chromosome(new AlleleBoolean(true, true));

        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET)) return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP)) return new Chromosome(new AlleleBoolean(true, true));

        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET)) return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR)) return new Chromosome(new AlleleString(true, ""));
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY)) return new Chromosome(new AlleleBoolean(true, false));
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET)) return new Chromosome(new AlleleClass(false, EntityPlayer.class));
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE)) return new Chromosome(new AlleleInteger(true, 0));
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE)) return new Chromosome(new AlleleBoolean(true, true));



        return super.getChromosomeFromGene(gene);
    }
}
