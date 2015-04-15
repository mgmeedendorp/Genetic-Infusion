package seremis.geninfusion.soul

import java.util

import seremis.geninfusion.api.soul.{EnumAlleleType, IMasterGene}

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

abstract class MasterGene(alleleType: EnumAlleleType) extends Gene(alleleType) with IMasterGene {

    var controlledGenes: ListBuffer[String] = ListBuffer()

    override def getControlledGenes: util.List[String] = controlledGenes.toList

    override def addControlledGene(name: String) = controlledGenes += name
}
