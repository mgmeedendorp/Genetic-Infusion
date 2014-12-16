package seremis.geninfusion.soul.entity.logic

import java.util

import net.minecraft.entity.EntityLiving
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import seremis.geninfusion.api.soul.util.Data
import seremis.geninfusion.helper.GIReflectionHelper
import seremis.geninfusion.util.INBTTagable

import scala.collection.mutable.ListBuffer

class VariableSyncLogic(entity: EntityLiving) extends INBTTagable {

    protected var data = new Data()
    protected var persistent: ListBuffer[String] = ListBuffer()

    protected var fields: ListBuffer[String] = {
        var clazz: Any = entity.getClass
        val list: ListBuffer[String] = ListBuffer()

        while(clazz != null) {
            for(field <- clazz.asInstanceOf[Class[_]].getDeclaredFields) {
                list += field.getName
            }
            clazz = clazz.asInstanceOf[Class[_]].getSuperclass
        }
        list
    }

    def makePersistent(name: String) {
        persistent += name
    }

    def setBoolean(name: String, variable: Boolean) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setBoolean(name, variable)
        }
    }

    def setByte(name: String, variable: Byte) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setByte(name, variable)
        }
    }

    def setShort(name: String, variable: Short) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setShort(name, variable)
        }
    }

    def setInteger(name: String, variable: Int) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setInteger(name, variable)
        }
    }

    def setFloat(name: String, variable: Float) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setFloat(name, variable)
        }
    }

    def setDouble(name: String, variable: Double) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setDouble(name, variable)
        }
    }

    def setLong(name: String, variable: Long) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setLong(name, variable)
        }
    }

    def setString(name: String, variable: String) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setString(name, variable)
        }
    }

    def setItemStack(name: String, variable: ItemStack) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setNBT(name, variable.writeToNBT(new NBTTagCompound()))
        }
    }

    def setNBT(name: String, variable: NBTTagCompound) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setNBT(name, variable)
        }
    }

    def setData(name: String, variable: Data) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setData(name, variable)
        }
    }

    def setObject(name: String, variable: Object) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {

        }
    }

    def getBoolean(name: String): Boolean = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Boolean]
        } else {
            data.getBoolean(name)
        }
    }

    def getByte(name: String): Byte = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Byte]
        } else {
            data.getByte(name)
        }
    }

    def getShort(name: String): Short = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Short]
        } else {
            data.getShort(name)
        }
    }

    def getInteger(name: String): Int = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Integer]
        } else {
            data.getInteger(name)
        }
    }

    def getFloat(name: String): Float = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Float]
        } else {
            data.getFloat(name)
        }
    }

    def getDouble(name: String): Double = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Double]
        } else {
            data.getDouble(name)
        }
    }

    def getLong(name: String): Long = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Long]
        } else {
            data.getLong(name)
        }
    }

    def getString(name: String): String = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[String]
        } else {
            data.getString(name)
        }
    }

    def getItemStack(name: String): ItemStack = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[ItemStack]
        } else {
            if(data.getNBT(name) != null) ItemStack.loadItemStackFromNBT(data.getNBT(name)) else null
        }
    }

    def getNBT(name: String): NBTTagCompound = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[NBTTagCompound]
        } else {
            data.getNBT(name)
        }
    }

    def getData(name: String): Data = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Data]
        } else {
            data.getData(name)
        }
    }

    def getObject(name: String): Object = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Object]
        }
        null
    }

    def setBooleanArray(name: String, variable: Array[Boolean]) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setBooleanArray(name, variable)
        }
    }

    def setByteArray(name: String, variable: Array[Byte]) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setByteArray(name, variable)
        }
    }

    def setShortArray(name: String, variable: Array[Short]) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setShortArray(name, variable)
        }
    }

    def setIntegerArray(name: String, variable: Array[Int]) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setIntegerArray(name, variable)
        }
    }

    def setFloatArray(name: String, variable: Array[Float]) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setFloatArray(name, variable)
        }
    }

    def setDoubleArray(name: String, variable: Array[Double]) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setDoubleArray(name, variable)
        }
    }

    def setLongArray(name: String, variable: Array[Long]) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setLongArray(name, variable)
        }
    }

    def setStringArray(name: String, variable: Array[String]) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setStringArray(name, variable)
        }
    }

    def setItemStackArray(name: String, variable: Array[ItemStack]) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setNBTArray(name, Array.tabulate(variable.length)(index => if(variable(index) != null) variable(index).writeToNBT(new NBTTagCompound()) else null))
        }
    }

    def setNBTArray(name: String, variable: Array[NBTTagCompound]) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setNBTArray(name, variable)
        }
    }

    def setDataArray(name: String, variable: Array[Data]) {
        if(fields.contains(name)) {
            GIReflectionHelper.setField(entity, name, variable)
        } else {
            data.setDataArray(name, variable)
        }
    }

    def getBooleanArray(name: String): Array[Boolean] = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Array[Boolean]]
        } else {
            data.getBooleanArray(name)
        }
    }

    def getByteArray(name: String): Array[Byte] = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Array[Byte]]
        } else {
            data.getByteArray(name)
        }
    }

    def getShortArray(name: String): Array[Short] = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Array[Short]]
        } else {
            data.getShortArray(name)
        }
    }

    def getIntegerArray(name: String): Array[Int] = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Array[Int]]
        } else {
            data.getIntegerArray(name)
        }
    }

    def getFloatArray(name: String): Array[Float] = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Array[Float]]
        } else {
            data.getFloatArray(name)
        }
    }

    def getDoubleArray(name: String): Array[Double] = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Array[Double]]
        } else {
            data.getDoubleArray(name)
        }
    }

    def getLongArray(name: String): Array[Long] = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Array[Long]]
        } else {
            data.getLongArray(name)
        }
    }

    def getStringArray(name: String): Array[String] = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Array[String]]
        } else {
            data.getStringArray(name)
        }
    }

    def getItemStackArray(name: String): Array[ItemStack] = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Array[ItemStack]]
        } else {
            if(getNBTArray(name) != null) Array.tabulate(getNBTArray(name).length)(index => if(getNBTArray(name)(index) != null) ItemStack.loadItemStackFromNBT(getNBTArray(name)(index)) else null) else null
        }
    }

    def getNBTArray(name: String): Array[NBTTagCompound] = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Array[NBTTagCompound]]
        } else {
            data.getNBTArray(name)
        }
    }

    def getDataArray(name: String): Array[Data] = {
        if(fields.contains(name)) {
            return GIReflectionHelper.getField(entity, name).asInstanceOf[Array[Data]]
        } else {
            data.getDataArray(name)
        }
    }

    protected val persistentData: Data = new Data()

    override def writeToNBT(compound: NBTTagCompound) {
        if(persistent.nonEmpty) {
            val tagList = new NBTTagList()
            val stringList = new util.ArrayList[String]()
            if(!data.booleanDataMap.isEmpty) {
                stringList.addAll(data.booleanDataMap.keySet)
                val booleanCompound = new NBTTagCompound()
                var j = 0
                for(i <- 0 until data.booleanDataMap.size) {
                    if(persistent.contains(stringList.get(i))) {
                        booleanCompound.setString("boolean" + j + "Name", stringList.get(i))
                        booleanCompound.setBoolean("boolean" + j + "Value", data.booleanDataMap.get(stringList.get(i)))
                        j += 1
                    }
                }
                if(j > 0) {
                    booleanCompound.setString("type", "boolean")
                    booleanCompound.setInteger("size", j)
                    tagList.appendTag(booleanCompound)
                }
                stringList.clear()
            }
            if(!data.byteDataMap.isEmpty) {
                stringList.addAll(data.byteDataMap.keySet)
                val byteCompound = new NBTTagCompound()
                var j = 0
                for(i <- 0 until data.byteDataMap.size) {
                    if(persistent.contains(stringList.get(i))) {
                        byteCompound.setString("byte" + j + "Name", stringList.get(i))
                        byteCompound.setByte("byte" + j + "Value", data.byteDataMap.get(stringList.get(i)))
                        j += 1
                    }
                }
                if(j > 0) {
                    byteCompound.setString("type", "byte")
                    byteCompound.setInteger("size", j)
                    tagList.appendTag(byteCompound)
                }
                stringList.clear()
            }
            if(!data.shortDataMap.isEmpty) {
                stringList.addAll(data.shortDataMap.keySet)
                val shortCompound = new NBTTagCompound()
                var j = 0
                for(i <- 0 until data.shortDataMap.size) {
                    if(persistent.contains(stringList.get(i))) {
                        shortCompound.setString("short" + j + "Name", stringList.get(i))
                        shortCompound.setShort("short" + j + "Value", data.shortDataMap.get(stringList.get(i)))
                        j += 1
                    }
                }
                if(j > 0) {
                    shortCompound.setString("type", "short")
                    shortCompound.setInteger("size", j)
                    tagList.appendTag(shortCompound)
                }
                stringList.clear()
            }
            if(!data.integerDataMap.isEmpty) {
                stringList.addAll(data.integerDataMap.keySet)
                val integerCompound = new NBTTagCompound()
                var j = 0
                for(i <- 0 until data.integerDataMap.size) {
                    if(persistent.contains(stringList.get(i))) {
                        integerCompound.setString("integer" + j + "Name", stringList.get(i))
                        integerCompound.setInteger("integer" + j + "Value", data.integerDataMap.get(stringList.get(i)))
                        j += 1
                    }
                }
                if(j > 0) {
                    integerCompound.setString("type", "integer")
                    integerCompound.setInteger("size", j)
                    tagList.appendTag(integerCompound)
                }
                stringList.clear()
            }
            if(!data.floatDataMap.isEmpty) {
                stringList.addAll(data.floatDataMap.keySet)
                val floatCompound = new NBTTagCompound()
                var j = 0
                for(i <- 0 until data.floatDataMap.size) {
                    if(persistent.contains(stringList.get(i))) {
                        floatCompound.setString("float" + j + "Name", stringList.get(i))
                        floatCompound.setFloat("float" + j + "Value", data.floatDataMap.get(stringList.get(i)))
                        j += 1
                    }
                }
                if(j > 0) {
                    floatCompound.setString("type", "float")
                    floatCompound.setInteger("size", j)
                    tagList.appendTag(floatCompound)
                }
                stringList.clear()
            }
            if(!data.doubleDataMap.isEmpty) {
                stringList.addAll(data.doubleDataMap.keySet)
                val doubleCompound = new NBTTagCompound()
                var j = 0
                for(i <- 0 until data.doubleDataMap.size) {
                    if(persistent.contains(stringList.get(i))) {
                        doubleCompound.setString("double" + j + "Name", stringList.get(i))
                        doubleCompound.setDouble("double" + j + "Value", data.doubleDataMap.get(stringList.get(i)))
                        j += 1
                    }
                }
                if(j > 0) {
                    doubleCompound.setString("type", "double")
                    doubleCompound.setInteger("size", j)
                    tagList.appendTag(doubleCompound)
                }
                stringList.clear()
            }
            if(!data.longDataMap.isEmpty) {
                stringList.addAll(data.longDataMap.keySet)
                val longCompound = new NBTTagCompound()
                var j = 0
                for(i <- 0 until data.longDataMap.size) {
                    if(persistent.contains(stringList.get(i))) {
                        longCompound.setString("long" + j + "Name", stringList.get(i))
                        longCompound.setLong("long" + j + "Value", data.longDataMap.get(stringList.get(i)))
                        j += 1
                    }
                }
                if(j > 0) {
                    longCompound.setString("type", "long")
                    longCompound.setInteger("size", j)
                    tagList.appendTag(longCompound)
                }
                stringList.clear()
            }
            if(!data.stringDataMap.isEmpty) {
                stringList.addAll(data.stringDataMap.keySet)
                val stringCompound = new NBTTagCompound()
                var j = 0
                for(i <- 0 until data.stringDataMap.size) {
                    if(persistent.contains(stringList.get(i))) {
                        stringCompound.setString("string" + j + "Name", stringList.get(i))
                        stringCompound.setString("string" + j + "Value", data.stringDataMap.get(stringList.get(i)))
                        j += 1
                    }
                }
                if(j > 0) {
                    stringCompound.setString("type", "string")
                    stringCompound.setInteger("size", j)
                    tagList.appendTag(stringCompound)
                }
                stringList.clear()
            }
            if(!data.nbtDataMap.isEmpty) {
                stringList.addAll(data.nbtDataMap.keySet)
                val nbtCompound = new NBTTagCompound()
                var j = 0
                for(i <- 0 until data.nbtDataMap.size) {
                    if(persistent.contains(stringList.get(i))) {
                        nbtCompound.setString("nbt" + j + "Name", stringList.get(i))
                        nbtCompound.setTag("nbt" + j + "Value", data.nbtDataMap.get(stringList.get(i)))
                        j += 1
                    }
                }
                if(j > 0) {
                    nbtCompound.setString("type", "nbt")
                    nbtCompound.setInteger("size", j)
                    tagList.appendTag(nbtCompound)
                }
                stringList.clear()
            }
            if(!data.dataDataMap.isEmpty) {
                stringList.addAll(data.dataDataMap.keySet)
                val dataCompound = new NBTTagCompound()
                var j = 0
                for(i <- 0 until data.dataDataMap.size) {
                    if(persistent.contains(stringList.get(i))) {
                        dataCompound.setString("data" + j + "Name", stringList.get(i))
                        val cmp = new NBTTagCompound()
                        data.dataDataMap.get(stringList.get(i)).writeToNBT(cmp)
                        dataCompound.setTag("data" + j + "Value", cmp)
                        j += 1
                    }
                }
                if(j > 0) {
                    dataCompound.setString("type", "data")
                    dataCompound.setInteger("size", j)
                    tagList.appendTag(dataCompound)
                }
                stringList.clear()
            }
            if(tagList.tagCount() > 0) {
                compound.setTag("data", tagList)
            }
        }
    }

    override def readFromNBT(compound: NBTTagCompound) {
        data.readFromNBT(compound)
    }

    private def setField(name: String, value: Any) {
        GIReflectionHelper.setField(this, name, value)
    }
}