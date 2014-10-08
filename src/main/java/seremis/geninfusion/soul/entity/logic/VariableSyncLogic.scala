package seremis.geninfusion.soul.entity.logic

import java.util.Map.Entry
import java.{lang, util}

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.helper.DataHelper
import seremis.geninfusion.misc.Data

import scala.util.control.Breaks

class VariableSyncLogic(entity: IVariableSyncEntity) {

  var variableData, persistentData = new Data()

  def setPersistentVariable(name: String, variable: Boolean) {
    persistentData.setBoolean(name, variable)
  }

  def setPersistentVariable(name: String, variable: Byte) {
    persistentData.setByte(name, variable)
  }

  def setPersistentVariable(name: String, variable: Int) {
    persistentData.setInteger(name, variable)
  }

  def setPersistentVariable(name: String, variable: Float) {
    persistentData.setFloat(name, variable)
  }

  def setPersistentVariable(name: String, variable: Double) {
    persistentData.setDouble(name, variable)
  }

  def setPersistentVariable(name: String, variable: String) {
    persistentData.setString(name, variable)
  }

  def setPersistentVariable(name: String, variable: ItemStack) {
    persistentData.setNBT(name, variable.writeToNBT(new NBTTagCompound()))
  }

  def getPersistentBoolean(name: String): Boolean = {
    persistentData.getBoolean(name)
  }

  def getPersistentByte(name: String): Byte = {
    persistentData.getByte(name)
  }

  def getPersistentInteger(name: String): Int = {
    persistentData.getInteger(name)
  }

  def getPersistentFloat(name: String): Float = {
    persistentData.getFloat(name)
  }

  def getPersistentDouble(name: String): Double = {
    persistentData.getDouble(name)
  }

  def getPersistentString(name: String): String = {
    persistentData.getString(name)
  }

  def getPersistentItemStack(name: String): ItemStack = {
    if(persistentData.getNBT(name) == null) null else ItemStack.loadItemStackFromNBT(persistentData.getNBT(name))
  }

  def setVariable(name: String, variable: Boolean) {
    variableData.setBoolean(name, variable)
  }

  def setVariable(name: String, variable: Byte) {
    variableData.setByte(name, variable)
  }

  def setVariable(name: String, variable: Int) {
    variableData.setInteger(name, variable)
  }

  def setVariable(name: String, variable: Float) {
    variableData.setFloat(name, variable)
  }

  def setVariable(name: String, variable: Double) {
    variableData.setDouble(name, variable)
  }

  def setVariable(name: String, variable: String) {
    variableData.setString(name, variable)
  }

  def setVariable(name: String, variable: ItemStack) {
    variableData.setNBT(name, variable.writeToNBT(new NBTTagCompound()))
  }

  def getBoolean(name: String): Boolean = {
    variableData.getBoolean(name)
  }

  def getByte(name: String): Byte = {
    variableData.getByte(name)
  }

  def getInteger(name: String): Int = {
    variableData.getInteger(name)
  }

  def getFloat(name: String): Float = {
    variableData.getFloat(name)
  }

  def getDouble(name: String): Double = {
    variableData.getDouble(name)
  }

  def getString(name: String): String = {
    variableData.getString(name)
  }

  def getItemStack(name: String): ItemStack = {
    ItemStack.loadItemStackFromNBT(variableData.getNBT(name))
  }

  def forceVariableSync() {
    this.syncVariables()
  }

  //The variables as they were the last tick
  protected var syncData: Data = new Data()

  def syncVariables() {
    //The variables in the entity class
    val data = DataHelper.writePrimitives(entity)
    val clss = entity.getClass

    val boolIterator: util.Iterator[Entry[String, lang.Boolean]] = syncData.booleanDataMap.entrySet().iterator()

    while(boolIterator.hasNext()) {
      val entry: Entry[String, lang.Boolean] = boolIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.booleanDataMap.containsKey(key) && data.booleanDataMap.get(key) != value) {
        syncData.booleanDataMap.put(key, data.booleanDataMap.get(key))
        if(persistentData.booleanDataMap.containsKey(key))
          persistentData.booleanDataMap.put(key, data.booleanDataMap.get(key))
        if(variableData.booleanDataMap.containsKey(key))
          variableData.booleanDataMap.put(key, data.booleanDataMap.get(key))
      } else if(persistentData.booleanDataMap.containsKey(key) && persistentData.getBoolean(key) != value) {
        syncData.booleanDataMap.put(key, persistentData.getBoolean(key))
        data.booleanDataMap.put(key, persistentData.getBoolean(key))
        setField(key, persistentData.getBoolean(key))
      } else if(variableData.booleanDataMap.containsKey(key) && variableData.getBoolean(key) != value) {
        syncData.booleanDataMap.put(key, variableData.getBoolean(key))
        data.booleanDataMap.put(key, variableData.getBoolean(key))
        setField(key, variableData.getBoolean(key))
      }
    }

    val byteIterator: util.Iterator[Entry[String, lang.Byte]] = syncData.byteDataMap.entrySet().iterator()

    while(byteIterator.hasNext()) {
      val entry: Entry[String, lang.Byte] = byteIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.byteDataMap.containsKey(key) && data.byteDataMap.get(key) != value) {
        syncData.byteDataMap.put(key, data.byteDataMap.get(key))
        if(persistentData.byteDataMap.containsKey(key))
          persistentData.byteDataMap.put(key, data.byteDataMap.get(key))
        if(variableData.byteDataMap.containsKey(key))
          variableData.byteDataMap.put(key, data.byteDataMap.get(key))
      } else if(persistentData.byteDataMap.containsKey(key) && persistentData.getByte(key) != value) {
        syncData.byteDataMap.put(key, persistentData.getByte(key))
        data.byteDataMap.put(key, persistentData.getByte(key))
        setField(key, persistentData.getByte(key))
      } else if(variableData.byteDataMap.containsKey(key) && variableData.getByte(key) != value) {
        syncData.byteDataMap.put(key, variableData.getByte(key))
        data.byteDataMap.put(key, variableData.getByte(key))
        setField(key, variableData.getByte(key))
      }
    }

    val shortIterator: util.Iterator[Entry[String, lang.Short]] = syncData.shortDataMap.entrySet().iterator()

    while(shortIterator.hasNext()) {
      val entry: Entry[String, lang.Short] = shortIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.shortDataMap.containsKey(key) && data.shortDataMap.get(key) != value) {
        syncData.shortDataMap.put(key, data.shortDataMap.get(key))
        if(persistentData.shortDataMap.containsKey(key))
          persistentData.shortDataMap.put(key, data.shortDataMap.get(key))
        if(variableData.shortDataMap.containsKey(key))
          variableData.shortDataMap.put(key, data.shortDataMap.get(key))
      } else if(persistentData.shortDataMap.containsKey(key) && persistentData.getShort(key) != value) {
        syncData.shortDataMap.put(key, persistentData.getShort(key))
        data.shortDataMap.put(key, persistentData.getShort(key))
        setField(key, persistentData.getShort(key))
      } else if(variableData.shortDataMap.containsKey(key) && variableData.getShort(key) != value) {
        syncData.shortDataMap.put(key, variableData.getShort(key))
        data.shortDataMap.put(key, variableData.getShort(key))
        setField(key, variableData.getShort(key))
      }
    }

    val intIterator: util.Iterator[Entry[String, lang.Integer]] = syncData.integerDataMap.entrySet().iterator()

    while(intIterator.hasNext()) {
      val entry: Entry[String, lang.Integer] = intIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.integerDataMap.containsKey(key) && data.integerDataMap.get(key) != value) {
        syncData.integerDataMap.put(key, data.integerDataMap.get(key))
        if(persistentData.integerDataMap.containsKey(key))
          persistentData.integerDataMap.put(key, data.integerDataMap.get(key))
        if(variableData.integerDataMap.containsKey(key))
          variableData.integerDataMap.put(key, data.integerDataMap.get(key))
      } else if(persistentData.integerDataMap.containsKey(key) && persistentData.getInteger(key) != value) {
        syncData.integerDataMap.put(key, persistentData.getInteger(key))
        data.integerDataMap.put(key, persistentData.getInteger(key))
        setField(key, persistentData.getInteger(key))
      } else if(variableData.integerDataMap.containsKey(key) && variableData.getInteger(key) != value) {
        syncData.integerDataMap.put(key, variableData.getInteger(key))
        data.integerDataMap.put(key, variableData.getInteger(key))
        setField(key, variableData.getInteger(key))
      }
    }

    val floatIterator: util.Iterator[Entry[String, lang.Float]] = syncData.floatDataMap.entrySet().iterator()

    while(floatIterator.hasNext()) {
      val entry: Entry[String, lang.Float] = floatIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.floatDataMap.containsKey(key) && data.floatDataMap.get(key) != value) {
        syncData.floatDataMap.put(key, data.floatDataMap.get(key))
        if(persistentData.floatDataMap.containsKey(key))
          persistentData.floatDataMap.put(key, data.floatDataMap.get(key))
        if(variableData.floatDataMap.containsKey(key))
          variableData.floatDataMap.put(key, data.floatDataMap.get(key))
      } else if(persistentData.floatDataMap.containsKey(key) && persistentData.getFloat(key) != value) {
        syncData.floatDataMap.put(key, persistentData.getFloat(key))
        data.floatDataMap.put(key, persistentData.getFloat(key))
        setField(key, persistentData.getFloat(key))
      } else if(variableData.floatDataMap.containsKey(key) && variableData.getFloat(key) != value) {
        syncData.floatDataMap.put(key, variableData.getFloat(key))
        data.floatDataMap.put(key, variableData.getFloat(key))
        setField(key, variableData.getFloat(key))
      }
    }

    val doubleIterator: util.Iterator[Entry[String, lang.Double]] = syncData.doubleDataMap.entrySet().iterator()

    while(doubleIterator.hasNext()) {
      val entry: Entry[String, lang.Double] = doubleIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.doubleDataMap.containsKey(key) && data.doubleDataMap.get(key) != value) {
        syncData.doubleDataMap.put(key, data.doubleDataMap.get(key))
        if(persistentData.doubleDataMap.containsKey(key))
          persistentData.doubleDataMap.put(key, data.doubleDataMap.get(key))
        if(variableData.doubleDataMap.containsKey(key))
          variableData.doubleDataMap.put(key, data.doubleDataMap.get(key))
      } else if(persistentData.doubleDataMap.containsKey(key) && persistentData.getDouble(key) != value) {
        syncData.doubleDataMap.put(key, persistentData.getDouble(key))
        data.doubleDataMap.put(key, persistentData.getDouble(key))
        setField(key, persistentData.getDouble(key))
      } else if(variableData.doubleDataMap.containsKey(key) && variableData.getDouble(key) != value) {
        syncData.doubleDataMap.put(key, variableData.getDouble(key))
        data.doubleDataMap.put(key, variableData.getDouble(key))
        setField(key, variableData.getDouble(key))
      }
    }

    val longIterator: util.Iterator[Entry[String, lang.Long]] = syncData.longDataMap.entrySet().iterator()

    while(longIterator.hasNext()) {
      val entry: Entry[String, lang.Long] = longIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.longDataMap.containsKey(key) && data.longDataMap.get(key) != value) {
        syncData.longDataMap.put(key, data.longDataMap.get(key))
        if(persistentData.longDataMap.containsKey(key))
          persistentData.longDataMap.put(key, data.longDataMap.get(key))
        if(variableData.longDataMap.containsKey(key))
          variableData.longDataMap.put(key, data.longDataMap.get(key))
      } else if(persistentData.longDataMap.containsKey(key) && persistentData.getLong(key) != value) {
        syncData.longDataMap.put(key, persistentData.getLong(key))
        data.longDataMap.put(key, persistentData.getLong(key))
        setField(key, persistentData.getLong(key))
      } else if(variableData.longDataMap.containsKey(key) && variableData.getLong(key) != value) {
        syncData.longDataMap.put(key, variableData.getLong(key))
        data.longDataMap.put(key, variableData.getLong(key))
        setField(key, variableData.getLong(key))
      }
    }

    val stringIterator: util.Iterator[Entry[String, lang.String]] = syncData.stringDataMap.entrySet().iterator()

    while(stringIterator.hasNext()) {
      val entry: Entry[String, lang.String] = stringIterator.next()
      val key = entry.getKey()
      val value = entry.getValue()

      if(data.stringDataMap.containsKey(key) && data.stringDataMap.get(key) != value) {
        syncData.stringDataMap.put(key, data.stringDataMap.get(key))
        if(persistentData.stringDataMap.containsKey(key))
          persistentData.stringDataMap.put(key, data.stringDataMap.get(key))
        if(variableData.stringDataMap.containsKey(key))
          variableData.stringDataMap.put(key, data.stringDataMap.get(key))
      } else if(persistentData.stringDataMap.containsKey(key) && persistentData.getString(key) != value) {
        syncData.stringDataMap.put(key, persistentData.getString(key))
        data.stringDataMap.put(key, persistentData.getString(key))
        setField(key, persistentData.getString(key))
      } else if(variableData.stringDataMap.containsKey(key) && variableData.getString(key) != value) {
        syncData.stringDataMap.put(key, variableData.getString(key))
        data.stringDataMap.put(key, variableData.getString(key))
        setField(key, variableData.getString(key))
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
