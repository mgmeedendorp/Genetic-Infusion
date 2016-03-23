package com.seremis.geninfusion.api.genetics

/**
  * The data of both alleles for a gene in an ISoul.
  * Every IGene has two alleles in an ISoul, one active, one passive.
  * @tparam A The type of the data.
  */
trait IChromosome[A] {
    def getActiveAllele: IAllele[A]
    def getPassiveAllele: IAllele[A]
}
