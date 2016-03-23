package com.seremis.geninfusion.registry

import com.seremis.geninfusion.GeneticInfusion
import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.GIApiInterface.IGeneDefaultsRegistry
import com.seremis.geninfusion.api.genetics.{IChromosome, ISoul}
import com.seremis.geninfusion.api.util.TypedName
import com.seremis.geninfusion.genetics.{AncestryLeaf, Soul}
import net.minecraft.entity.{EntityList, EntityLiving}
import org.apache.logging.log4j.Level

import scala.collection.immutable.TreeMap
import scala.collection.mutable.{HashMap, ListBuffer}

class GeneDefaultsRegistry extends IGeneDefaultsRegistry {

    var defaultsMap: HashMap[Class[_ <: EntityLiving], ListBuffer[(TypedName[_], IChromosome[_])]] = HashMap()

    override def register[A](clzz: Class[_ <: EntityLiving], geneName: TypedName[A], defaultValue: IChromosome[A]): Unit = {
        var list: ListBuffer[(TypedName[_], IChromosome[_])] = defaultsMap.getOrElse(clzz, ListBuffer())

        list += (geneName -> defaultValue)

        defaultsMap += (clzz -> list)
    }

    override def isClassRegistered(clzz: Class[_ <: EntityLiving]): Boolean = defaultsMap.get(clzz).nonEmpty

    @throws[IllegalArgumentException]
    override def getDefaultValueForClass[A](clzz: Class[_ <: EntityLiving], geneName: TypedName[A]): IChromosome[A] = {
        val option = defaultsMap.get(clzz)

        if(option.nonEmpty) {
            val defaults = option.get

            for(default <- defaults) {
                if(default._1 == geneName) {
                    return default._2.asInstanceOf[IChromosome[A]]
                }
            }
            GeneticInfusion.logger.log(Level.INFO, "The registered default gene '" + geneName.name + "' for '" + clzz.getName + "' is missing, returning defaults. This may go lead to weird behaviour.")

            GIApiInterface.geneRegistry.getGeneForName(geneName).getDefaultValue
        } else {
            clzzNotRegistered(clzz)
        }
    }

    @throws[IllegalArgumentException]
    override def getSoulForClass(clzz: Class[_ <: EntityLiving]): ISoul = {
        val option = defaultsMap.get(clzz)

        if(option.nonEmpty) {
            val ancestry = AncestryLeaf(EntityList.classToStringMapping.get(clzz), clzz)
            var data = TreeMap.empty[TypedName[_], IChromosome[_]]

            val defaults = option.get

            for(default <- defaults) {
                data += (default._1 -> default._2)
            }

            new Soul(data, ancestry)
        } else {
            clzzNotRegistered(clzz)
        }
    }

    @throws[IllegalArgumentException]
    def clzzNotRegistered(clzz: Class[_]) = throw new IllegalArgumentException("There are no registered genes for class with name '" + clzz.getName + "', did someone request data too early (before/during pre-init) or register too late (after pre-init)?")
}
