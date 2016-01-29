package seremis.geninfusion.soul.standardSoul

import net.minecraft.client.model.ModelSpider
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntitySpider
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.lib.AttachmentPoints
import seremis.geninfusion.api.lib.CuboidTypes.{General, Spider}
import seremis.geninfusion.api.lib.Genes._
import seremis.geninfusion.api.soul.IChromosome
import seremis.geninfusion.soul.{Allele, Chromosome}

class StandardSoulSpider extends StandardSoul {

    override def getStandardSoulEntity: Class[_ <: EntityLiving] = classOf[EntitySpider]

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene == GeneDeathSound)
            return new Chromosome(gene, new Allele(false, "mob.spider.death", classOf[String]))
        if(gene == GeneHurtSound)
            return new Chromosome(gene, new Allele(true, "mob.spider.say", classOf[String]))
        if(gene == GeneItemDrops)
            return new Chromosome(gene, new Allele(true, Array(new ItemStack(Items.string)), classOf[Array[ItemStack]]))
        if(gene == GeneLivingSound)
            return new Chromosome(gene, new Allele(false, "mob.spider.say", classOf[String]))
        if(gene == GeneRareItemDropChances)
            return new Chromosome(gene, new Allele(true, Array(0.33F), classOf[Array[Float]]))
        if(gene == GeneRareItemDrops)
            return new Chromosome(gene, new Allele(true, Array(new ItemStack(Items.spider_eye)), classOf[Array[ItemStack]]))
        if(gene == GeneSplashSound)
            return new Chromosome(gene, new Allele(true, "game.hostile.swim.splash", classOf[String]))
        if(gene == GeneSwimSound)
            return new Chromosome(gene, new Allele(true, "game.hostile.swim", classOf[String]))
        if(gene == GeneWalkSound)
            return new Chromosome(gene, new Allele(true, "mob.spider.step", classOf[String]))
        if(gene == GeneImmuneToPoison)
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene == GeneAffectedByWeb)
            return new Chromosome(gene, new Allele(false, false, classOf[Boolean]))
        if(gene == GeneCanClimbWalls)
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))

        if(gene == GeneAttackTargetVisible)
            return new Chromosome(gene, new Allele(false, false, classOf[Boolean]))
        if(gene == GeneMaxAttackBrightness)
            return new Chromosome(gene, new Allele(false, 0.5F, classOf[Float]))
        if(gene == GeneJumpAtAttackTarget)
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))

        //AI genes
        if(gene == GeneAIHurtByTarget)
            return new Chromosome(gene, new Allele(false, false, classOf[Boolean]))
        if(gene == GeneAILookIdle)
            return new Chromosome(gene, new Allele(false, false, classOf[Boolean]))
        if(gene == GeneAINearestAttackableTarget)
            return new Chromosome(gene, new Allele(false, false, classOf[Boolean]))
        if(gene == GeneAISwimming)
            return new Chromosome(gene, new Allele(false, false, classOf[Boolean]))
        if(gene == GeneAIWander)
            return new Chromosome(gene, new Allele(false, false, classOf[Boolean]))
        if(gene == GeneAIWatchClosest)
            return new Chromosome(gene, new Allele(false, false, classOf[Boolean]))

        //Rendering related Genes.
        if(gene == GeneModelAdult)
            return new Chromosome(gene, new Allele(true, model, classOf[Model]))
        if(gene == GeneTexture)
            return new Chromosome(gene, new Allele(true, textureStringToNBT("textures/entity/spider/spider.png"), classOf[NBTTagCompound]), new Allele(false, textureStringToNBT("textures/entity/spider/spider.png"), classOf[NBTTagCompound]))

        super.getChromosomeFromGene(entity, gene)
    }

    val model: Model = {
        val model = new Model
        val modelSpider = new ModelSpider

        modelSpider.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, null)

        model.addPart(ModelPart.rendererToPart(modelSpider.spiderHead, General.Head, AttachmentPoints.Spider.Head))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderBody, General.Body, AttachmentPoints.Spider.Body))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderNeck, General.Neck, AttachmentPoints.Spider.Neck))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg1, Spider.LegHindRight, AttachmentPoints.Spider.LegRight))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg2, Spider.LegHindLeft, AttachmentPoints.Spider.LegLeft))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg3, Spider.LegMiddleHindRight, AttachmentPoints.Spider.LegRight))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg4, Spider.LegMiddleHindLeft, AttachmentPoints.Spider.LegLeft))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg5, Spider.LegMiddleFrontRight, AttachmentPoints.Spider.LegRight))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg6, Spider.LegMiddleFrontLeft, AttachmentPoints.Spider.LegLeft))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg7, Spider.LegFrontRight, AttachmentPoints.Spider.LegRight))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg8, Spider.LegFrontLeft, AttachmentPoints.Spider.LegLeft))

        model
    }
}
