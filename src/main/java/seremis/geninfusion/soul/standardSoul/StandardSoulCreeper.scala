package seremis.geninfusion.soul.standardSoul

import net.minecraft.client.model.ModelCreeper
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.{EntityCreeper, EntitySkeleton}
import net.minecraft.entity.passive.EntityOcelot
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import seremis.geninfusion.api.soul.IChromosome
import seremis.geninfusion.api.soul.lib.Genes._
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.soul.{Allele, Chromosome}

class StandardSoulCreeper extends StandardSoul {

    override def isStandardSoulForEntity(entity: EntityLiving): Boolean = {
        entity.isInstanceOf[EntityCreeper]
    }

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene == GENE_DEATH_SOUND)
            return new Chromosome(new Allele(true, "mob.creeper.death", classOf[String]))
        if(gene == GENE_HURT_SOUND)
            return new Chromosome(new Allele(true, "mob.creeper.say", classOf[String]))
        if(gene == GENE_ITEM_DROPS)
            return new Chromosome(new Allele(true, Array(new ItemStack(Items.gunpowder)), classOf[Array[ItemStack]]))
        if(gene == GENE_PICKS_UP_ITEMS)
            return new Chromosome(new Allele(true, false, classOf[Boolean]))
        if(gene == GENE_RARE_ITEM_DROP_CHANCES)
            return new Chromosome(new Allele(true, Array(0.0F), classOf[Array[Float]]))
        if(gene == GENE_RARE_ITEM_DROPS)
            return new Chromosome(new Allele(true, null, classOf[Array[ItemStack]]))
        if(gene == GENE_SPLASH_SOUND)
            return new Chromosome(new Allele(true, "game.hostile.swim.splash", classOf[String]))
        if(gene == GENE_SWIM_SOUND)
            return new Chromosome(new Allele(true, "game.hostile.swim", classOf[String]))
        if(gene == GENE_USE_NEW_AI)
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene == GENE_USE_OLD_AI)
            return new Chromosome(new Allele(true, false, classOf[Boolean]))
        if(gene == GENE_CAN_BE_CHARGED)
            return new Chromosome(new Allele(false, true, classOf[Boolean]))

        //AI
        if(gene == GENE_AI_SWIMMING)
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene == GENE_AI_SWIMMING_INDEX)
            return new Chromosome(new Allele(false, 1, classOf[Int]))

        if(gene == GENE_AI_CREEPER_SWELL)
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene == GENE_AI_CREEPER_SWELL_INDEX)
            return new Chromosome(new Allele(true, 2, classOf[Int]))

        if(gene == GENE_AI_AVOID_ENTITY)
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene == GENE_AI_AVOID_ENTITY_TARGET)
            return new Chromosome(new Allele(false, Array(classOf[EntityOcelot]), classOf[Array[Class[_]]]))

        if(gene == GENE_AI_ATTACK_ON_COLLIDE)
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene == GENE_AI_ATTACK_ON_COLLIDE_INDEX)
            return new Chromosome(new Allele(false, Array(4), classOf[Array[Int]]))
        if(gene == GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY)
            return new Chromosome(new Allele(false, Array(false), classOf[Array[Boolean]]))
        if(gene == GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED)
            return new Chromosome(new Allele(false, Array(1.0D), classOf[Array[Double]]))
        if(gene == GENE_AI_ATTACK_ON_COLLIDE_TARGET)
            return new Chromosome(new Allele(false, null, classOf[Array[Class[_]]]))

        if(gene == GENE_AI_WANDER)
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene == GENE_AI_WANDER_INDEX)
            return new Chromosome(new Allele(true, 5, classOf[Int]))
        if(gene == GENE_AI_WANDER_MOVE_SPEED)
            return new Chromosome(new Allele(false, 0.8D, classOf[Double]))

        if(gene == GENE_AI_WATCH_CLOSEST)
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene == GENE_AI_WATCH_CLOSEST_INDEX)
            return new Chromosome(new Allele(true, Array(6), classOf[Array[Int]]))
        if(gene == GENE_AI_WATCH_CLOSEST_CHANCE)
            return new Chromosome(new Allele(true, Array(0.02F), classOf[Array[Float]]))
        if(gene == GENE_AI_WATCH_CLOSEST_RANGE)
            return new Chromosome(new Allele(true, Array(8.0F), classOf[Array[Float]]))
        if(gene == GENE_AI_WATCH_CLOSEST_TARGET)
            return new Chromosome(new Allele(true, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))

        if(gene == GENE_AI_LOOK_IDLE)
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene == GENE_AI_LOOK_IDLE_INDEX)
            return new Chromosome(new Allele(true, 6, classOf[Int]))

        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET)
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX)
            return new Chromosome(new Allele(false, Array(1), classOf[Array[Int]]))
        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR)
            return new Chromosome(new Allele(true, Array(""), classOf[Array[String]]))
        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY)
            return new Chromosome(new Allele(true, Array(false), classOf[Array[Boolean]]))
        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET)
            return new Chromosome(new Allele(false, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))
        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE)
            return new Chromosome(new Allele(true, Array(0), classOf[Array[Int]]))
        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE)
            return new Chromosome(new Allele(true, Array(true), classOf[Array[Boolean]]))

        if(gene == GENE_AI_HURT_BY_TARGET)
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene == GENE_AI_HURT_BY_TARGET_INDEX)
            return new Chromosome(new Allele(true, 2, classOf[Int]))
        if(gene == GENE_AI_HURT_BY_TARGET_CALL_HELP)
            return new Chromosome(new Allele(true, false, classOf[Boolean]))

        //Rendering related Genes.
        if(gene == GENE_MODEL)
            return new Chromosome(new Allele(true, ModelPart.getModelPartsFromModel(new ModelCreeper(), entity), classOf[Array[ModelPart]]))
        if(gene == GENE_TEXTURE)
            return new Chromosome(new Allele(true, "textures/entity/creeper/creeper.png", classOf[String]), new Allele(false, "textures/entity/creeper/creeper.png", classOf[String]))

        if(gene == GENE_DROPS_ITEM_WHEN_KILLED_BY_SPECIFIC_ENTITY)
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene == GENE_KILLED_BY_SPECIFIC_ENTITY_DROPS)
            return new Chromosome(new Allele(true, Array(Items.record_11, Items.record_13, Items.record_blocks, Items.record_cat, Items.record_chirp, Items.record_far, Items.record_mall, Items.record_mellohi, Items.record_stal, Items.record_strad, Items.record_wait, Items.record_ward).map(b => new ItemStack(b)), classOf[Array[ItemStack]]))
        if(gene == GENE_KILLED_BY_SPECIFIC_ENTITY_ENTITY)
            return new Chromosome(new Allele(true, classOf[EntitySkeleton], classOf[Class[_]]))

        super.getChromosomeFromGene(entity, gene)
    }
}
