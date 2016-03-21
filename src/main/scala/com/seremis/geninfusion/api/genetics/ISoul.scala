package com.seremis.geninfusion.api.genetics

import com.seremis.geninfusion.api.soulentity.IEntityMethod
import com.seremis.geninfusion.api.util.{INBTTagable, TypedName}

/**
  * The soul is the data structure that holds all of the data that persists through generations,
  * the genetic data of the entity. Every IEntitySoul has one ISoul of which half of the values will
  * be inherited by any offspring.
  * The ISoul is immutable, as the genetic data is determined the moment in which the genetic data
  * of the parents is determined, and it should not be changed throughout the entity's life.
  */
trait ISoul extends INBTTagable {

    /**
      * Returns the value of the active IAlleleData for this gene.
      */
    def getActiveValueForGene[A](geneName: TypedName[A]): A

    /**
      * Returns the value of the inactive IAlleleData of this gene.
      */
    def getInactiveValueForGene[A](geneName: TypedName[A]): A

    /**
      * Get the active and inactive IAlleleData for a gene name.
      * @param geneName The gene's name
      * @tparam A The type of the gene.
      * @return The IAlleleData for this gene. First data in the tuple is active, second inactive.
      */
    def getAlleleDataForGene[A](geneName: TypedName[A]): (IAlleleData[A], IAlleleData[A])

    /**
      * Returns the IAncestry object for this soul.
      */
    def getAncestry: IAncestry

    /**
      * Get a map with all the typed Gene names as keys and the corresponding
      * IAlleleData, the first element in the tuple being the active data, the second the inactive.
      * Every key-value pair has the same type parameters.
      */
    def getAllAlleleData: Map[TypedName[_], (IAlleleData[_], IAlleleData[_])]

    /**
      * Get all the IEntityMethods from all registered that are applicable to this soul.
      * @return all applicable IEntityMethods.
      */
    def getEntityMethods: Array[IEntityMethod[_]]

    /**
      * Checks whether all registered genes are defined for this soul,
      * then fixes any errors.
      * These changes can happen due to updated mods.
      * @return Whether the genome needed to be fixed.
      */
    def fixGenome(): Boolean
}
