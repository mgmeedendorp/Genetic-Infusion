package seremis.geninfusion.soul.standardSoul

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.{EntityCreeper, EntitySkeleton}
import net.minecraft.entity.passive.EntityOcelot
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import seremis.geninfusion.api.lib.Genes._
import seremis.geninfusion.api.lib.Models
import seremis.geninfusion.api.render.Model
import seremis.geninfusion.api.soul.IChromosome
import seremis.geninfusion.soul.{Allele, Chromosome}

class StandardSoulCreeper extends StandardSoul {

    override def getStandardSoulEntity: Class[_ <: EntityLiving] = classOf[EntityCreeper]

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene == GeneDeathSound)
            return new Chromosome(gene, new Allele(true, "mob.creeper.death", classOf[String]))
        if(gene == GeneHurtSound)
            return new Chromosome(gene, new Allele(true, "mob.creeper.say", classOf[String]))
        if(gene == GeneItemDrops)
            return new Chromosome(gene, new Allele(true, Array(new ItemStack(Items.gunpowder)), classOf[Array[ItemStack]]))
        if(gene == GenePicksUpItems)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneRareItemDropChances)
            return new Chromosome(gene, new Allele(true, Array(0.0F), classOf[Array[Float]]))
        if(gene == GeneRareItemDrops)
            return new Chromosome(gene, new Allele(true, null, classOf[Array[ItemStack]]))
        if(gene == GeneSplashSound)
            return new Chromosome(gene, new Allele(true, "game.hostile.swim.splash", classOf[String]))
        if(gene == GeneSwimSound)
            return new Chromosome(gene, new Allele(true, "game.hostile.swim", classOf[String]))
        if(gene == GeneUseNewAI)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneUseOldAI)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneCanBeCharged)
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))

        //AI
        if(gene == GeneAISwimming)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAISwimmingIndex)
            return new Chromosome(gene, new Allele(false, 1, classOf[Int]))

        if(gene == GeneAICreeperSwell)
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene == GeneAICreeperSwellIndex)
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))

        if(gene == GeneAIAvoidEntity)
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene == GeneAIAvoidEntityTarget)
            return new Chromosome(gene, new Allele(false, Array(classOf[EntityOcelot]), classOf[Array[Class[_]]]))

        if(gene == GeneAIAttackOnCollide)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAIAttackOnCollideIndex)
            return new Chromosome(gene, new Allele(false, Array(4), classOf[Array[Int]]))
        if(gene == GeneAIAttackOnCollideLongMemory)
            return new Chromosome(gene, new Allele(false, Array(false), classOf[Array[Boolean]]))
        if(gene == GeneAIAttackOnCollideMoveSpeed)
            return new Chromosome(gene, new Allele(false, Array(1.0D), classOf[Array[Double]]))
        if(gene == GeneAIAttackOnCollideTarget)
            return new Chromosome(gene, new Allele(false, null, classOf[Array[Class[_]]]))

        if(gene == GeneAIWander)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAIWanderIndex)
            return new Chromosome(gene, new Allele(true, 5, classOf[Int]))
        if(gene == GeneAIWanderMoveSpeed)
            return new Chromosome(gene, new Allele(false, 0.8D, classOf[Double]))

        if(gene == GeneAIWatchClosest)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAIWatchClosestIndex)
            return new Chromosome(gene, new Allele(true, Array(6), classOf[Array[Int]]))
        if(gene == GeneAIWatchClosestChance)
            return new Chromosome(gene, new Allele(true, Array(0.02F), classOf[Array[Float]]))
        if(gene == GeneAIWatchClosestRange)
            return new Chromosome(gene, new Allele(true, Array(8.0F), classOf[Array[Float]]))
        if(gene == GeneAIWatchClosestTarget)
            return new Chromosome(gene, new Allele(true, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))

        if(gene == GeneAILookIdle)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAILookIdleIndex)
            return new Chromosome(gene, new Allele(true, 6, classOf[Int]))

        if(gene == GeneAINearestAttackableTarget)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAINearestAttackableTargetIndex)
            return new Chromosome(gene, new Allele(false, Array(1), classOf[Array[Int]]))
        if(gene == GeneAINearestAttackableTargetEntitySelector)
            return new Chromosome(gene, new Allele(true, Array(""), classOf[Array[String]]))
        if(gene == GeneAINearestAttackableTargetNearbyOnly)
            return new Chromosome(gene, new Allele(true, Array(false), classOf[Array[Boolean]]))
        if(gene == GeneAINearestAttackableTargetTarget)
            return new Chromosome(gene, new Allele(false, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))
        if(gene == GeneAINearestAttackableTargetTargetChance)
            return new Chromosome(gene, new Allele(true, Array(0), classOf[Array[Int]]))
        if(gene == GeneAINearestAttackableTargetVisible)
            return new Chromosome(gene, new Allele(true, Array(true), classOf[Array[Boolean]]))

        if(gene == GeneAIHurtByTarget)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAIHurtByTargetIndex)
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))
        if(gene == GeneAIHurtByTargetCallHelp)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))

        //Rendering related Genes.
        if(gene == GeneModelAdult)
            return new Chromosome(gene, new Allele(true, Models.creeper, classOf[Model]))

        if(gene == GeneDropsItemWhenKilledBySpecificEntity)
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene == GeneKilledBySpecificEntityDrops)
            return new Chromosome(gene, new Allele(true, Array(Items.record_11, Items.record_13, Items.record_blocks, Items.record_cat, Items.record_chirp, Items.record_far, Items.record_mall, Items.record_mellohi, Items.record_stal, Items.record_strad, Items.record_wait, Items.record_ward).map(b => new ItemStack(b)), classOf[Array[ItemStack]]))
        if(gene == GeneKilledBySpecificEntityEntity)
            return new Chromosome(gene, new Allele(true, classOf[EntitySkeleton], classOf[Class[_]]))

        super.getChromosomeFromGene(entity, gene)
    }
}
