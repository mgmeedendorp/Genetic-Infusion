package seremis.geninfusion.soul

import seremis.geninfusion.api.soul._

import scala.util.Random

class Gene(clzz: Class[_]) extends IGene {

    val rand = new Random()
    var mutate = true

    override def getAlleleType: IAlleleType = SoulHelper.alleleTypeRegistry.getAlleleTypeForClass(clzz)

    override def inherit(chromosome1: IChromosome, chromosome2: IChromosome): IChromosome = {
        val allele1 = if(rand.nextBoolean()) chromosome1.getPrimary else chromosome1.getSecondary
        val allele2 = if(rand.nextBoolean()) chromosome2.getPrimary else chromosome2.getSecondary

        SoulHelper.instanceHelper.getIChromosomeInstance(allele1, allele2)
    }

    override def advancedInherit(parent1: Array[IChromosome], parent2: Array[IChromosome], offspring: Array[IChromosome]): IChromosome = {
        val geneId = SoulHelper.geneRegistry.getGeneId(this)

        val allele1 = if(rand.nextBoolean()) parent1(geneId).getPrimary else parent1(geneId).getSecondary
        val allele2 = if(rand.nextBoolean()) parent2(geneId).getPrimary else parent2(geneId).getSecondary

        SoulHelper.instanceHelper.getIChromosomeInstance(allele1, allele2)
    }

    override def mutate(chromosome: IChromosome): IChromosome = {
        mutate = false
        if(mutate) {
            var allele1Data = chromosome.getPrimary.getAlleleData
            var allele2Data = chromosome.getSecondary.getAlleleData

            getAlleleType.getAlleleTypeClass match {
                case a if a == classOf[Boolean] =>
                    if(rand.nextBoolean())
                        allele1Data = !allele1Data.asInstanceOf[Boolean]
                    else
                        allele2Data = !allele2Data.asInstanceOf[Boolean]
                case a if a == classOf[Boolean] =>
                    if(rand.nextBoolean())
                        allele1Data = allele1Data.asInstanceOf[Byte] * (rand.nextFloat() * 2)
                    else
                        allele2Data = allele2Data.asInstanceOf[Byte] * (rand.nextFloat() * 2)
                case a if a == classOf[Short] =>
                    if(rand.nextBoolean())
                        allele1Data = allele1Data.asInstanceOf[Short] * (rand.nextFloat() * 2)
                    else
                        allele2Data = allele2Data.asInstanceOf[Short] * (rand.nextFloat() * 2)
                case a if a == classOf[Int] =>
                    if(rand.nextBoolean())
                        allele1Data = allele1Data.asInstanceOf[Int] * (rand.nextFloat() * 2)
                    else
                        allele2Data = allele2Data.asInstanceOf[Int] * (rand.nextFloat() * 2)
                case a if a == classOf[Float] =>
                    if(rand.nextBoolean())
                        allele1Data = allele1Data.asInstanceOf[Float] * (rand.nextFloat() * 2)
                    else
                        allele2Data = allele2Data.asInstanceOf[Float] * (rand.nextFloat() * 2)
                case a if a == classOf[Double] =>
                    if(rand.nextBoolean())
                        allele1Data = allele1Data.asInstanceOf[Double] * (rand.nextFloat() * 2)
                    else
                        allele2Data = allele2Data.asInstanceOf[Double] * (rand.nextFloat() * 2)
                case a if a == classOf[Long] =>
                    if(rand.nextBoolean())
                        allele1Data = allele1Data.asInstanceOf[Long] * (rand.nextFloat() * 2)
                    else
                        allele2Data = allele2Data.asInstanceOf[Long] * (rand.nextFloat() * 2)
                case a if a == classOf[Array[Boolean]] =>
                    if(rand.nextBoolean()) {
                        val index = rand.nextInt(allele1Data.asInstanceOf[Array[Boolean]].length)
                        allele1Data.asInstanceOf[Array[Boolean]](index) != allele1Data.asInstanceOf[Array[Boolean]](index)
                    } else {
                        val index = rand.nextInt(allele2Data.asInstanceOf[Array[Boolean]].length)
                        allele2Data.asInstanceOf[Array[Boolean]](index) != allele2Data.asInstanceOf[Array[Boolean]](index)
                    }
                case a if a == classOf[Array[Byte]] =>
                    if(rand.nextBoolean()) {
                        val index = rand.nextInt(allele1Data.asInstanceOf[Array[Byte]].length)
                        allele1Data.asInstanceOf[Array[Byte]](index) = (allele1Data.asInstanceOf[Array[Byte]](index) * (rand.nextFloat() * 2)).toByte
                    } else {
                        val index = rand.nextInt(allele2Data.asInstanceOf[Array[Byte]].length)
                        allele2Data.asInstanceOf[Array[Byte]](index) = (allele2Data.asInstanceOf[Array[Byte]](index) * (rand.nextFloat() * 2)).toByte
                    }
                case a if a == classOf[Array[Short]] =>
                    if(rand.nextBoolean()) {
                        val index = rand.nextInt(allele1Data.asInstanceOf[Array[Short]].length)
                        allele1Data.asInstanceOf[Array[Short]](index) = (allele1Data.asInstanceOf[Array[Short]](index) * (rand.nextFloat() * 2)).toShort
                    } else {
                        val index = rand.nextInt(allele2Data.asInstanceOf[Array[Short]].length)
                        allele2Data.asInstanceOf[Array[Short]](index) = (allele2Data.asInstanceOf[Array[Short]](index) * (rand.nextFloat() * 2)).toShort
                    }
                case a if a == classOf[Array[Int]] =>
                    if(rand.nextBoolean()) {
                        val index = rand.nextInt(allele1Data.asInstanceOf[Array[Int]].length)
                        allele1Data.asInstanceOf[Array[Int]](index) = (allele1Data.asInstanceOf[Array[Int]](index) * (rand.nextFloat() * 2)).toInt
                    } else {
                        val index = rand.nextInt(allele2Data.asInstanceOf[Array[Int]].length)
                        allele2Data.asInstanceOf[Array[Int]](index) = (allele2Data.asInstanceOf[Array[Int]](index) * (rand.nextFloat() * 2)).toInt
                    }
                case a if a == classOf[Array[Float]] =>
                    if(rand.nextBoolean()) {
                        val index = rand.nextInt(allele1Data.asInstanceOf[Array[Float]].length)
                        allele1Data.asInstanceOf[Array[Float]](index) = allele1Data.asInstanceOf[Array[Float]](index) * (rand.nextFloat() * 2)
                    } else {
                        val index = rand.nextInt(allele2Data.asInstanceOf[Array[Float]].length)
                        allele2Data.asInstanceOf[Array[Float]](index) = allele2Data.asInstanceOf[Array[Float]](index) * (rand.nextFloat() * 2)
                    }
                case a if a == classOf[Array[Double]] =>
                    if(rand.nextBoolean()) {
                        val index = rand.nextInt(allele1Data.asInstanceOf[Array[Double]].length)
                        allele1Data.asInstanceOf[Array[Double]](index) = allele1Data.asInstanceOf[Array[Double]](index) * (rand.nextFloat() * 2)
                    } else {
                        val index = rand.nextInt(allele2Data.asInstanceOf[Array[Double]].length)
                        allele2Data.asInstanceOf[Array[Double]](index) = allele2Data.asInstanceOf[Array[Double]](index) * (rand.nextFloat() * 2)
                    }
                case a if a == classOf[Array[Long]] =>
                    if(rand.nextBoolean()) {
                        val index = rand.nextInt(allele1Data.asInstanceOf[Array[Long]].length)
                        allele1Data.asInstanceOf[Array[Long]](index) = (allele1Data.asInstanceOf[Array[Long]](index) * (rand.nextFloat() * 2)).toLong
                    } else {
                        val index = rand.nextInt(allele2Data.asInstanceOf[Array[Long]].length)
                        allele2Data.asInstanceOf[Array[Long]](index) = (allele2Data.asInstanceOf[Array[Long]](index) * (rand.nextFloat() * 2)).toLong
                    }
                case _ =>

            }
        }
        chromosome
    }

    override def noMutations: IGene = {
        mutate = false
        this
    }
}
