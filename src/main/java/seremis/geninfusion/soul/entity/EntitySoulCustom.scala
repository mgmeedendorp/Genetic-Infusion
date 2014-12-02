package seremis.geninfusion.soul.entity

import java.io.IOException
import java.util.Random

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import io.netty.buffer.ByteBuf
import net.minecraft.entity._
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{CompressedStreamTools, NBTSizeTracker, NBTTagCompound}
import net.minecraft.util.{AxisAlignedBB, DamageSource}
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.Data
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ISoul, SoulHelper}
import seremis.geninfusion.entity.GIEntityLiving
import seremis.geninfusion.soul.allele.{AlleleFloat, AlleleInteger, AlleleString}
import seremis.geninfusion.soul.entity.logic.VariableSyncLogic
import seremis.geninfusion.soul.{Soul, TraitHandler}
import seremis.geninfusion.util.INBTTagable

class EntitySoulCustom(world: World) extends GIEntityLiving(world) with IEntitySoulCustom with IEntityAdditionalSpawnData with INBTTagable {

  var soul: ISoul = null
  var syncLogic: VariableSyncLogic = new VariableSyncLogic(this)

  def this(world: World, soul: ISoul, x: Double, y: Double, z: Double) {
    this(world)
    setPosition(x, y, z)
    setSize(0.8F, 1.7F)
    this.soul = soul
  }

  override def getSoul: ISoul = soul

  override def writeSpawnData(data: ByteBuf) {
    val compound: NBTTagCompound = new NBTTagCompound
    writeToNBT(compound)
    var abyte: Array[Byte] = null
    try {
      abyte = CompressedStreamTools.compress(compound)
    } catch {
      case e: Exception => e.printStackTrace()
        return
    }
    data.writeShort(abyte.length.asInstanceOf[Short])
    data.writeBytes(abyte)
  }

  override def readSpawnData(data: ByteBuf) {
    val short1: Short = data.readShort
    val abyte: Array[Byte] = new Array[Byte](short1)
    data.readBytes(abyte)
    var compound: NBTTagCompound = null
    try {
      compound = CompressedStreamTools.func_152457_a(abyte, NBTSizeTracker.field_152451_a)
    } catch {
      case e: IOException =>
        e.printStackTrace()
        return
    }
    readFromNBT(compound)
  }

  override def getWorld: World = {
    worldObj
  }

  override def getBoundingBox: AxisAlignedBB = boundingBox

  override def getEntityId: Int = super.getEntityId

  override def onDeathUpdate() = super.onDeathUpdate()

  override def setFlag(id: Int, value: Boolean) = super.setFlag(id, value)

  override def getFlag(id: Int): Boolean = super.getFlag(id)

  override def getRandom: Random = rand

  //TODO
  //override def isChild: Boolean = getBoolean("isChild")

  override def getTalkInterval: Int = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_TALK_INTERVAL).asInstanceOf[AlleleInteger].value

  override def getDeathSound: String = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_DEATH_SOUND).asInstanceOf[AlleleString].value

  override def getLivingSound: String = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_LIVING_SOUND).asInstanceOf[AlleleString].value

  override def getHurtSound: String = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_HURT_SOUND).asInstanceOf[AlleleString].value

  override def getSplashSound: String = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_SPLASH_SOUND).asInstanceOf[AlleleString].value

  override def getSwimSound: String = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_SWIM_SOUND).asInstanceOf[AlleleString].value

  override def getSoundVolume: Float = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_SOUND_VOLUME).asInstanceOf[AlleleFloat].value

  override def applyEntityAttributes() {
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.maxHealth)
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.knockbackResistance)
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.movementSpeed)
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.attackDamage)
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.followRange)
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D)
  }

  private var firstUpdate: Boolean = true

  override def onUpdate() {
    if (ForgeHooks.onLivingUpdate(this)) return
    if (firstUpdate) {
      firstUpdate = false
      TraitHandler.firstTick(this)
    }
    TraitHandler.entityUpdate(this)
  }

  override def onDeath(source: DamageSource) {
    if (ForgeHooks.onLivingDeath(this, source)) return
    if (source.getEntity != null) {
      source.getEntity.onKillEntity(this)
    }
    TraitHandler.entityDeath(this, source)
  }

  override def onKillEntity(entity: EntityLivingBase) {
    TraitHandler.onKillEntity(this, entity)
  }

  override def attackEntityFrom(source: DamageSource, damage: Float): Boolean = {
    TraitHandler.attackEntityFrom(this, source, damage)
  }

  override def attackEntity(entity: Entity, distance: Float) {
    TraitHandler.attackEntity(this, entity, distance)
  }

  override def damageEntity(source: DamageSource, damage: Float) {
    TraitHandler.damageEntity(this, source, damage)
  }

  override def onSpawnWithEgg(data: IEntityLivingData): IEntityLivingData = {
    TraitHandler.spawnEntityFromEgg(this, data)
  }

  override def playSound(name: String, volume: Float, pitch: Float) {
    TraitHandler.playSoundAtEntity(this, name, volume, pitch)
  }

  override def updateAITick() {
    super.updateAITick()
    TraitHandler.updateAITick(this)
  }

  override def interact(player: EntityPlayer): Boolean = {
    //TODO interactboolean
    TraitHandler.entityRightClicked(this, player)
    true
  }

  override def applyEntityCollision(entity: Entity) {
    super.applyEntityCollision(entity)
  }

  override def attackEntityAsMob(entity: Entity): Boolean = TraitHandler.attackEntityAsMob(this, entity)

  override def isMovementCeased: Boolean = false

  override def findPlayerToAttack(): Entity = TraitHandler.findPlayerToAttack(this)

  override def readFromNBT(compound: NBTTagCompound) {
    super.readFromNBT(compound)
    soul = new Soul(compound)
    if (compound.hasKey("data")) {
      syncLogic.readFromNBT(compound)
      //TODO check if this works
//      setPosition(posX, posY, posZ)
    }
  }

  override def writeToNBT(compound: NBTTagCompound) {
    super.writeToNBT(compound)
    soul.writeToNBT(compound)
    syncLogic.writeToNBT(compound)
  }

  override def makePersistent(name: String) = syncLogic.makePersistent(name)

  override def setBoolean(name: String, variable: Boolean) = syncLogic.setBoolean(name, variable)

  override def setByte(name: String, variable: Byte) = syncLogic.setByte(name, variable)

  override def setShort(name: String, variable: Short) = syncLogic.setShort(name, variable)

  override def setInteger(name: String, variable: Int) = syncLogic.setInteger(name, variable)

  override def setFloat(name: String, variable: Float) = syncLogic.setFloat(name, variable)

  override def setDouble(name: String, variable: Double) = syncLogic.setDouble(name, variable)

  override def setLong(name: String, variable: Long) = syncLogic.setDouble(name, variable)

  override def setString(name: String, variable: String) = syncLogic.setString(name, variable)

  override def setItemStack(name: String, variable: ItemStack) = syncLogic.setItemStack(name, variable)

  override def setNBT(name: String, variable: NBTTagCompound) = syncLogic.setNBT(name, variable)

  override def setData(name: String, variable: Data) = syncLogic.setData(name, variable)

  override def setObject(name: String, variable: Object) = syncLogic.setObject(name, variable)

  override def getBoolean(name: String): Boolean = syncLogic.getBoolean(name)

  override def getByte(name: String): Byte = syncLogic.getByte(name)

  override def getShort(name: String): Short = syncLogic.getShort(name)

  override def getInteger(name: String): Int = syncLogic.getInteger(name)

  override def getFloat(name: String): Float = syncLogic.getFloat(name)

  override def getDouble(name: String): Double = syncLogic.getDouble(name)

  override def getLong(name: String): Long = syncLogic.getLong(name)

  override def getString(name: String): String = syncLogic.getString(name)

  override def getItemStack(name: String): ItemStack = syncLogic.getItemStack(name)

  override def getNBT(name: String): NBTTagCompound = syncLogic.getNBT(name)

  override def getData(name: String): Data = syncLogic.getData(name)

  override def getObject(name: String): Object = syncLogic.getObject(name)

  override def setBooleanArray(name: String, value: Array[Boolean]) = syncLogic.setBooleanArray(name, value)

  override def setByteArray(name: String, value: Array[Byte]) = syncLogic.setByteArray(name, value)

  override def setShortArray(name: String, value: Array[Short]) = syncLogic.setShortArray(name, value)

  override def setIntegerArray(name: String, value: Array[Int]) = syncLogic.setIntegerArray(name, value)

  override def setFloatArray(name: String, value: Array[Float]) = syncLogic.setFloatArray(name, value)

  override def setDoubleArray(name: String, value: Array[Double]) = syncLogic.setDoubleArray(name, value)

  override def setLongArray(name: String, value: Array[Long]) = syncLogic.setLongArray(name, value)

  override def setStringArray(name: String, value: Array[String]) = syncLogic.setStringArray(name, value)

  override def setItemStackArray(name: String, value: Array[ItemStack]) = syncLogic.setItemStackArray(name, value)

  override def setNBTArray(name: String, value: Array[NBTTagCompound]) = syncLogic.setNBTArray(name, value)

  override def setDataArray(name: String, value: Array[Data]) = syncLogic.setDataArray(name, value)

  override def getBooleanArray(name: String): Array[Boolean] = syncLogic.getBooleanArray(name)

  override def getByteArray(name: String): Array[Byte] = syncLogic.getByteArray(name)

  override def getShortArray(name: String): Array[Short] = syncLogic.getShortArray(name)

  override def getIntegerArray(name: String): Array[Int] = syncLogic.getIntegerArray(name)

  override def getFloatArray(name: String): Array[Float] = syncLogic.getFloatArray(name)

  override def getDoubleArray(name: String): Array[Double] = syncLogic.getDoubleArray(name)

  override def getLongArray(name: String): Array[Long] = syncLogic.getLongArray(name)

  override def getStringArray(name: String): Array[String] = syncLogic.getStringArray(name)

  override def getItemStackArray(name: String): Array[ItemStack] = syncLogic.getItemStackArray(name)

  override def getNBTArray(name: String): Array[NBTTagCompound] = syncLogic.getNBTArray(name)

  override def getDataArray(name: String): Array[Data] = syncLogic.getDataArray(name)
}