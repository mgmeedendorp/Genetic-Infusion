package seremis.geninfusion.util

import seremis.geninfusion.api.soul.{IChromosome, IGene, ISoul, SoulHelper}
import seremis.geninfusion.api.util.AncestryNode

object GenomeHelper {

    def fixGenomeErrors(soul: ISoul): Array[IChromosome] = fixGenomeErrors(soul.getAncestryNode, soul.getChromosomes)

    def fixGenomeErrors(ancestry: AncestryNode, chromosomes: Array[IChromosome]): Array[IChromosome] = {
        if(!isGenomeFixed(chromosomes)) {
            val genes = SoulHelper.geneRegistry.getGenes

            val fixedChromosomes = new Array[IChromosome](Math.max(genes.length, chromosomes.length))
            Array.copy(chromosomes, 0, fixedChromosomes, 0, chromosomes.length)

            val geneNames = genes.map(g => SoulHelper.geneRegistry.getGeneName(g).get)

            for(index <- geneNames.indices) {
                val current = geneNames(index)
                val loadedNames = fixedChromosomes.map(c => if(c != null) c.getGeneName else "")
                val loaded = loadedNames(index)

                if(current != loaded) {
                    var foundIndex: Option[Int] = None

                    for(i <- chromosomes.indices if current == chromosomes(i).getGeneName) {
                        foundIndex = Some(i)
                    }

                    if(foundIndex.nonEmpty) {
                        fixedChromosomes(index) = chromosomes(foundIndex.get)
                    } else {
                        fixedChromosomes(index) = getNewInheritedChromosome(ancestry, genes(index))
                    }
                } else {
                    fixedChromosomes(index) = chromosomes(index)
                }
            }
            return fixedChromosomes.dropRight(fixedChromosomes.length - genes.length)
        }
        chromosomes
    }

    def isGenomeFixed(genome: Array[IChromosome]): Boolean = {
        val genes = SoulHelper.geneRegistry.getGenes

        if(genes.length != genome.length) {
            return false
        } else if(genes.length == genome.length) {
            for((current, loaded) <- genes.map(g => SoulHelper.geneRegistry.getGeneName(g).get) zip genome.map(c => c.getGeneName)) {
                if(current != loaded) {
                    return false
                }
            }
        }
        true
    }

    def getNewInheritedChromosome(ancestry: AncestryNode, gene: IGene): IChromosome = ancestry.getIChromosomeFromGene(gene)
}
