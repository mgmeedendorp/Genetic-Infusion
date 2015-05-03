package seremis.geninfusion.soul.traits

import net.minecraft.entity.EntityLiving
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ChunkCoordinates
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.IEntitySoulCustom

class TraitHomeArea extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        entity.makePersistent(ENTITY_MAXIMUM_HOME_DISTANCE)
        entity.setFloat(ENTITY_MAXIMUM_HOME_DISTANCE, -1.0F)
    }

    override def isWithinHomeDistanceCurrentPosition(entity: IEntitySoulCustom): Boolean = {
        val living = entity.asInstanceOf[EntityLiving]

        entity.isWithinHomeDistance(Math.floor(living.posX).toInt, Math.floor(living.posY).toInt, Math.floor(living.posZ).toInt)
    }

    override def isWithinHomeDistance(entity: IEntitySoulCustom, x: Int, y: Int, z: Int): Boolean = {
        val maximumHomeDistance = entity.getFloat(ENTITY_MAXIMUM_HOME_DISTANCE)
        val homePosition = entity.getObject(ENTITY_HOME_POSITION).asInstanceOf[ChunkCoordinates]

        maximumHomeDistance == -1.0F || homePosition != null || homePosition.getDistanceSquared(x, y, z) < maximumHomeDistance * maximumHomeDistance
    }

    override def getHomePosition(entity: IEntitySoulCustom): ChunkCoordinates = entity.getObject(ENTITY_HOME_POSITION).asInstanceOf[ChunkCoordinates]


    override def setHomeArea(entity: IEntitySoulCustom, x: Int, y: Int, z: Int, maxDistance: Int) {
        val coords = new ChunkCoordinates(0, 0, 0)
        coords.set(x, y, z)

        entity.setObject(ENTITY_HOME_POSITION, coords)
        entity.setFloat(ENTITY_MAXIMUM_HOME_DISTANCE, maxDistance.toFloat)
    }

    override def getMaxHomeDistance(entity: IEntitySoulCustom): Float = entity.getFloat(ENTITY_MAXIMUM_HOME_DISTANCE)

    override def detachHome(entity: IEntitySoulCustom) {
        entity.setFloat(ENTITY_MAXIMUM_HOME_DISTANCE, -1.0F)
    }

    override def hasHome(entity: IEntitySoulCustom): Boolean = {
        entity.getFloat(ENTITY_MAXIMUM_HOME_DISTANCE) != -1.0F
    }

    override def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val coords = entity.getObject(ENTITY_HOME_POSITION).asInstanceOf[ChunkCoordinates]

        val coordCompound = new NBTTagCompound()
        coordCompound.setInteger("coordX", coords.posX)
        coordCompound.setInteger("coordY", coords.posY)
        coordCompound.setInteger("coordZ", coords.posZ)

        compound.setTag("homePosition", coordCompound)
    }

    override def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        if(compound.hasKey(ENTITY_HOME_POSITION)) {
            val coords = new ChunkCoordinates(0, 0, 0)

            val coordCompound = compound.getCompoundTag("homePosition")
            coords.posX = coordCompound.getInteger("coordX")
            coords.posY = coordCompound.getInteger("coordY")
            coords.posZ = coordCompound.getInteger("coordZ")

            entity.setObject(ENTITY_HOME_POSITION, coords)
        } else {
            entity.setObject(ENTITY_HOME_POSITION, new ChunkCoordinates(0, 0, 0))
        }
    }
}
