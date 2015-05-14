package seremis.geninfusion.soul.standardSoul

import net.minecraft.client.model.ModelSkeleton
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntitySkeleton
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import seremis.geninfusion.api.soul.IChromosome
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.soul.{Allele, Chromosome}

class StandardSoulSkeleton extends StandardSoul {

    override def isStandardSoulForEntity(entity: EntityLiving): Boolean = {
        entity.isInstanceOf[EntitySkeleton] && entity.asInstanceOf[EntitySkeleton].getSkeletonType != 1
    }

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene.equals(Genes.GENE_BURNS_IN_DAYLIGHT))
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_DEATH_SOUND))
            return new Chromosome(new Allele(true, "mob.skeleton.death", classOf[String]))
        if(gene.equals(Genes.GENE_HURT_SOUND))
            return new Chromosome(new Allele(false, "mob.skeleton.hurt", classOf[String]))
        if(gene.equals(Genes.GENE_ITEM_DROPS))
            return new Chromosome(new Allele(false, Array(new ItemStack(Items.arrow), new ItemStack(Items.bone)), classOf[Array[ItemStack]]))
        if(gene.equals(Genes.GENE_LIVING_SOUND))
            return new Chromosome(new Allele(false, "mob.skeleton.say", classOf[String]))
        if(gene.equals(Genes.GENE_PICKS_UP_ITEMS))
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_RARE_ITEM_DROPS))
            return new Chromosome(new Allele(false, Array(new ItemStack(Items.arrow), new ItemStack(Items.bone)), classOf[Array[ItemStack]]))
        if(gene.equals(Genes.GENE_SPLASH_SOUND))
            return new Chromosome(new Allele(false, "game.hostile.swim.splash", classOf[String]))
        if(gene.equals(Genes.GENE_SWIM_SOUND))
            return new Chromosome(new Allele(false, "game.hostile.swim", classOf[String]))
        if(gene.equals(Genes.GENE_WALK_SOUND))
            return new Chromosome(new Allele(true, "mob.skeleton.step", classOf[String]))
        if(gene.equals(Genes.GENE_USE_NEW_AI))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_USE_OLD_AI))
            return new Chromosome(new Allele(true, false, classOf[Boolean]))

        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_INDEX))
            return new Chromosome(new Allele(true, 4, classOf[Int]))
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME))
            return new Chromosome(new Allele(false, 60, classOf[Int]))
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME))
            return new Chromosome(new Allele(false, 20, classOf[Int]))
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MOVE_SPEED))
            return new Chromosome(new Allele(false, 1.0D, classOf[Double]))
        if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER))
            return new Chromosome(new Allele(false, 15F, classOf[Float]))

        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE))
            return new Chromosome(new Allele(true, false, classOf[Boolean]))

        if(gene.equals(Genes.GENE_AI_SWIMMING))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_SWIMMING_INDEX))
            return new Chromosome(new Allele(true, 0, classOf[Int]))

        if(gene.equals(Genes.GENE_AI_RESTRICT_SUN))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_RESTRICT_SUN_INDEX))
            return new Chromosome(new Allele(true, 2, classOf[Int]))

        if(gene.equals(Genes.GENE_AI_FLEE_SUN))
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_FLEE_SUN_INDEX))
            return new Chromosome(new Allele(true, 3, classOf[Int]))
        if(gene.equals(Genes.GENE_AI_FLEE_SUN_MOVE_SPEED))
            return new Chromosome(new Allele(false, 1.0D, classOf[Double]))

        if(gene.equals(Genes.GENE_AI_LOOK_IDLE))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_LOOK_IDLE_INDEX))
            return new Chromosome(new Allele(false, 6, classOf[Int]))

        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_INDEX))
            return new Chromosome(new Allele(true, 1, classOf[Int]))
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP))
            return new Chromosome(new Allele(true, false, classOf[Boolean]))

        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX))
            return new Chromosome(new Allele(true, Array(2), classOf[Array[Int]]))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR))
            return new Chromosome(new Allele(true, Array(""), classOf[Array[String]]))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY))
            return new Chromosome(new Allele(true, Array(false), classOf[Array[Boolean]]))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET))
            return new Chromosome(new Allele(false, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE))
            return new Chromosome(new Allele(true, Array(0), classOf[Array[Int]]))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE))
            return new Chromosome(new Allele(true, Array(true), classOf[Array[Boolean]]))



        if(gene.equals(Genes.GENE_MODEL))
            return new Chromosome(new Allele(true, ModelPart.getModelPartsFromModel(new ModelSkeleton(), entity), classOf[Array[ModelPart]]))
        if(gene.equals(Genes.GENE_TEXTURE))
            return new Chromosome(new Allele(true, "textures/entity/skeleton/skeleton.png", classOf[String]), new Allele(false, "textures/entity/skeleton/skeleton.png", classOf[String]))

        super.getChromosomeFromGene(entity, gene)
    }
}
