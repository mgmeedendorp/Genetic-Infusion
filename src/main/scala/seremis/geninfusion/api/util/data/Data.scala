package seremis.geninfusion.api.util.data

import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import seremis.geninfusion.util.INBTTagable

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

class Data extends INBTTagable {

    var integerDataMap: Map[String, Integer] = Map()
    var floatDataMap: Map[String, Float] = Map()
    var doubleDataMap: Map[String, Double] = Map()
    var longDataMap: Map[String, Long] = Map()
    var booleanDataMap: Map[String, Boolean] = Map()
    var stringDataMap: Map[String, String] = Map()
    var byteDataMap: Map[String, Byte] = Map()
    var shortDataMap: Map[String, Short] = Map()
    var dataDataMap: Map[String, Data] = Map()
    var nbtDataMap: Map[String, NBTTagCompound] = Map()

    var objectDataMap: Map[String, Any] = Map()

    def this(compound: NBTTagCompound) {
        this()
        readFromNBT(compound)
    }

    def setBoolean(key: String, value: Boolean) {
        booleanDataMap += (key -> value)
    }

    def setByte(key: String, value: Byte) {
        byteDataMap += (key -> value)
    }

    def setShort(key: String, value: Short) {
        shortDataMap += (key -> value)
    }

    def setInteger(key: String, value: Int) {
        integerDataMap += (key -> value)
    }

    def setFloat(key: String, value: Float) {
        floatDataMap += (key -> value)
    }

    def setDouble(key: String, value: Double) {
        doubleDataMap += (key -> value)
    }

    def setLong(key: String, value: Long) {
        longDataMap += (key -> value)
    }

    def setString(key: String, value: String) {
        stringDataMap += (key -> value)
    }

    def setNBT(key: String, value: NBTTagCompound) {
        nbtDataMap += (key -> value)
    }

    def setData(key: String, value: Data) {
        dataDataMap += (key -> value)
    }

    /**
     * This sets an object. Objects will NOT be stored in NBT when the writeToNBT() method is called, so it will not
     * persist over saves.
     *
     * @param key   The name of the object
     * @param value The object to be stored
     */
    def setObject(key: String, value: Any) {
        objectDataMap += (key -> value)
    }

    def getBoolean(key: String): Boolean = {
        if (!booleanDataMap.contains(key)) false else booleanDataMap.get(key).get
    }

    def getByte(key: String): Byte = {
        if (!byteDataMap.contains(key)) 0 else byteDataMap.get(key).get
    }

    def getShort(key: String): Short = {
        if (!shortDataMap.contains(key)) 0 else shortDataMap.get(key).get
    }

    def getInteger(key: String): Int = {
        if (!integerDataMap.contains(key)) 0 else integerDataMap.get(key).get
    }

    def getFloat(key: String): Float = {
        if (!floatDataMap.contains(key)) 0 else floatDataMap.get(key).get
    }

    def getDouble(key: String): Double = {
        if (!doubleDataMap.contains(key)) 0 else doubleDataMap.get(key).get
    }

    def getLong(key: String): Long = {
        if (!longDataMap.contains(key)) 0 else longDataMap.get(key).get
    }

    def getString(key: String): String = if(!stringDataMap.contains(key)) null else stringDataMap.get(key).get

    def getNBT(key: String): NBTTagCompound = if(!nbtDataMap.contains(key)) null else nbtDataMap.get(key).get

    def getData(key: String): Data = if(!dataDataMap.contains(key)) null else dataDataMap.get(key).get

    /**
     * This gets an object. Objects will NOT be stored in NBT when the writeToNBT() method is called, so it will not
     * persist over saves.
     *
     * @param key The name of the object
     */
    def getObject(key: String): Any = objectDataMap.get(key).get

    def setBooleanArray(key: String, value: Array[Boolean]) {
        val arrayData = new Data()
        for (i <- 0 until value.length) {
            arrayData.setBoolean(key + "." + i, value(i))
        }
        arrayData.setInteger("length", value.length)
        setData(key, arrayData)
    }

    def setByteArray(key: String, value: Array[Byte]) {
        val arrayData = new Data()
        for (i <- 0 until value.length) {
            arrayData.setByte(key + "." + i, value(i))
        }
        arrayData.setInteger("length", value.length)
        setData(key, arrayData)
    }

    def setShortArray(key: String, value: Array[Short]) {
        val arrayData = new Data()
        for (i <- 0 until value.length) {
            arrayData.setShort(key + "." + i, value(i))
        }
        arrayData.setInteger("length", value.length)
        setData(key, arrayData)
    }

    def setIntegerArray(key: String, value: Array[Int]) {
        val arrayData = new Data()
        for (i <- 0 until value.length) {
            arrayData.setInteger(key + "." + i, value(i))
        }
        arrayData.setInteger("length", value.length)
        setData(key, arrayData)
    }

    def setFloatArray(key: String, value: Array[Float]) {
        val arrayData = new Data()
        for (i <- 0 until value.length) {
            arrayData.setFloat(key + "." + i, value(i))
        }
        arrayData.setInteger("length", value.length)
        setData(key, arrayData)
    }

    def setDoubleArray(key: String, value: Array[Double]) {
        val arrayData = new Data()
        for (i <- 0 until value.length) {
            arrayData.setDouble(key + "." + i, value(i))
        }
        arrayData.setInteger("length", value.length)
        setData(key, arrayData)
    }

    def setLongArray(key: String, value: Array[Long]) {
        val arrayData = new Data()
        for (i <- 0 until value.length) {
            arrayData.setLong(key + "." + i, value(i))
        }
        arrayData.setInteger("length", value.length)
        setData(key, arrayData)
    }

    def setStringArray(key: String, value: Array[String]) {
        val arrayData = new Data()
        for (i <- 0 until value.length) {
            arrayData.setString(key + "." + i, value(i))
        }
        arrayData.setInteger("length", value.length)
        setData(key, arrayData)
    }

    def setNBTArray(key: String, value: Array[NBTTagCompound]) {
        val arrayData = new Data()
        for (i <- 0 until value.length) {
            arrayData.setNBT(key + "." + i, value(i))
        }
        arrayData.setInteger("length", value.length)
        setData(key, arrayData)
    }

    def setDataArray(key: String, value: Array[Data]) {
        val arrayData = new Data()
        for (i <- 0 until value.length) {
            arrayData.setData(key + "." + i, value(i))
        }
        arrayData.setInteger("length", value.length)
        setData(key, arrayData)
    }

    def getBooleanArray(key: String): Array[Boolean] = {
        val arrayData = getData(key)
        if (arrayData != null) {
            val array = Array.ofDim[Boolean](arrayData.getInteger("length"))
            for (i <- 0 until array.length) {
                array(i) = arrayData.getBoolean(key + "." + i)
            }
            return array
        }
        null
    }

    def getByteArray(key: String): Array[Byte] = {
        val arrayData = getData(key)
        if (arrayData != null) {
            val array = Array.ofDim[Byte](arrayData.getInteger("length"))
            for (i <- 0 until array.length) {
                array(i) = arrayData.getByte(key + "." + i)
            }
            return array
        }
        null
    }

    def getShortArray(key: String): Array[Short] = {
        val arrayData = getData(key)
        if (arrayData != null) {
            val array = Array.ofDim[Short](arrayData.getInteger("length"))
            for (i <- 0 until array.length) {
                array(i) = arrayData.getShort(key + "." + i)
            }
            return array
        }
        null
    }

    def getIntegerArray(key: String): Array[Int] = {
        val arrayData = getData(key)
        if (arrayData != null) {
            val array = Array.ofDim[Int](arrayData.getInteger("length"))
            for (i <- 0 until array.length) {
                array(i) = arrayData.getInteger(key + "." + i)
            }
            return array
        }
        null
    }

    def getFloatArray(key: String): Array[Float] = {
        val arrayData = getData(key)
        if (arrayData != null) {
            val array = Array.ofDim[Float](arrayData.getInteger("length"))
            for (i <- 0 until array.length) {
                array(i) = arrayData.getFloat(key + "." + i)
            }
            return array
        }
        null
    }

    def getDoubleArray(key: String): Array[Double] = {
        val arrayData = getData(key)
        if (arrayData != null) {
            val array = Array.ofDim[Double](arrayData.getInteger("length"))
            for (i <- 0 until array.length) {
                array(i) = arrayData.getDouble(key + "." + i)
            }
            return array
        }
        null
    }

    def getLongArray(key: String): Array[Long] = {
        val arrayData = getData(key)
        if (arrayData != null) {
            val array = Array.ofDim[Long](arrayData.getInteger("length"))
            for (i <- 0 until array.length) {
                array(i) = arrayData.getLong(key + "." + i)
            }
            return array
        }
        null
    }

    def getStringArray(key: String): Array[String] = {
        val arrayData = getData(key)
        if (arrayData != null) {
            val array = Array.ofDim[String](arrayData.getInteger("length"))
            for (i <- 0 until array.length) {
                array(i) = arrayData.getString(key + "." + i)
            }
            return array
        }
        null
    }

    def getNBTArray(key: String): Array[NBTTagCompound] = {
        val arrayData = getData(key)
        if (arrayData != null) {
            val array = Array.ofDim[NBTTagCompound](arrayData.getInteger("length"))
            for (i <- 0 until array.length) {
                array(i) = arrayData.getNBT(key + "." + i)
            }
            return array
        }
        null
    }

    def getDataArray(key: String): Array[Data] = {
        val arrayData = getData(key)
        if (arrayData != null) {
            val array = Array.ofDim[Data](arrayData.getInteger("length"))
            for (i <- 0 until array.length) {
                array(i) = arrayData.getData(key + "." + i)
            }
            return array
        }
        null
    }

    override def toString: String = {
        "Data[booleans: " + booleanDataMap + ", bytes: " + byteDataMap + ", shorts: " + shortDataMap + ", integers: " + integerDataMap + ", floats: " + floatDataMap + ", doubles: " + doubleDataMap + ", longs: " + longDataMap + ", strings: " + stringDataMap + ", data: " + dataDataMap + ", nbt: " + nbtDataMap + ", object: " + objectDataMap + "]"
    }

    def isEmpty: Boolean = {
        booleanDataMap.isEmpty && byteDataMap.isEmpty && shortDataMap.isEmpty && integerDataMap.isEmpty && floatDataMap.isEmpty && doubleDataMap.isEmpty && longDataMap.isEmpty && stringDataMap.isEmpty && nbtDataMap.isEmpty && dataDataMap.isEmpty && objectDataMap.isEmpty
    }

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        val tagList = new NBTTagList()
        val stringList: ListBuffer[String] = ListBuffer()
        if (booleanDataMap.nonEmpty) {
            stringList.addAll(booleanDataMap.keySet)
            val booleanCompound = new NBTTagCompound()
            for (i <- 0 until booleanDataMap.size) {
                booleanCompound.setString("boolean" + i + "Name", stringList.get(i))
                booleanCompound.setBoolean("boolean" + i + "Value", booleanDataMap.get(stringList.get(i)).get)
            }
            booleanCompound.setString("type", "boolean")
            booleanCompound.setInteger("size", booleanDataMap.size)
            tagList.appendTag(booleanCompound)
            stringList.clear()
        }
        if (byteDataMap.nonEmpty) {
            stringList.addAll(byteDataMap.keySet)
            val byteCompound = new NBTTagCompound()
            for (i <- 0 until byteDataMap.size) {
                byteCompound.setString("byte" + i + "Name", stringList.get(i))
                byteCompound.setByte("byte" + i + "Value", byteDataMap.get(stringList.get(i)).get)
            }
            byteCompound.setString("type", "byte")
            byteCompound.setInteger("size", byteDataMap.size)
            tagList.appendTag(byteCompound)
            stringList.clear()
        }
        if (shortDataMap.nonEmpty) {
            stringList.addAll(shortDataMap.keySet)
            val shortCompound = new NBTTagCompound()
            for (i <- 0 until shortDataMap.size) {
                shortCompound.setString("short" + i + "Name", stringList.get(i))
                shortCompound.setShort("short" + i + "Value", shortDataMap.get(stringList.get(i)).get)
            }
            shortCompound.setString("type", "short")
            shortCompound.setInteger("size", shortDataMap.size)
            tagList.appendTag(shortCompound)
            stringList.clear()
        }
        if (integerDataMap.nonEmpty) {
            stringList.addAll(integerDataMap.keySet)
            val integerCompound = new NBTTagCompound()
            for (i <- 0 until integerDataMap.size) {
                integerCompound.setString("integer" + i + "Name", stringList.get(i))
                integerCompound.setInteger("integer" + i + "Value", integerDataMap.get(stringList.get(i)).get)
            }
            integerCompound.setString("type", "integer")
            integerCompound.setInteger("size", integerDataMap.size)
            tagList.appendTag(integerCompound)
            stringList.clear()
        }
        if (floatDataMap.nonEmpty) {
            stringList.addAll(floatDataMap.keySet)
            val floatCompound = new NBTTagCompound()
            for (i <- 0 until floatDataMap.size) {
                floatCompound.setString("float" + i + "Name", stringList.get(i))
                floatCompound.setFloat("float" + i + "Value", floatDataMap.get(stringList.get(i)).get)
            }
            floatCompound.setString("type", "float")
            floatCompound.setInteger("size", floatDataMap.size)
            stringList.clear()
        }
        if (doubleDataMap.nonEmpty) {
            stringList.addAll(doubleDataMap.keySet)
            val doubleCompound = new NBTTagCompound()
            for (i <- 0 until doubleDataMap.size) {
                doubleCompound.setString("double" + i + "Name", stringList.get(i))
                doubleCompound.setDouble("double" + i + "Value", doubleDataMap.get(stringList.get(i)).get)
            }
            doubleCompound.setString("type", "double")
            doubleCompound.setInteger("size", doubleDataMap.size)
            stringList.clear()
        }
        if (longDataMap.nonEmpty) {
            stringList.addAll(longDataMap.keySet)
            val longCompound = new NBTTagCompound()
            for (i <- 0 until longDataMap.size) {
                longCompound.setString("long" + i + "Name", stringList.get(i))
                longCompound.setLong("long" + i + "Value", longDataMap.get(stringList.get(i)).get)
            }
            longCompound.setString("type", "long")
            longCompound.setInteger("size", longDataMap.size)
            stringList.clear()
        }
        if (stringDataMap.nonEmpty) {
            stringList.addAll(stringDataMap.keySet)
            val stringCompound = new NBTTagCompound()
            for (i <- 0 until stringDataMap.size) {
                stringCompound.setString("string" + i + "Name", stringList.get(i))
                stringCompound.setString("string" + i + "Value", stringDataMap.get(stringList.get(i)).get)
            }
            stringCompound.setString("type", "string")
            stringCompound.setInteger("size", stringDataMap.size)
            stringList.clear()
        }
        if (nbtDataMap.nonEmpty) {
            stringList.addAll(nbtDataMap.keySet)
            val nbtCompound = new NBTTagCompound()
            for (i <- 0 until nbtDataMap.size) {
                nbtCompound.setString("nbt" + i + "Name", stringList.get(i))
                nbtCompound.setTag("nbt" + i + "Value", nbtDataMap.get(stringList.get(i)).get)
            }
            nbtCompound.setString("type", "nbt")
            nbtCompound.setInteger("size", nbtDataMap.size)
            stringList.clear()
        }
        if (dataDataMap.nonEmpty) {
            stringList.addAll(dataDataMap.keySet)
            val dataCompound = new NBTTagCompound()
            for (i <- 0 until dataDataMap.size) {
                dataCompound.setString("data" + i + "Name", stringList.get(i))
                val cmp = new NBTTagCompound()
                dataDataMap.get(stringList.get(i)).get.writeToNBT(cmp)
                dataCompound.setTag("data" + i + "Value", cmp)
            }
            dataCompound.setString("type", "data")
            dataCompound.setInteger("size", dataDataMap.size)
            stringList.clear()
        }
        compound.setTag("data", tagList)
        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        if (compound.hasKey("data")) {
            val tagList = compound.getTag("data").asInstanceOf[NBTTagList]
            var compoundBoolean: NBTTagCompound = null
            var compoundByte: NBTTagCompound = null
            var compoundShort: NBTTagCompound = null
            var compoundInteger: NBTTagCompound = null
            var compoundFloat: NBTTagCompound = null
            var compoundDouble: NBTTagCompound = null
            var compoundLong: NBTTagCompound = null
            var compoundString: NBTTagCompound = null
            var compoundNBT: NBTTagCompound = null
            var compoundData: NBTTagCompound = null
            for (i <- 0 until tagList.tagCount()) {
                if (tagList.getCompoundTagAt(i).getString("type") == "boolean") {
                    compoundBoolean = tagList.getCompoundTagAt(i)
                } else if (tagList.getCompoundTagAt(i).getString("type") == "byte") {
                    compoundByte = tagList.getCompoundTagAt(i)
                } else if (tagList.getCompoundTagAt(i).getString("type") == "short") {
                    compoundShort = tagList.getCompoundTagAt(i)
                } else if (tagList.getCompoundTagAt(i).getString("type") == "integer") {
                    compoundInteger = tagList.getCompoundTagAt(i)
                } else if (tagList.getCompoundTagAt(i).getString("type") == "float") {
                    compoundFloat = tagList.getCompoundTagAt(i)
                } else if (tagList.getCompoundTagAt(i).getString("type") == "double") {
                    compoundDouble = tagList.getCompoundTagAt(i)
                } else if (tagList.getCompoundTagAt(i).getString("type") == "long") {
                    compoundLong = tagList.getCompoundTagAt(i)
                } else if (tagList.getCompoundTagAt(i).getString("type") == "string") {
                    compoundString = tagList.getCompoundTagAt(i)
                } else if (tagList.getCompoundTagAt(i).getString("type") == "nbt") {
                    compoundNBT = tagList.getCompoundTagAt(i)
                } else if (tagList.getCompoundTagAt(i).getString("type") == "data") {
                    compoundData = tagList.getCompoundTagAt(i)
                }
            }
            if (compoundBoolean != null) {
                val size = compoundBoolean.getInteger("size")
                for (i <- 0 until size) {
                    val name = compoundBoolean.getString("boolean" + i + "Name")
                    val value = compoundBoolean.getBoolean("boolean" + i + "Value")
                    booleanDataMap.put(name, value)
                }
            }
            if (compoundByte != null) {
                val size = compoundByte.getInteger("size")
                for (i <- 0 until size) {
                    val name = compoundByte.getString("byte" + i + "Name")
                    val value = compoundByte.getByte("byte" + i + "Value")
                    byteDataMap.put(name, value)
                }
            }
            if (compoundShort != null) {
                val size = compoundShort.getInteger("size")
                for (i <- 0 until size) {
                    val name = compoundShort.getString("short" + i + "Name")
                    val value = compoundShort.getShort("short" + i + "Value")
                    shortDataMap.put(name, value)
                }
            }
            if (compoundInteger != null) {
                val size = compoundInteger.getInteger("size")
                for (i <- 0 until size) {
                    val name = compoundInteger.getString("integer" + i + "Name")
                    val value = compoundInteger.getInteger("integer" + i + "Value")
                    integerDataMap.put(name, value)
                }
            }
            if (compoundFloat != null) {
                val size = compoundFloat.getInteger("size")
                for (i <- 0 until size) {
                    val name = compoundFloat.getString("float" + i + "Name")
                    val value = compoundFloat.getFloat("float" + i + "Value")
                    floatDataMap.put(name, value)
                }
            }
            if (compoundDouble != null) {
                val size = compoundDouble.getInteger("size")
                for (i <- 0 until size) {
                    val name = compoundDouble.getString("double" + i + "Name")
                    val value = compoundDouble.getDouble("double" + i + "Value")
                    doubleDataMap.put(name, value)
                }
            }
            if (compoundLong != null) {
                val size = compoundLong.getInteger("size")
                for (i <- 0 until size) {
                    val name = compoundLong.getString("long" + i + "Name")
                    val value = compoundLong.getLong("long" + i + "Value")
                    longDataMap.put(name, value)
                }
            }
            if (compoundString != null) {
                val size = compoundString.getInteger("size")
                for (i <- 0 until size) {
                    val name = compoundString.getString("string" + i + "Name")
                    val value = compoundString.getString("string" + i + "Value")
                    stringDataMap.put(name, value)
                }
            }
            if (compoundNBT != null) {
                val size = compoundNBT.getInteger("size")
                for (i <- 0 until size) {
                    val name = compoundNBT.getString("nbt" + i + "Name")
                    val value = compoundNBT.getCompoundTag("nbt" + i + "Name")
                    nbtDataMap.put(name, value)
                }
            }
            if (compoundData != null) {
                val size = compoundData.getInteger("size")
                for (i <- 0 until size) {
                    val name = compoundData.getString("data" + i + "Name")
                    val value = compoundData.getCompoundTag("data" + i + "Value")
                    dataDataMap.put(name, new Data(value))
                }
            }
        }
        compound
    }

    override def equals(obj: Any): Boolean = {
        if (obj != null && obj.isInstanceOf[Data]) {
            val data = obj.asInstanceOf[Data]
            return booleanDataMap == data.booleanDataMap && byteDataMap == data.byteDataMap && shortDataMap == data.shortDataMap && integerDataMap == data.integerDataMap && floatDataMap == data.floatDataMap && doubleDataMap == data.doubleDataMap && longDataMap == data.longDataMap && stringDataMap == data.stringDataMap && nbtDataMap == data.nbtDataMap && dataDataMap == data.dataDataMap
        }
        false
    }

    def add(data: Data): Data = {
        booleanDataMap.putAll(data.booleanDataMap)
        byteDataMap.putAll(data.byteDataMap)
        shortDataMap.putAll(data.shortDataMap)
        integerDataMap.putAll(data.integerDataMap)
        floatDataMap.putAll(data.floatDataMap)
        doubleDataMap.putAll(data.doubleDataMap)
        longDataMap.putAll(data.longDataMap)
        stringDataMap.putAll(data.stringDataMap)
        nbtDataMap.putAll(data.nbtDataMap)
        dataDataMap.putAll(data.dataDataMap)
        this
    }
}