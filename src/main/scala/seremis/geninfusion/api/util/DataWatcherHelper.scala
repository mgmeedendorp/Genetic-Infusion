package seremis.geninfusion.api.util

import java.nio.ByteBuffer
import java.util

import net.minecraft.crash.CrashReport
import net.minecraft.entity.DataWatcher
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ChunkCoordinates
import seremis.geninfusion.api.soul.lib.VariableLib
import seremis.geninfusion.entity.GIEntity
import seremis.geninfusion.helper.GIReflectionHelper
import scala.collection.JavaConverters._
import scala.collection.immutable.HashMap

import scala.collection.mutable
import scala.collection.mutable.ListBuffer


object DataWatcherHelper {

    var ids: mutable.WeakHashMap[DataWatcher, Map[String, Int]] = mutable.WeakHashMap()
    var names: mutable.WeakHashMap[DataWatcher, ListBuffer[String]] = mutable.WeakHashMap()

    var clientSideIds: Map[Int, Map[String, Int]] = Map()
    var clientSideDataWatchers: Map[Int, DataWatcher] = Map()

    /**
     * Adds an object to a dataWatcher at the first empty Id.
     * Allowed Objects:
     * Byte
     * Short
     * Integer
     * Float
     * String
     * ItemStack
     * ChunkCoordinates
     *
     * @param dataWatcher The dataWatcher to add the object to.
     * @param obj The object to add to the dataWatcher.
     * @param name The name of the object.
     * @return The Id of the added object.
     */
    def addObjectAtUnusedId(dataWatcher: DataWatcher, obj: Any, name: String): Int = {
        if(!getWatchedEntity(dataWatcher).worldObj.isRemote) {
            val watchedObjects = getWatchedObjects(dataWatcher)
            val watchedObjectIds = getWatchedObjects(dataWatcher).keys.toList

            for(i <- Byte.MinValue.toInt to Byte.MaxValue.toInt) {
                if(!watchedObjectIds.contains(i)) {
                    addMapping(dataWatcher, i, name)
                    dataWatcher.addObject(i, obj.asInstanceOf[AnyRef])
                    return i
                }
            }
        } else if(!clientSideIds.contains(getWatchedEntity(dataWatcher).getEntityId) || !clientSideIds.get(getWatchedEntity(dataWatcher).getEntityId).get.contains(name)) {
            clientSideDataWatchers += (getWatchedEntity(dataWatcher).getEntityId -> dataWatcher)
        } else {
            val entity = getWatchedEntity(dataWatcher)
            val mapping = clientSideIds.get(entity.getEntityId).get
            val id = mapping.get(name).get

            addMapping(dataWatcher, id, name)
            dataWatcher.addObject(id, obj.asInstanceOf[AnyRef])

//            clientSideIds -= entity.getEntityId

            return id
        }

        println(CrashReport.makeCrashReport(new ArrayIndexOutOfBoundsException, "The dataWatcher mapping is completely filled. Try combining multiple variables into one to conserve mappings.").getCompleteReport)
        -1
    }
    
    def getWatchedObjects(dataWatcher: DataWatcher): Map[Int, DataWatcher.WatchableObject] = mapAsScalaMapConverter(GIReflectionHelper.getField(dataWatcher, VariableLib.DataWatcherWatchedObjects).asInstanceOf[util.HashMap[Int, DataWatcher.WatchableObject]]).asScala.toMap
    
    def setWatchedObjects(dataWatcher: DataWatcher, watchedObjects: Map[Int, DataWatcher.WatchableObject]) = GIReflectionHelper.setField(dataWatcher, VariableLib.DataWatcherWatchedObjects, watchedObjects)

    def getWatchedEntity(dataWatcher: DataWatcher): GIEntity = GIReflectionHelper.getField(dataWatcher, VariableLib.DataWatcherWatchedEntity).asInstanceOf[GIEntity]

    protected def addMapping(dataWatcher: DataWatcher, id: Int, name: String) {
        val entity = getWatchedEntity(dataWatcher)

        val entityIdArray = ByteBuffer.allocate(4).putInt(entity.getEntityId).array()
        val mappingIdArray = ByteBuffer.allocate(4).putInt(id).array()
        val mappingNameArray = name.getBytes

        entity.sendEntityDataToClient(-128, entityIdArray ++ mappingIdArray ++ mappingNameArray)

        var map: Map[String, Int] = null

        if(!ids.contains(dataWatcher))
            map = Map()
        else
            map = ids.get(dataWatcher).get

        map += (name -> id)
        ids += (dataWatcher -> map)

        var nameList: ListBuffer[String] = null

        if(!names.contains(dataWatcher))
            nameList = ListBuffer()
        else
            nameList = names.get(dataWatcher).get

        nameList += name
        names += (dataWatcher -> nameList)
    }

    /**
     * Gets an Object watched by a dataWatcher when it is registered through addObjectAtUnusedId().
     * @param dataWatcher The dataWatcher watching the requested object.
     * @param name The name of the object.
     * @return The object from the specified dataWatcher added with the given name.
     */
    def getObjectFromDataWatcher(dataWatcher: DataWatcher, name: String): AnyRef = {
        getWatchedObjects(dataWatcher).get(ids.get(dataWatcher).get.get(name).get).get.asInstanceOf[DataWatcher.WatchableObject].getObject
    }

    def getObjectId(dataWatcher: DataWatcher, name: String): Int = {
        ids.get(dataWatcher).get.get(name).get
    }

    def isNameRegistered(dataWatcher: DataWatcher, name: String): Boolean = names.contains(dataWatcher) && names.get(dataWatcher).get.contains(name)

    def updateObject(dataWatcher: DataWatcher, name: String, obj: Any) {
        dataWatcher.updateObject(getObjectId(dataWatcher, name), obj)
    }

    /**
     * Writes an object from a dataWatcher to NBT.
     * @param compound The NBTTagCompound to write the object to.
     * @param dataWatcher The dataWatcher containing this object.
     * @param name The name of this object as registered by addObjectAtUnusedId().
     * @return The NBTTagCompound with the object.
     */
    def writeObjectToNBT(compound: NBTTagCompound, dataWatcher: DataWatcher, name: String): NBTTagCompound = {
        val obj: Any = getObjectFromDataWatcher(dataWatcher, name)

        obj match {
            case b: Byte =>
                compound.setByte(name, b)
                compound.setString(name + ".type", "byte")
            case s: Short =>
                compound.setShort(name, s)
                compound.setString(name + ".type", "short")
            case i: Integer =>
                compound.setInteger(name, i)
                compound.setString(name + ".type", "integer")
            case f: Float =>
                compound.setFloat(name, f)
                compound.setString(name + ".type", "float")
            case s: String =>
                compound.setString(name, s)
                compound.setString(name + ".type", "string")
            case i: ItemStack =>
                compound.setTag(name, i.writeToNBT(new NBTTagCompound))
                compound.setString(name + ".type", "itemStack")
            case c: ChunkCoordinates =>
                compound.setInteger(name + ".x", c.posX)
                compound.setInteger(name + ".y", c.posY)
                compound.setInteger(name + ".z", c.posZ)
                compound.setString(name + ".type", "chunkCoordinates")
        }
        compound
    }

    /**
     * Reads an object from NBT and updates it's value in a dataWatcher. The object needs to be registered through addObjectAtUnusedId().
     * @param compound The NBTTagCompound containing the object.
     * @param dataWatcher The dataWatcher to add the object to.
     * @param name The name of the object.
     */
    def readObjectFromNBT(compound: NBTTagCompound, dataWatcher: DataWatcher, name: String) {
        if(ids.contains(dataWatcher)) {
            val id = getObjectId(dataWatcher, name)
            val dataType = compound.getString(name + ".type")

            if(dataType == "byte") dataWatcher.updateObject(id, compound.getByte(name))
            if(dataType == "short") dataWatcher.updateObject(id, compound.getShort(name))
            if(dataType == "integer") dataWatcher.updateObject(id, compound.getInteger(name))
            if(dataType == "float") dataWatcher.updateObject(id, compound.getFloat(name))
            if(dataType == "string") dataWatcher.updateObject(id, compound.getString(name))
            if(dataType == "itemStack") dataWatcher.updateObject(id, ItemStack.loadItemStackFromNBT(compound.getCompoundTag(name)))
            if(dataType == "chunkCoordinates") dataWatcher.updateObject(id, new ChunkCoordinates(compound.getInteger(name + ".x"), compound.getInteger(name + ".y"), compound.getInteger(name + ".z")))
        }
    }

    def receivePacketOnClient(value: Array[Byte]) {
        val entityIdArray = value.splitAt(4)._1
        val mappingIdArray = value.slice(4, 8)
        val mappingNameArray = value.splitAt(8)._2

        val entityId = ByteBuffer.wrap(entityIdArray).getInt
        val mappingId = ByteBuffer.wrap(mappingIdArray).getInt
        val mappingName = new String(mappingNameArray)

        if(!clientSideDataWatchers.contains(entityId)) {
            var map: Map[String, Int] = null

            if(clientSideIds.contains(entityId)) {
                map = clientSideIds.get(entityId).get
            } else {
                map = Map()
            }

            map += (mappingName -> mappingId)
            clientSideIds += (entityId -> map)
        } else {
            val dataWatcher = clientSideDataWatchers.get(entityId).get

            addMapping(dataWatcher, mappingId, mappingName)
            dataWatcher.addObject(id, git.asInstanceOf[AnyRef])
        }
    }
}
