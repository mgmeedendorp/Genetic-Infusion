package seremis.geninfusion.soul.gene

import java.awt.image.BufferedImage
import javafx.geometry.Rectangle2D

import net.minecraft.util.ResourceLocation
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.api.soul.{EnumAlleleType, IChromosome, SoulHelper}
import seremis.geninfusion.helper.GITextureHelper
import seremis.geninfusion.lib.Localizations
import seremis.geninfusion.soul.entity.animation.AnimationCache
import seremis.geninfusion.soul.{Allele, Gene}

import scala.collection.mutable.ListBuffer

class GeneModel extends Gene(EnumAlleleType.MODELPART_ARRAY) {

    override def mutate(chromosome: IChromosome): IChromosome = {
        var allele1Data = chromosome.getPrimary.getAlleleData.asInstanceOf[Array[ModelPart]]
        val allele2Data = chromosome.getSecondary.getAlleleData.asInstanceOf[Array[ModelPart]]

        if(rand.nextBoolean()) {
            for(part <- allele1Data) {
                if(rand.nextInt(100) < 20) {
                    part.mutate
                }
            }
        }
        else {
            for(part <- allele2Data) {
                if(rand.nextInt(100) < 20) {
                    part.mutate
                }
            }
        }

        chromosome
    }

    override def advancedInherit(parent1: Array[IChromosome], parent2: Array[IChromosome], offspring: Array[IChromosome]): IChromosome = {
        val geneIdModel = SoulHelper.geneRegistry.getGeneId(this)
        val geneIdTexture = SoulHelper.geneRegistry.getGeneId(Genes.GENE_TEXTURE)
        val geneIdHeight = SoulHelper.geneRegistry.getGeneId(Genes.GENE_HEIGHT)
        val geneIdWidth = SoulHelper.geneRegistry.getGeneId(Genes.GENE_WIDTH)

        val textureChromosome1 = parent1(geneIdTexture)
        val textureChromosome2 = parent2(geneIdTexture)

        val texture1 = GITextureHelper.getBufferedImage(toResource(textureChromosome1.getPrimary.getAlleleData.asInstanceOf[String]))
        val texture2 = GITextureHelper.getBufferedImage(toResource(textureChromosome2.getPrimary.getAlleleData.asInstanceOf[String]))
        val texture3 = GITextureHelper.getBufferedImage(toResource(textureChromosome1.getSecondary.getAlleleData.asInstanceOf[String]))
        val texture4 = GITextureHelper.getBufferedImage(toResource(textureChromosome2.getSecondary.getAlleleData.asInstanceOf[String]))

        val chromosome1 = parent1(geneIdModel)
        val chromosome2 = parent2(geneIdModel)

        val allele1 = chromosome1.getPrimary.getAlleleData.asInstanceOf[Array[ModelPart]]
        val allele2 = chromosome2.getPrimary.getAlleleData.asInstanceOf[Array[ModelPart]]
        val allele3 = chromosome1.getSecondary.getAlleleData.asInstanceOf[Array[ModelPart]]
        val allele4 = chromosome2.getSecondary.getAlleleData.asInstanceOf[Array[ModelPart]]

        val head1 = AnimationCache.getModelHead(allele1)
        val head2 = AnimationCache.getModelHead(allele2)
        val head3 = AnimationCache.getModelHead(allele3)
        val head4 = AnimationCache.getModelHead(allele4)
        val arms1 = AnimationCache.getModelArms(allele1)
        val arms2 = AnimationCache.getModelArms(allele2)
        val arms3 = AnimationCache.getModelArms(allele3)
        val arms4 = AnimationCache.getModelArms(allele4)
        val legs1 = AnimationCache.getModelLegs(allele1)
        val legs2 = AnimationCache.getModelLegs(allele2)
        val legs3 = AnimationCache.getModelLegs(allele3)
        val legs4 = AnimationCache.getModelLegs(allele4)
        val wings1 = AnimationCache.getModelWings(allele1)
        val wings2 = AnimationCache.getModelWings(allele2)
        val wings3 = AnimationCache.getModelWings(allele3)
        val wings4 = AnimationCache.getModelWings(allele4)
        val body1 = Array(AnimationCache.getModelBody(allele1))
        val body2 = Array(AnimationCache.getModelBody(allele2))
        val body3 = Array(AnimationCache.getModelBody(allele3))
        val body4 = Array(AnimationCache.getModelBody(allele4))

        val inherited1: ListBuffer[(Array[ModelPart], BufferedImage)] = ListBuffer()
        val inherited2: ListBuffer[(Array[ModelPart], BufferedImage)] = ListBuffer()

        randomlyInherit(inherited1, head1, texture1, head3, texture3)
        randomlyInherit(inherited1, arms1, texture1, arms3, texture3)
        randomlyInherit(inherited1, legs1, texture1, legs3, texture3)
        randomlyInherit(inherited1, wings1, texture1, wings3, texture3)
        randomlyInherit(inherited1, body1, texture1, body3, texture3)
        randomlyInherit(inherited2, head2, texture2, head4, texture4)
        randomlyInherit(inherited2, arms2, texture2, arms4, texture4)
        randomlyInherit(inherited2, legs2, texture2, legs4, texture4)
        randomlyInherit(inherited2, wings2, texture2, wings4, texture4)
        randomlyInherit(inherited2, body2, texture2, body4, texture4)

        val parent1Tuple = createParentTexture(inherited1)
        val parent2Tuple = createParentTexture(inherited2)

        AnimationCache.attachModelPartsToBody(allele1, allele3, parent1Tuple._2.to[Array])
        AnimationCache.attachModelPartsToBody(allele2, allele4, parent2Tuple._2.to[Array])

        var parent1Texture = parent1Tuple._1
        var parent2Texture = parent2Tuple._1

        val textureId = SoulHelper.getNextAvailableTextureID
        val parent1TextureLocationString = Localizations.LOC_ENTITY_CUSTOM_TEXTURES + textureId + "_texture1.png"
        val parent2TextureLocationString = Localizations.LOC_ENTITY_CUSTOM_TEXTURES + textureId + "_texture2.png"

        val parent1TextureLocation = toResource(parent1TextureLocationString)
        val parent2TextureLocation = toResource(parent2TextureLocationString)

        parent1Texture = GITextureHelper.mergeImages(parent1Texture, parent2Texture)
        parent2Texture = GITextureHelper.mergeImages(parent2Texture, parent1Texture)

        GITextureHelper.writeBufferedImage(parent1Texture, parent1TextureLocation)
        GITextureHelper.writeBufferedImage(parent2Texture, parent2TextureLocation)

        val textureAllele1 = new Allele(true, parent1TextureLocationString, EnumAlleleType.STRING)
        val textureAllele2 = new Allele(false, parent2TextureLocationString, EnumAlleleType.STRING)

        offspring(geneIdTexture) = SoulHelper.instanceHelper.getIChromosomeInstance(textureAllele1, textureAllele2)

        val widthAllele1 = new Allele(true, AnimationCache.getModelWidth(parent1Tuple._2.to[Array]), EnumAlleleType.FLOAT)
        val widthAllele2 = new Allele(false, AnimationCache.getModelWidth(parent2Tuple._2.to[Array]), EnumAlleleType.FLOAT)

        offspring(geneIdWidth) = SoulHelper.instanceHelper.getIChromosomeInstance(widthAllele1, widthAllele2)

        val heightAllele1 = new Allele(true, AnimationCache.getModelHeight(parent1Tuple._2.to[Array]), EnumAlleleType.FLOAT)
        val heightAllele2 = new Allele(false, AnimationCache.getModelHeight(parent2Tuple._2.to[Array]), EnumAlleleType.FLOAT)

        offspring(geneIdHeight) = SoulHelper.instanceHelper.getIChromosomeInstance(heightAllele1, heightAllele2)

        val resultAllele1 = new Allele(true, parent1Tuple._2.to[Array], EnumAlleleType.MODELPART_ARRAY)
        val resultAllele2 = new Allele(false, parent2Tuple._2.to[Array], EnumAlleleType.MODELPART_ARRAY)

        SoulHelper.instanceHelper.getIChromosomeInstance(resultAllele1, resultAllele2)
    }

    private def randomlyInherit(inherited: ListBuffer[(Array[ModelPart], BufferedImage)], parent1: Array[ModelPart], texture1: BufferedImage, parent2: Array[ModelPart], texture2: BufferedImage) {
        if (rand.nextBoolean()) {
            inherited += (parent1 -> texture1)
        } else {
            inherited += (parent2 -> texture2)
        }
    }

    private def toResource(location: String): ResourceLocation = new ResourceLocation(location)

    def createParentTexture(inherited: ListBuffer[(Array[ModelPart], BufferedImage)]): (BufferedImage, ListBuffer[ModelPart]) = {
        val modelPartImages: ListBuffer[BufferedImage] = ListBuffer()
        val textureRects: ListBuffer[Rectangle2D] = ListBuffer()
        val parts: ListBuffer[ModelPart] = ListBuffer()

        for (tuple <- inherited) {
            val partArray = tuple._1
            val wholeTexture = tuple._2

            for (part <- partArray) {
                val image = GITextureHelper.getModelPartTexture(part, wholeTexture)
                textureRects += new Rectangle2D(0, 0, image.getWidth, image.getHeight)
                modelPartImages += image
                parts += part
            }
        }

        val result = GITextureHelper.stitchImages(textureRects, modelPartImages)

        for (i <- 0 until parts.length) {
            val part = parts(i)
            val rect = result._2(i)
            GITextureHelper.changeModelPartTextureSize(part, (result._1.getWidth, result._1.getHeight))
            GITextureHelper.moveModelPartTextureOffset(part, (rect.getMinX.toInt, rect.getMinY.toInt))
        }
        (result._1, parts)
    }
}
