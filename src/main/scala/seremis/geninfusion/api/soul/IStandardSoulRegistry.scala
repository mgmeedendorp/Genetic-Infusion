package seremis.geninfusion.api.soul

import net.minecraft.entity.EntityLiving

trait IStandardSoulRegistry {

    def register(standard: IStandardSoul)

    def getStandardSoulForEntity(entity: EntityLiving): Option[IStandardSoul]

    def getSoulForEntity(entity: EntityLiving): Option[ISoul]

    def getStandardSouls: Array[IStandardSoul]
}
