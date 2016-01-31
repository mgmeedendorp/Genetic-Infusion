package seremis.geninfusion.api.soul

import java.util.Random

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{DataWatcher, Entity, EntityAgeable, IRangedAttackMob}
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.potion.PotionEffect
import net.minecraft.util.{AxisAlignedBB, ChunkCoordinates, DamageSource, ResourceLocation}
import net.minecraft.world.World
import seremis.geninfusion.api.util.data.Data

trait IEntitySoulCustom extends IRangedAttackMob {

    def getSoul_I: ISoul

    def getWorld_I: World

    def getBoundingBox_I: AxisAlignedBB

    def getEntityId_I: Int

    def getRandom_I: Random

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
    def getObject(name: String): Any



    def getEntityData_I: NBTTagCompound

    def playSound_I(sound: String, volume: Float, pitch: Float)

    def attackEntityFrom_I(source: DamageSource, damage: Float): Boolean

    def attackEntity_I(entity: Entity, distance: Float)

    def attackEntityAsMob_I(entity: Entity): Boolean

    def setFlag_I(id: Int, value: Boolean)

    def getFlag_I(id: Int): Boolean

    def onDeathUpdate_I()

    def damageEntity_I(source: DamageSource, damage: Float)

    def updateAITick_I()

    def canDespawn_I: Boolean

    def isMovementCeased_I: Boolean

    def findPlayerToAttack_I: Entity

    def setBeenAttacked_I()

    def getDeathSound_I: String

    def getHurtSound_I: String

    def getSoundVolume_I: Float

    def getSoundPitch_I: Float

    def applyArmorCalculations_I(source: DamageSource, damage: Float): Float

    def applyPotionDamageCalculations_I(source: DamageSource, damage: Float): Float

    def damageArmor_I(damage: Float)

    def setOnFireFromLava_I()

    def getBlockPathWeight_I(x: Int, y: Int, z: Int): Float

    def updateWanderPath_I()

    def updateEntityActionState_I()

    def decreaseAirSupply_I(air: Int): Int

    def updatePotionEffects_I()

    def jump_I()

    def collideWithNearbyEntities_I()

    def func_110146_f_I(p_110146_1_ : Float, p_110146_2_ : Float): Float

    def isWithinHomeDistance_I(x: Int, y: Int, z: Int): Boolean

    def isWithinHomeDistanceCurrentPosition_I: Boolean

    def getHomePosition_I: ChunkCoordinates

    def hasHome_I: Boolean

    def getMaxHomeDistance_I: Float

    def detachHome_I()

    def setHomeArea_I(x: Int, y: Int, z: Int, maxDistance: Int)

    def despawnEntity_I()

    def setRotation_I(rotationYaw: Float, rotationPitch: Float)

    def updateArmSwingProgress_I()

    def isTamed_I: Boolean

    def dealFireDamage_I(damage: Int)

    def setSize_I(width: Float, height: Float)

    def getCustomNameTag_I: String

    def setCustomNameTag_I(nameTag: String)

    def hasCustomNameTag_I: Boolean

    def getEntityTexture_I: ResourceLocation

    def getExperiencePoints_I(player: EntityPlayer): Int

    def createChild_I(ageable: EntityAgeable): EntityAgeable

    def setScale_I(scale: Float)

    def setScaleForAge_I(isChild: Boolean)

    def getGrowingAge_I: Int

    def setGrowingAge_I(growingAge: Int)

    def addGrowth_I(growingAge: Int)

    def getDataWatcher_I: DataWatcher

    def isPotionApplicable_I(potionEffect: PotionEffect): Boolean

    def setInWeb_I

    def isOnLadder_I: Boolean

    def entityInit_I

    def isSprinting_I: Boolean

    def setSprinting_I(sprinting: Boolean)

    def isSneaking_I: Boolean

    def setSneaking_I(sneaking: Boolean)
}
