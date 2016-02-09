package seremis.geninfusion.api.soul

import net.minecraft.entity.IRangedAttackMob
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.util.data.Data

trait IEntitySoulCustom extends IRangedAttackMob {

    def getSoul: ISoul


    def makePersistent(name: String)

    /*
     * Use these methods to get and set a variable in the entity class. This can be used to set position, motion etc but also custom variables, like bar, boo and foo.
     */
    def setBoolean(name: String, variable: Boolean)

    def setByte(name: String, variable: Byte)

    def setShort(name: String, variable: Short)

    def setInteger(name: String, variable: Int)

    def setFloat(name: String, variable: Float)

    def setDouble(name: String, variable: Double)

    def setString(name: String, variable: String)

    def setLong(name: String, variable: Long)

    def setItemStack(name: String, variable: ItemStack)

    def setNBT(name: String, variable: NBTTagCompound)

    def setData(name: String, variable: Data)

    def getBoolean(name: String): Boolean

    def getByte(name: String): Byte

    def getShort(name: String): Short

    def getInteger(name: String): Int

    def getFloat(name: String): Float

    def getDouble(name: String): Double

    def getLong(name: String): Long

    def getString(name: String): String

    def getItemStack(name: String): ItemStack

    def getNBT(name: String): NBTTagCompound

    def getData(name: String): Data

    def setBooleanArray(name: String, variable: Array[Boolean])

    def setByteArray(name: String, variable: Array[Byte])

    def setShortArray(name: String, variable: Array[Short])

    def setIntegerArray(name: String, variable: Array[Int])

    def setFloatArray(name: String, variable: Array[Float])

    def setDoubleArray(name: String, variable: Array[Double])

    def setLongArray(name: String, variable: Array[Long])

    def setStringArray(name: String, variable: Array[String])

    def setItemStackArray(name: String, variable: Array[ItemStack])

    def setNBTArray(name: String, variable: Array[NBTTagCompound])

    def setDataArray(name: String, variable: Array[Data])

    /**
     * Use this to set an Object. The Object will not persist over saves, write an Object to a Data object if you want
     * it to persist.
     */
    def setObject(name: String, `object`: Any)

    def getBooleanArray(name: String): Array[Boolean]

    def getByteArray(name: String): Array[Byte]

    def getShortArray(name: String): Array[Short]

    def getIntegerArray(name: String): Array[Int]

    def getFloatArray(name: String): Array[Float]

    def getDoubleArray(name: String): Array[Double]

    def getLongArray(name: String): Array[Long]

    def getStringArray(name: String): Array[String]

    def getItemStackArray(name: String): Array[ItemStack]

    def getNBTArray(name: String): Array[NBTTagCompound]

    def getDataArray(name: String): Array[Data]

    /**
     * Use this to get an Object. The Object will not persist over saves, write an Object to a Data object if you want
     * it to persist.
     *
     * @param name The name of the variable
     */
    def getObject[T](name: String): T


    def callMethod[T](srgName: String, args: Any*): T
}
