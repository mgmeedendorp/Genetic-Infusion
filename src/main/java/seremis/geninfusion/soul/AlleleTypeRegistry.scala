package seremis.geninfusion.soul

import seremis.geninfusion.api.soul.{IAlleleType, IAlleleTypeRegistry}

class AlleleTypeRegistry extends IAlleleTypeRegistry {

    var alleleTypes: Map[Class[_], IAlleleType] = Map()
    var alleleTypesInv: Map[IAlleleType, Class[_]] = Map()

    override def registerAlleleType(alleleType: IAlleleType) {
        alleleTypes += (alleleType.getAlleleTypeClass -> alleleType)
        alleleTypesInv += (alleleType -> alleleType.getAlleleTypeClass)
    }

    override def getAlleleTypeForClass(clzz: Class[_]): IAlleleType = alleleTypes.get(clzz).get

    override def hasClassAlleleType(clzz: Class[_]): Boolean = getAlleleTypeForClass(clzz) != null
}
