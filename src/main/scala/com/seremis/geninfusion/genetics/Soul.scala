package com.seremis.geninfusion.genetics

import com.seremis.geninfusion.api.genetics.{IAlleleData, IAncestry, ISoul}
import com.seremis.geninfusion.api.soulentity.IEntityMethod
import com.seremis.geninfusion.api.util.TypedName
import net.minecraft.nbt.NBTTagCompound

class Soul extends ISoul {

    /**
      * Returns the value of the active IAlleleData for this gene.
      */
    override def getActiveValueForGene[A](geneName: TypedName[A]): A = ???

    /**
      * Checks whether all registered genes are defined for this soul,
      * then fixes any errors.
      * These changes can happen due to updated mods.
      *
      * @return Whether the genome needed to be fixed.
      */
    override def fixGenome(): Boolean = ???

    /**
      * Get the active and inactive IAlleleData for a gene name.
      *
      * @param geneName The gene's name
      * @tparam A The type of the gene.
      * @return The IAlleleData for this gene. First data in the tuple is active, second inactive.
      */
    override def getAlleleDataForGene[A](geneName: TypedName[A]): (IAlleleData[A], IAlleleData[A]) = ???

    /**
      * Get all the IEntityMethods from all registered that are applicable to this soul.
      *
      * @return all applicable IEntityMethods.
      */
    override def getEntityMethods: Array[IEntityMethod[_]] = ???

    /**
      * Get a map with all the typed Gene names as keys and the corresponding
      * IAlleleData, the first element in the tuple being the active data, the second the inactive.
      * Every key-value pair has the same type parameters.
      */
    override def getAllAlleleData: Map[TypedName[_], (IAlleleData[_], IAlleleData[_])] = ???

    /**
      * Returns the value of the inactive IAlleleData of this gene.
      */
    override def getInactiveValueForGene[A](geneName: TypedName[A]): A = ???

    /**
      * Returns the IAncestry object for this soul.
      */
    override def getAncestry: IAncestry = ???

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = ???

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = ???
}
