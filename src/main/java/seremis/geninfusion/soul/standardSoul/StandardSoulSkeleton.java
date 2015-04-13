package seremis.geninfusion.soul.standardSoul;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.*;

public class StandardSoulSkeleton extends StandardSoul {

    @Override
    public boolean isStandardSoulForEntity(EntityLiving entity) {
        //Is Skeleton and not a Wither Skeleton
        return entity instanceof EntitySkeleton && ((EntitySkeleton) entity).getSkeletonType() != 1;
    }

    @Override
    public IChromosome getChromosomeFromGene(EntityLiving entity, String gene) {
        if(gene.equals(Genes.GENE_BURNS_IN_DAYLIGHT))
            return new Chromosome(new AlleleBoolean(false, true));
        if(gene.equals(Genes.GENE_DEATH_SOUND))
            return new Chromosome(new AlleleString(true, "mob.skeleton.death"));
        if(gene.equals(Genes.GENE_HURT_SOUND))
            return new Chromosome(new AlleleString(false, "mob.skeleton.hurt"));
        if(gene.equals(Genes.GENE_ITEM_DROPS))
            return new Chromosome(new AlleleInventory(false, new ItemStack[]{new ItemStack(Items.arrow), new ItemStack(Items.bone)}));
        if(gene.equals(Genes.GENE_LIVING_SOUND))
            return new Chromosome(new AlleleString(false, "mob.skeleton.say"));
        if(gene.equals(Genes.GENE_PICKS_UP_ITEMS))
            return new Chromosome(new AlleleBoolean(false, true));
        if(gene.equals(Genes.GENE_RARE_ITEM_DROPS))
            return new Chromosome(new AlleleInventory(false, new ItemStack[]{new ItemStack(Items.iron_ingot), new ItemStack(Items.carrot), new ItemStack(Items.potato)}));
        if(gene.equals(Genes.GENE_SET_ON_FIRE_FROM_ATTACK))
            return new Chromosome(new AlleleBoolean(false, true));
        if(gene.equals(Genes.GENE_SPLASH_SOUND))
            return new Chromosome(new AlleleString(false, "game.hostile.swim.splash"));
        if(gene.equals(Genes.GENE_SWIM_SOUND))
            return new Chromosome(new AlleleString(false, "game.hostile.swim"));
        if(gene.equals(Genes.GENE_WALK_SOUND))
            return new Chromosome(new AlleleString(true, "mob.skeleton.step"));
        if(gene.equals(Genes.GENE_USE_NEW_AI))
            return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_USE_OLD_AI))
            return new Chromosome(new AlleleBoolean(true, false));

        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK))
            return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_INDEX))
            return new Chromosome(new AlleleInteger(true, 4));
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME))
            return new Chromosome(new AlleleInteger(false, 60));
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME))
            return new Chromosome(new AlleleInteger(false, 20));
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MOVE_SPEED))
            return new Chromosome(new AlleleDouble(false, 1.0D));
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER))
            return new Chromosome(new AlleleFloat(false, 15F));

        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE))
            return new Chromosome(new AlleleBoolean(true, false));

        if(gene.equals(Genes.GENE_AI_SWIMMING))
            return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_SWIMMING_INDEX))
            return new Chromosome(new AlleleInteger(true, 0));

        if(gene.equals(Genes.GENE_AI_RESTRICT_SUN))
            return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_RESTRICT_SUN_INDEX))
            return new Chromosome(new AlleleInteger(true, 2));

        if(gene.equals(Genes.GENE_AI_FLEE_SUN))
            return new Chromosome(new AlleleBoolean(false, true));
        if(gene.equals(Genes.GENE_AI_FLEE_SUN_INDEX))
            return new Chromosome(new AlleleInteger(true, 3));
        if(gene.equals(Genes.GENE_AI_FLEE_SUN_MOVE_SPEED))
            return new Chromosome(new AlleleDouble(false, 1.0D));

        if(gene.equals(Genes.GENE_AI_LOOK_IDLE))
            return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_LOOK_IDLE_INDEX))
            return new Chromosome(new AlleleInteger(false, 6));

        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET))
            return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_INDEX))
            return new Chromosome(new AlleleInteger(true, 1));
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP))
            return new Chromosome(new AlleleBoolean(true, false));

        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET))
            return new Chromosome(new AlleleBoolean(true, true));
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX))
            return new Chromosome(new AlleleIntArray(true, new int[]{2}));
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR))
            return new Chromosome(new AlleleStringArray(true, new String[]{""}));
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY))
            return new Chromosome(new AlleleBooleanArray(true, new boolean[]{false}));
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET))
            return new Chromosome(new AlleleClassArray(false, new Class[]{EntityPlayer.class}));
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE))
            return new Chromosome(new AlleleIntArray(true, new int[]{0}));
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE))
            return new Chromosome(new AlleleBooleanArray(true, new boolean[]{true}));



        if(gene.equals(Genes.GENE_MODEL))
            return new Chromosome(new AlleleModelPartArray(true, ModelPart.getModelPartsFromModel(new ModelSkeleton(), entity)));
        if(gene.equals(Genes.GENE_TEXTURE))
            return new Chromosome(new AlleleString(true, "textures/entity/skeleton/skeleton.png"), new AlleleString(false, "textures/entity/skeleton/skeleton.png"));

        return super.getChromosomeFromGene(entity, gene);
    }
}
