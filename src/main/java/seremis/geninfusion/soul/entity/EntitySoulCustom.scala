package seremis.geninfusion.soul.entity

import java.io.IOException
import java.lang.reflect.Field
import java.util.Random
import java.{lang, util}

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import cpw.mods.fml.relauncher.ReflectionHelper
import io.netty.buffer.ByteBuf
import net.minecraft.entity._
import net.minecraft.entity.ai.EntityAITasks
import net.minecraft.entity.ai.attributes.BaseAttributeMap
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{CompressedStreamTools, NBTSizeTracker, NBTTagCompound}
import net.minecraft.potion.PotionEffect
import net.minecraft.util.{AxisAlignedBB, CombatTracker, DamageSource}
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ISoul, SoulHelper}
import seremis.geninfusion.entity.GIEntityLiving
import seremis.geninfusion.helper.DataHelper
import seremis.geninfusion.misc.Data
import seremis.geninfusion.soul.allele.{AlleleFloat, AlleleString}
import seremis.geninfusion.soul.{Soul, TraitHandler}

import scala.collection.JavaConversions._

class EntitySoulCustom(world: World) extends GIEntityLiving(world) with IEntitySoulCustom with IEntityAdditionalSpawnData {

  def this(world: World, soul: ISoul, x: Double, y: Double, z: Double) {
    this(world)
    setPosition(x, y, z)
    setSize(0.8F, 1.7F)
    this.soul = soul
  }

  var soul: ISoul = null

  override def getSoul: ISoul = soul

  override def writeSpawnData(data: ByteBuf) {
    val compound: NBTTagCompound = new NBTTagCompound
    writeToNBT(compound)
    var abyte: Array[Byte] = null
    try {
      abyte = CompressedStreamTools.compress(compound)
    }
    catch {
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
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
        return
    }
    readFromNBT(compound)
  }

  override def getWorld: World = {
    worldObj
  }

  override def getBoundingBox: AxisAlignedBB = {
    boundingBox
  }

  override def getEntityId: Int = {
    super.getEntityId
  }

  override def getAttributeMap: BaseAttributeMap = {
    super.getAttributeMap
  }

  override def getCombatTracker: CombatTracker = {
    super.func_110142_aN
  }

  private var creatureAttribute: EnumCreatureAttribute = null

  override def getCreatureAttribute: EnumCreatureAttribute = {
    creatureAttribute
  }

  override def getActivePotionsMap: util.HashMap[Integer, PotionEffect] = {
    var `var`: util.HashMap[Integer, PotionEffect] = new util.HashMap[Integer, PotionEffect]
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "activePotionsMap", "field_70147_f")
      field.setAccessible(true)
      `var` = field.get(this).asInstanceOf[util.HashMap[Integer, PotionEffect]]
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
    }
    `var`
  }

  override def getDataWatcher: DataWatcher = {
    super.getDataWatcher()
  }

  override def getTasks: EntityAITasks = {
    tasks
  }

  override def getTargetTasks: EntityAITasks = {
    targetTasks
  }

  override def getLeashedCompound: NBTTagCompound = {
    var `var`: NBTTagCompound = new NBTTagCompound
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "field_110170_bx", "field_110170_bx")
      field.setAccessible(true)
      `var` = field.get(this).asInstanceOf[NBTTagCompound]
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
    }
    `var`
  }

  override def onDeathUpdate {
    super.onDeathUpdate()
  }

  override def setFlag(id: Int, value: Boolean) {
    super.setFlag(id, value)
  }

  override def getFlag(id: Int): Boolean = {
    super.getFlag(id)
  }

  override def getRandom: Random = {
    rand
  }

  override def isChild: Boolean = {
    false
    //TODO this
  }

  private var talkInterval: Int = 0

  override def getTalkInterval: Int = {
    talkInterval
  }

  override def getDeathSound: String = {
    SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_DEATH_SOUND).asInstanceOf[AlleleString].value
  }

  override def getLivingSound: String = {
    SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_LIVING_SOUND).asInstanceOf[AlleleString].value
  }

  override def getHurtSound: String = {
    SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_HURT_SOUND).asInstanceOf[AlleleString].value
  }

  override def getSplashSound: String = {
    SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_SPLASH_SOUND).asInstanceOf[AlleleString].value
  }

  override def getSwimSound: String = {
    SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_SWIM_SOUND).asInstanceOf[AlleleString].value
  }

  override def getSoundVolume: Float = {
    SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_SOUND_VOLUME).asInstanceOf[AlleleFloat].value
  }

  override def applyEntityAttributes {
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.maxHealth)
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.knockbackResistance)
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.movementSpeed)
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.attackDamage)
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.followRange)
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D)
  }

  private var firstUpdate: Boolean = true

  override def onUpdate {
    syncVariables
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
    !ForgeHooks.onLivingAttack(this, source, damage) && TraitHandler.attackEntityFrom(this, source, damage)
  }

  override def attackEntity(entity: Entity, distance: Float) {
    TraitHandler.attackEntity(this, entity, distance);
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

  override def updateAITick {
    TraitHandler.updateAITick(this)
  }

  override def attackEntityAsMob(entity: Entity): Boolean = {
    TraitHandler.attackEntityAsMob(this, entity)
  }

  override def readFromNBT(compound: NBTTagCompound) {
    super.readFromNBT(compound)
    soul = new Soul(compound)
    persistentData = new Data(compound)
  }

  override def writeToNBT(compound: NBTTagCompound) {
    super.writeToNBT(compound)
    soul.writeToNBT(compound)
    persistentData.writeToNBT(compound)
  }

  protected var persistentData, variableData = new Data();

  override def setPersistentVariable(name: String, variable: Boolean) {
    persistentData.setBoolean(name, variable)
  }

  override def setPersistentVariable(name: String, variable: Byte) {
    persistentData.setByte(name, variable)
  }

  override def setPersistentVariable(name: String, variable: Int) {
    persistentData.setInteger(name, variable)
  }

  override def setPersistentVariable(name: String, variable: Float) {
    persistentData.setFloat(name, variable)
  }

  override def setPersistentVariable(name: String, variable: Double) {
    persistentData.setDouble(name, variable)
  }

  override def setPersistentVariable(name: String, variable: String) {
    persistentData.setString(name, variable)
  }

  override def setPersistentVariable(name: String, variable: ItemStack) {
    persistentData.setNBT(name, variable.writeToNBT(new NBTTagCompound()))
  }

  override def getPersistentBoolean(name: String): Boolean = {
    persistentData.getBoolean(name)
  }

  override def getPersistentByte(name: String): Byte = {
    persistentData.getByte(name)
  }

  override def getPersistentInteger(name: String): Int = {
    persistentData.getInteger(name)
  }

  override def getPersistentFloat(name: String): Float = {
    persistentData.getFloat(name)
  }

  override def getPersistentDouble(name: String): Double = {
    persistentData.getDouble(name)
  }

  override def getPersistentString(name: String): String = {
    persistentData.getString(name)
  }

  override def getPersistentItemStack(name: String): ItemStack = {
    if(persistentData.getNBT(name) == null) null else ItemStack.loadItemStackFromNBT(persistentData.getNBT(name))
  }

  override def setVariable(name: String, variable: Boolean) {
    variableData.setBoolean(name, variable)
  }

  override def setVariable(name: String, variable: Byte) {
    variableData.setByte(name, variable)
  }

  override def setVariable(name: String, variable: Int) {
    variableData.setInteger(name, variable)
  }

  override def setVariable(name: String, variable: Float) {
    variableData.setFloat(name, variable)
  }

  override def setVariable(name: String, variable: Double) {
    variableData.setDouble(name, variable)
  }

  override def setVariable(name: String, variable: String) {
    variableData.setString(name, variable)
  }

  override def setVariable(name: String, variable: ItemStack) {
    variableData.setNBT(name, variable.writeToNBT(new NBTTagCompound()))
  }

  override def getBoolean(name: String): Boolean = {
    variableData.getBoolean(name)
  }

  override def getByte(name: String): Byte = {
    variableData.getByte(name)
  }

  override def getInteger(name: String): Int = {
    variableData.getInteger(name)
  }

  override def getFloat(name: String): Float = {
    variableData.getFloat(name)
  }

  override def getDouble(name: String): Double = {
    variableData.getDouble(name)
  }

  override def getString(name: String): String = {
    variableData.getString(name)
  }

  override def getItemStack(name: String): ItemStack = {
    ItemStack.loadItemStackFromNBT(variableData.getNBT(name))
  }

  override def forceVariableSync() {
    this.syncVariables()
  }

  //The variables as they were the last tick
  protected var syncData: Data = DataHelper.writePrimitives(this)

  def syncVariables() {
    //The variables in the entity class
    val data = DataHelper.writePrimitives(this)

    val clss = this.getClass

//    val iterator: util.Iterator[Entry[String, lang.Boolean]] = syncData.booleanDataMap.entrySet().iterator()
//
//    while(iterator.hasNext()) {
//      val entry: Entry[String, lang.Boolean] = iterator.next()
//      val key = entry.getKey()
//      val value = entry.getValue()
//
//    }
//    for ((a, b) <- syncData.booleanDataMap) {
//      if(data.booleanDataMap.containsKey(key) && data.booleanDataMap.get(key) != value) {
//        syncData.booleanDataMap.put(key, value)
//        clss.getField(key).setAccessible(true)
//        clss.getField(key).setBoolean(this, value)
//      }

      syncData.booleanDataMap = new java.util.HashMap[String, lang.Boolean](syncData.booleanDataMap).transform((key, value) => { var returnType = value;
        if(value != data.booleanDataMap.apply(key)) { persistentData.booleanDataMap.put(key, data.booleanDataMap.apply(key)); returnType = data.booleanDataMap.apply(key) }
        else if(value != persistentData.booleanDataMap.apply(key)){ data.booleanDataMap.put(key, persistentData.booleanDataMap.apply(key)); returnType = persistentData.booleanDataMap.apply(key) }
        returnType });
//    }
  }
}
