package seremis.geninfusion.api.soul

import java.util.Random

import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.client.renderer.entity.RenderLiving
import net.minecraft.entity.{Entity, IRangedAttackMob}
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{AxisAlignedBB, ChunkCoordinates, DamageSource}
import net.minecraft.world.World
import seremis.geninfusion.api.soul.util.Data

trait IEntitySoulCustom extends IRangedAttackMob {

    def getSoul: ISoul

    def getWorld: World

    def getBoundingBox: AxisAlignedBB

    def getEntityId: Int

    def getRandom: Random

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
    def setObject(name: String, `object`: AnyRef)

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
    def getObject(name: String): AnyRef

    def getEntityData: NBTTagCompound

    def playSound(sound: String, volume: Float, pitch: Float)

    def attackEntityFrom(source: DamageSource, damage: Float): Boolean

    def attackEntity(entity: Entity, distance: Float)

    def attackEntityAsMob(entity: Entity): Boolean

    def setFlag(id: Int, value: Boolean)

    def getFlag(id: Int): Boolean

    def onDeathUpdate

    def damageEntity(source: DamageSource, damage: Float)

    def updateAITick

    def canDespawn: Boolean

    def isMovementCeased: Boolean

    def findPlayerToAttack: Entity

    def setBeenAttacked

    def getDeathSound: String

    def getHurtSound: String

    def getSoundVolume: Float

    def getSoundPitch: Float

    def applyArmorCalculations(source: DamageSource, damage: Float): Float

    def applyPotionDamageCalculations(source: DamageSource, damage: Float): Float

    def damageArmor(damage: Float)

    def setOnFireFromLava

    def getBlockPathWeight(x: Int, y: Int, z: Int): Float

    def updateWanderPath

    def updateEntityActionState

    def decreaseAirSupply(air: Int): Int

    def updatePotionEffects

    def jump

    def collideWithNearbyEntities

    def func_110146_f(p_110146_1_ : Float, p_110146_2_ : Float): Float

    def isWithinHomeDistance(x: Int, y: Int, z: Int): Boolean

    def isWithinHomeDistanceCurrentPosition: Boolean

    def getHomePosition: ChunkCoordinates

    def hasHome: Boolean

    def getMaxHomeDistance: Float

    def detachHome

    def setHomeArea(x: Int, y: Int, z: Int, maxDistance: Int)

    def despawnEntity

    def setRotation(rotationYaw: Float, rotationPitch: Float)

    def updateArmSwingProgress

    def isTamed: Boolean

    def dealFireDamage(damage: Int)

    @SideOnly(Side.CLIENT)
    def getEntityRender: RenderLiving

    @SideOnly(Side.CLIENT)
    def setEntityRender(render: RenderLiving)

    def setSize(width: Float, height: Float)
}
