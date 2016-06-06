package com.seremis.geninfusion.genetics

import com.seremis.geninfusion.api.genetics.{IChromosome, IGene}
import com.seremis.geninfusion.api.util.GeneName

import scala.util.Random

class Gene[A](geneName: GeneName[A], defaultValue: IChromosome[A]) extends IGene[A] {

    lazy val rand = new Random

    var mutate = true

    override def getGeneName: GeneName[A] = geneName

    override def inherit(parent1: IChromosome[A], parent2: IChromosome[A]): IChromosome[A] = {
        val allele1 = if(rand.nextBoolean()) parent1.getActiveAllele.copy() else parent2.getActiveAllele.copy()
        val allele2 = if(rand.nextBoolean()) parent1.getPassiveAllele.copy() else parent2.getPassiveAllele.copy()

        Chromosome(allele1, allele2)
    }

    override def noMutations(): Unit = mutate = false

    override def mutate(data: IChromosome[A]): IChromosome[A] = {
        var active: A = data.getActiveAllele.getData
        var passive: A = data.getPassiveAllele.getData

        if(rand.nextBoolean()) {
            active = mutateData(active).asInstanceOf[A]
        } else {
            passive = mutateData(passive).asInstanceOf[A]
        }

        Chromosome(new Allele(active, data.getActiveAllele.isDominant), new Allele(passive, data.getPassiveAllele.isDominant))
    }

    def mutateData(data: A): Any = {
        data match {
            case d : Boolean => !d
            case d : Byte => d * (rand.nextFloat() * 2)
            case d : Short => d * (rand.nextFloat() * 2)
            case d : Int => d * (rand.nextFloat() * 2)
            case d : Float => d * (rand.nextFloat() * 2)
            case d : Double => d * (rand.nextFloat() * 2)
            case d : Long => d * (rand.nextFloat() * 2)
        }
    }

    override def getDefaultValue: IChromosome[A] = defaultValue
}
