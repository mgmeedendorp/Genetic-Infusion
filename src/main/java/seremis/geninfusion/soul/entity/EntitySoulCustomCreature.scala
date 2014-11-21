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
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{CompressedStreamTools, NBTSizeTracker, NBTTagCompound}
import net.minecraft.pathfinding.PathEntity
import net.minecraft.potion.PotionEffect
import net.minecraft.util.{AxisAlignedBB, ChunkCoordinates, CombatTracker, DamageSource}
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.{Data, DataHelper, UtilSoulEntity}
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ISoul, SoulHelper}
import seremis.geninfusion.core.proxy.CommonProxy
import seremis.geninfusion.entity.GIEntityCreature
import seremis.geninfusion.helper.GIReflectionHelper
import seremis.geninfusion.soul.allele.{AlleleFloat, AlleleInteger, AlleleString}
import seremis.geninfusion.soul.entity.logic.{IVariableSyncEntity, VariableSyncLogic}
import seremis.geninfusion.soul.{Soul, TraitHandler}

import scala.collection.JavaConverters._

class EntitySoulCustomCreature(world: World) extends GIEntityCreature(world) with IEntitySoulCustom with IEntityAdditionalSpawnData with IVariableSyncEntity {

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

  override def getAttributeMap: BaseAttributeMap = super.getAttributeMap

  override def getCombatTracker: CombatTracker = super.func_110142_aN

  var creatureAttribute: EnumCreatureAttribute = null

  override def getCreatureAttribute: EnumCreatureAttribute = creatureAttribute

  override def getActivePotionsMap: util.HashMap[Integer, PotionEffect] = {
    var value: util.HashMap[Integer, PotionEffect] = new util.HashMap[Integer, PotionEffect]
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "activePotionsMap", "field_70147_f")
      field.setAccessible(true)
      value = field.get(this).asInstanceOf[util.HashMap[Integer, PotionEffect]]
      field.setAccessible(false)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    value
  }

  override def getDataWatcher: DataWatcher = super.getDataWatcher

  override def getTasks: EntityAITasks = tasks

  override def getTargetTasks: EntityAITasks = targetTasks

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
    TraitHandler.updateAITick(this)
  }

  override def interact(player: EntityPlayer): Boolean = {
    //TODO interactboolean
    TraitHandler.entityRightClicked(this, player)
    UtilSoulEntity.faceEntity(this, getWorld().getClosestPlayerToEntity(this, 50), 30.0F, 30.0F)
    println(getWorld().getClosestPlayerToEntity(this, 50))
    println(getFloat("rotationYaw"))
    true
  }

  override def attackEntityAsMob(entity: Entity): Boolean = TraitHandler.attackEntityAsMob(this, entity)

  override def readFromNBT(compound: NBTTagCompound) {
    soul = new Soul(compound)
    if (compound.hasKey("data")) {
      syncLogic.readFromNBT(compound)
      initPersistent = false
      posX = syncLogic.getDouble("posX")
      posY = syncLogic.getDouble("posY")
      posZ = syncLogic.getDouble("posZ")
      setPosition(posX, posY, posZ)
    }
  }

  override def writeToNBT(compound: NBTTagCompound) {
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

  override def forceVariableSync() {
    syncLogic.syncVariables()
  }

  override def forceVariableSync(variables: Array[String]) {
    syncLogic.syncVariables(variables)
  }

  private var initPersistent = true

  initVariables()

  override def initVariables() {
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
    syncLogic.makePersistent("absorptionAmount")
    syncLogic.makePersistent("fire")
    syncLogic.makePersistent("persistenceRequired")
    syncLogic.makePersistent("capturedDrops")
    syncLogic.makePersistent("equipment")
    syncLogic.makePersistent("canPickUpLoot")

    if (initPersistent) {
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
      syncLogic.setIntegerArray("capturedDrops", Array.fill(1)(0))
      syncLogic.setItemStackArray("equipment", Array.fill(5)(null))
      syncLogic.setBoolean("canPickUpLoot", canPickUpLoot)
    }
    syncLogic.setDouble("prevPosX", prevPosX)
    syncLogic.setDouble("prevPosY", prevPosY)
    syncLogic.setDouble("prevPosZ", prevPosZ)
    if (CommonProxy.instance.isRenderWorld(worldObj)) {
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
    syncLogic.setInteger("attackingPlayer", 0)
    syncLogic.setInteger("lastAttacker", 0)
    syncLogic.setInteger("attackTarget", 0)
    syncLogic.setInteger("lastAttackerTime", getLastAttackerTime)
    syncLogic.setInteger("entityLivingToAttack", 0)

    syncLogic.setInteger("entityToAttack", 0)
    syncLogic.setBoolean("hasAttacked", hasAttacked)
    syncLogic.setInteger("fleeingTick", fleeingTick)
    syncLogic.setFloat("maximumHomeDistance", func_110174_bM)
    syncLogic.setData("homePosition", DataHelper.writePrimitives(new ChunkCoordinates()))
  }

  var syncHomePosition: ChunkCoordinates = null
  var syncMyEntitySize: EnumEntitySize = null
  var syncRiddenByEntity, syncRidingEntity, syncLeashedToEntity, syncEntityToAttack: Entity = null
  var syncLastAttacker, syncAttackTarget, syncEntityLivingToAttack: EntityLivingBase = null
  var syncAttackingPlayer: EntityPlayer = null
  var syncCapturedDrops: util.ArrayList[EntityItem] = new util.ArrayList[EntityItem]()
  var syncEquipment: Array[ItemStack] = Array.fill(5)(null)
  var syncPathToEntity: PathEntity = null
  var syncInvulnerable, syncIsChild, syncPersistenceRequired, syncIsLeashed, syncCanPickUpLoot, syncHasAttacked: Boolean = false
  var syncFire, syncLastAttackerTime, syncFleeingTick: Int = 0
  var syncHealth, syncAbsorptionAmount, syncLandMovementFactor, syncMaximumHomeDistance: Float = 0.0F
  var syncEntityRiderPitchDelta, syncEntityRiderYawDelta: Double = 0.0D

  override def syncNonPrimitives(variables: Array[String]) {
    //TODO Change things, this doesn't work in obfuscated environments
    val all = variables(0).equals("all")

    if (variables.contains("absorptionAmount") || all) {
      if (syncAbsorptionAmount != getAbsorptionAmount) {
        setFloat("absorptionAmount", getAbsorptionAmount)
        syncAbsorptionAmount = getAbsorptionAmount
      } else if (syncAbsorptionAmount != getFloat("absorptionAmount")) {
        setAbsorptionAmount(getFloat("absorptionAmount"))
        syncAbsorptionAmount = getFloat("absorptionAmount")
      }
    }

    if (variables.contains("myEntitySize") || all) {
      if (syncMyEntitySize != myEntitySize) {
        setInteger("myEntitySize", myEntitySize.ordinal)
        syncMyEntitySize = myEntitySize
      } else if (syncMyEntitySize != EnumEntitySize.values()(getInteger("myEntitySize"))) {
        myEntitySize = EnumEntitySize.values()(getInteger("myEntitySize"))
        syncMyEntitySize = myEntitySize
      }
    }

    if (variables.contains("fire") || all) {
      val fire: Int = GIReflectionHelper.getField(this, "fire").asInstanceOf[Int]
      if (syncFire != fire) {
        setInteger("fire", fire)
        syncFire = fire
      } else if (syncFire != getInteger("fire")) {
        GIReflectionHelper.setField(this, "fire", getInteger("fire"))
        syncFire = getInteger("fire")
      }
    }

    if (variables.contains("health") || all) {
      val health: Float = dataWatcher.getWatchableObjectFloat(6)
      if (syncHealth != health) {
        setFloat("health", health)
        syncHealth = health
      } else if (syncHealth != getFloat("health")) {
        dataWatcher.updateObject(6, getFloat("health"))
        syncHealth = getFloat("health")
      }
    }

    if (variables.contains("landMovementFactor") || all) {
      val landMovementFactor: Float = GIReflectionHelper.getField(this, "landMovementFactor").asInstanceOf[Float]
      if (syncLandMovementFactor != landMovementFactor) {
        setFloat("landMovementFactor", landMovementFactor)
        syncLandMovementFactor = landMovementFactor
      } else if (syncLandMovementFactor != getFloat("landMovementFactor")) {
        GIReflectionHelper.setField(this, "landMovementFactor", getFloat("landMovementFactor"))
        syncLandMovementFactor = getFloat("landMovementFactor")
      }
    }

    if (variables.contains("invulnerable") || all) {
      val invulnerable: Boolean = GIReflectionHelper.getField(this, "invulnerable").asInstanceOf[Boolean]
      if (syncInvulnerable != invulnerable) {
        setBoolean("invulnerable", invulnerable)
        syncInvulnerable = invulnerable
      } else if (syncInvulnerable != getBoolean("invulnerable")) {
        GIReflectionHelper.setField(this, "invulnerable", getBoolean("invulnerable"))
        syncInvulnerable = getBoolean("invulnerable")
      }
    }

    if (variables.contains("creatureAttribute") || all) {
      if (creatureAttribute != EnumCreatureAttribute.values()(getInteger("creatureAttribute"))) {
        creatureAttribute = EnumCreatureAttribute.values()(getInteger("creatureAttribute"))
      }
    }

    if (variables.contains("persistenceRequired") || all) {
      val persistenceRequired: Boolean = GIReflectionHelper.getField(this, "persistenceRequired").asInstanceOf[Boolean]
      if (syncPersistenceRequired != persistenceRequired) {
        setBoolean("persistenceRequired", persistenceRequired)
        syncPersistenceRequired = persistenceRequired
      } else if (syncPersistenceRequired != getBoolean("persistenceRequired")) {
        GIReflectionHelper.setField(this, "persistenceRequired", getBoolean("persistenceRequired"))
        syncPersistenceRequired = getBoolean("persistenceRequired")
      }
    }

    if (variables.contains("riddenByEntity") || all) {
      if (syncRiddenByEntity != riddenByEntity) {
        setInteger("riddenByEntity", if (riddenByEntity != null) riddenByEntity.getEntityId else 0)
        syncRiddenByEntity = riddenByEntity
      } else if (syncRiddenByEntity != (if (getInteger("riddenByEntity") != 0) getWorld.getEntityByID(getInteger("riddenByEntity")) else null)) {
        riddenByEntity = if (getInteger("riddenByEntity") != 0) getWorld.getEntityByID(getInteger("riddenByEntity")) else null
        syncRiddenByEntity = riddenByEntity
      }
    }

    if (variables.contains("ridingEntity") || all) {
      if (syncRidingEntity != ridingEntity) {
        setInteger("ridingEntity", if (ridingEntity != null) ridingEntity.getEntityId else 0)
        syncRidingEntity = ridingEntity
      } else if (syncRidingEntity != (if (getInteger("ridingEntity") != 0) getWorld.getEntityByID(getInteger("ridingEntity")) else null)) {
        ridingEntity = if (getInteger("ridingEntity") != 0) getWorld.getEntityByID(getInteger("ridingEntity")) else null
        syncRidingEntity = ridingEntity
      }
    }

    if (variables.contains("entityRiderPitchDelta") || all) {
      val entityRiderPitchDelta: Double = GIReflectionHelper.getField(this, "entityRiderPitchDelta").asInstanceOf[Double]
      if (syncEntityRiderPitchDelta != entityRiderPitchDelta) {
        setDouble("entityRiderPitchDelta", entityRiderPitchDelta)
        syncEntityRiderPitchDelta = entityRiderPitchDelta
      } else if (syncEntityRiderPitchDelta != getDouble("entityRiderPitchDelta")) {
        GIReflectionHelper.setField(this, "entityRiderPitchDelta", getDouble("entityRiderPitchDelta"))
        syncEntityRiderPitchDelta = entityRiderPitchDelta
      }
    }

    if (variables.contains("entityRiderYawDelta") || all) {
      val entityRiderYawDelta: Double = GIReflectionHelper.getField(this, "entityRiderYawDelta").asInstanceOf[Double]
      if (syncEntityRiderYawDelta != entityRiderYawDelta) {
        setDouble("entityRiderYawDelta", entityRiderYawDelta)
        syncEntityRiderYawDelta = entityRiderYawDelta
      } else if (syncEntityRiderYawDelta != getDouble("entityRiderYawDelta")) {
        GIReflectionHelper.setField(this, "entityRiderYawDelta", getDouble("entityRiderYawDelta"))
        syncEntityRiderYawDelta = entityRiderYawDelta
      }
    }

    if (variables.contains("isLeashed") || all) {
      val isLeashed: Boolean = GIReflectionHelper.getField(this, "isLeashed").asInstanceOf[Boolean]
      if (syncIsLeashed != isLeashed) {
        setBoolean("isLeashed", isLeashed)
        syncIsLeashed = isLeashed
      } else if (syncIsLeashed != getBoolean("isLeashed")) {
        GIReflectionHelper.setField(this, "isLeashed", getBoolean("isLeashed"))
        syncIsLeashed = getBoolean("isLeashed")
      }
    }

    if (variables.contains("leashedToEntity") || all) {
      val leashedToEntity: Entity = GIReflectionHelper.getField(this, "leashedToEntity").asInstanceOf[Entity]
      if (syncLeashedToEntity != leashedToEntity) {
        setInteger("leashedToEntity", if (leashedToEntity != null) leashedToEntity.getEntityId else 0)
        syncLeashedToEntity = leashedToEntity
      } else if (syncLeashedToEntity != (if (getInteger("leashedToEntity") != 0) getWorld.getEntityByID(getInteger("leashedToEntity")) else null)) {
        GIReflectionHelper.setField(this, "leashedToEntity", if (getInteger("leashedToEntity") != 0) getWorld.getEntityByID(getInteger("leashedToEntity")) else null)
        syncLeashedToEntity = leashedToEntity
      }
    }

    if (variables.contains("capturedDrops") || all) {
      var customCapturedDropsNBT = getNBTArray("capturedDrops")
      if (customCapturedDropsNBT == null) customCapturedDropsNBT = Array.fill(1)(null)
      val customCapturedDrops = new util.ArrayList(Array.tabulate(customCapturedDropsNBT.length)(index => {
        if (customCapturedDropsNBT(index) != null) {
          val ent = new EntityItem(worldObj);
          ent.readFromNBT(customCapturedDropsNBT(index));
          ent
        } else null
      }).toList.asJava)
      if (!syncCapturedDrops.equals(capturedDrops)) {
        setNBTArray("capturedDrops", capturedDrops.asScala.map(item => {
          val nbt = new NBTTagCompound()
          item.writeEntityToNBT(nbt)
          nbt
        }).toArray)
        syncCapturedDrops = capturedDrops
      } else if (syncCapturedDrops.equals(customCapturedDrops)) {
        capturedDrops = customCapturedDrops
        syncCapturedDrops = capturedDrops
      }
    }

    if (variables.contains("equipment") || all) {
      val equipment = GIReflectionHelper.getField(this, "equipment").asInstanceOf[Array[ItemStack]]
      if (syncEquipment.deep != equipment.deep) {
        setItemStackArray("equipment", equipment)
        syncEquipment = equipment
      } else if (syncEquipment.deep != getItemStackArray("equipment").deep) {
        GIReflectionHelper.setField(this, "equipment", getItemStackArray("equipment"))
        syncEquipment = getItemStackArray("equipment")
      }
    }

    if (variables.contains("canPickUpLoot") || all) {
      if (syncCanPickUpLoot != canPickUpLoot) {
        setBoolean("canPickUpLoot", canPickUpLoot)
        syncCanPickUpLoot = canPickUpLoot
      } else if (syncCanPickUpLoot != getBoolean("canPickUpLoot")) {
        setCanPickUpLoot(getBoolean("canPickUpLoot"))
        syncCanPickUpLoot = canPickUpLoot
      }
    }

    if (variables.contains("attackingPlayer") || all) {
      if (syncAttackingPlayer != attackingPlayer) {
        setInteger("attackingPlayer", if (attackingPlayer != null) attackingPlayer.getEntityId else 0)
        syncAttackingPlayer = attackingPlayer
      } else if (syncAttackingPlayer != (if (getInteger("attackingPlayer") != 0) getWorld.getEntityByID(getInteger("attackingPlayer")) else null)) {
        attackingPlayer = if (getInteger("attackingPlayer") != 0) getWorld.getEntityByID(getInteger("attackingPlayer")).asInstanceOf[EntityPlayer] else null
        syncAttackingPlayer = attackingPlayer
      }
    }

    if (variables.contains("lastAttacker") || all) {
      val lastAttacker: EntityLivingBase = GIReflectionHelper.getField(this, "lastAttacker").asInstanceOf[EntityLivingBase]
      if (syncLastAttacker != lastAttacker) {
        setInteger("lastAttacker", if (lastAttacker != null) lastAttacker.getEntityId else 0)
        syncLastAttacker = lastAttacker
      } else if (syncLastAttacker != (if (getInteger("lastAttacker") != 0) getWorld.getEntityByID(getInteger("lastAttacker")) else null)) {
        GIReflectionHelper.setField(this, "lastAttacker", if (getInteger("lastAttacker") != 0) getWorld.getEntityByID(getInteger("lastAttacker")) else null)
        syncLastAttacker = lastAttacker
      }
    }

    if (variables.contains("attackTarget") || all) {
      if (syncAttackTarget != getAttackTarget) {
        setInteger("attackTarget", if (getAttackTarget != null) getAttackTarget.getEntityId else 0)
        syncAttackTarget = getAttackTarget
      } else if (syncAttackTarget != (if (getInteger("attackTarget") != 0) getWorld.getEntityByID(getInteger("attackTarget")) else null)) {
        setAttackTarget(if (getInteger("attackTarget") != 0) getWorld.getEntityByID(getInteger("attackTarget")).asInstanceOf[EntityPlayer] else null)
        syncAttackTarget = getAttackTarget
      }
    }

    if (variables.contains("lastAttackerTime") || all) {
      val lastAttackerTime: Int = getLastAttackerTime
      if (syncLastAttackerTime != lastAttackerTime) {
        setInteger("lastAttackerTime", lastAttackerTime)
        syncLastAttackerTime = lastAttackerTime
      } else if (syncLastAttackerTime != getInteger("lastAttackerTime")) {
        GIReflectionHelper.setField(this, "lastAttackerTime", getInteger("lastAttackerTime"))
        syncLastAttackerTime = getInteger("lastAttackerTime")
      }
    }

    if (variables.contains("entityLivingToAttack") || all) {
      val entityLivingToAttack: EntityLivingBase = GIReflectionHelper.getField(this, "entityLivingToAttack").asInstanceOf[EntityLivingBase]
      if (syncEntityLivingToAttack != entityLivingToAttack) {
        setInteger("entityLivingToAttack", if (entityLivingToAttack != null) entityLivingToAttack.getEntityId else 0)
        syncEntityLivingToAttack = entityLivingToAttack
      } else if (syncEntityLivingToAttack != (if (getInteger("entityLivingToAttack") != 0) getWorld.getEntityByID(getInteger("entityLivingToAttack")) else null)) {
        GIReflectionHelper.setField(this, "entityLivingToAttack", if (getInteger("entityLivingToAttack") != 0) getWorld.getEntityByID(getInteger("entityLivingToAttack")) else null)
        syncEntityLivingToAttack = entityLivingToAttack
      }
    }

    if (variables.contains("entityToAttack") || all) {
      if (syncEntityToAttack != entityToAttack) {
        setInteger("entityToAttack", if (entityToAttack != null) entityToAttack.getEntityId else 0)
        syncEntityToAttack = entityToAttack
      } else if (syncEntityToAttack != (if (getInteger("entityToAttack") != 0) getWorld.getEntityByID(getInteger("entityToAttack")) else null)) {
        entityToAttack = if (getInteger("entityToAttack") != 0) getWorld.getEntityByID(getInteger("entityToAttack")) else null
        syncEntityToAttack = entityToAttack
      }
    }

    if (variables.contains("maximumHomeDistance") || all) {
      if (syncMaximumHomeDistance != func_110174_bM) {
        setFloat("maximumHomeDistance", func_110174_bM)
        syncMaximumHomeDistance = func_110174_bM
      } else if (syncMaximumHomeDistance != getFloat("maximumHomeDistance")) {
        GIReflectionHelper.setField(this, "maximumHomeDistance", getFloat("maximumHomeDistance"))
        syncMaximumHomeDistance = getFloat("maximumHomeDistance")
      }
    }

    if (variables.contains("homePosition") || all) {
      val customHomePosition = DataHelper.applyData(getData("homePosition"), new ChunkCoordinates()).asInstanceOf[ChunkCoordinates]
      if (syncHomePosition != getHomePosition) {
        setData("homePosition", DataHelper.writePrimitives(getHomePosition))
        syncHomePosition = getHomePosition
      } else if (syncHomePosition.compareTo(customHomePosition) != 0) {
        GIReflectionHelper.setField(this, "homePosition", customHomePosition)
        syncHomePosition = getHomePosition
      }
    }

    if (variables.contains("pathToEntity") || all) {
      val pathToEntity = GIReflectionHelper.getField(this, "pathToEntity").asInstanceOf[PathEntity]
      val customPathToEntity = UtilSoulEntity.readPathEntity(this, "pathToEntity")

      if (syncPathToEntity != pathToEntity) {
        UtilSoulEntity.writePathEntity(this, pathToEntity, "pathToEntity")
        syncPathToEntity = pathToEntity
      } else if (syncPathToEntity != customPathToEntity) {
        GIReflectionHelper.setField(this, "pathToEntity", customPathToEntity)
        syncPathToEntity = customPathToEntity
      }
    }
  }
}