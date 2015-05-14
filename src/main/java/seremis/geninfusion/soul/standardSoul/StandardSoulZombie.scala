package seremis.geninfusion.soul.standardSoul

import net.minecraft.client.model.ModelZombie
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.entity.passive.EntityVillager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import seremis.geninfusion.api.soul.IChromosome
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.soul.{Allele, Chromosome}

class StandardSoulZombie extends StandardSoul {

    override def isStandardSoulForEntity(entity: EntityLiving): Boolean = entity.isInstanceOf[EntityZombie]

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene.equals(Genes.GENE_BURNS_IN_DAYLIGHT))
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_DEATH_SOUND))
            return new Chromosome(new Allele(true, "mob.zombie.death", classOf[String]))
        if(gene.equals(Genes.GENE_HURT_SOUND))
            return new Chromosome(new Allele(false, "mob.zombie.hurt", classOf[String]))
        if(gene.equals(Genes.GENE_ITEM_DROPS))
            return new Chromosome(new Allele(false, Array(new ItemStack(Items.rotten_flesh)), classOf[Array[ItemStack]]))
        if(gene.equals(Genes.GENE_LIVING_SOUND))
            return new Chromosome(new Allele(false, "mob.zombie.say", classOf[String]))
        if(gene.equals(Genes.GENE_PICKS_UP_ITEMS))
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_RARE_ITEM_DROPS))
            return new Chromosome(new Allele(false, Array(new ItemStack(Items.iron_ingot), new ItemStack(Items.carrot), new ItemStack(Items.potato)), classOf[Array[ItemStack]]))
        if(gene.equals(Genes.GENE_SET_ON_FIRE_FROM_ATTACK))
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_SPLASH_SOUND))
            return new Chromosome(new Allele(false, "game.hostile.swim.splash", classOf[String]))
        if(gene.equals(Genes.GENE_SWIM_SOUND))
            return new Chromosome(new Allele(false, "game.hostile.swim", classOf[String]))
        if(gene.equals(Genes.GENE_WALK_SOUND))
            return new Chromosome(new Allele(true, "mob.zombie.step", classOf[String]))
        if(gene.equals(Genes.GENE_USE_NEW_AI))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_USE_OLD_AI))
            return new Chromosome(new Allele(true, false, classOf[Boolean]))

        if(gene.equals(Genes.GENE_AI_SWIMMING))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_SWIMMING_INDEX))
            return new Chromosome(new Allele(true, 0, classOf[Int]))

        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_INDEX))
            return new Chromosome(new Allele(true, Array(2, 4), classOf[Array[Int]]))
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY))
            return new Chromosome(new Allele(true, Array(false, true), classOf[Array[Boolean]]))
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED))
            return new Chromosome(new Allele(true, Array(1.0D, 1.0D), classOf[Array[Double]]))
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_TARGET))
            return new Chromosome(new Allele(true, Array(classOf[EntityPlayer], classOf[EntityVillager]), classOf[Array[Class[_]]]))

        if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_INDEX))
            return new Chromosome(new Allele(true, 5, classOf[Int]))
        if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED))
            return new Chromosome(new Allele(false, 1.0D, classOf[Double]))

        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_INDEX))
            return new Chromosome(new Allele(true, 6, classOf[Int]))
        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL))
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED))
            return new Chromosome(new Allele(true, 1.0D, classOf[Double]))

        if(gene.equals(Genes.GENE_AI_WANDER))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_WANDER_INDEX))
            return new Chromosome(new Allele(true, 7, classOf[Int]))
        if(gene.equals(Genes.GENE_AI_WANDER_MOVE_SPEED))
            return new Chromosome(new Allele(true, 1.0D, classOf[Double]))

        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_INDEX))
            return new Chromosome(new Allele(true, Array(8), classOf[Array[Int]]))
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_CHANCE))
            return new Chromosome(new Allele(true, Array(0.02F), classOf[Array[Float]]))
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_RANGE))
            return new Chromosome(new Allele(true, Array(8.0F), classOf[Array[Float]]))
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_TARGET))
            return new Chromosome(new Allele(true, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))

        if(gene.equals(Genes.GENE_AI_LOOK_IDLE))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_LOOK_IDLE_INDEX))
            return new Chromosome(new Allele(true, 8, classOf[Int]))

        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_INDEX))
            return new Chromosome(new Allele(true, 1, classOf[Int]))
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))

        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX))
            return new Chromosome(new Allele(true, Array(2, 4), classOf[Array[Int]]))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR))
            return new Chromosome(new Allele(true, Array("", ""), classOf[Array[String]]))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY))
            return new Chromosome(new Allele(true, Array(false, false), classOf[Array[Boolean]]))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET))
            return new Chromosome(new Allele(false, Array(classOf[EntityPlayer], classOf[EntityVillager]), classOf[Array[Class[_]]]))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE))
            return new Chromosome(new Allele(true, Array(0, 0), classOf[Array[Int]]))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE))
            return new Chromosome(new Allele(true, Array(true, false), classOf[Array[Boolean]]))

        if(gene.equals(Genes.GENE_MODEL))
            return new Chromosome(new Allele(true, ModelPart.getModelPartsFromModel(new ModelZombie(), entity), classOf[Array[ModelPart]]))
        if(gene.equals(Genes.GENE_TEXTURE))
            return new Chromosome(new Allele(true, "textures/entity/zombie/zombie.png", classOf[String]), new Allele(false, "textures/entity/zombie/zombie.png", classOf[String]))


        super.getChromosomeFromGene(entity, gene)
    }
}
