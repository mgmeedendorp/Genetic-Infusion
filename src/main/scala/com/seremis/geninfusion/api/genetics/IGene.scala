package com.seremis.geninfusion.api.genetics

import com.seremis.geninfusion.api.util.TypedName

trait IGene[A] {

    def getGeneName: TypedName[A]

    def getDefaultValue: IChromosome[A]
    def noMutations()

    def mutate(data: IChromosome[A]): IChromosome[A]
    def inherit(parent1: IChromosome[A], parent2: IChromosome[A]): IChromosome[A]
}
