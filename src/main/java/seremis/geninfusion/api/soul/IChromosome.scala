package seremis.geninfusion.api.soul

import seremis.geninfusion.util.INBTTagable

trait IChromosome extends INBTTagable {

    def getActive: IAllele

    def getRecessive: IAllele

    def getPrimary: IAllele

    def getSecondary: IAllele
}
