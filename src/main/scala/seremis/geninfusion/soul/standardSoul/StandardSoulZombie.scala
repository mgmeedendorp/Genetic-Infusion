package seremis.geninfusion.soul.standardSoul

import net.minecraft.client.model.ModelZombie
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.entity.passive.EntityVillager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.lib.Genes._
import seremis.geninfusion.api.soul.IChromosome
import seremis.geninfusion.soul.{Allele, Chromosome}

class StandardSoulZombie extends StandardSoul {

    override def getStandardSoulEntity: Class[_ <: EntityLiving] = classOf[EntityZombie]

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene.equals(GeneBurnsInDaylight))
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene.equals(GeneDeathSound))
            return new Chromosome(gene, new Allele(true, "mob.zombie.death", classOf[String]))
        if(gene.equals(GeneHurtSound))
            return new Chromosome(gene, new Allele(false, "mob.zombie.hurt", classOf[String]))
        if(gene.equals(GeneItemDrops))
            return new Chromosome(gene, new Allele(false, Array(new ItemStack(Items.rotten_flesh)), classOf[Array[ItemStack]]))
        if(gene.equals(GeneLivingSound))
            return new Chromosome(gene, new Allele(false, "mob.zombie.say", classOf[String]))
        if(gene.equals(GenePicksUpItems))
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene.equals(GeneRareItemDrops))
            return new Chromosome(gene, new Allele(false, Array(new ItemStack(Items.iron_ingot), new ItemStack(Items.carrot), new ItemStack(Items.potato)), classOf[Array[ItemStack]]))
        if(gene.equals(GeneSetOnFireFromAttack))
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene.equals(GeneSplashSound))
            return new Chromosome(gene, new Allele(false, "game.hostile.swim.splash", classOf[String]))
        if(gene.equals(GeneSwimSound))
            return new Chromosome(gene, new Allele(false, "game.hostile.swim", classOf[String]))
        if(gene.equals(GeneWalkSound))
            return new Chromosome(gene, new Allele(true, "mob.zombie.step", classOf[String]))
        if(gene.equals(GeneUseNewAI))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(GeneUseOldAI))
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))

        if(gene == GeneChildSpeedModifier)
            return new Chromosome(gene, new Allele(false, 0.5D, classOf[Double]))
        if(gene == GeneChildXPModifier)
            return new Chromosome(gene, new Allele(true, 2.5F, classOf[Float]))

        if(gene.equals(GeneAISwimming))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(GeneAISwimmingIndex))
            return new Chromosome(gene, new Allele(true, 0, classOf[Int]))

        if(gene.equals(GeneAIAttackOnCollide))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(GeneAIAttackOnCollideIndex))
            return new Chromosome(gene, new Allele(true, Array(2, 4), classOf[Array[Int]]))
        if(gene.equals(GeneAIAttackOnCollideLongMemory))
            return new Chromosome(gene, new Allele(true, Array(false, true), classOf[Array[Boolean]]))
        if(gene.equals(GeneAIAttackOnCollideMoveSpeed))
            return new Chromosome(gene, new Allele(true, Array(1.0D, 1.0D), classOf[Array[Double]]))
        if(gene.equals(GeneAIAttackOnCollideTarget))
            return new Chromosome(gene, new Allele(true, Array(classOf[EntityPlayer], classOf[EntityVillager]), classOf[Array[Class[_]]]))

        if(gene.equals(GeneAIMoveTowardsRestriction))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(GeneAIMoveTowardsRestrictionIndex))
            return new Chromosome(gene, new Allele(true, 5, classOf[Int]))
        if(gene.equals(GeneAIMoveTowardsRestrictionMoveSpeed))
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))

        if(gene.equals(GeneAIMoveThroughVillage))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(GeneAIMoveThroughVillageIndex))
            return new Chromosome(gene, new Allele(true, 6, classOf[Int]))
        if(gene.equals(GeneAIMoveThroughVillageIsNocturnal))
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene.equals(GeneAIMoveThroughVillageMoveSpeed))
            return new Chromosome(gene, new Allele(true, 1.0D, classOf[Double]))

        if(gene.equals(GeneAIWander))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(GeneAIWanderIndex))
            return new Chromosome(gene, new Allele(true, 7, classOf[Int]))
        if(gene.equals(GeneAIWanderMoveSpeed))
            return new Chromosome(gene, new Allele(true, 1.0D, classOf[Double]))

        if(gene.equals(GeneAIWatchClosest))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(GeneAIWatchClosestIndex))
            return new Chromosome(gene, new Allele(true, Array(8), classOf[Array[Int]]))
        if(gene.equals(GeneAIWatchClosestChance))
            return new Chromosome(gene, new Allele(true, Array(0.02F), classOf[Array[Float]]))
        if(gene.equals(GeneAIWatchClosestRange))
            return new Chromosome(gene, new Allele(true, Array(8.0F), classOf[Array[Float]]))
        if(gene.equals(GeneAIWatchClosestTarget))
            return new Chromosome(gene, new Allele(true, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))

        if(gene.equals(GeneAILookIdle))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(GeneAILookIdleIndex))
            return new Chromosome(gene, new Allele(true, 8, classOf[Int]))

        if(gene.equals(GeneAIHurtByTarget))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(GeneAIHurtByTargetIndex))
            return new Chromosome(gene, new Allele(true, 1, classOf[Int]))
        if(gene.equals(GeneAIHurtByTargetCallHelp))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))

        if(gene.equals(GeneAINearestAttackableTarget))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(GeneAINearestAttackableTargetIndex))
            return new Chromosome(gene, new Allele(true, Array(2, 4), classOf[Array[Int]]))
        if(gene.equals(GeneAINearestAttackableTargetEntitySelector))
            return new Chromosome(gene, new Allele(true, Array("", ""), classOf[Array[String]]))
        if(gene.equals(GeneAINearestAttackableTargetNearbyOnly))
            return new Chromosome(gene, new Allele(true, Array(false, false), classOf[Array[Boolean]]))
        if(gene.equals(GeneAINearestAttackableTargetTarget))
            return new Chromosome(gene, new Allele(false, Array(classOf[EntityPlayer], classOf[EntityVillager]), classOf[Array[Class[_]]]))
        if(gene.equals(GeneAINearestAttackableTargetTargetChance))
            return new Chromosome(gene, new Allele(true, Array(0, 0), classOf[Array[Int]]))
        if(gene.equals(GeneAINearestAttackableTargetVisible))
            return new Chromosome(gene, new Allele(true, Array(true, false), classOf[Array[Boolean]]))

        if(gene.equals(GeneModelAdult))
            return new Chromosome(gene, new Allele(true, model, classOf[Model]))
        if(gene.equals(GeneTexture))
            return new Chromosome(gene, new Allele(true, textureStringToNBT("textures/entity/zombie/zombie.png"), classOf[NBTTagCompound]), new Allele(false, textureStringToNBT("textures/entity/zombie/zombie.png"), classOf[NBTTagCompound]))


        super.getChromosomeFromGene(entity, gene)
    }

    val model: Model = {
        val modelZombie = new ModelZombie()

        modelZombie.bipedRightArm.rotateAngleX = -1.57F
        modelZombie.bipedLeftArm.rotateAngleX = -1.57F

        modelBiped(modelZombie, true)
    }
}
