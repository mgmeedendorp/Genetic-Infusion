package seremis.geninfusion.api.soul

import seremis.geninfusion.util.INBTTagable

trait IAllele extends INBTTagable {

    def isDominant: Boolean

    def setAlleleData(data: Any)
    def getAlleleData: Any
    def getAlleleType: IAlleleType
}
