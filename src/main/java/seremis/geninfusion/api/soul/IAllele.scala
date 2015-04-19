package seremis.geninfusion.api.soul

import seremis.geninfusion.util.INBTTagable

trait IAllele extends INBTTagable {

    def isDominant: Boolean

    def getAlleleData: Any
}
