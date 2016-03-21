package com.seremis.geninfusion.api.genetics

import com.seremis.geninfusion.api.util.TypedName
import net.minecraft.entity.EntityLiving

trait IAncestry {

    def getUniqueAncestors(): Array[Class[_ <: EntityLiving]]

    def determineGeneValueFromAncestry[A](geneName: TypedName[A]): A
}
