package com.seremis.geninfusion.genetics

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.genetics.{IAncestry, IGeneData, ISoul}
import com.seremis.geninfusion.api.soulentity.IEntityMethod
import com.seremis.geninfusion.api.util.TypedName

import scala.collection.immutable.TreeMap

class Soul(genome: TreeMap[TypedName[_], IGeneData[_]], ancestry: IAncestry) extends ISoul {

    checkParameters()

    var entityMethods: Array[IEntityMethod[_]] = _

    @throws[IllegalArgumentException]
    def getValueFromMap[A](name: TypedName[A]): IGeneData[A] = {
        genome.getOrElse(name, geneNotAvailable(name)).asInstanceOf[IGeneData[A]]
    }

    /**
      * Returns the value of the active IAlleleData for this gene.
      */
    @throws[IllegalArgumentException]
    override def getActiveValueForGene[A](name: TypedName[A]): A = getValueFromMap(name).getActiveAllele.getData

    /**
      * Returns the value of the inactive IAlleleData of this gene.
      */
    @throws[IllegalArgumentException]
    override def getPassiveValueForGene[A](name: TypedName[A]): A = getValueFromMap(name).getPassiveAllele.getData

    /**
      * Get all the IEntityMethods from all registered that are applicable to this soul.
      *
      * @return all applicable IEntityMethods.
      */
    override def getEntityMethods: Array[IEntityMethod[_]] = {
        if(entityMethods.isEmpty) {
            //TODO fill
        }
        entityMethods
    }

    /**
      * Returns the IAncestry object for this soul.
      */
    override def getAncestry: IAncestry = ancestry

    @throws[IllegalArgumentException]
    def geneNotAvailable(name: TypedName[_]) = throw new IllegalArgumentException("There is no registered gene called '" + name.name + "' available. Is this gene registered?")

    @throws[IllegalArgumentException]
    def genomeMalformed(reason: String) = throw new IllegalArgumentException("The genome for a soul was malformed. " + reason)

    @throws[IllegalArgumentException]
    def checkParameters() {
        if(genome.nonEmpty) {
            val registeredGenes = GIApiInterface.geneRegistry.getAllGenes.sortBy(gene => gene.getGeneName)

            if(registeredGenes.length == genome.size) {

                var reason = ""

                var index = 0
                for((genomeGene, _) <- genome) {
                    val registeredGene = registeredGenes(index).getGeneName

                    if(genomeGene != registeredGene) {
                        reason += System.lineSeparator() + "\t The gene in the genome (" + genomeGene + ") does not match up with the gene that should be in that position (" + registeredGene + ")."
                    }

                    index += 1
                }

                if(reason != "") {
                    genomeMalformed("The genome has one or more errors: " + reason)
                }

            } else {
                genomeMalformed("The genome (" + genome.size + " genes) does not contain all registered genes (" + registeredGenes.length + " genes).")
            }
        } else {
            genomeMalformed("The genome map is empty.")
        }
    }

    override def toString = "Soul[genome = '" + genome.values.mkString(",") + "', ancestry = '" + ancestry + "]"
}
