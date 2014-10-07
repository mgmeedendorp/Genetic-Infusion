package seremis.geninfusion.soul.entity

import java.io.IOException
import java.lang.reflect.Field
import java.util.Map.Entry
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
import seremis.geninfusion.core.proxy.CommonProxy
import seremis.geninfusion.entity.GIEntityLiving
import seremis.geninfusion.helper.DataHelper
import seremis.geninfusion.misc.Data
import seremis.geninfusion.soul.allele.{AlleleFloat, AlleleString}
import seremis.geninfusion.soul.{Soul, TraitHandler}

import scala.util.control.Breaks

class EntitySoulCustom(world: World) extends GIEntityLiving(world) with IEntitySoulCustom with IEntityAdditionalSpawnData {

  def this(world: World, soul: ISoul, x: Double, y: Double, z: Double) {
    this(world)
    setPosition(x, y, z)
    setSize(0.8F, 1.7F)
    this.soul = soul
    initVariables();
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
    if(!persistentData.isEmpty) {
      initPersistent = false
    }
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

  private var initPersistent = true;

  protected def initVariables() {
    if(initPersistent) {
      persistentData.setInteger("ticksExisted", ticksExisted)
      persistentData.setDouble("posX", posX)
      persistentData.setDouble("posY", posY)
      persistentData.setDouble("posZ", posZ)
      persistentData.setDouble("motionX", motionX)
      persistentData.setDouble("motionY", motionY)
      persistentData.setDouble("motionZ", motionZ)
      persistentData.setDouble("rotationYaw", rotationYaw)
      persistentData.setDouble("rotationPitch", rotationPitch)
      persistentData.setBoolean("onGround", onGround)
      persistentData.setBoolean("isDead", isDead)
      persistentData.setInteger("dimension", dimension)
      persistentData.setInteger("timeUntilPortal", timeUntilPortal)
      persistentData.setInteger("hurtTime", hurtTime)
      persistentData.setInteger("deathTime", deathTime)
      persistentData.setInteger("attackTime", attackTime)
      persistentData.setInteger("livingSoundTime", livingSoundTime)
      persistentData.setInteger("talkInterval", talkInterval)
      //TODO absorptionAmount
    }

    variableData.setDouble("prevPosX", prevPosX)
    variableData.setDouble("prevPosY", prevPosY)
    variableData.setDouble("prevPosZ", prevPosZ)
    if(CommonProxy.instance.isRenderWorld(worldObj)) {
      variableData.setInteger("serverPosX", serverPosX)
      variableData.setInteger("serverPosY", serverPosY)
      variableData.setInteger("serverPosZ", serverPosZ)
    }
    variableData.setDouble("lastTickPosX", lastTickPosX)
    variableData.setDouble("lastTickPosY", lastTickPosY)
    variableData.setDouble("lastTickPosZ", lastTickPosZ)
    variableData.setDouble("newPosX", newPosX)
    variableData.setDouble("newPosY", newPosY)
    variableData.setDouble("newPosZ", newPosZ)
    variableData.setInteger("newPosRotationIncrements", newPosRotationIncrements)
    variableData.setFloat("jumpMovementFActor", jumpMovementFactor)
    variableData.setFloat("moveStrafing", moveStrafing)
    variableData.setFloat("moveForward", moveForward)
    variableData.setFloat("randomYawVelocity", randomYawVelocity)
    variableData.setFloat("width", width)
    variableData.setFloat("height", height)
    variableData.setFloat("ySize", ySize)
    variableData.setFloat("yOffset", yOffset)
    variableData.setFloat("stepHeight", stepHeight)
    variableData.setFloat("prevRotationYaw", prevRotationYaw)
    variableData.setFloat("prevRotationPitch", prevRotationPitch)
    variableData.setDouble("newRotationYaw", newRotationYaw)
    variableData.setDouble("newRotationPitch", newRotationPitch)
    variableData.setFloat("defaultPitch", defaultPitch)
    variableData.setInteger("fireResistance", fireResistance)
    variableData.setBoolean("inWater", inWater)
    variableData.setBoolean("isInWeb", isInWeb)
    variableData.setBoolean("isAirBorne", isAirBorne)
    variableData.setBoolean("preventEntitySpawning", preventEntitySpawning)
    variableData.setBoolean("forceSpawn", forceSpawn)
    variableData.setBoolean("noClip", noClip)
    variableData.setFloat("attackedAtYaw", attackedAtYaw)
    variableData.setBoolean("isJumping", isJumping)
    variableData.setBoolean("addedToChunk", addedToChunk)
    variableData.setInteger("chunkCoordX", chunkCoordX)
    variableData.setInteger("chunkCoordY", chunkCoordY)
    variableData.setInteger("chunkCoordZ", chunkCoordZ)
    variableData.setInteger("portalCounter", portalCounter)
    variableData.setInteger("teleportDirection", teleportDirection)
    variableData.setBoolean("inPortal", inPortal)
    variableData.setBoolean("isCollided", isCollided)
    variableData.setBoolean("isCollidedHorizontally", isCollidedHorizontally)
    variableData.setBoolean("isCollidedVertically", isCollidedVertically)
    variableData.setBoolean("velocityChanged", velocityChanged)
    variableData.setFloat("entityCollisionReduction", entityCollisionReduction)
    variableData.setFloat("distanceWalkedModified", distanceWalkedModified)
    variableData.setFloat("prevDistanceWalkedModfied", prevDistanceWalkedModified)
    variableData.setFloat("distanceWalkedOnStepModified", distanceWalkedOnStepModified)
    variableData.setFloat("fallDistance", fallDistance)
    variableData.setDouble("renderDistanceWeight", renderDistanceWeight)
    variableData.setBoolean("ignoreFrustumCheck", ignoreFrustumCheck)
    variableData.setBoolean("isSwingInProgress", isSwingInProgress)
    variableData.setInteger("swingProgressInt", swingProgressInt)
    variableData.setInteger("arrowHitTimer", arrowHitTimer)
    variableData.setFloat("prevSwingProgress", prevSwingProgress)
    variableData.setFloat("swingProgress", swingProgress)
    variableData.setFloat("prevLimbSwingAmount", prevLimbSwingAmount)
    variableData.setFloat("limbSwingAmount", limbSwingAmount)
    variableData.setFloat("limbSwing", limbSwing)
    variableData.setFloat("prevCameraPitch", prevCameraPitch)
    variableData.setFloat("cameraPitch", cameraPitch)
    variableData.setFloat("prevRenderYawOffset", prevRenderYawOffset)
    variableData.setFloat("renderYawOffset", renderYawOffset)
    variableData.setFloat("prevRotationYawHead", prevRotationYawHead)
    variableData.setFloat("rotationYawHead", rotationYawHead)
    variableData.setBoolean("captureDrops", captureDrops)
    variableData.setFloat("prevHealth", prevHealth)
    variableData.setInteger("maxHurtTime", maxHurtTime)
    variableData.setInteger("hurtResistantTime", hurtResistantTime)
    variableData.setInteger("maxHurtResistantTime", maxHurtResistantTime)
    variableData.setInteger("entityAge", entityAge)
    variableData.setInteger("recentlyHit", recentlyHit)
    variableData.setInteger("scoreValue", scoreValue)
    variableData.setFloat("lastDamage", lastDamage)
    variableData.setInteger("experienceValue", experienceValue)
    variableData.setInteger("numTicksToChaseTarget", numTicksToChaseTarget)



    //TODO myEntitySize
    //TODO fire (datawatcher)
    //TODO landMovementFactor
    //TODO invulnerable
    //TODO isChild
    //TODO creatureAttribute
    //TODO persistencRequired
    //TODO isSprinting (get&setFlag)
    //TODO riddenByEntity
    //TODO ridingEntity
    //TODO entityRiderYawDelta
    //TODO entityRiderPitchDelta
    //TODO isLeashed
    //TODO leashedToEntity
    //TODO capturedDrops
    //TODO equipment
    //TODO canPickUpLoot
    //TODO health
    //TODO attackingPlayer
    //TODO lastAttacker
    //TODO attackTarget
    //TODO lastAttackerTime
    //TODO entityLivingToAttack
  }

  //The variables as they were the last tick
  protected var syncData: Data = DataHelper.writePrimitives(this)

  private def syncVariables() {
    //The variables in the entity class
    val data = DataHelper.writePrimitives(this)
    val clss = this.getClass

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
  }

  private def setField(name: String, value: Any) {
    var superClass: Any = this.getClass
    val outer = new Breaks

    outer.breakable {
      while (superClass != null) {
        for (field <- superClass.asInstanceOf[Class[_]].getDeclaredFields()) {
          if (field.getName().equals(name)) {
            field.setAccessible(true)
            field.set(this, value)
            outer.break
          }
        }
        superClass = superClass.asInstanceOf[Class[_]].getSuperclass
      }
      outer.break
    }
  }
}