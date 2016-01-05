package seremis.geninfusion.soul.gene

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import javax.imageio.ImageIO

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.api.soul.{IChromosome, SoulHelper}
import seremis.geninfusion.api.util.render.animation.AnimationCache
import seremis.geninfusion.api.util.render.model.Model
import seremis.geninfusion.soul.Allele
import seremis.geninfusion.util.UtilModel

class GeneModel extends Gene(classOf[Model]) {

    override def mutate(chromosome: IChromosome): IChromosome = {
        val allele1Data = chromosome.getPrimary.getAlleleData.asInstanceOf[Model].getAllParts
        val allele2Data = chromosome.getSecondary.getAlleleData.asInstanceOf[Model].getAllParts

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
        val geneIdModel = SoulHelper.geneRegistry.getGeneId(this).get
        val geneIdTexture = SoulHelper.geneRegistry.getGeneId(Genes.GeneTexture).get
        val geneIdHeight = SoulHelper.geneRegistry.getGeneId(Genes.GeneHeight).get
        val geneIdWidth = SoulHelper.geneRegistry.getGeneId(Genes.GeneWidth).get

        val textureParent1 = parent1(geneIdTexture)
        val textureParent2 = parent2(geneIdTexture)

        val textureParent1Primary = ImageIO.read(new ByteArrayInputStream(textureParent1.getPrimary.getAlleleData.asInstanceOf[NBTTagCompound].getByteArray("textureBytes")))
        val textureParent2Primary = ImageIO.read(new ByteArrayInputStream(textureParent2.getPrimary.getAlleleData.asInstanceOf[NBTTagCompound].getByteArray("textureBytes")))
        val textureParent1Secondary = ImageIO.read(new ByteArrayInputStream(textureParent1.getSecondary.getAlleleData.asInstanceOf[NBTTagCompound].getByteArray("textureBytes")))
        val textureParent2Secondary = ImageIO.read(new ByteArrayInputStream(textureParent2.getSecondary.getAlleleData.asInstanceOf[NBTTagCompound].getByteArray("textureBytes")))

        val modelParent1 = parent1(geneIdModel)
        val modelParent2 = parent2(geneIdModel)

        val parent1Model = if(rand.nextBoolean()) (modelParent1.getPrimary.getAlleleData.asInstanceOf[Model].copy(), textureParent1Primary) else (modelParent1.getSecondary.getAlleleData.asInstanceOf[Model].copy(), textureParent1Secondary)
        val parent2Model = if(rand.nextBoolean()) (modelParent2.getPrimary.getAlleleData.asInstanceOf[Model].copy(), textureParent2Primary) else (modelParent2.getSecondary.getAlleleData.asInstanceOf[Model].copy(), textureParent2Secondary)

        val child = UtilModel.randomlyCombineModels(parent1Model._1, parent1Model._2, parent2Model._1, parent2Model._2)


        val dominantModel = child._1._1
        val recessiveModel = child._2._1

        val dominantTexture = child._1._2
        val recessiveTexture = child._2._2

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

        offspring(geneIdTexture) = SoulHelper.instanceHelper.getIChromosomeInstance(Genes.GeneTexture, textureAllele1, textureAllele2)

        val widthAllele1 = new Allele(true, AnimationCache.getModelWidth(dominantModel) * 0.8F, classOf[Float])
        val widthAllele2 = new Allele(false, AnimationCache.getModelWidth(recessiveModel) * 0.8F, classOf[Float])

        offspring(geneIdWidth) = SoulHelper.instanceHelper.getIChromosomeInstance(Genes.GeneWidth, widthAllele1, widthAllele2)

        val heightAllele1 = new Allele(true, AnimationCache.getModelHeight(dominantModel), classOf[Float])
        val heightAllele2 = new Allele(false, AnimationCache.getModelHeight(recessiveModel), classOf[Float])

        offspring(geneIdHeight) = SoulHelper.instanceHelper.getIChromosomeInstance(Genes.GeneHeight, heightAllele1, heightAllele2)

        val resultAllele1 = new Allele(true, dominantModel, classOf[Model])
        val resultAllele2 = new Allele(false, recessiveModel, classOf[Model])

        SoulHelper.instanceHelper.getIChromosomeInstance(Genes.GeneModel, resultAllele1, resultAllele2)
    }
}
