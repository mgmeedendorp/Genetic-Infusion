package com.seremis.geninfusion.api.genetics

import com.seremis.geninfusion.api.util.{INBTTagable, TypedName}
import net.minecraft.entity.EntityLiving

trait IAncestry extends INBTTagable {

    def getUniqueAncestors(): Array[Class[_ <: EntityLiving]]

    def determineGeneValueFromAncestry[A](geneName: TypedName[A]): IChromosome[A]
}
