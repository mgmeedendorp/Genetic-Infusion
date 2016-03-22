package com.seremis.geninfusion.api.genetics

import com.seremis.geninfusion.api.util.TypedName

/**
  * The data of both alleles for a gene in an ISoul.
  * Every IGene has two alleles in an ISoul, one active, one passive.
  * @tparam A The type of the data.
  */
trait IGeneData[A] {

    def getName: TypedName[A]
    def getActiveAllele: IAllele[A]
    def getPassiveAllele: IAllele[A]
}
