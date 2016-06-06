package com.seremis.geninfusion.registry

import com.seremis.geninfusion.api.GIApiInterface.IGeneRegistry
import com.seremis.geninfusion.api.genetics.{IChromosome, IGene}
import com.seremis.geninfusion.api.util.GeneName
import com.seremis.geninfusion.genetics.Gene

import scala.collection.mutable.{ArrayBuffer, HashMap}

class GeneRegistry extends IGeneRegistry {

    var genesMapped: HashMap[GeneName[_], IGene[_]] = HashMap()
    var genesListed: ArrayBuffer[IGene[_]] = ArrayBuffer()

    override def register(gene: IGene[_]) {
        genesMapped += (gene.getGeneName -> gene)
        genesListed += gene
    }

    override def register[A](name: GeneName[A], defaultValue: IChromosome[A]) = register(new Gene(name, defaultValue))

    override def getAllGenes: Array[IGene[_]] = genesListed.toArray

    override def hasGeneForName(name: GeneName[_]): Boolean = genesMapped.get(name).nonEmpty

    @throws[IllegalArgumentException]
    override def getGeneForName[A](name: GeneName[A]): IGene[A] = {
        val option = genesMapped.get(name)

        if(option.nonEmpty) {
            option.get.asInstanceOf[IGene[A]]
        } else {
            throw new IllegalArgumentException("There exists no registered IGene with name '" + name + "'!")
        }
    }
}
