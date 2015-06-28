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
import seremis.geninfusion.api.util.render.model.ModelPart
import seremis.geninfusion.soul.{Allele, Chromosome}

class StandardSoulZombie extends StandardSoul {

    override def isStandardSoulForEntity(entity: EntityLiving): Boolean = entity.isInstanceOf[EntityZombie]

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene.equals(Genes.GeneBurnsInDaylight))
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneDeathSound))
            return new Chromosome(new Allele(true, "mob.zombie.death", classOf[String]))
        if(gene.equals(Genes.GeneHurtSound))
            return new Chromosome(new Allele(false, "mob.zombie.hurt", classOf[String]))
        if(gene.equals(Genes.GeneItemDrops))
            return new Chromosome(new Allele(false, Array(new ItemStack(Items.rotten_flesh)), classOf[Array[ItemStack]]))
        if(gene.equals(Genes.GeneLivingSound))
            return new Chromosome(new Allele(false, "mob.zombie.say", classOf[String]))
        if(gene.equals(Genes.GenePicksUpItems))
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneRareItemDrops))
            return new Chromosome(new Allele(false, Array(new ItemStack(Items.iron_ingot), new ItemStack(Items.carrot), new ItemStack(Items.potato)), classOf[Array[ItemStack]]))
        if(gene.equals(Genes.GeneSetOnFireFromAttack))
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneSplashSound))
            return new Chromosome(new Allele(false, "game.hostile.swim.splash", classOf[String]))
        if(gene.equals(Genes.GeneSwimSound))
            return new Chromosome(new Allele(false, "game.hostile.swim", classOf[String]))
        if(gene.equals(Genes.GeneWalkSound))
            return new Chromosome(new Allele(true, "mob.zombie.step", classOf[String]))
        if(gene.equals(Genes.GeneUseNewAI))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneUseOldAI))
            return new Chromosome(new Allele(true, false, classOf[Boolean]))

        if(gene.equals(Genes.GeneAISwimming))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAISwimmingIndex))
            return new Chromosome(new Allele(true, 0, classOf[Int]))

        if(gene.equals(Genes.GeneAIAttackOnCollide))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAIAttackOnCollideIndex))
            return new Chromosome(new Allele(true, Array(2, 4), classOf[Array[Int]]))
        if(gene.equals(Genes.GeneAIAttackOnCollideLongMemory))
            return new Chromosome(new Allele(true, Array(false, true), classOf[Array[Boolean]]))
        if(gene.equals(Genes.GeneAIAttackOnCollideMoveSpeed))
            return new Chromosome(new Allele(true, Array(1.0D, 1.0D), classOf[Array[Double]]))
        if(gene.equals(Genes.GeneAIAttackOnCollideTarget))
            return new Chromosome(new Allele(true, Array(classOf[EntityPlayer], classOf[EntityVillager]), classOf[Array[Class[_]]]))

        if(gene.equals(Genes.GeneAIMoveTowardsRestriction))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAIMoveTowardsRestrictionIndex))
            return new Chromosome(new Allele(true, 5, classOf[Int]))
        if(gene.equals(Genes.GeneAIMoveTowardsRestrictionMoveSpeed))
            return new Chromosome(new Allele(false, 1.0D, classOf[Double]))

        if(gene.equals(Genes.GeneAIMoveThroughVillage))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAIMoveThroughVillageIndex))
            return new Chromosome(new Allele(true, 6, classOf[Int]))
        if(gene.equals(Genes.GeneAIMoveThroughVillageIsNocturnal))
            return new Chromosome(new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAIMoveThroughVillageMoveSpeed))
            return new Chromosome(new Allele(true, 1.0D, classOf[Double]))

        if(gene.equals(Genes.GeneAIWander))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAIWanderIndex))
            return new Chromosome(new Allele(true, 7, classOf[Int]))
        if(gene.equals(Genes.GeneAIWanderMoveSpeed))
            return new Chromosome(new Allele(true, 1.0D, classOf[Double]))

        if(gene.equals(Genes.GeneAIWatchClosest))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAIWatchClosestIndex))
            return new Chromosome(new Allele(true, Array(8), classOf[Array[Int]]))
        if(gene.equals(Genes.GeneAIWatchClosestChance))
            return new Chromosome(new Allele(true, Array(0.02F), classOf[Array[Float]]))
        if(gene.equals(Genes.GeneAIWatchClosestRange))
            return new Chromosome(new Allele(true, Array(8.0F), classOf[Array[Float]]))
        if(gene.equals(Genes.GeneAIWatchClosestTarget))
            return new Chromosome(new Allele(true, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))

        if(gene.equals(Genes.GeneAILookIdle))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAILookIdleIndex))
            return new Chromosome(new Allele(true, 8, classOf[Int]))

        if(gene.equals(Genes.GeneAIHurtByTarget))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAIHurtByTargetIndex))
            return new Chromosome(new Allele(true, 1, classOf[Int]))
        if(gene.equals(Genes.GeneAIHurtByTargetCallHelp))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))

        if(gene.equals(Genes.GeneAINearestAttackableTarget))
            return new Chromosome(new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAINearestAttackableTargetIndex))
            return new Chromosome(new Allele(true, Array(2, 4), classOf[Array[Int]]))
        if(gene.equals(Genes.GeneAINearestAttackableTargetEntitySelector))
            return new Chromosome(new Allele(true, Array("", ""), classOf[Array[String]]))
        if(gene.equals(Genes.GeneAINearestAttackableTargetNearbyOnly))
            return new Chromosome(new Allele(true, Array(false, false), classOf[Array[Boolean]]))
        if(gene.equals(Genes.GeneAINearestAttackableTargetTarget))
            return new Chromosome(new Allele(false, Array(classOf[EntityPlayer], classOf[EntityVillager]), classOf[Array[Class[_]]]))
        if(gene.equals(Genes.GeneAINearestAttackableTargetTargetChance))
            return new Chromosome(new Allele(true, Array(0, 0), classOf[Array[Int]]))
        if(gene.equals(Genes.GeneAINearestAttackableTargetVisible))
            return new Chromosome(new Allele(true, Array(true, false), classOf[Array[Boolean]]))

        if(gene.equals(Genes.GeneModel))
            return new Chromosome(new Allele(true, ModelPart.getModelPartsFromModel(new ModelZombie(), entity), classOf[Array[ModelPart]]))
        if(gene.equals(Genes.GeneTexture))
            return new Chromosome(new Allele(true, "textures/entity/zombie/zombie.png", classOf[String]), new Allele(false, "textures/entity/zombie/zombie.png", classOf[String]))


        super.getChromosomeFromGene(entity, gene)
    }
}
