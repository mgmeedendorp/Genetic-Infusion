package com.seremis.geninfusion.genetics

import com.seremis.geninfusion.api.genetics.{IAllele, IGeneData}
import com.seremis.geninfusion.api.util.TypedName

case class GeneData[A](name: TypedName[A], allele1: IAllele[A], allele2: IAllele[A]) extends IGeneData[A] {

    override def getName = name

    override def toString: String = "AlleleData[name = '" + name.toString + "', allele1 = '" + allele1.toString + "', allele2 = '"+ allele2.toString + "']"

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
