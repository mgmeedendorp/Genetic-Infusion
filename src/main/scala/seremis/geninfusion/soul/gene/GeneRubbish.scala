package seremis.geninfusion.soul.gene

import seremis.geninfusion.api.soul.IChromosome
import seremis.geninfusion.soul.Gene

//TEST GENE
class GeneRubbish extends Gene(classOf[Boolean]) {
    override def advancedInherit(parent1: Array[IChromosome], parent2: Array[IChromosome], offspring: Array[IChromosome]): IChromosome = {
        println("ADVANCED INHERIT!")

        super.advancedInherit(parent1, parent2, offspring)
    }
}
