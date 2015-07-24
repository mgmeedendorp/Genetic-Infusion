package seremis.geninfusion.soul.gene

import java.awt.geom.Rectangle2D.Double
import java.awt.image.BufferedImage
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import javax.imageio.ImageIO

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.{IChromosome, SoulHelper}
import seremis.geninfusion.api.util.render.animation.AnimationCache
import seremis.geninfusion.api.util.render.model.ModelPart
import seremis.geninfusion.helper.GITextureHelper
import seremis.geninfusion.soul.{Allele, Gene}

import scala.collection.mutable.ListBuffer

class GeneModel extends Gene(classOf[Array[ModelPart]]) {

    override def mutate(chromosome: IChromosome): IChromosome = {
        val allele1Data = chromosome.getPrimary.getAlleleData.asInstanceOf[Array[ModelPart]]
        val allele2Data = chromosome.getSecondary.getAlleleData.asInstanceOf[Array[ModelPart]]

        if(rand.nextBoolean()) {
            for(part <- allele1Data) {
                if(rand.nextInt(100) < 20) {
                    part.mutate()
                }
            }
        }
        else {
            for(part <- allele2Data) {
                if(rand.nextInt(100) < 20) {
                    part.mutate()
                }
            }
        }

        chromosome
    }

    override def advancedInherit(parent1: Array[IChromosome], parent2: Array[IChromosome], offspring: Array[IChromosome]): IChromosome = {
        val geneIdModel = SoulHelper.geneRegistry.getGeneId(this)
        val geneIdTexture = SoulHelper.geneRegistry.getGeneId(Genes.GeneTexture)
        val geneIdHeight = SoulHelper.geneRegistry.getGeneId(Genes.GeneHeight)
        val geneIdWidth = SoulHelper.geneRegistry.getGeneId(Genes.GeneWidth)

        val textureParent1 = parent1(geneIdTexture)
        val textureParent2 = parent2(geneIdTexture)

        val textureParent1Primary = ImageIO.read(new ByteArrayInputStream(textureParent1.getPrimary.getAlleleData.asInstanceOf[NBTTagCompound].getByteArray("textureBytes")))
        val textureParent2Primary = ImageIO.read(new ByteArrayInputStream(textureParent2.getPrimary.getAlleleData.asInstanceOf[NBTTagCompound].getByteArray("textureBytes")))
        val textureParent1Secondary = ImageIO.read(new ByteArrayInputStream(textureParent1.getSecondary.getAlleleData.asInstanceOf[NBTTagCompound].getByteArray("textureBytes")))
        val textureParent2Secondary = ImageIO.read(new ByteArrayInputStream(textureParent2.getSecondary.getAlleleData.asInstanceOf[NBTTagCompound].getByteArray("textureBytes")))

        val modelParent1 = parent1(geneIdModel)
        val modelParent2 = parent2(geneIdModel)

        val modelParent1Primary = modelParent1.getPrimary.getAlleleData.asInstanceOf[Array[ModelPart]]
        val modelParent2Primary = modelParent2.getPrimary.getAlleleData.asInstanceOf[Array[ModelPart]]
        val modelParent1Secondary = modelParent1.getSecondary.getAlleleData.asInstanceOf[Array[ModelPart]]
        val modelParent2Secondary = modelParent2.getSecondary.getAlleleData.asInstanceOf[Array[ModelPart]]

        val combinedParent1 = randomlyCombineModels(modelParent1Primary, textureParent1Primary, modelParent1Secondary, textureParent1Secondary)
        val combinedParent2 = randomlyCombineModels(modelParent2Primary, textureParent2Primary, modelParent2Secondary, textureParent2Secondary)
        
        val combinedParent1Tuple = createParentTexture(combinedParent1._1)
        val combinedParent2Tuple = createParentTexture(combinedParent2._1)

        try {
            AnimationCache.attachModelPartsToBody(modelParent1Primary, modelParent1Secondary, combinedParent1Tuple._2.to[Array])
        } catch {
            case e: Exception => e.printStackTrace()
        }

        try {
            AnimationCache.attachModelPartsToBody(modelParent2Primary, modelParent2Secondary, combinedParent2Tuple._2.to[Array])
        } catch {
            case e: Exception => e.printStackTrace()
        }

        val child = randomlyCombineModels(combinedParent1Tuple._2.to[Array], combinedParent1Tuple._1, combinedParent2Tuple._2.to[Array], combinedParent2Tuple._1)

        val dominantTuple = createParentTexture(child._1)
        val recessiveTuple = createParentTexture(child._2)

        try {
            AnimationCache.attachModelPartsToBody(combinedParent1Tuple._2.to[Array], combinedParent2Tuple._2.to[Array], dominantTuple._2.to[Array])
        } catch {
            case e: Exception => e.printStackTrace()
        }

        try {
            AnimationCache.attachModelPartsToBody(combinedParent1Tuple._2.to[Array], combinedParent2Tuple._2.to[Array], recessiveTuple._2.to[Array])
        } catch {
            case e: Exception => e.printStackTrace()
        }

        val dominantTexture = dominantTuple._1
        val recessiveTexture = recessiveTuple._1

        val dominantOut = new ByteArrayOutputStream()
        val recessiveOut = new ByteArrayOutputStream()

        ImageIO.write(dominantTexture, "png", dominantOut)
        ImageIO.write(recessiveTexture, "png", recessiveOut)

        val dominantNBT = new NBTTagCompound
        val recessiveNBT = new NBTTagCompound

        dominantNBT.setByteArray("textureBytes", dominantOut.toByteArray)
        recessiveNBT.setByteArray("textureBytes", recessiveOut.toByteArray)

        val textureAllele1 = new Allele(true, dominantNBT, classOf[NBTTagCompound])
        val textureAllele2 = new Allele(false, recessiveNBT, classOf[NBTTagCompound])

        offspring(geneIdTexture) = SoulHelper.instanceHelper.getIChromosomeInstance(textureAllele1, textureAllele2)

        val widthAllele1 = new Allele(true, AnimationCache.getModelWidth(dominantTuple._2.to[Array]), classOf[Float])
        val widthAllele2 = new Allele(false, AnimationCache.getModelWidth(recessiveTuple._2.to[Array]), classOf[Float])

        offspring(geneIdWidth) = SoulHelper.instanceHelper.getIChromosomeInstance(widthAllele1, widthAllele2)

        val heightAllele1 = new Allele(true, AnimationCache.getModelHeight(dominantTuple._2.to[Array]), classOf[Float])
        val heightAllele2 = new Allele(false, AnimationCache.getModelHeight(recessiveTuple._2.to[Array]), classOf[Float])

        offspring(geneIdHeight) = SoulHelper.instanceHelper.getIChromosomeInstance(heightAllele1, heightAllele2)

        val resultAllele1 = new Allele(true, dominantTuple._2.to[Array], classOf[Array[ModelPart]])
        val resultAllele2 = new Allele(false, recessiveTuple._2.to[Array], classOf[Array[ModelPart]])

        SoulHelper.instanceHelper.getIChromosomeInstance(resultAllele1, resultAllele2)
    }

    private def randomlyInherit(inherited1: ListBuffer[(Array[ModelPart], BufferedImage)], inherited2: ListBuffer[(Array[ModelPart], BufferedImage)], parent1: Array[ModelPart], texture1: BufferedImage, parent2: Array[ModelPart], texture2: BufferedImage) {
        if (rand.nextBoolean()) {
            inherited1 += (parent1 -> texture1)
            inherited2 += (parent2 -> texture2)
        } else {
            inherited1 += (parent2 -> texture2)
            inherited2 += (parent1 -> texture1)
        }
    }

    def createParentTexture(inherited: ListBuffer[(Array[ModelPart], BufferedImage)]): (BufferedImage, ListBuffer[ModelPart]) = {
        val modelPartImages: ListBuffer[BufferedImage] = ListBuffer()
        val textureRects: ListBuffer[Double] = ListBuffer()
        val parts: ListBuffer[ModelPart] = ListBuffer()

        for (tuple <- inherited) {
            val partArray = tuple._1
            val wholeTexture = tuple._2

            for (part <- partArray if part != null) {
                val image = GITextureHelper.getModelPartTexture(part, wholeTexture)
                textureRects += new Double(0, 0, image.getWidth, image.getHeight)
                modelPartImages += image
                parts += part
            }
        }

        val result = GITextureHelper.stitchImages(textureRects, modelPartImages)

        for (i <- parts.indices) {
            val part = parts(i)
            val rect = result._2(i)
            GITextureHelper.changeModelPartTextureSize(part, (result._1.getWidth, result._1.getHeight))
            GITextureHelper.moveModelPartTextureOffset(part, (rect.getMinX.toInt, rect.getMinY.toInt))

            part.resetDisplayList()
        }
        (result._1, parts)
    }

    def randomlyCombineModels(model1: Array[ModelPart], texture1: BufferedImage, model2: Array[ModelPart], texture2: BufferedImage): (ListBuffer[(Array[ModelPart], BufferedImage)], ListBuffer[(Array[ModelPart], BufferedImage)]) = {
        val head1 = AnimationCache.getModelHead(model1)
        val head2 = AnimationCache.getModelHead(model2)
        val arms1 = AnimationCache.getModelArms(model1).getOrElse(new Array[ModelPart](0))
        val arms2 = AnimationCache.getModelArms(model2).getOrElse(new Array[ModelPart](0))
        val legs1 = AnimationCache.getModelLegs(model1)
        val legs2 = AnimationCache.getModelLegs(model2)
        val wings1 = AnimationCache.getModelWings(model1)
        val wings2 = AnimationCache.getModelWings(model2)
        val body1 = Array(AnimationCache.getModelBody(model1))
        val body2 = Array(AnimationCache.getModelBody(model2))

        val combined1: ListBuffer[(Array[ModelPart], BufferedImage)] = ListBuffer()
        val combined2: ListBuffer[(Array[ModelPart], BufferedImage)] = ListBuffer()

        randomlyInherit(combined1, combined2, head1, texture1, head2, texture2)
        randomlyInherit(combined1, combined2, arms1, texture1, arms2, texture2)
        randomlyInherit(combined1, combined2, legs1, texture1, legs2, texture2)
        randomlyInherit(combined1, combined2, wings1, texture1, wings2, texture2)
        randomlyInherit(combined1, combined2, body1, texture1, body2, texture2)

        combined1 -> combined2
    }
}
