package seremis.geninfusion.soul.standardSoul

import net.minecraft.client.model.ModelSkeleton
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntitySkeleton
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.IChromosome
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.util.render.model.{Model, ModelPart}
import seremis.geninfusion.soul.{Allele, Chromosome}

class StandardSoulSkeleton extends StandardSoul {

    override def getStandardSoulEntity: Class[_ <: EntityLiving] = classOf[EntitySkeleton]


    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene.equals(Genes.GeneBurnsInDaylight))
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneDeathSound))
            return new Chromosome(gene, new Allele(true, "mob.skeleton.death", classOf[String]))
        if(gene.equals(Genes.GeneHurtSound))
            return new Chromosome(gene, new Allele(true, "mob.skeleton.hurt", classOf[String]))
        if(gene.equals(Genes.GeneItemDrops))
            return new Chromosome(gene, new Allele(false, Array(new ItemStack(Items.arrow), new ItemStack(Items.bone)), classOf[Array[ItemStack]]))
        if(gene.equals(Genes.GeneLivingSound))
            return new Chromosome(gene, new Allele(false, "mob.skeleton.say", classOf[String]))
        if(gene.equals(Genes.GenePicksUpItems))
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneRareItemDrops))
            return new Chromosome(gene, new Allele(false, Array(new ItemStack(Items.arrow), new ItemStack(Items.bone)), classOf[Array[ItemStack]]))
        if(gene.equals(Genes.GeneSplashSound))
            return new Chromosome(gene, new Allele(false, "game.hostile.swim.splash", classOf[String]))
        if(gene.equals(Genes.GeneSwimSound))
            return new Chromosome(gene, new Allele(false, "game.hostile.swim", classOf[String]))
        if(gene.equals(Genes.GeneWalkSound))
            return new Chromosome(gene, new Allele(true, "mob.skeleton.step", classOf[String]))
        if(gene.equals(Genes.GeneUseNewAI))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneUseOldAI))
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))

        if(gene.equals(Genes.GeneAIArrowAttack))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAIArrowAttackIndex))
            return new Chromosome(gene, new Allele(true, 4, classOf[Int]))
        if(gene.equals(Genes.GeneAIArrowAttackMaxRangedAttackTime))
            return new Chromosome(gene, new Allele(false, 60, classOf[Int]))
        if(gene.equals(Genes.GeneAIArrowAttackMinRangedAttackTime))
            return new Chromosome(gene, new Allele(false, 20, classOf[Int]))
        if(gene.equals(Genes.GeneAIArrowAttackMoveSpeed))
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))
        if(gene.equals(Genes.GeneAIArrowAttackRangedAttackTimeModifier))
            return new Chromosome(gene, new Allele(false, 15F, classOf[Float]))

        if(gene.equals(Genes.GeneAIAttackOnCollide))
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))

        if(gene.equals(Genes.GeneAISwimming))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAISwimmingIndex))
            return new Chromosome(gene, new Allele(true, 0, classOf[Int]))

        if(gene.equals(Genes.GeneAIRestrictSun))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAIRestrictSunIndex))
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))

        if(gene.equals(Genes.GeneAIFleeSun))
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAIFleeSunIndex))
            return new Chromosome(gene, new Allele(true, 3, classOf[Int]))
        if(gene.equals(Genes.GeneAIFleeSunMoveSpeed))
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))

        if(gene.equals(Genes.GeneAILookIdle))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAILookIdleIndex))
            return new Chromosome(gene, new Allele(false, 6, classOf[Int]))

        if(gene.equals(Genes.GeneAIHurtByTarget))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAIHurtByTargetIndex))
            return new Chromosome(gene, new Allele(true, 1, classOf[Int]))
        if(gene.equals(Genes.GeneAIHurtByTargetCallHelp))
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))

        if(gene.equals(Genes.GeneAINearestAttackableTarget))
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene.equals(Genes.GeneAINearestAttackableTargetIndex))
            return new Chromosome(gene, new Allele(true, Array(2), classOf[Array[Int]]))
        if(gene.equals(Genes.GeneAINearestAttackableTargetEntitySelector))
            return new Chromosome(gene, new Allele(true, Array(""), classOf[Array[String]]))
        if(gene.equals(Genes.GeneAINearestAttackableTargetNearbyOnly))
            return new Chromosome(gene, new Allele(true, Array(false), classOf[Array[Boolean]]))
        if(gene.equals(Genes.GeneAINearestAttackableTargetTarget))
            return new Chromosome(gene, new Allele(false, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))
        if(gene.equals(Genes.GeneAINearestAttackableTargetTargetChance))
            return new Chromosome(gene, new Allele(true, Array(0), classOf[Array[Int]]))
        if(gene.equals(Genes.GeneAINearestAttackableTargetVisible))
            return new Chromosome(gene, new Allele(true, Array(true), classOf[Array[Boolean]]))



        if(gene.equals(Genes.GeneModel))
            return new Chromosome(gene, new Allele(true, model, classOf[Model]))
        if(gene.equals(Genes.GeneTexture))
            return new Chromosome(gene, new Allele(true, textureStringToNBT("textures/entity/skeleton/skeleton.png"), classOf[NBTTagCompound]), new Allele(false, textureStringToNBT("textures/entity/skeleton/skeleton.png"), classOf[NBTTagCompound]))

        super.getChromosomeFromGene(entity, gene)
    }

    val model: Model = modelBiped(new ModelSkeleton(), false)
}
