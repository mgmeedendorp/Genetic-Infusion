package seremis.geninfusion.soul.traits

import net.minecraft.entity.EntityLiving
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.IEntitySoulCustom

class TraitHomeArea extends Trait {

    override def entityInit(entity: IEntitySoulCustom) {
        entity.makePersistent(EntityMaximumHomeDistance)
        entity.setFloat(EntityMaximumHomeDistance, -1.0F)
        entity.setObject(EntityHomePosition, new ChunkCoordinates(0, 0, 0))
    }

    override def isWithinHomeDistanceCurrentPosition(entity: IEntitySoulCustom): Boolean = {
        val living = entity.asInstanceOf[EntityLiving]

        entity.isWithinHomeDistance_I(Math.floor(living.posX).toInt, Math.floor(living.posY).toInt, Math.floor(living.posZ).toInt)
    }

    override def isWithinHomeDistance(entity: IEntitySoulCustom, x: Int, y: Int, z: Int): Boolean = {
        val maximumHomeDistance = entity.getFloat(EntityMaximumHomeDistance)
        val homePosition = entity.getObject(EntityHomePosition).asInstanceOf[ChunkCoordinates]

        maximumHomeDistance == -1.0F || homePosition != null || homePosition.getDistanceSquared(x, y, z) < maximumHomeDistance * maximumHomeDistance
    }

    override def getHomePosition(entity: IEntitySoulCustom): ChunkCoordinates = entity.getObject(EntityHomePosition).asInstanceOf[ChunkCoordinates]


    override def setHomeArea(entity: IEntitySoulCustom, x: Int, y: Int, z: Int, maxDistance: Int) {
        val coords = new ChunkCoordinates(0, 0, 0)
        coords.set(x, y, z)

        entity.setObject(EntityHomePosition, coords)
        entity.setFloat(EntityMaximumHomeDistance, maxDistance.toFloat)
    }

    override def getMaxHomeDistance(entity: IEntitySoulCustom): Float = entity.getFloat(EntityMaximumHomeDistance)

    override def detachHome(entity: IEntitySoulCustom) {
        entity.setFloat(EntityMaximumHomeDistance, -1.0F)
    }

    override def hasHome(entity: IEntitySoulCustom): Boolean = {
        entity.getFloat(EntityMaximumHomeDistance) != -1.0F
    }

    override def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val coords = entity.getObject(EntityHomePosition).asInstanceOf[ChunkCoordinates]

        val coordCompound = new NBTTagCompound()
        coordCompound.setInteger("coordX", coords.posX)
        coordCompound.setInteger("coordY", coords.posY)
        coordCompound.setInteger("coordZ", coords.posZ)

        compound.setTag("homePosition", coordCompound)
    }

    override def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        if(compound.hasKey(EntityHomePosition)) {
            val coords = new ChunkCoordinates(0, 0, 0)

            val coordCompound = compound.getCompoundTag("homePosition")
            coords.posX = coordCompound.getInteger("coordX")
            coords.posY = coordCompound.getInteger("coordY")
            coords.posZ = coordCompound.getInteger("coordZ")

            entity.setObject(EntityHomePosition, coords)
        } else {
            entity.setObject(EntityHomePosition, new ChunkCoordinates(0, 0, 0))
        }
    }
}
