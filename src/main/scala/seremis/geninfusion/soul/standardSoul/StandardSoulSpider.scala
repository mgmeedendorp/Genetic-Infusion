package seremis.geninfusion.soul.standardSoul

import net.minecraft.client.model.ModelSpider
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntitySpider
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.IChromosome
import seremis.geninfusion.api.soul.lib.Genes._
import seremis.geninfusion.api.soul.lib.ModelPartTypes
import seremis.geninfusion.api.util.render.model.{Model, ModelPart}
import seremis.geninfusion.soul.{Allele, Chromosome}
import seremis.geninfusion.util.GIModelBox

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
        if(gene == GeneModel)
            return new Chromosome(gene, new Allele(true, model, classOf[Model]))
        if(gene == GeneTexture)
            return new Chromosome(gene, new Allele(true, textureStringToNBT("textures/entity/spider/spider.png"), classOf[NBTTagCompound]), new Allele(false, textureStringToNBT("textures/entity/spider/spider.png"), classOf[NBTTagCompound]))

        super.getChromosomeFromGene(entity, gene)
    }

    val model: Model = {
        val model = new Model
        val modelSpider = new ModelSpider

        modelSpider.setRotationAngles(0.0F, 0.0F, 1.3997523F, 0.0F, 0.0F, 0.0F, null)

        model.addPart(ModelPart.rendererToPart(modelSpider.spiderHead, ModelPartTypes.Head))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderBody, ModelPartTypes.Body))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderNeck, ModelPartTypes.Body))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg1, ModelPartTypes.LegsRight))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg2, ModelPartTypes.LegsLeft))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg3, ModelPartTypes.LegsRight))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg4, ModelPartTypes.LegsLeft))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg5, ModelPartTypes.LegsRight))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg6, ModelPartTypes.LegsLeft))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg7, ModelPartTypes.LegsRight))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg8, ModelPartTypes.LegsLeft))

        model
    }
}
