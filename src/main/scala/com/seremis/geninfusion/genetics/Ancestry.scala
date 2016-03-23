package com.seremis.geninfusion.genetics

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.genetics.{IAncestry, IChromosome}
import com.seremis.geninfusion.api.util.TypedName
import net.minecraft.entity.EntityLiving

abstract class Ancestry(parent1: Option[IAncestry], parent2: Option[IAncestry]) extends IAncestry

case class AncestryLeaf(readableName: String, clzz: Class[_ <: EntityLiving]) extends Ancestry(None, None) {
    override def getUniqueAncestors(): Array[Class[_ <: EntityLiving]] = Array(clzz)

    override def determineGeneValueFromAncestry[A](geneName: TypedName[A]): IChromosome[A] = GIApiInterface.geneDefaultsRegistry.getDefaultValueForClass(clzz, geneName)

    override def toString = "AncestryLeaf[name = '" + readableName + "', clzz = '" + clzz.getName + "']"
}

case class AncestryNode(parent1: IAncestry, parent2: IAncestry) extends Ancestry(Some(parent1), Some(parent2)) {

    override def getUniqueAncestors(): Array[Class[_ <: EntityLiving]] = parent1.getUniqueAncestors() ++ parent2.getUniqueAncestors()

    override def determineGeneValueFromAncestry[A](geneName: TypedName[A]): IChromosome[A] = {
        val gene1 = parent1.determineGeneValueFromAncestry(geneName)
        val gene2 = parent2.determineGeneValueFromAncestry(geneName)

        GIApiInterface.geneRegistry.getGeneForName(geneName).inherit(gene1, gene2)
    }

    override def toString = "AncestryNode[parent1 = '" + parent1 + "', parent2 = '" + parent2 + "']"
}
