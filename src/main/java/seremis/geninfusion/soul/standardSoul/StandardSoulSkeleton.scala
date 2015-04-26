package seremis.geninfusion.soul.standardSoul

import net.minecraft.client.model.ModelSkeleton
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntitySkeleton
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import seremis.geninfusion.api.soul.{EnumAlleleType, IChromosome}
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.soul.Chromosome
import seremis.geninfusion.soul.Allele

class StandardSoulSkeleton extends StandardSoul {

    override def isStandardSoulForEntity(entity: EntityLiving): Boolean = {
        entity.isInstanceOf[EntitySkeleton] && entity.asInstanceOf[EntitySkeleton].getSkeletonType != 1
    }

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene.equals(Genes.GENE_BURNS_IN_DAYLIGHT))
            return new Chromosome(new Allele(false, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_DEATH_SOUND))
            return new Chromosome(new Allele(true, "mob.skeleton.death", EnumAlleleType.STRING))
        if(gene.equals(Genes.GENE_HURT_SOUND))
            return new Chromosome(new Allele(false, "mob.skeleton.hurt", EnumAlleleType.STRING))
        if(gene.equals(Genes.GENE_ITEM_DROPS))
            return new Chromosome(new Allele(false, Array(new ItemStack(Items.arrow), new ItemStack(Items.bone)), EnumAlleleType.ITEMSTACK_ARRAY))
        if(gene.equals(Genes.GENE_LIVING_SOUND))
            return new Chromosome(new Allele(false, "mob.skeleton.say", EnumAlleleType.STRING))
        if(gene.equals(Genes.GENE_PICKS_UP_ITEMS))
            return new Chromosome(new Allele(false, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_RARE_ITEM_DROPS))
            return new Chromosome(new Allele(false, Array(new ItemStack(Items.arrow), new ItemStack(Items.bone)), EnumAlleleType.ITEMSTACK_ARRAY))
        if(gene.equals(Genes.GENE_SPLASH_SOUND))
            return new Chromosome(new Allele(false, "game.hostile.swim.splash", EnumAlleleType.STRING))
        if(gene.equals(Genes.GENE_SWIM_SOUND))
            return new Chromosome(new Allele(false, "game.hostile.swim", EnumAlleleType.STRING))
        if(gene.equals(Genes.GENE_WALK_SOUND))
            return new Chromosome(new Allele(true, "mob.skeleton.step", EnumAlleleType.STRING))
        if(gene.equals(Genes.GENE_USE_NEW_AI))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_USE_OLD_AI))
            return new Chromosome(new Allele(true, false, EnumAlleleType.BOOLEAN))

        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_INDEX))
            return new Chromosome(new Allele(true, 4, EnumAlleleType.INTEGER))
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME))
            return new Chromosome(new Allele(false, 60, EnumAlleleType.INTEGER))
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME))
            return new Chromosome(new Allele(false, 20, EnumAlleleType.INTEGER))
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MOVE_SPEED))
            return new Chromosome(new Allele(false, 1.0D, EnumAlleleType.DOUBLE))
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER))
            return new Chromosome(new Allele(false, 15F, EnumAlleleType.FLOAT))

        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE))
            return new Chromosome(new Allele(true, false, EnumAlleleType.BOOLEAN))

        if(gene.equals(Genes.GENE_AI_SWIMMING))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_SWIMMING_INDEX))
            return new Chromosome(new Allele(true, 0, EnumAlleleType.INTEGER))

        if(gene.equals(Genes.GENE_AI_RESTRICT_SUN))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_RESTRICT_SUN_INDEX))
            return new Chromosome(new Allele(true, 2, EnumAlleleType.INTEGER))

        if(gene.equals(Genes.GENE_AI_FLEE_SUN))
            return new Chromosome(new Allele(false, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_FLEE_SUN_INDEX))
            return new Chromosome(new Allele(true, 3, EnumAlleleType.INTEGER))
        if(gene.equals(Genes.GENE_AI_FLEE_SUN_MOVE_SPEED))
            return new Chromosome(new Allele(false, 1.0D, EnumAlleleType.DOUBLE))

        if(gene.equals(Genes.GENE_AI_LOOK_IDLE))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_LOOK_IDLE_INDEX))
            return new Chromosome(new Allele(false, 6, EnumAlleleType.INTEGER))

        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_INDEX))
            return new Chromosome(new Allele(true, 1, EnumAlleleType.INTEGER))
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP))
            return new Chromosome(new Allele(true, false, EnumAlleleType.BOOLEAN))

        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX))
            return new Chromosome(new Allele(true, Array(2), EnumAlleleType.INTEGER_ARRAY))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR))
            return new Chromosome(new Allele(true, Array(""), EnumAlleleType.STRING_ARRAY))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY))
            return new Chromosome(new Allele(true, Array(false), EnumAlleleType.BOOLEAN_ARRAY))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET))
            return new Chromosome(new Allele(false, Array(classOf[EntityPlayer]), EnumAlleleType.CLASS_ARRAY))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE))
            return new Chromosome(new Allele(true, Array(0), EnumAlleleType.INTEGER_ARRAY))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE))
            return new Chromosome(new Allele(true, Array(true), EnumAlleleType.BOOLEAN_ARRAY))



        if(gene.equals(Genes.GENE_MODEL))
            return new Chromosome(new Allele(true, ModelPart.getModelPartsFromModel(new ModelSkeleton(), entity), EnumAlleleType.MODELPART_ARRAY))
        if(gene.equals(Genes.GENE_TEXTURE))
            return new Chromosome(new Allele(true, "textures/entity/skeleton/skeleton.png", EnumAlleleType.STRING), new Allele(false, "textures/entity/skeleton/skeleton.png", EnumAlleleType.STRING))

        super.getChromosomeFromGene(entity, gene)
    }
}
