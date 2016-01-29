package seremis.geninfusion.soul.gene

import seremis.geninfusion.api.render.Model
import seremis.geninfusion.api.soul.{IChromosome, SoulHelper}
import seremis.geninfusion.api.util.UtilModel
import seremis.geninfusion.soul.Allele

class GeneModel extends Gene(classOf[Model]) {

    override def mutate(chromosome: IChromosome): IChromosome = chromosome

    override def advancedInherit(parent1: Array[IChromosome], parent2: Array[IChromosome], offspring: Array[IChromosome]): IChromosome = {
        val geneIdModel = SoulHelper.geneRegistry.getGeneId(this).get

        val modelParent1 = parent1(geneIdModel)
        val modelParent2 = parent2(geneIdModel)

        val parent1Model = if(rand.nextBoolean()) modelParent1.getPrimary.getAlleleData.asInstanceOf[Model].copy() else modelParent1.getSecondary.getAlleleData.asInstanceOf[Model].copy()
        val parent2Model = if(rand.nextBoolean()) modelParent2.getPrimary.getAlleleData.asInstanceOf[Model].copy() else modelParent2.getSecondary.getAlleleData.asInstanceOf[Model].copy()

        val child = UtilModel.randomlyCombineModels(parent1Model, parent2Model)

        val resultAllele1 = new Allele(true, child._1, classOf[Model])
        val resultAllele2 = new Allele(false, child._2, classOf[Model])

        SoulHelper.instanceHelper.getIChromosomeInstance(SoulHelper.geneRegistry.getGeneName(this).get, resultAllele1, resultAllele2)
    }
}
