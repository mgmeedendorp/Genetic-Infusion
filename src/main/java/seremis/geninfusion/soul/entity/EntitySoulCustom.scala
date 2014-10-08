package seremis.geninfusion.soul.entity

import java.io.IOException
import java.lang.reflect.Field
import java.util
import java.util.Random

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
import seremis.geninfusion.misc.Data
import seremis.geninfusion.soul.allele.{AlleleFloat, AlleleString}
import seremis.geninfusion.soul.entity.logic.{IVariableSyncEntity, VariableSyncLogic}
import seremis.geninfusion.soul.{Soul, TraitHandler}

class EntitySoulCustom(world: World) extends GIEntityLiving(world) with IEntitySoulCustom with IEntityAdditionalSpawnData with IVariableSyncEntity {

  var soul: ISoul = _
  var syncLogic: VariableSyncLogic = new VariableSyncLogic(this)

  def this(world: World, soul: ISoul, x: Double, y: Double, z: Double) {
    this(world)
    setPosition(x, y, z)
    setSize(0.8F, 1.7F)
    this.soul = soul
    initVariables()
  }


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
    syncLogic.syncVariables()
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
    syncLogic.persistentData = new Data(compound)
    if(!syncLogic.persistentData.isEmpty) {
      initPersistent = false
    }
  }

  override def writeToNBT(compound: NBTTagCompound) {
    super.writeToNBT(compound)
    soul.writeToNBT(compound)
    syncLogic.persistentData.writeToNBT(compound)
  }

  override def setPersistentVariable(name: String, variable: Boolean) {
    syncLogic.setPersistentVariable(name, variable)
  }

  override def setPersistentVariable(name: String, variable: Byte) {
    syncLogic.setPersistentVariable(name, variable)
  }

  override def setPersistentVariable(name: String, variable: Int) {
    syncLogic.setPersistentVariable(name, variable)
  }

  override def setPersistentVariable(name: String, variable: Float) {
    syncLogic.setPersistentVariable(name, variable)
  }

  override def setPersistentVariable(name: String, variable: Double) {
    syncLogic.setPersistentVariable(name, variable)
  }

  override def setPersistentVariable(name: String, variable: String) {
    syncLogic.setPersistentVariable(name, variable)
  }

  override def setPersistentVariable(name: String, variable: ItemStack) {
    syncLogic.setPersistentVariable(name, variable)
  }

  override def getPersistentBoolean(name: String): Boolean = {
    syncLogic.getPersistentBoolean(name)
  }

  override def getPersistentByte(name: String): Byte = {
    syncLogic.getPersistentByte(name)
  }

  override def getPersistentInteger(name: String): Int = {
    syncLogic.getPersistentInteger(name)
  }

  override def getPersistentFloat(name: String): Float = {
    syncLogic.getPersistentFloat(name)
  }

  override def getPersistentDouble(name: String): Double = {
    syncLogic.getPersistentDouble(name)
  }

  override def getPersistentString(name: String): String = {
    syncLogic.getPersistentString(name)
  }

  override def getPersistentItemStack(name: String): ItemStack = {
    syncLogic.getPersistentItemStack(name)
  }

  override def setVariable(name: String, variable: Boolean) {
    syncLogic.setVariable(name, variable)
  }

  override def setVariable(name: String, variable: Byte) {
    syncLogic.setVariable(name, variable)
  }

  override def setVariable(name: String, variable: Int) {
    syncLogic.setVariable(name, variable)
  }

  override def setVariable(name: String, variable: Float) {
    syncLogic.setVariable(name, variable)
  }

  override def setVariable(name: String, variable: Double) {
    syncLogic.setVariable(name, variable)
  }

  override def setVariable(name: String, variable: String) {
    syncLogic.setVariable(name, variable)
  }

  override def setVariable(name: String, variable: ItemStack) {
    syncLogic.setVariable(name, variable)
  }

  override def getBoolean(name: String): Boolean = {
    syncLogic.getBoolean(name)
  }

  override def getByte(name: String): Byte = {
    syncLogic.getByte(name)
  }

  override def getInteger(name: String): Int = {
    syncLogic.getInteger(name)
  }

  override def getFloat(name: String): Float = {
    syncLogic.getFloat(name)
  }

  override def getDouble(name: String): Double = {
    syncLogic.getDouble(name)
  }

  override def getString(name: String): String = {
    syncLogic.getString(name)
  }

  override def getItemStack(name: String): ItemStack = {
    syncLogic.getItemStack(name)
  }

  override def forceVariableSync() {
    syncLogic.syncVariables()
  }

  private var initPersistent = true;

  def initVariables() {
    val persistentData = syncLogic.persistentData
    val variableData = syncLogic.variableData

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
  }

  protected def syncNonPrimitives() {
    //TODO absorptionAmount persistent


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
}