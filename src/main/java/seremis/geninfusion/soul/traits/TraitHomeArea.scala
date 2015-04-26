package seremis.geninfusion.soul.traits

import net.minecraft.entity.EntityLiving
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ChunkCoordinates
import seremis.geninfusion.api.soul.IEntitySoulCustom

class TraitHomeArea extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        entity.makePersistent("maximumHomeDistance")
        entity.setFloat("maximumHomeDistance", -1.0F)
    }

    override def isWithinHomeDistanceCurrentPosition(entity: IEntitySoulCustom): Boolean = {
        val living = entity.asInstanceOf[EntityLiving]

        entity.isWithinHomeDistance(Math.floor(living.posX).toInt, Math.floor(living.posY).toInt, Math.floor(living.posZ).toInt)
    }

    override def isWithinHomeDistance(entity: IEntitySoulCustom, x: Int, y: Int, z: Int): Boolean = {
        val maximumHomeDistance = entity.getFloat("maximumHomeDistance")
        val homePosition = entity.getObject("homePosition").asInstanceOf[ChunkCoordinates]

        maximumHomeDistance == -1.0F || homePosition != null || homePosition.getDistanceSquared(x, y, z) < maximumHomeDistance * maximumHomeDistance
    }

    override def getHomePosition(entity: IEntitySoulCustom): ChunkCoordinates = entity.getObject("homePosition").asInstanceOf[ChunkCoordinates]


    override def setHomeArea(entity: IEntitySoulCustom, x: Int, y: Int, z: Int, maxDistance: Int) {
        val coords = new ChunkCoordinates(0, 0, 0)
        coords.set(x, y, z)

        entity.setObject("homePosition", coords)
        entity.setFloat("maximumHomeDistance", maxDistance.toFloat)
    }

    override def getMaxHomeDistance(entity: IEntitySoulCustom): Float = entity.getFloat("maximumHomeDistance")

    override def detachHome(entity: IEntitySoulCustom) {
        entity.setFloat("maximumHomeDistance", -1.0F)
    }

    override def hasHome(entity: IEntitySoulCustom): Boolean = {
        entity.getFloat("maximumHomeDistance") != -1.0F
    }

    override def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val coords = entity.getObject("homePosition").asInstanceOf[ChunkCoordinates]

        val coordCompound = new NBTTagCompound()
        coordCompound.setInteger("coordX", coords.posX)
        coordCompound.setInteger("coordY", coords.posY)
        coordCompound.setInteger("coordZ", coords.posZ)

        compound.setTag("homePosition", coordCompound)
    }

    override def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        if(compound.hasKey("homePosition")) {
            val coords = new ChunkCoordinates(0, 0, 0)

            val coordCompound = compound.getCompoundTag("homePosition")
            coords.posX = coordCompound.getInteger("coordX")
            coords.posY = coordCompound.getInteger("coordY")
            coords.posZ = coordCompound.getInteger("coordZ")

            entity.setObject("homePosition", coords)
        } else {
            entity.setObject("homePosition", new ChunkCoordinates(0, 0, 0))
        }
    }
}
