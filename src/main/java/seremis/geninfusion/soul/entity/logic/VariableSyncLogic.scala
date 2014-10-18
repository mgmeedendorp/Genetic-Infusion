package seremis.geninfusion.soul.entity.logic

import java.util.Map.Entry
import java.{lang, util}

import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import seremis.geninfusion.api.soul.util.Data
import seremis.geninfusion.helper.DataHelper
import seremis.geninfusion.util.INBTTagable

import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

class VariableSyncLogic(entity: IVariableSyncEntity) extends INBTTagable {

  protected var data = new Data()
  protected var persistent: ListBuffer[String] = ListBuffer()

  def makePersistent(name: String): Unit = {
    persistent+(name)
  }

  def setBoolean(name: String, variable: Boolean) = data.setBoolean(name, variable)

  def setByte(name: String, variable: Byte) = data.setByte(name, variable)

  def setShort(name: String, variable: Short) = data.setShort(name, variable)

  def setInteger(name: String, variable: Int) = data.setInteger(name, variable)

  def setFloat(name: String, variable: Float) = data.setFloat(name, variable)

  def setDouble(name: String, variable: Double) = data.setDouble(name, variable)

  def setLong(name: String, variable: Long) = data.setLong(name, variable)

  def setString(name: String, variable: String) = data.setString(name, variable)

  def setItemStack(name: String, variable: ItemStack) = data.setNBT(name, variable.writeToNBT(new NBTTagCompound()))

  def setNBT(name: String, variable: NBTTagCompound) = data.setNBT(name, variable)

  def setData(name: String, variable: Data) = data.setData(name, variable)

  def getBoolean(name: String): Boolean = data.getBoolean(name)

  def getByte(name: String): Byte = data.getByte(name)

  def getShort(name: String): Short = data.getShort(name)

  def getInteger(name: String): Int = data.getInteger(name)

  def getFloat(name: String): Float = data.getFloat(name)

  def getDouble(name: String): Double = data.getDouble(name)

  def getLong(name: String): Long = data.getLong(name)

  def getString(name: String): String = data.getString(name)

  def getItemStack(name: String): ItemStack = if(data.getNBT(name) != null) ItemStack.loadItemStackFromNBT(data.getNBT(name)) else null

  def getNBT(name: String): NBTTagCompound = data.getNBT(name)

  def getData(name: String): Data = data.getData(name)

  protected val persistentData: Data = new Data()

  override def writeToNBT(compound: NBTTagCompound) {
    if(!persistent.isEmpty) {
      val tagList = new NBTTagList()
      val stringList = new util.ArrayList[String]()
      if (!data.booleanDataMap.isEmpty) {
        stringList.addAll(data.booleanDataMap.keySet)
        val booleanCompound = new NBTTagCompound()
        var j = 0
        for (i <- 0 until data.booleanDataMap.size) {
          if (persistent.contains(stringList.get(i))) {
            booleanCompound.setString("boolean" + j + "Name", stringList.get(i))
            booleanCompound.setBoolean("boolean" + j + "Value", data.booleanDataMap.get(stringList.get(i)))
            j += 1
          }
        }
        if (j > 0) {
          booleanCompound.setString("type", "boolean")
          booleanCompound.setInteger("size", j)
          tagList.appendTag(booleanCompound)
        }
        stringList.clear()
      }
      if (!data.byteDataMap.isEmpty) {
        stringList.addAll(data.byteDataMap.keySet)
        val byteCompound = new NBTTagCompound()
        var j = 0
        for (i <- 0 until data.byteDataMap.size) {
          if (persistent.contains(stringList.get(i))) {
            byteCompound.setString("byte" + j + "Name", stringList.get(i))
            byteCompound.setByte("byte" + j + "Value", data.byteDataMap.get(stringList.get(i)))
            j += 1
          }
        }
        if (j > 0) {
          byteCompound.setString("type", "byte")
          byteCompound.setInteger("size", j)
          tagList.appendTag(byteCompound)
        }
        stringList.clear()
      }
      if (!data.shortDataMap.isEmpty) {
        stringList.addAll(data.shortDataMap.keySet)
        val shortCompound = new NBTTagCompound()
        var j = 0
        for (i <- 0 until data.shortDataMap.size) {
          if (persistent.contains(stringList.get(i))) {
            shortCompound.setString("short" + j + "Name", stringList.get(i))
            shortCompound.setShort("short" + j + "Value", data.shortDataMap.get(stringList.get(i)))
            j += 1
          }
        }
        if (j > 0) {
          shortCompound.setString("type", "short")
          shortCompound.setInteger("size", j)
          tagList.appendTag(shortCompound)
        }
        stringList.clear()
      }
      if (!data.integerDataMap.isEmpty) {
        stringList.addAll(data.integerDataMap.keySet)
        val integerCompound = new NBTTagCompound()
        var j = 0
        for (i <- 0 until data.integerDataMap.size) {
          if (persistent.contains(stringList.get(i))) {
            integerCompound.setString("integer" + j + "Name", stringList.get(i))
            integerCompound.setInteger("integer" + j + "Value", data.integerDataMap.get(stringList.get(i)))
            j += 1
          }
        }
        if (j > 0) {
          integerCompound.setString("type", "integer")
          integerCompound.setInteger("size", j)
          tagList.appendTag(integerCompound)
        }
        stringList.clear()
      }
      if (!data.floatDataMap.isEmpty) {
        stringList.addAll(data.floatDataMap.keySet)
        val floatCompound = new NBTTagCompound()
        var j = 0
        for (i <- 0 until data.floatDataMap.size) {
          if (persistent.contains(stringList.get(i))) {
            floatCompound.setString("float" + j + "Name", stringList.get(i))
            floatCompound.setFloat("float" + j + "Value", data.floatDataMap.get(stringList.get(i)))
            j += 1
          }
        }
        if (j > 0) {
          floatCompound.setString("type", "float")
          floatCompound.setInteger("size", j)
          tagList.appendTag(floatCompound)
        }
        stringList.clear()
      }
      if (!data.doubleDataMap.isEmpty) {
        stringList.addAll(data.doubleDataMap.keySet)
        val doubleCompound = new NBTTagCompound()
        var j = 0
        for (i <- 0 until data.doubleDataMap.size) {
          if (persistent.contains(stringList.get(i))) {
            doubleCompound.setString("double" + j + "Name", stringList.get(i))
            doubleCompound.setDouble("double" + j + "Value", data.doubleDataMap.get(stringList.get(i)))
            j += 1
          }
        }
        if (j > 0) {
          doubleCompound.setString("type", "double")
          doubleCompound.setInteger("size", j)
          tagList.appendTag(doubleCompound)
        }
        stringList.clear()
      }
      if (!data.longDataMap.isEmpty) {
        stringList.addAll(data.longDataMap.keySet)
        val longCompound = new NBTTagCompound()
        var j = 0
        for (i <- 0 until data.longDataMap.size) {
          if (persistent.contains(stringList.get(i))) {
            longCompound.setString("long" + j + "Name", stringList.get(i))
            longCompound.setLong("long" + j + "Value", data.longDataMap.get(stringList.get(i)))
            j += 1
          }
        }
        if (j > 0) {
          longCompound.setString("type", "long")
          longCompound.setInteger("size", j)
          tagList.appendTag(longCompound)
        }
        stringList.clear()
      }
      if (!data.stringDataMap.isEmpty) {
        stringList.addAll(data.stringDataMap.keySet)
        val stringCompound = new NBTTagCompound()
        var j = 0
        for (i <- 0 until data.stringDataMap.size) {
          if (persistent.contains(stringList.get(i))) {
            stringCompound.setString("string" + j + "Name", stringList.get(i))
            stringCompound.setString("string" + j + "Value", data.stringDataMap.get(stringList.get(i)))
            j += 1
          }
        }
        if (j > 0) {
          stringCompound.setString("type", "string")
          stringCompound.setInteger("size", j)
          tagList.appendTag(stringCompound)
        }
        stringList.clear()
      }
      if (!data.nbtDataMap.isEmpty) {
        stringList.addAll(data.nbtDataMap.keySet)
        val nbtCompound = new NBTTagCompound()
        var j = 0
        for (i <- 0 until data.nbtDataMap.size) {
          if (persistent.contains(stringList.get(i))) {
            nbtCompound.setString("nbt" + j + "Name", stringList.get(i))
            nbtCompound.setTag("nbt" + j + "Value", data.nbtDataMap.get(stringList.get(i)))
            j += 1
          }
        }
        if (j > 0) {
          nbtCompound.setString("type", "nbt")
          nbtCompound.setInteger("size", j)
          tagList.appendTag(nbtCompound)
        }
        stringList.clear()
      }
      if (!data.dataDataMap.isEmpty) {
        stringList.addAll(data.dataDataMap.keySet)
        val dataCompound = new NBTTagCompound()
        var j = 0
        for (i <- 0 until data.dataDataMap.size) {
          if (persistent.contains(stringList.get(i))) {
            dataCompound.setString("data" + j + "Name", stringList.get(i))
            val cmp = new NBTTagCompound()
            data.dataDataMap.get(stringList.get(i)).writeToNBT(cmp)
            dataCompound.setTag("data" + j + "Value", cmp)
            j += 1
          }
        }
        if (j > 0) {
          dataCompound.setString("type", "data")
          dataCompound.setInteger("size", j)
          tagList.appendTag(dataCompound)
        }
        stringList.clear()
      }
      if (tagList.tagCount() > 0) {
        compound.setTag("data", tagList)

        val persistentCompound = new NBTTagCompound()
        for((variable, count) <- persistent.zipWithIndex) {
          persistentCompound.setString("persistent" + count, variable)
        }
        compound.setTag("persistent", persistentCompound)
      }
    }
  }

  override def readFromNBT(compound: NBTTagCompound) {
    data.readFromNBT(compound)
  }

  def forceVariableSync() {
    this.syncVariables()
  }

  //The variables as they were the last tick
  protected var prevData: Data = new Data()

  def syncVariables() {
    //The variables in the entity class
    val data = DataHelper.writePrimitives(entity)
    val clss = entity.getClass

    val boolIterator: util.Iterator[Entry[String, lang.Boolean]] = prevData.booleanDataMap.entrySet().iterator()

    while(boolIterator.hasNext()) {
      val entry: Entry[String, lang.Boolean] = boolIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.booleanDataMap.containsKey(key) && data.booleanDataMap.get(key) != value) {
        prevData.booleanDataMap.put(key, data.booleanDataMap.get(key))
        data.booleanDataMap.put(key, data.booleanDataMap.get(key))
      } else if(data.booleanDataMap.containsKey(key) && data.getBoolean(key) != value) {
        prevData.booleanDataMap.put(key, data.getBoolean(key))
        data.booleanDataMap.put(key, data.getBoolean(key))
        setField(key, data.getBoolean(key))
      }
    }

    val byteIterator: util.Iterator[Entry[String, lang.Byte]] = prevData.byteDataMap.entrySet().iterator()

    while(byteIterator.hasNext()) {
      val entry: Entry[String, lang.Byte] = byteIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.byteDataMap.containsKey(key) && data.byteDataMap.get(key) != value) {
        prevData.byteDataMap.put(key, data.byteDataMap.get(key))
        data.byteDataMap.put(key, data.byteDataMap.get(key))
      } else if(data.byteDataMap.containsKey(key) && data.getByte(key) != value) {
        prevData.byteDataMap.put(key, data.getByte(key))
        data.byteDataMap.put(key, data.getByte(key))
        setField(key, data.getByte(key))
      }
    }

    val shortIterator: util.Iterator[Entry[String, lang.Short]] = prevData.shortDataMap.entrySet().iterator()

    while(shortIterator.hasNext()) {
      val entry: Entry[String, lang.Short] = shortIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.shortDataMap.containsKey(key) && data.shortDataMap.get(key) != value) {
        prevData.shortDataMap.put(key, data.shortDataMap.get(key))
        data.shortDataMap.put(key, data.shortDataMap.get(key))
      } else if(data.shortDataMap.containsKey(key) && data.getShort(key) != value) {
        prevData.shortDataMap.put(key, data.getShort(key))
        data.shortDataMap.put(key, data.getShort(key))
        setField(key, data.getShort(key))
      }
    }

    val intIterator: util.Iterator[Entry[String, lang.Integer]] = prevData.integerDataMap.entrySet().iterator()

    while(intIterator.hasNext()) {
      val entry: Entry[String, lang.Integer] = intIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.integerDataMap.containsKey(key) && data.integerDataMap.get(key) != value) {
        prevData.integerDataMap.put(key, data.integerDataMap.get(key))
        data.integerDataMap.put(key, data.integerDataMap.get(key))
      } else if(data.integerDataMap.containsKey(key) && data.getInteger(key) != value) {
        prevData.integerDataMap.put(key, data.getInteger(key))
        data.integerDataMap.put(key, data.getInteger(key))
        setField(key, data.getInteger(key))
      }
    }

    val floatIterator: util.Iterator[Entry[String, lang.Float]] = prevData.floatDataMap.entrySet().iterator()

    while(floatIterator.hasNext()) {
      val entry: Entry[String, lang.Float] = floatIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.floatDataMap.containsKey(key) && data.floatDataMap.get(key) != value) {
        prevData.floatDataMap.put(key, data.floatDataMap.get(key))
        data.floatDataMap.put(key, data.floatDataMap.get(key))
      } else if(data.floatDataMap.containsKey(key) && data.getFloat(key) != value) {
        prevData.floatDataMap.put(key, data.getFloat(key))
        data.floatDataMap.put(key, data.getFloat(key))
        setField(key, data.getFloat(key))
      }
    }

    val doubleIterator: util.Iterator[Entry[String, lang.Double]] = prevData.doubleDataMap.entrySet().iterator()

    while(doubleIterator.hasNext()) {
      val entry: Entry[String, lang.Double] = doubleIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.doubleDataMap.containsKey(key) && data.doubleDataMap.get(key) != value) {
        prevData.doubleDataMap.put(key, data.doubleDataMap.get(key))
        data.doubleDataMap.put(key, data.doubleDataMap.get(key))
      } else if(data.doubleDataMap.containsKey(key) && data.getDouble(key) != value) {
        prevData.doubleDataMap.put(key, data.getDouble(key))
        data.doubleDataMap.put(key, data.getDouble(key))
        setField(key, data.getDouble(key))
      }
    }

    val longIterator: util.Iterator[Entry[String, lang.Long]] = prevData.longDataMap.entrySet().iterator()

    while(longIterator.hasNext()) {
      val entry: Entry[String, lang.Long] = longIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.longDataMap.containsKey(key) && data.longDataMap.get(key) != value) {
        prevData.longDataMap.put(key, data.longDataMap.get(key))
        data.longDataMap.put(key, data.longDataMap.get(key))
      } else if(data.longDataMap.containsKey(key) && data.getLong(key) != value) {
        prevData.longDataMap.put(key, data.getLong(key))
        data.longDataMap.put(key, data.getLong(key))
        setField(key, data.getLong(key))
      }
    }

    val stringIterator: util.Iterator[Entry[String, lang.String]] = prevData.stringDataMap.entrySet().iterator()

    while(stringIterator.hasNext()) {
      val entry: Entry[String, lang.String] = stringIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.stringDataMap.containsKey(key) && data.stringDataMap.get(key) != value) {
        prevData.stringDataMap.put(key, data.stringDataMap.get(key))
        data.stringDataMap.put(key, data.stringDataMap.get(key))
      } else if(data.stringDataMap.containsKey(key) && data.getString(key) != value) {
        prevData.stringDataMap.put(key, data.getString(key))
        data.stringDataMap.put(key, data.getString(key))
        setField(key, data.getString(key))
      }
    }
    entity.syncNonPrimitives()
  }

  private def setField(name: String, value: Any) {
    var superClass: Any = entity.getClass
    val outer = new Breaks

    outer.breakable {
      while (superClass != null) {
        for (field <- superClass.asInstanceOf[Class[_]].getDeclaredFields()) {
          if (field.getName().equals(name)) {
            field.setAccessible(true)
            field.set(entity, value)
            outer.break
          }
        }
        superClass = superClass.asInstanceOf[Class[_]].getSuperclass
      }
      outer.break
    }
  }
}