package seremis.geninfusion.soul

import seremis.geninfusion.api.soul.{IAlleleType, IAlleleTypeRegistry}

class AlleleTypeRegistry extends IAlleleTypeRegistry {

    var alleleTypes: Map[Class[_], IAlleleType] = Map()
    var alleleTypesInv: Map[IAlleleType, Class[_]] = Map()

    var alleleTypeIds: Map[IAlleleType, Int] = Map()
    var alleleTypeIdsInv: Map[Int, IAlleleType] = Map()

    override def registerAlleleType(alleleType: IAlleleType) {
        alleleTypes += (alleleType.getAlleleTypeClass -> alleleType)
        alleleTypesInv += (alleleType -> alleleType.getAlleleTypeClass)

        alleleTypeIds += (alleleType -> alleleTypeIds.size)
        alleleTypeIdsInv += (alleleTypeIdsInv.size -> alleleType)
    }

    override def getAlleleTypeForClass(clzz: Class[_]): IAlleleType = alleleTypes.get(clzz).get

    override def hasClassAlleleType(clzz: Class[_]): Boolean = getAlleleTypeForClass(clzz) != null

    override def getRegisteredAlleleTypes: List[IAlleleType] = alleleTypes.values.toList

    override def getAlleleTypeId(alleleType: IAlleleType): Int = alleleTypeIds.get(alleleType).get

    override def getAlleleTypeFromId(id: Int): IAlleleType = alleleTypeIdsInv.get(id).get
}
