package com.seremis.geninfusion.genetics

import com.seremis.geninfusion.api.genetics.{IAllele, IChromosome}

case class Chromosome[A](allele1: IAllele[A], allele2: IAllele[A]) extends IChromosome[A] {

    override def toString: String = "Chromosome[allele1 = '" + allele1.toString + "', allele2 = '"+ allele2.toString + "']"

    override def getActiveAllele: IAllele[A] = {
        if(allele1.isDominant) {
            allele1
        } else {
            allele2
        }
    }

    override def getPassiveAllele: IAllele[A] = {
        if(allele1.isDominant) {
            allele2
        } else {
            allele1
        }
    }
}
