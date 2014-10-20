package seremis.geninfusion.soul.entity

import java.io.IOException
import java.lang.reflect.Field
import java.util
import java.util.Random

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import cpw.mods.fml.relauncher.ReflectionHelper
import io.netty.buffer.ByteBuf
import net.minecraft.entity.Entity.EnumEntitySize
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
import seremis.geninfusion.api.soul.util.Data
import seremis.geninfusion.core.proxy.CommonProxy
import seremis.geninfusion.entity.GIEntityLiving
import seremis.geninfusion.helper.GIReflectionHelper
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

  override def getBoundingBox: AxisAlignedBB = boundingBox

  override def getEntityId: Int = super.getEntityId

  override def getAttributeMap: BaseAttributeMap = super.getAttributeMap

  override def getCombatTracker: CombatTracker = super.func_110142_aN

  //TODO look into this
  override def getCreatureAttribute: EnumCreatureAttribute = super.getCreatureAttribute

  override def getActivePotionsMap: util.HashMap[Integer, PotionEffect] = {
    var value: util.HashMap[Integer, PotionEffect] = new util.HashMap[Integer, PotionEffect]
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "activePotionsMap", "field_70147_f")
      field.setAccessible(true)
      value = field.get(this).asInstanceOf[util.HashMap[Integer, PotionEffect]]
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
    }
    value
  }

  override def getDataWatcher: DataWatcher = super.getDataWatcher

  override def getTasks: EntityAITasks = tasks

  override def getTargetTasks: EntityAITasks = targetTasks

  override def onDeathUpdate = super.onDeathUpdate

  override def setFlag(id: Int, value: Boolean) = super.setFlag(id, value)

  override def getFlag(id: Int): Boolean = super.getFlag(id)

  override def getRandom: Random = rand

  override def isChild: Boolean = getBoolean("isChild")

  private var talkInterval: Int = 0
//TODO this
  override def getTalkInterval: Int = talkInterval

  override def getDeathSound: String = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_DEATH_SOUND).asInstanceOf[AlleleString].value

  override def getLivingSound: String = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_LIVING_SOUND).asInstanceOf[AlleleString].value

  override def getHurtSound: String = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_HURT_SOUND).asInstanceOf[AlleleString].value

  override def getSplashSound: String = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_SPLASH_SOUND).asInstanceOf[AlleleString].value

  override def getSwimSound: String = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_SWIM_SOUND).asInstanceOf[AlleleString].value

  override def getSoundVolume: Float = SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_SOUND_VOLUME).asInstanceOf[AlleleFloat].value

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

  override def attackEntityAsMob(entity: Entity): Boolean = TraitHandler.attackEntityAsMob(this, entity)

  override def readFromNBT(compound: NBTTagCompound) {
    super.readFromNBT(compound)
    soul = new Soul(compound)
    if(compound.hasKey("data")) {
      syncLogic.readFromNBT(compound)
    } else {
      initPersistent = true
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

  override def forceVariableSync() {
    syncLogic.syncVariables()
  }

  private var initPersistent = true;

  def initVariables() {
    syncLogic.makePersistent("ticksExisted")
    syncLogic.makePersistent("posX")
    syncLogic.makePersistent("posY")
    syncLogic.makePersistent("posZ")
    syncLogic.makePersistent("motionX")
    syncLogic.makePersistent("motionY")
    syncLogic.makePersistent("motionZ")
    syncLogic.makePersistent("rotationYaw")
    syncLogic.makePersistent("rotationPitch")
    syncLogic.makePersistent("onGround")
    syncLogic.makePersistent("isDead")
    syncLogic.makePersistent("dimension")
    syncLogic.makePersistent("timeUntilPortal")
    syncLogic.makePersistent("hurtTime")
    syncLogic.makePersistent("deathTime")
    syncLogic.makePersistent("attackTime")
    syncLogic.makePersistent("livingSoundTime")
    syncLogic.makePersistent("talkInterval")
    syncLogic.makePersistent("absorptionAmount")
    syncLogic.makePersistent("fire")

    if(initPersistent) {
      syncLogic.setInteger("ticksExisted", ticksExisted)
      syncLogic.setDouble("posX", posX)
      syncLogic.setDouble("posY", posY)
      syncLogic.setDouble("posZ", posZ)
      syncLogic.setDouble("motionX", motionX)
      syncLogic.setDouble("motionY", motionY)
      syncLogic.setDouble("motionZ", motionZ)
      syncLogic.setDouble("rotationYaw", rotationYaw)
      syncLogic.setDouble("rotationPitch", rotationPitch)
      syncLogic.setBoolean("onGround", onGround)
      syncLogic.setBoolean("isDead", isDead)
      syncLogic.setInteger("dimension", dimension)
      syncLogic.setInteger("timeUntilPortal", timeUntilPortal)
      syncLogic.setInteger("hurtTime", hurtTime)
      syncLogic.setInteger("deathTime", deathTime)
      syncLogic.setInteger("attackTime", attackTime)
      syncLogic.setInteger("livingSoundTime", livingSoundTime)
      syncLogic.setInteger("talkInterval", talkInterval)
    }

    syncLogic.setDouble("prevPosX", prevPosX)
    syncLogic.setDouble("prevPosY", prevPosY)
    syncLogic.setDouble("prevPosZ", prevPosZ)
    if(CommonProxy.instance.isRenderWorld(worldObj)) {
      syncLogic.setInteger("serverPosX", serverPosX)
      syncLogic.setInteger("serverPosY", serverPosY)
      syncLogic.setInteger("serverPosZ", serverPosZ)
    }
    syncLogic.setDouble("lastTickPosX", lastTickPosX)
    syncLogic.setDouble("lastTickPosY", lastTickPosY)
    syncLogic.setDouble("lastTickPosZ", lastTickPosZ)
    syncLogic.setDouble("newPosX", newPosX)
    syncLogic.setDouble("newPosY", newPosY)
    syncLogic.setDouble("newPosZ", newPosZ)
    syncLogic.setInteger("newPosRotationIncrements", newPosRotationIncrements)
    syncLogic.setFloat("jumpMovementFActor", jumpMovementFactor)
    syncLogic.setFloat("moveStrafing", moveStrafing)
    syncLogic.setFloat("moveForward", moveForward)
    syncLogic.setFloat("randomYawVelocity", randomYawVelocity)
    syncLogic.setFloat("width", width)
    syncLogic.setFloat("height", height)
    syncLogic.setFloat("ySize", ySize)
    syncLogic.setFloat("yOffset", yOffset)
    syncLogic.setFloat("stepHeight", stepHeight)
    syncLogic.setFloat("prevRotationYaw", prevRotationYaw)
    syncLogic.setFloat("prevRotationPitch", prevRotationPitch)
    syncLogic.setDouble("newRotationYaw", newRotationYaw)
    syncLogic.setDouble("newRotationPitch", newRotationPitch)
    syncLogic.setFloat("defaultPitch", defaultPitch)
    syncLogic.setInteger("fireResistance", fireResistance)
    syncLogic.setBoolean("inWater", inWater)
    syncLogic.setBoolean("isInWeb", isInWeb)
    syncLogic.setBoolean("isAirBorne", isAirBorne)
    syncLogic.setBoolean("preventEntitySpawning", preventEntitySpawning)
    syncLogic.setBoolean("forceSpawn", forceSpawn)
    syncLogic.setBoolean("noClip", noClip)
    syncLogic.setFloat("attackedAtYaw", attackedAtYaw)
    syncLogic.setBoolean("isJumping", isJumping)
    syncLogic.setBoolean("addedToChunk", addedToChunk)
    syncLogic.setInteger("chunkCoordX", chunkCoordX)
    syncLogic.setInteger("chunkCoordY", chunkCoordY)
    syncLogic.setInteger("chunkCoordZ", chunkCoordZ)
    syncLogic.setInteger("portalCounter", portalCounter)
    syncLogic.setInteger("teleportDirection", teleportDirection)
    syncLogic.setBoolean("inPortal", inPortal)
    syncLogic.setBoolean("isCollided", isCollided)
    syncLogic.setBoolean("isCollidedHorizontally", isCollidedHorizontally)
    syncLogic.setBoolean("isCollidedVertically", isCollidedVertically)
    syncLogic.setBoolean("velocityChanged", velocityChanged)
    syncLogic.setFloat("entityCollisionReduction", entityCollisionReduction)
    syncLogic.setFloat("distanceWalkedModified", distanceWalkedModified)
    syncLogic.setFloat("prevDistanceWalkedModfied", prevDistanceWalkedModified)
    syncLogic.setFloat("distanceWalkedOnStepModified", distanceWalkedOnStepModified)
    syncLogic.setFloat("fallDistance", fallDistance)
    syncLogic.setDouble("renderDistanceWeight", renderDistanceWeight)
    syncLogic.setBoolean("ignoreFrustumCheck", ignoreFrustumCheck)
    syncLogic.setBoolean("isSwingInProgress", isSwingInProgress)
    syncLogic.setInteger("swingProgressInt", swingProgressInt)
    syncLogic.setInteger("arrowHitTimer", arrowHitTimer)
    syncLogic.setFloat("prevSwingProgress", prevSwingProgress)
    syncLogic.setFloat("swingProgress", swingProgress)
    syncLogic.setFloat("prevLimbSwingAmount", prevLimbSwingAmount)
    syncLogic.setFloat("limbSwingAmount", limbSwingAmount)
    syncLogic.setFloat("limbSwing", limbSwing)
    syncLogic.setFloat("prevCameraPitch", prevCameraPitch)
    syncLogic.setFloat("cameraPitch", cameraPitch)
    syncLogic.setFloat("prevRenderYawOffset", prevRenderYawOffset)
    syncLogic.setFloat("renderYawOffset", renderYawOffset)
    syncLogic.setFloat("prevRotationYawHead", prevRotationYawHead)
    syncLogic.setFloat("rotationYawHead", rotationYawHead)
    syncLogic.setBoolean("captureDrops", captureDrops)
    syncLogic.setFloat("prevHealth", prevHealth)
    syncLogic.setInteger("maxHurtTime", maxHurtTime)
    syncLogic.setInteger("hurtResistantTime", hurtResistantTime)
    syncLogic.setInteger("maxHurtResistantTime", maxHurtResistantTime)
    syncLogic.setInteger("entityAge", entityAge)
    syncLogic.setInteger("recentlyHit", recentlyHit)
    syncLogic.setInteger("scoreValue", scoreValue)
    syncLogic.setFloat("lastDamage", lastDamage)
    syncLogic.setInteger("experienceValue", experienceValue)
    syncLogic.setInteger("numTicksToChaseTarget", numTicksToChaseTarget)

    syncLogic.setInteger("myEntitySize", myEntitySize.ordinal())
  }

  var syncMyEntitySize: EnumEntitySize = null
  var syncInvulnerable: Boolean = false
  var syncFire: Int = 0
  var syncHealth, syncAbsorptionAmount, syncLandMovementFactor: Float = 0.0F

  protected def syncNonPrimitives() {
    if(syncAbsorptionAmount != getAbsorptionAmount) {
      setFloat("absorptionAmount", getAbsorptionAmount)
      syncAbsorptionAmount = getAbsorptionAmount
    } else if(syncAbsorptionAmount != getFloat("absorptionAmount")) {
      setAbsorptionAmount(getFloat("absorptionAmount"))
      syncAbsorptionAmount = getFloat("absorptionAmount")
    }

    if (syncMyEntitySize != myEntitySize) {
      setInteger("myEntitySize", myEntitySize.ordinal)
      syncMyEntitySize = myEntitySize
    } else if (syncMyEntitySize != EnumEntitySize.values()(getInteger("myEntitySize"))) {
      myEntitySize = EnumEntitySize.values()(getInteger("myEntitySize"))
      syncMyEntitySize = myEntitySize
    }

    val fire: Int = GIReflectionHelper.getField(this, "fire").asInstanceOf[Int]
    if(syncFire != fire) {
      setInteger("fire", fire)
      syncFire = fire
    } else if(syncFire != getInteger("fire")) {
      GIReflectionHelper.setField(this, "fire", getInteger("fire"))
      syncFire = getInteger("fire")
    }

    val health: Float = dataWatcher.getWatchableObjectFloat(6)
    if(syncHealth != health) {
      setFloat("health", health)
      syncHealth = health
    } else if(syncHealth != getFloat("health")) {
      dataWatcher.updateObject(6, getFloat("health"))
      syncHealth = getFloat("health")
    }

    val landMovementFactor: Float = GIReflectionHelper.getField(this, "landMovementFactor").asInstanceOf[Float]
    if(syncLandMovementFactor != landMovementFactor) {
      setFloat("landMovementFactor", landMovementFactor)
      syncLandMovementFactor = landMovementFactor
    } else if (syncLandMovementFactor != getFloat("landMovementFactor")) {
      GIReflectionHelper.setField(this, "landMovementFactor", getFloat("landMovementFactor"))
      syncLandMovementFactor = getFloat("landMovementFactor")
    }

    val invulnerable: Boolean = GIReflectionHelper.getField(this, "invulnerable").asInstanceOf[Boolean]
    if(syncInvulnerable != invulnerable) {
      setBoolean("invulnerable", invulnerable)
      syncInvulnerable = invulnerable
    } else if(syncInvulnerable != getBoolean("invulnerable")) {
      GIReflectionHelper.setField(this, "invulnerable", getBoolean("invulnerable"))
      syncInvulnerable = getBoolean("invulnerable")
    }

    //TODO Change things, this doesn't work in obfuscated environments

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
    //TODO attackingPlayer
    //TODO lastAttacker
    //TODO attackTarget
    //TODO lastAttackerTime
    //TODO entityLivingToAttack
    //TODO leashedCompound
  }
}