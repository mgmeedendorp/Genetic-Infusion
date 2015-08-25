package seremis.geninfusion.soul

import seremis.geninfusion.api.soul.{IChromosome, IMasterGene, SoulHelper}
import seremis.geninfusion.soul.gene.Gene

import scala.collection.mutable.ListBuffer

abstract class MasterGene(clzz: Class[_]) extends Gene(clzz) with IMasterGene {

    var controlledGenes: ListBuffer[String] = ListBuffer()

    var combinedInherit = false

    override def getControlledGenes: List[String] = controlledGenes.toList

    override def addControlledGene(name: String) = controlledGenes += name

    override def setCombinedInherit: IMasterGene = {
        combinedInherit = true
        SoulHelper.geneRegistry.registerCustomInheritance(this)
        this
    }

    override def hasCombinedInherit: Boolean = combinedInherit

    override def advancedInherit(parent1: Array[IChromosome], parent2: Array[IChromosome], offspring: Array[IChromosome]): IChromosome = {
        val geneId = SoulHelper.geneRegistry.getGeneId(this).get

        val primary = rand.nextBoolean()

        for(name <- getControlledGenes) {
            val geneId = SoulHelper.geneRegistry.getGeneId(name).get

            val allele1 = if(primary) parent1(geneId).getPrimary else parent1(geneId).getSecondary
            val allele2 = if(primary) parent2(geneId).getPrimary else parent2(geneId).getSecondary

            offspring(geneId) = SoulHelper.instanceHelper.getIChromosomeInstance(name, allele1, allele2)
        }

        val allele1 = if(primary) parent1(geneId).getPrimary else parent1(geneId).getSecondary
        val allele2 = if(primary) parent2(geneId).getPrimary else parent2(geneId).getSecondary

        SoulHelper.instanceHelper.getIChromosomeInstance(SoulHelper.geneRegistry.getGeneName(this).get, allele1, allele2)
    }
}
