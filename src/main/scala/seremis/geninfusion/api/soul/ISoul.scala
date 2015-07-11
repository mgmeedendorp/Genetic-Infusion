package seremis.geninfusion.api.soul

import seremis.geninfusion.util.INBTTagable

trait ISoul extends INBTTagable {

    def getChromosomes: Array[IChromosome]
}
