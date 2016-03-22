package com.seremis.geninfusion.api.genetics

import com.seremis.geninfusion.api.util.TypedName

trait IGene[A] {

    def getGeneName: TypedName[A]

    def getDefaultValue: IGeneData[A]
    def noMutations()

    def mutate(data: IGeneData[A]): IGeneData[A]
    def inherit(parent1: IGeneData[A], parent2: IGeneData[A]): IGeneData[A]
}
