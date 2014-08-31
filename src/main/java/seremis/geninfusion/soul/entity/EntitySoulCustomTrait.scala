package seremis.geninfusion.soul.entity

import java.io.IOException
import java.lang.reflect.Field
import java.util
import java.util.{Random, ArrayList, HashMap}
import java.util.concurrent.ConcurrentHashMap

import cpw.mods.fml.common.ObfuscationReflectionHelper
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import cpw.mods.fml.relauncher.ReflectionHelper
import io.netty.buffer.ByteBuf
import net.minecraft.entity.Entity.EnumEntitySize
import net.minecraft.entity.ai.EntityAITasks
import net.minecraft.entity.ai.attributes.BaseAttributeMap
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity._
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTSizeTracker, CompressedStreamTools, NBTTagCompound, NBTTagList}
import net.minecraft.potion.PotionEffect
import net.minecraft.util.{CombatTracker, AxisAlignedBB, DamageSource}
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.common.util.Constants
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper, ISoul}
import seremis.geninfusion.core.proxy.CommonProxy
import seremis.geninfusion.entity.GIEntityLiving
import seremis.geninfusion.item.ModItems
import seremis.geninfusion.soul.allele.{AlleleFloat, AlleleString}
import seremis.geninfusion.soul.{Soul, TraitHandler}

trait EntitySoulCustomTrait extends GIEntityLivingScala with IEntityAdditionalSpawnData with IEntitySoulCustom {

  protected var soul: ISoul = null

  override def getSoul: ISoul = soul

  override def writeSpawnData(data: ByteBuf) {
    val compound: NBTTagCompound = new NBTTagCompound
    writeToNBT(compound)
    var abyte: Array[Byte] = null
    try {
      abyte = CompressedStreamTools.compress(compound)
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
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
    getRand
  }

  override def isChild: Boolean = {
    isChildVar
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

  private var syncTicksExisted: Int = 0

  protected def syncVariables() {
    syncCoordinates()
    syncMovement()
    syncRotation()
    syncSize()
    syncEnvironment()
    syncChunk()
    syncDimensions()
    syncCollisions()
    syncInventory()
    syncWalking()
    syncRender()
    syncRiding()
    syncHealthStuff()
    syncAttack()
    syncSound()
    if (syncTicksExisted != ticksExisted) {
      persistentInteger.put("ticksExisted", ticksExisted)
      syncTicksExisted = ticksExisted
    }
    else if (ticksExisted != getPersistentInteger("ticksExisted")) {
      ticksExisted = getPersistentInteger("ticksExisted")
      syncTicksExisted = ticksExisted
    }
  }

  private var syncPosX, syncPosY, syncPosZ, syncPrevPosX, syncPrevPosY, syncPrevPosZ, syncLastTickPosX, syncLastTickPosY, syncLastTickPosZ, syncNewPosX, syncNewPosY, syncNewPosZ: Double = 0
  private var syncServerPosX, syncServerPosY, syncServerPosZ, syncNewPosRotationIncrements: Int = 0

  private def syncCoordinates() {
    if (syncPosX != posX) {
      persistentDouble.put("posX", posX)
      syncPosX = posX
    }
    else if (syncPosX != getPersistentDouble("posX")) {
      posX = getPersistentDouble("posX")
      syncPosX = posX
    }
    if (syncPosY != posY) {
      persistentDouble.put("posY", posY)
      syncPosY = posY
    }
    else if (syncPosY != getPersistentDouble("posY")) {
      posY = getPersistentDouble("posY")
      syncPosY = posY
    }
    if (syncPosZ != posZ) {
      persistentDouble.put("posZ", posZ)
      syncPosZ = posZ
    }
    else if (syncPosZ != getPersistentDouble("posZ")) {
      posZ = getPersistentDouble("posZ")
      syncPosZ = posZ
    }
    if (syncPrevPosX != prevPosX) {
      variableDouble.put("prevPosX", prevPosX)
      syncPrevPosX = prevPosX
    }
    else if (syncPrevPosX != getDouble("prevPosX")) {
      prevPosX = getDouble("prevPosX")
      syncPrevPosX = prevPosX
    }
    if (syncPrevPosY != prevPosY) {
      variableDouble.put("prevPosY", prevPosY)
      syncPrevPosY = prevPosY
    }
    else if (syncPrevPosY != getDouble("prevPosY")) {
      prevPosY = getDouble("prevPosY")
      syncPrevPosY = prevPosY
    }
    if (syncPrevPosZ != prevPosZ) {
      variableDouble.put("prevPosZ", prevPosZ)
      syncPrevPosZ = prevPosZ
    }
    else if (syncPrevPosZ != getDouble("prevPosZ")) {
      prevPosZ = getDouble("prevPosZ")
      syncPrevPosZ = prevPosZ
    }
    if (CommonProxy.instance.isRenderWorld(worldObj)) {
      if (syncServerPosX != serverPosX) {
        variableInteger.put("serverPosX", serverPosX)
        syncServerPosX = serverPosX
      }
      else if (syncServerPosX != getInteger("serverPosX")) {
        serverPosX = getInteger("serverPosX")
        syncServerPosX = serverPosX
      }
      if (syncServerPosY != serverPosY) {
        variableInteger.put("serverPosY", serverPosY)
        syncServerPosY = serverPosY
      }
      else if (syncServerPosY != getInteger("serverPosY")) {
        serverPosY = getInteger("serverPosY")
        syncServerPosY = serverPosY
      }
      if (syncServerPosZ != serverPosZ) {
        variableInteger.put("serverPosZ", serverPosZ)
        syncServerPosZ = serverPosZ
      }
      else if (syncServerPosZ != getInteger("serverPosZ")) {
        serverPosZ = getInteger("serverPosZ")
        syncServerPosZ = serverPosZ
      }
    }
    if (syncLastTickPosX != lastTickPosX) {
      variableDouble.put("lastTickPosX", lastTickPosX)
      syncLastTickPosX = lastTickPosX
    }
    else if (syncLastTickPosX != getDouble("lastTickPosX")) {
      lastTickPosX = getDouble("lastTickPosX")
      syncLastTickPosX = lastTickPosX
    }
    if (syncLastTickPosY != lastTickPosY) {
      variableDouble.put("lastTickPosY", lastTickPosY)
      syncLastTickPosY = lastTickPosY
    }
    else if (syncLastTickPosY != getDouble("lastTickPosY")) {
      lastTickPosY = getDouble("lastTickPosY")
      syncLastTickPosY = lastTickPosY
    }
    if (syncLastTickPosZ != lastTickPosZ) {
      variableDouble.put("lastTickPosZ", lastTickPosZ)
      syncLastTickPosZ = lastTickPosZ
    }
    else if (syncLastTickPosZ != getDouble("lastTickPosZ")) {
      lastTickPosZ = getDouble("lastTickPosZ")
      syncLastTickPosZ = lastTickPosZ
    }
    if (syncNewPosRotationIncrements != getNewPosRotationIncrements) {
      variableInteger.put("newPosRotationIncrements", getNewPosRotationIncrements)
      syncNewPosRotationIncrements = getNewPosRotationIncrements
    }
    else if (syncNewPosRotationIncrements != getInteger("newPosRotationIncrements")) {
      setNewPosRotationIncrements(getInteger("newPosRotationIncrements"))
      syncNewPosRotationIncrements = getNewPosRotationIncrements
    }
    if (syncNewPosX != getNewPosX) {
      variableDouble.put("newPosX", getNewPosX)
      syncNewPosX = getNewPosX
    }
    else if (syncNewPosX != getDouble("newPosX")) {
      setNewPosX(getDouble("newPosX"))
      syncNewPosX = getNewPosX
    }
    if (syncNewPosY != getNewPosY) {
      variableDouble.put("newPosY", getNewPosY)
      syncNewPosY = getNewPosY
    }
    else if (syncNewPosY != getDouble("newPosY")) {
      setNewPosY(getDouble("newPosY"))
      syncNewPosY = getNewPosY
    }
    if (syncNewPosZ != getNewPosZ) {
      variableDouble.put("newPosZ", getNewPosZ)
      syncNewPosZ = getNewPosZ
    }
    else if (syncNewPosZ != getDouble("newPosZ")) {
      setNewPosZ(getDouble("newPosZ"))
      syncNewPosZ = getNewPosZ
    }
  }

  private var syncMotionX, syncMotionY, syncMotionZ: Double = .0
  private var syncJumpMovementFactor, syncMoveStrafing, syncMoveForward, syncRandomYawVelocity, syncLandMovementFactor: Float = 0F

  private def syncMovement() {
    if (syncMotionX != motionX) {
      persistentDouble.put("motionX", motionX)
      syncMotionX = motionX
    }
    else if (syncMotionX != getPersistentDouble("motionX")) {
      motionX = getPersistentDouble("motionX")
      syncMotionX = motionX
    }
    if (syncMotionY != motionY) {
      persistentDouble.put("motionY", motionY)
      syncMotionY = motionY
    }
    else if (syncMotionY != getPersistentDouble("motionY")) {
      motionY = getPersistentDouble("motionY")
      syncMotionY = motionY
    }
    if (syncMotionZ != motionZ) {
      persistentDouble.put("motionZ", motionZ)
      syncMotionZ = motionZ
    }
    else if (syncMotionZ != getPersistentDouble("motionZ")) {
      motionZ = getPersistentDouble("motionZ")
      syncMotionZ = motionZ
    }
    if (syncJumpMovementFactor != jumpMovementFactor) {
      variableFloat.put("jumpMovementFactor", jumpMovementFactor)
      syncJumpMovementFactor = jumpMovementFactor
    }
    else if (syncJumpMovementFactor != getFloat("jumpMovementFactor")) {
      jumpMovementFactor = getFloat("jumpMovementFactor")
      syncJumpMovementFactor = jumpMovementFactor
    }
    if (syncMoveStrafing != moveStrafing) {
      variableFloat.put("moveStrafing", moveStrafing)
      syncMoveStrafing = moveStrafing
    }
    else if (syncMoveStrafing != getFloat("moveStrafing")) {
      moveStrafing = getFloat("moveStrafing")
      syncMoveStrafing = moveStrafing
    }
    if (syncMoveForward != moveForward) {
      variableFloat.put("moveForward", moveForward)
      syncMoveForward = moveForward
    }
    else if (syncMoveForward != getFloat("moveForward")) {
      moveForward = getFloat("moveForward")
      syncMoveForward = moveForward
    }
    if (syncRandomYawVelocity != getRandomYawVelocity) {
      variableFloat.put("randomYawVelocity", getRandomYawVelocity)
      syncRandomYawVelocity = getRandomYawVelocity
    }
    else if (syncRandomYawVelocity != getFloat("randomYawVelocity")) {
      setRandomYawVelocity(getFloat("randomYawVelocity"))
      syncRandomYawVelocity = getRandomYawVelocity
    }
    if (syncLandMovementFactor != getLandMovementFactor) {
      variableFloat.put("landMovementFactor", getLandMovementFactor)
      syncLandMovementFactor = getLandMovementFactor
    }
    else if (syncLandMovementFactor != getFloat("landMovementFactor")) {
      setAIMoveSpeed(getFloat("landMovementFactor"))
      syncLandMovementFactor = getLandMovementFactor
    }
  }

  private var syncWidth, syncHeight, syncYSize, syncYOffset, syncStepHeight: Float = 0F
  private var syncMyEntitySize: Entity.EnumEntitySize = null

  private def syncSize() {
    if (syncWidth != width) {
      variableFloat.put("width", width)
      syncWidth = width
    }
    else if (syncWidth != getFloat("width")) {
      width = getFloat("width")
      syncWidth = width
    }
    if (syncHeight != height) {
      variableFloat.put("height", height)
      syncHeight = height
    }
    else if (syncHeight != getFloat("height")) {
      height = getFloat("height")
      syncHeight = height
    }
    if (syncYSize != ySize) {
      variableFloat.put("ySize", ySize)
      syncYSize = ySize
    }
    else if (syncYSize != getFloat("ySize")) {
      ySize = getFloat("ySize")
      syncYSize = ySize
    }
    if (syncMyEntitySize != myEntitySize) {
      variableInteger.put("myEntitySize", myEntitySize.ordinal)
      syncMyEntitySize = myEntitySize
    }
    else if (syncMyEntitySize != EnumEntitySize.values()(getInteger("myEntitySize"))) {
      myEntitySize = EnumEntitySize.values()(getInteger("myEntitySize"))
      syncMyEntitySize = myEntitySize
    }
    if (syncYOffset != yOffset) {
      variableFloat.put("yOffset", yOffset)
      syncYOffset = yOffset
    }
    else if (syncYOffset != getFloat("yOffset")) {
      yOffset = getFloat("yOffset")
      syncYOffset = yOffset
    }
    if (syncStepHeight != stepHeight) {
      variableFloat.put("stepHeight", stepHeight)
      syncStepHeight = stepHeight
    }
    else if (syncStepHeight != getFloat("stepHeight")) {
      stepHeight = getFloat("stepHeight")
      syncStepHeight = stepHeight
    }
  }

  private var syncNewYaw, syncNewPitch: Double = .0
  private var syncYaw, syncPitch, syncPrevYaw, syncPrevPitch, syncDefaultPitch: Float = 0F

  private def syncRotation() {
    if (syncYaw != rotationYaw) {
      persistentFloat.put("rotationYaw", rotationYaw)
      syncYaw = rotationYaw
    }
    else if (syncYaw != getPersistentFloat("rotationYaw")) {
      rotationYaw = getPersistentFloat("rotationYaw")
      syncYaw = rotationYaw
    }
    if (syncPitch != rotationPitch) {
      persistentFloat.put("rotationPitch", rotationPitch)
      syncPitch = rotationPitch
    }
    else if (syncPitch != getPersistentFloat("rotationPitch")) {
      rotationPitch = getPersistentFloat("rotationPitch")
      syncPitch = rotationPitch
    }
    if (syncPrevYaw != prevRotationYaw) {
      variableFloat.put("prevRotationYaw", prevRotationYaw)
      syncPrevYaw = prevRotationYaw
    }
    else if (syncPrevYaw != getFloat("prevRotationYaw")) {
      prevRotationYaw = getFloat("prevRotationYaw")
      syncPrevYaw = prevRotationYaw
    }
    if (syncPrevPitch != prevRotationPitch) {
      variableFloat.put("prevRotationPitch", prevRotationPitch)
      syncPrevPitch = prevRotationPitch
    }
    else if (syncPrevPitch != getFloat("prevRotationPitch")) {
      prevRotationPitch = getFloat("prevRotationPitch")
      syncPrevPitch = prevRotationPitch
    }
    if (syncNewYaw != getNewRotationYaw) {
      variableDouble.put("newRotationYaw", getNewRotationYaw)
      syncNewYaw = getNewRotationYaw
    }
    else if (syncNewYaw != getDouble("newRotationYaw")) {
      setNewRotationYaw(getDouble("newRotationYaw"))
      syncNewYaw = getNewRotationYaw
    }
    if (syncNewPitch != getNewRotationPitch) {
      variableDouble.put("newRotationPitch", getNewRotationPitch)
      syncNewPitch = getNewRotationPitch
    }
    else if (syncNewPitch != getDouble("newRotationPitch")) {
      setNewRotationPitch(getDouble("newRotationPitch"))
      syncNewPitch = getNewRotationPitch
    }
    if (syncDefaultPitch != getDefaultPitch) {
      variableFloat.put("defaultPitch", getDefaultPitch)
      syncDefaultPitch = getDefaultPitch
    }
    else if (syncDefaultPitch != getFloat("defaultPitch")) {
      setDefaultPitch(getFloat("defaultPitch"))
      syncDefaultPitch = getDefaultPitch
    }
  }

  private var syncAttackedAtYaw: Float = 0F
  private var syncFire, syncFireResistance, syncAir: Int = 0
  private var syncInWater, syncOnGround, syncIsInWeb, syncIsDead, syncIsAirBorne, syncPreventEntitySpawning, syncForceSpawn, syncNoClip, syncInvulnerable, syncIsJumping, syncIsChild: Boolean = false

  private var isChildVar: Boolean = false

  private def syncEnvironment() {
    if (syncFire != getFire) {
      persistentInteger.put("fire", getFire)
      syncFire = getFire
    }
    else if (syncFire != getPersistentInteger("fire")) {
      setFireTicks(getPersistentInteger("fire"))
      syncFire = getFire
    }
    if (syncAir != getAir) {
      persistentInteger.put("air", getAir)
      syncAir = getAir
    }
    else if (syncAir != getPersistentInteger("air")) {
      setAir(getPersistentInteger("air"))
      syncAir = getAir
    }
    if (syncFireResistance != fireResistance) {
      variableInteger.put("fireResistance", fireResistance)
      syncFireResistance = fireResistance
    }
    else if (syncFireResistance != getInteger("fireResistance")) {
      fireResistance = getInteger("fireResistance")
      syncFireResistance = fireResistance
    }
    if (syncInWater != getInWater) {
      variableBoolean.put("inWater", getInWater)
      syncInWater = getInWater
    }
    else if (syncInWater != getBoolean("inWater")) {
      setInWater(getBoolean("inWater"))
      syncInWater = getInWater
    }
    if (syncOnGround != onGround) {
      persistentBoolean.put("onGround", onGround)
      syncOnGround = onGround
    }
    else if (syncOnGround != getPersistentBoolean("onGround")) {
      onGround = getPersistentBoolean("onGround")
      syncOnGround = onGround
    }
    if (syncIsInWeb != getIsInWeb) {
      variableBoolean.put("isInWeb", getIsInWeb)
      syncIsInWeb = getIsInWeb
    }
    else if (syncIsInWeb != getBoolean("isInWeb")) {
      setIsInWeb(getBoolean("isInWeb"))
      syncIsInWeb = getIsInWeb
    }
    if (syncIsDead != isDead) {
      persistentBoolean.put("isDead", isDead)
      syncIsDead = isDead
    }
    else if (syncIsDead != getPersistentBoolean("isDead")) {
      isDead = getPersistentBoolean("isDead")
      syncIsDead = isDead
    }
    if (syncIsAirBorne != isAirBorne) {
      variableBoolean.put("isAirBorne", isAirBorne)
      syncIsAirBorne = isAirBorne
    }
    else if (syncIsAirBorne != getBoolean("isAirBorne")) {
      isAirBorne = getBoolean("isAirBorne")
      syncIsAirBorne = isAirBorne
    }
    if (syncPreventEntitySpawning != preventEntitySpawning) {
      variableBoolean.put("preventEntitySpawning", preventEntitySpawning)
      syncPreventEntitySpawning = preventEntitySpawning
    }
    else if (syncPreventEntitySpawning != getBoolean("preventEntitySpawning")) {
      preventEntitySpawning = getBoolean("preventEntitySpawning")
      syncPreventEntitySpawning = preventEntitySpawning
    }
    if (syncForceSpawn != forceSpawn) {
      variableBoolean.put("forceSpawn", forceSpawn)
      syncForceSpawn = forceSpawn
    }
    else if (syncForceSpawn != getBoolean("forceSpawn")) {
      forceSpawn = getBoolean("forceSpawn")
      syncForceSpawn = forceSpawn
    }
    if (syncNoClip != noClip) {
      variableBoolean.put("noClip", noClip)
      syncNoClip = noClip
    }
    else if (syncNoClip != getBoolean("noClip")) {
      noClip = getBoolean("noClip")
      syncNoClip = noClip
    }
    if (syncInvulnerable != isEntityInvulnerable) {
      variableBoolean.put("invulnerable", isEntityInvulnerable)
      syncInvulnerable = isEntityInvulnerable
    }
    else if (syncInvulnerable != getBoolean("invulnerable")) {
      setInvulnerable(getBoolean("invulnerable"))
      syncInvulnerable = isEntityInvulnerable
    }
    if (syncAttackedAtYaw != attackedAtYaw) {
      persistentFloat.put("attackedAtYaw", attackedAtYaw)
      syncAttackedAtYaw = attackedAtYaw
    }
    else if (syncAttackedAtYaw != getPersistentFloat("attackedAtYaw")) {
      attackedAtYaw = getPersistentFloat("attackedAtYaw")
      syncAttackedAtYaw = attackedAtYaw
    }
    if (syncIsJumping != getIsJumping) {
      variableBoolean.put("isJumping", getIsJumping)
      syncIsJumping = getIsJumping
    }
    else if (syncForceSpawn != getBoolean("isJumping")) {
      setIsJumping(getBoolean("isJumping"))
      syncIsJumping = getIsJumping
    }
    if (syncIsChild != isChildVar) {
      variableBoolean.put("isChild", isChildVar)
      syncIsChild = isChildVar
    }
    else if (syncIsChild != getBoolean("isChild")) {
      isChildVar = getBoolean("isChild")
      syncIsChild = isChildVar
    }
    if (creatureAttribute != EnumCreatureAttribute.values()(getInteger("creatureAttribute"))) {
      creatureAttribute = EnumCreatureAttribute.values()(getInteger("creatureAttribute"))
    }
  }

  private var syncAddedToChunk, syncPersistenceRequired: Boolean = false
  private var syncChunkCoordX, syncChunkCoordY, syncChunkCoordZ: Int = 0

  private def syncChunk() {
    if (syncAddedToChunk != addedToChunk) {
      variableBoolean.put("addedToChunk", addedToChunk)
      syncAddedToChunk = addedToChunk
    }
    else if (syncAddedToChunk != getBoolean("addedToChunk")) {
      addedToChunk = getBoolean("addedToChunk")
      syncAddedToChunk = addedToChunk
    }
    if (syncChunkCoordX != chunkCoordX) {
      variableInteger.put("chunkCoordX", chunkCoordX)
      syncChunkCoordX = chunkCoordX
    }
    else if (syncChunkCoordX != getInteger("chunkCoordX")) {
      chunkCoordX = getInteger("chunkCoordX")
      syncChunkCoordX = chunkCoordX
    }
    if (syncChunkCoordY != chunkCoordY) {
      variableInteger.put("chunkCoordY", chunkCoordY)
      syncChunkCoordY = chunkCoordY
    }
    else if (syncChunkCoordY != getInteger("chunkCoordY")) {
      chunkCoordY = getInteger("chunkCoordY")
      syncChunkCoordY = chunkCoordY
    }
    if (syncChunkCoordZ != chunkCoordZ) {
      variableInteger.put("chunkCoordZ", chunkCoordZ)
      syncChunkCoordZ = chunkCoordZ
    }
    else if (syncChunkCoordZ != getInteger("chunkCoordZ")) {
      chunkCoordZ = getInteger("chunkCoordZ")
      syncChunkCoordZ = chunkCoordZ
    }
    if (syncPersistenceRequired != isNoDespawnRequired) {
      persistentBoolean.put("persistenceRequired", isNoDespawnRequired)
      syncPersistenceRequired = isNoDespawnRequired
    }
    else if (syncPersistenceRequired != getPersistentBoolean("persistenceRequired")) {
      setPersistenceRequired(getPersistentBoolean("persistenceRequired"))
      syncPersistenceRequired = isNoDespawnRequired
    }
  }

  private var syncDimension, syncPortalCounter, syncTimeUntilPortal, syncTeleportDirection: Int = 0
  private var syncInPortal: Boolean = false

  private def syncDimensions() {
    if (syncDimension != dimension) {
      persistentInteger.put("dimension", dimension)
      syncDimension = dimension
    }
    else if (syncDimension != getPersistentInteger("dimension")) {
      dimension = getPersistentInteger("dimension")
      syncDimension = dimension
    }
    if (syncPortalCounter != getPortalCounter) {
      variableInteger.put("portalCounter", getPortalCounter)
      syncPortalCounter = getPortalCounter
    }
    else if (syncPortalCounter != getInteger("portalCounter")) {
      setPortalCounter(getInteger("portalCounter"))
      syncPortalCounter = getPortalCounter
    }
    if (syncTimeUntilPortal != timeUntilPortal) {
      persistentInteger.put("timeUntilPortal", timeUntilPortal)
      syncTimeUntilPortal = timeUntilPortal
    }
    else if (syncTimeUntilPortal != getPersistentInteger("timeUntilPortal")) {
      timeUntilPortal = getPersistentInteger("timeUntilPortal")
      syncTimeUntilPortal = timeUntilPortal
    }
    if (syncTeleportDirection != getTeleportDirection) {
      variableInteger.put("teleportDirection", getTeleportDirection)
      syncTeleportDirection = getTeleportDirection
    }
    else if (syncTeleportDirection != getInteger("teleportDirection")) {
      setTeleportDirection(getInteger("teleportDirection"))
      syncTeleportDirection = getTeleportDirection
    }
    if (syncInPortal != getInPortal) {
      variableBoolean.put("inPortal", getInPortal)
      syncInPortal = getInPortal
    }
    else if (syncInPortal != getBoolean("inPortal")) {
      setInPortal(getBoolean("inPortal"))
      syncInPortal = getInPortal
    }
  }

  private var syncIsCollidedHorizontally, syncIsCollidedVertically, syncIsCollided, syncVelocityChanged: Boolean = false
  private var syncEntityCollisionReduction: Float = 0F

  private def syncCollisions() {
    if (syncIsCollidedHorizontally != isCollidedHorizontally) {
      variableBoolean.put("isCollidedHorizontally", isCollidedHorizontally)
      syncIsCollidedHorizontally = isCollidedHorizontally
    }
    else if (syncIsCollidedHorizontally != getBoolean("isCollidedHorizontally")) {
      isCollidedHorizontally = getBoolean("isCollidedHorizontally")
      syncIsCollidedHorizontally = isCollidedHorizontally
    }
    if (syncIsCollidedVertically != isCollidedVertically) {
      variableBoolean.put("isCollidedVertically", isCollidedVertically)
      syncIsCollidedVertically = isCollidedVertically
    }
    else if (syncIsCollidedVertically != getBoolean("isCollidedVertically")) {
      isCollidedVertically = getBoolean("isCollidedVertically")
      syncIsCollidedVertically = isCollidedVertically
    }
    if (syncIsCollided != isCollided) {
      variableBoolean.put("isCollided", isCollided)
      syncIsCollided = isCollided
    }
    else if (syncIsCollided != getBoolean("isCollided")) {
      isCollided = getBoolean("isCollided")
      syncIsCollided = isCollided
    }
    if (syncVelocityChanged != velocityChanged) {
      variableBoolean.put("velocityChanged", velocityChanged)
      syncVelocityChanged = velocityChanged
    }
    else if (syncVelocityChanged != getBoolean("velocityChanged")) {
      velocityChanged = getBoolean("velocityChanged")
      syncVelocityChanged = velocityChanged
    }
    if (syncEntityCollisionReduction != entityCollisionReduction) {
      variableFloat.put("entityCollisionReduction", entityCollisionReduction)
      syncEntityCollisionReduction = entityCollisionReduction
    }
    else if (syncEntityCollisionReduction != getFloat("entityCollisionReduction")) {
      entityCollisionReduction = getFloat("entityCollisionReduction")
      syncEntityCollisionReduction = entityCollisionReduction
    }
  }

  private var syncDistanceWalkedModified, syncPrevDistanceWalkedModified, syncDistanceWalkedOnStepModified, syncFallDistance: Float = 0F
  private var syncSprinting: Boolean = false

  private def syncWalking() {
    if (syncDistanceWalkedModified != distanceWalkedModified) {
      variableFloat.put("distanceWalkedModified", distanceWalkedModified)
      syncDistanceWalkedModified = distanceWalkedModified
    }
    else if (syncDistanceWalkedModified != getFloat("distanceWalkedModified")) {
      distanceWalkedModified = getFloat("distanceWalkedModified")
      syncDistanceWalkedModified = distanceWalkedModified
    }
    if (syncPrevDistanceWalkedModified != prevDistanceWalkedModified) {
      variableFloat.put("prevDistanceWalkedModified", prevDistanceWalkedModified)
      syncPrevDistanceWalkedModified = prevDistanceWalkedModified
    }
    else if (syncPrevDistanceWalkedModified != getFloat("prevDistanceWalkedModified")) {
      prevDistanceWalkedModified = getFloat("prevDistanceWalkedModified")
      syncPrevDistanceWalkedModified = prevDistanceWalkedModified
    }
    if (syncDistanceWalkedOnStepModified != distanceWalkedOnStepModified) {
      variableFloat.put("distanceWalkedOnStepModified", distanceWalkedOnStepModified)
      syncDistanceWalkedOnStepModified = distanceWalkedOnStepModified
    }
    else if (syncDistanceWalkedOnStepModified != getFloat("distanceWalkedOnStepModified")) {
      distanceWalkedOnStepModified = getFloat("distanceWalkedOnStepModified")
      syncDistanceWalkedOnStepModified = distanceWalkedOnStepModified
    }
    if (syncFallDistance != fallDistance) {
      persistentFloat.put("fallDistance", fallDistance)
      syncFallDistance = fallDistance
    }
    else if (syncFallDistance != getPersistentFloat("fallDistance")) {
      fallDistance = getPersistentFloat("fallDistance")
      syncFallDistance = fallDistance
    }
    if (syncSprinting != isSprinting) {
      variableBoolean.put("isSprinting", isSprinting)
      syncSprinting = isSprinting
    }
    else if (syncSprinting != getBoolean("isSprinting")) {
      setFlag(3, getBoolean("isSprinting"))
      syncSprinting = isSprinting
    }
  }

  private var syncRenderDistanceWeight: Double = .0
  private var syncPrevSwingProgress, syncSwingProgress, syncPrevLimbSwingAmount, syncLimbSwingAmount, syncLimbSwing, syncPrevCameraPitch, syncCameraPitch, syncRenderYawOffset, syncPrevRenderYawOffset, syncRotationYawHead, syncPrevRotationYawHead: Float = .0F
  private var syncSwingProgressInt, syncArrowHitTimer: Int = 0
  private var syncIgnoreFrustumCheck, syncIsSwingInProgress: Boolean = false

  private def syncRender() {
    if (syncRenderDistanceWeight != renderDistanceWeight) {
      variableDouble.put("renderDistanceWeight", renderDistanceWeight)
      syncRenderDistanceWeight = renderDistanceWeight
    }
    else if (syncRenderDistanceWeight != getDouble("renderDistanceWeight")) {
      renderDistanceWeight = getDouble("renderDistanceWeight")
      syncRenderDistanceWeight = renderDistanceWeight
    }
    if (syncIgnoreFrustumCheck != ignoreFrustumCheck) {
      variableBoolean.put("ignoreFrustumCheck", ignoreFrustumCheck)
      syncIgnoreFrustumCheck = ignoreFrustumCheck
    }
    else if (syncIgnoreFrustumCheck != getBoolean("ignoreFrustumCheck")) {
      ignoreFrustumCheck = getBoolean("ignoreFrustumCheck")
      syncIgnoreFrustumCheck = ignoreFrustumCheck
    }
    if (syncIsSwingInProgress != isSwingInProgress) {
      variableBoolean.put("isSwingInProgress", isSwingInProgress)
      syncIsSwingInProgress = isSwingInProgress
    }
    else if (syncIsSwingInProgress != getBoolean("isSwingInProgress")) {
      isSwingInProgress = getBoolean("isSwingInProgress")
      syncIsSwingInProgress = isSwingInProgress
    }
    if (syncSwingProgressInt != swingProgressInt) {
      variableInteger.put("swingProgressInt", swingProgressInt)
      syncSwingProgressInt = swingProgressInt
    }
    else if (syncSwingProgressInt != getInteger("swingProgressInt")) {
      swingProgressInt = getInteger("swingProgressInt")
      syncSwingProgressInt = swingProgressInt
    }
    if (syncArrowHitTimer != arrowHitTimer) {
      variableInteger.put("arrowTimer", arrowHitTimer)
      syncArrowHitTimer = arrowHitTimer
    }
    else if (syncArrowHitTimer != getInteger("arrowTimer")) {
      arrowHitTimer = getInteger("arrowTimer")
      syncArrowHitTimer = arrowHitTimer
    }
    if (syncPrevSwingProgress != prevSwingProgress) {
      variableFloat.put("prevSwingProgress", prevSwingProgress)
      syncPrevSwingProgress = prevSwingProgress
    }
    else if (syncPrevSwingProgress != getFloat("prevSwingProgress")) {
      prevSwingProgress = getFloat("prevSwingProgress")
      syncPrevSwingProgress = prevSwingProgress
    }
    if (syncSwingProgress != swingProgress) {
      variableFloat.put("swingProgress", swingProgress)
      syncSwingProgress = swingProgress
    }
    else if (syncSwingProgress != getFloat("SwingProgress")) {
      swingProgress = getFloat("SwingProgress")
      syncSwingProgress = swingProgress
    }
    if (syncPrevLimbSwingAmount != prevLimbSwingAmount) {
      variableFloat.put("prevLimbSwingAmount", prevLimbSwingAmount)
      syncPrevLimbSwingAmount = prevLimbSwingAmount
    }
    else if (syncPrevLimbSwingAmount != getFloat("prevLimbSwingAmount")) {
      prevLimbSwingAmount = getFloat("prevLimbSwingAmount")
      syncPrevLimbSwingAmount = prevLimbSwingAmount
    }
    if (syncLimbSwingAmount != limbSwingAmount) {
      variableFloat.put("limbSwingAmount", limbSwingAmount)
      syncLimbSwingAmount = limbSwingAmount
    }
    else if (syncLimbSwingAmount != getFloat("limbSwingAmount")) {
      limbSwingAmount = getFloat("limbSwingAmount")
      syncLimbSwingAmount = limbSwingAmount
    }
    if (syncLimbSwing != limbSwing) {
      variableFloat.put("limbSwing", limbSwing)
      syncLimbSwing = limbSwing
    } else if (syncLimbSwing != getFloat("limbSwing")) {
      limbSwing = getFloat("limbSwing")
      syncLimbSwing = limbSwing
    }
    if (syncPrevCameraPitch != prevCameraPitch) {
      variableFloat.put("prevCameraPitch", prevCameraPitch)
      syncPrevCameraPitch = prevCameraPitch
    }
    else if (syncPrevCameraPitch != getFloat("prevCameraPitch")) {
      prevCameraPitch = getFloat("prevCameraPitch")
      syncPrevCameraPitch = prevCameraPitch
    }
    if (syncCameraPitch != cameraPitch) {
      variableFloat.put("cameraPitch", cameraPitch)
      syncCameraPitch = cameraPitch
    }
    else if (syncCameraPitch != getFloat("cameraPitch")) {
      cameraPitch = getFloat("cameraPitch")
      syncCameraPitch = cameraPitch
    }
    if (syncPrevRenderYawOffset != prevRenderYawOffset) {
      variableFloat.put("prevRenderYawOffset", prevRenderYawOffset)
      syncPrevRenderYawOffset = prevRenderYawOffset
    }
    else if (syncPrevRenderYawOffset != getFloat("prevRenderYawOffset")) {
      prevRenderYawOffset = getFloat("prevRenderYawOffset")
      syncPrevRenderYawOffset = prevRenderYawOffset
    }
    if (syncRenderYawOffset != renderYawOffset) {
      variableFloat.put("renderYawOffset", renderYawOffset)
      syncRenderYawOffset = renderYawOffset
    }
    else if (syncRenderYawOffset != getFloat("renderYawOffset")) {
      renderYawOffset = getFloat("renderYawOffset")
      syncRenderYawOffset = renderYawOffset
    }
    if (syncPrevRotationYawHead != prevRotationYawHead) {
      variableFloat.put("prevRotationYawHead", prevRotationYawHead)
      syncPrevRotationYawHead = prevRotationYawHead
    }
    else if (syncPrevRotationYawHead != getFloat("prevRotationYawHead")) {
      prevRotationYawHead = getFloat("prevRotationYawHead")
      syncPrevRotationYawHead = prevRotationYawHead
    }
    if (syncRotationYawHead != rotationYawHead) {
      variableFloat.put("rotationYawHead", rotationYawHead)
      syncRotationYawHead = rotationYawHead
    }
    else if (syncRotationYawHead != getFloat("rotationYawHead")) {
      rotationYawHead = getFloat("rotationYawHead")
      syncRotationYawHead = rotationYawHead
    }
  }

  private var syncRiddenByEntity, syncRidingEntity, syncLeashedToEntity: Entity = null
  private var syncEntityRiderPitchDelta, syncEntityRiderYawDelta: Double = .0
  private var syncIsLeashed: Boolean = false

  private def syncRiding() {
    if (syncRiddenByEntity != riddenByEntity && riddenByEntity != null) {
      variableInteger.put("riddenByEntityID", riddenByEntity.getEntityId)
      syncRiddenByEntity = riddenByEntity
    }
    else if (syncRiddenByEntity != worldObj.getEntityByID(getInteger("riddenByEntityID")) && getInteger("riddenByEntityID") != 0) {
      riddenByEntity = worldObj.getEntityByID(getInteger("riddenByEntityID"))
      syncRiddenByEntity = riddenByEntity
    }
    if (syncRidingEntity != ridingEntity && ridingEntity != null) {
      persistentInteger.put("ridingEntityID", ridingEntity.getEntityId)
      syncRidingEntity = ridingEntity
    }
    else if (syncRidingEntity != worldObj.getEntityByID(getPersistentInteger("ridingEntityID")) && getPersistentInteger("ridingEntityID") != 0) {
      ridingEntity = worldObj.getEntityByID(getPersistentInteger("ridingEntityID"))
      syncRidingEntity = ridingEntity
    }
    if (syncEntityRiderPitchDelta != getEntityRiderPitchDelta) {
      variableDouble.put("entityRiderPitchDelta", getEntityRiderPitchDelta)
      syncEntityRiderPitchDelta = getEntityRiderPitchDelta
    }
    else if (syncEntityRiderPitchDelta != getDouble("entityRiderPitchDelta")) {
      setEntityRiderPitchDelta(getDouble("entityRiderPitchDelta"))
      syncEntityRiderPitchDelta = getEntityRiderPitchDelta
    }
    if (syncEntityRiderYawDelta != getEntityRiderYawDelta) {
      variableDouble.put("entityRiderYawDelta", getEntityRiderYawDelta)
      syncEntityRiderYawDelta = getEntityRiderYawDelta
    }
    else if (syncEntityRiderYawDelta != getDouble("entityRiderYawDelta")) {
      setEntityRiderYawDelta(getDouble("entityRiderYawDelta"))
      syncEntityRiderYawDelta = getEntityRiderYawDelta
    }
    if (syncIsLeashed != getLeashed) {
      persistentBoolean.put("isLeashed", getLeashed)
      syncIsLeashed = getLeashed
    }
    else if (syncIsLeashed != getPersistentBoolean("isLeashed")) {
      setIsLeashed(getPersistentBoolean("isLeashed"))
      syncIsLeashed = getLeashed
    }
    if (syncLeashedToEntity != getLeashedToEntity) {
      persistentInteger.put("leashedToEntity", getLeashedToEntity.getEntityId)
      syncLeashedToEntity = getLeashedToEntity
    }
    else if (syncLeashedToEntity != worldObj.getEntityByID(getPersistentInteger("leashedToEntity"))) {
      setLeashedToEntity(worldObj.getEntityByID(getPersistentInteger("leashedToEntity")))
      syncLeashedToEntity = getLeashedToEntity
    }
  }

  private var syncEquipmentDropChances: Array[Float] = new Array[Float](5)
  private var syncCaptureDrops, syncCanPickUpLoot: Boolean = false
  private var syncCapturedDrops: util.ArrayList[EntityItem] = new util.ArrayList[EntityItem]
  private var syncEquipment: Array[ItemStack] = new Array[ItemStack](5)

  private def syncInventory() {
    if (syncCaptureDrops != captureDrops) {
      variableBoolean.put("captureDrops", captureDrops)
      syncCaptureDrops = captureDrops
    }
    else if (syncCaptureDrops != getBoolean("captureDrops")) {
      captureDrops = getBoolean("captureDrops")
      syncCaptureDrops = captureDrops
    }
    if (syncCapturedDrops.size() != capturedDrops.size() || syncCapturedDrops.size() != getPersistentItemStackArrayLength("capturedDrops")) {
      val max = Math.max(capturedDrops.size(), getPersistentItemStackArrayLength("capturedDrops"))
      if (max > capturedDrops.size()) {
        val diff = max - capturedDrops.size()
        for (i <- 0 until diff) {
          syncCapturedDrops.add(null)
          capturedDrops.add(null)
        }
      } else if (max > getPersistentItemStackArrayLength("capturedDrops")) {
        val diff = max - getPersistentItemStackArrayLength("capturedDrops")
        for (i <- 0 until diff) {
          syncCapturedDrops.add(null)
        }
      }
    }
    val max = Math.max(capturedDrops.size(), getPersistentItemStackArrayLength("capturedDrops"))
    for (i <- 0 until max) {
      if (syncCapturedDrops.get(i) != capturedDrops.get(i)) {
        persistentItemStack.put("capturedDrops." + i, if (capturedDrops.get(i) != null) capturedDrops.get(i).getEntityItem else null)
        syncCapturedDrops.add(i, capturedDrops.get(i))
      } else if (syncCapturedDrops.get(i) == null && getPersistentItemStack("capturedDrops." + i) != null || syncCapturedDrops.get(i) != null && !syncCapturedDrops.get(i).getEntityItem.isItemEqual(getPersistentItemStack("capturedDrops." + i))) {
        if (getPersistentItemStack("capturedDrops." + i) != null) {
          capturedDrops.add(i, new EntityItem(worldObj, posX, posY, posZ, getPersistentItemStack("capturedDrops." + i)))
          syncCapturedDrops.add(i, capturedDrops.get(i))
        } else {
          capturedDrops.remove(i)
          syncCapturedDrops.remove(i)
        }
      }
    }
    persistentInteger.put("capturedDrops.size", syncCapturedDrops.size())

    for (i <- 0 until getLastActiveItems.length) {
      if (syncEquipment(i) != getLastActiveItems()(i)) {
        persistentItemStack.put("equipment." + i, getLastActiveItems()(i))
        syncEquipment(i) = getLastActiveItems()(i)
      } else if (syncEquipment(i) != getPersistentItemStack("equipment." + i)) {
        setCurrentItemOrArmor(i, getPersistentItemStack("equipment." + i))
        syncEquipment(i) = getLastActiveItems()(i)
      }
    }
    for (i <- 0 until getEquipmentDropChances.length) {
      if (syncEquipmentDropChances(i) != getEquipmentDropChances(i)) {
        persistentFloat.put("equipmentDropChances." + i, getEquipmentDropChances(i))
        syncEquipmentDropChances(i) = getEquipmentDropChances(i)
      } else if (syncEquipmentDropChances(i) != getPersistentFloat("equipmentDropChances." + i)) {
        setEquipmentDropChances(getPersistentFloat("equipmentDropChances." + i), i)
        syncEquipmentDropChances(i) = getEquipmentDropChances(i)
      }
    }
    if (syncCanPickUpLoot != canPickUpLoot) {
      persistentBoolean.put("canPickUpLoot", canPickUpLoot)
      syncCanPickUpLoot = canPickUpLoot
    }
    else if (syncCanPickUpLoot != getPersistentBoolean("canPickUpLoot")) {
      setCanPickUpLoot(getPersistentBoolean("canPickUpLoot"))
      syncCanPickUpLoot = canPickUpLoot
    }
  }

  private var syncPrevHealth, syncHealth: Float = 0F
  private var syncHurtTime, syncMaxHurtTime, syncDeathTime, syncHurtResistantTime, syncMaxHurtResistantTime, syncEntityAge: Int = 0

  private def syncHealthStuff() {
    if (syncPrevHealth != prevHealth) {
      variableFloat.put("prevHealth", prevHealth)
      syncPrevHealth = prevHealth
    }
    else if (syncPrevHealth != getFloat("prevHealth")) {
      prevHealth = getFloat("prevHealth")
      syncPrevHealth = prevHealth
    }
    if (syncHealth != getHealth) {
      persistentFloat.put("health", getHealth)
      syncHealth = getHealth
    }
    else if (syncHealth != getPersistentFloat("health")) {
      setHealth(getPersistentFloat("health"))
      syncHealth = getHealth
    }
    if (syncHurtTime != hurtTime) {
      persistentInteger.put("hurtTime", hurtTime)
      syncHurtTime = hurtTime
    }
    else if (syncHurtTime != getPersistentInteger("hurtTime")) {
      hurtTime = getPersistentInteger("hurtTime")
      syncHurtTime = hurtTime
    }
    if (syncMaxHurtTime != maxHurtTime) {
      variableInteger.put("maxHurtTime", maxHurtTime)
      syncMaxHurtTime = maxHurtTime
    }
    else if (syncMaxHurtTime != getInteger("maxHurtTime")) {
      maxHurtTime = getInteger("maxHurtTime")
      syncMaxHurtTime = maxHurtTime
    }
    if (syncDeathTime != deathTime) {
      persistentInteger.put("deathTime", deathTime)
      syncDeathTime = deathTime
    }
    else if (syncDeathTime != getPersistentInteger("deathTime")) {
      deathTime = getPersistentInteger("deathTime")
      syncDeathTime = deathTime
    }
    if (syncHurtResistantTime != hurtResistantTime) {
      variableInteger.put("hurtResistantTime", hurtResistantTime)
      syncHurtResistantTime = hurtResistantTime
    }
    else if (syncHurtResistantTime != getInteger("hurtResistantTime")) {
      hurtResistantTime = getInteger("hurtResistantTime")
      syncHurtResistantTime = hurtResistantTime
    }
    if (syncMaxHurtResistantTime != maxHurtResistantTime) {
      variableInteger.put("maxHurtResistantTime", maxHurtResistantTime)
      syncMaxHurtResistantTime = maxHurtResistantTime
    }
    else if (syncMaxHurtResistantTime != getInteger("maxHurtResistantTime")) {
      maxHurtResistantTime = getInteger("maxHurtResistantTime")
      syncMaxHurtResistantTime = maxHurtResistantTime
    }
    if (syncEntityAge != getEntityAge) {
      variableInteger.put("entityAge", getEntityAge)
      syncEntityAge = getEntityAge
    }
    else if (syncEntityAge != getInteger("entityAge")) {
      setEntityAge(getInteger("entityAge"))
      syncEntityAge = getEntityAge
    }
  }

  private var syncLastDamage, syncAbsorptionAmount: Float = 0F
  private var syncAttackTime, syncRecentlyHit, syncScoreValue, syncLastAttackerTime, syncExperienceValue, syncNumTicksToChaseTarget: Int = 0
  private var syncAttackingPlayer: EntityPlayer = null
  private var syncLastAttacker, syncAttackTarget, syncEntityLivingToAttack: EntityLivingBase = null

  private def syncAttack() {
    if (syncAttackTime != attackTime) {
      persistentInteger.put("attackTime", attackTime)
      syncAttackTime = attackTime
    }
    else if (syncAttackTime != getPersistentInteger("attackTime")) {
      attackTime = getPersistentInteger("attackTime")
      syncAttackTime = attackTime
    }
    if (syncAttackingPlayer != getAttackingPlayer) {
      variableInteger.put("attackingPlayerID", getAttackingPlayer.getEntityId)
      syncAttackingPlayer = getAttackingPlayer
    }
    else if (syncAttackingPlayer != worldObj.getEntityByID(getInteger("attackingPlayerID")) && getInteger("attackingPlayerID") != 0) {
      setAttackingPlayer(worldObj.getEntityByID(getInteger("attackingPlayerID")).asInstanceOf[EntityPlayer])
      syncAttackingPlayer = getAttackingPlayer
    }
    if (syncRecentlyHit != getRecentlyHit) {
      variableInteger.put("recentlyHit", getRecentlyHit)
      syncRecentlyHit = getRecentlyHit
    }
    else if (syncRecentlyHit != getInteger("recentlyHit")) {
      setRecentlyHit(getInteger("recentlyHit"))
      syncRecentlyHit = getRecentlyHit
    }
    if (syncScoreValue != getScoreValue) {
      variableInteger.put("scoreValue", getScoreValue)
      syncScoreValue = getScoreValue
    }
    else if (syncScoreValue != getInteger("scoreValue")) {
      setScoreValue(getInteger("scoreValue"))
      syncScoreValue = getScoreValue
    }
    if (syncLastDamage != getLastDamage) {
      variableFloat.put("lastDamage", getLastDamage)
      syncLastDamage = getLastDamage
    }
    else if (syncLastDamage != getFloat("lastDamage")) {
      setLastDamage(getFloat("lastDamage"))
      syncLastDamage = getLastDamage
    }
    if (syncLastAttacker != getLastAttacker) {
      variableInteger.put("lastAttackerID", getLastAttacker.getEntityId)
      syncLastAttacker = getLastAttacker
    }
    else if (syncLastAttacker != worldObj.getEntityByID(getInteger("lastAttackerID")) && getInteger("lastAttackerID") != 0) {
      setLastAttacker(worldObj.getEntityByID(getInteger("lastAttackerID")))
      syncLastAttacker = getLastAttacker
    }
    if (syncLastAttackerTime != getLastAttackerTime) {
      variableInteger.put("lastAttackerTime", getLastAttackerTime)
      syncLastAttackerTime = getLastAttackerTime
    }
    else if (syncLastAttackerTime != getInteger("lastAttackerTime")) {
      setLastAttackerTime(getInteger("lastAttackerTime"))
      syncLastAttackerTime = getLastAttackerTime
    }
    if (syncExperienceValue != getExperienceValue) {
      variableInteger.put("experienceValue", getExperienceValue)
      syncExperienceValue = getExperienceValue
    }
    else if (syncExperienceValue != getInteger("experienceValue")) {
      setExperienceValue(getInteger("experienceValue"))
      syncExperienceValue = getExperienceValue
    }
    if (syncAttackTarget != getAttackTarget) {
      variableInteger.put("attackTarget", getAttackTarget.getEntityId)
      syncAttackTarget = getAttackTarget
    }
    else if (getInteger("attackTarget") != 0 && syncAttackTarget != worldObj.getEntityByID(getInteger("attackTarget"))) {
      setAttackTarget(worldObj.getEntityByID(getInteger("attackTarget")).asInstanceOf[EntityLivingBase])
      syncAttackTarget = getAttackTarget
    }
    if (syncAttackTime != attackTime) {
      persistentInteger.put("attackTime", attackTime)
      syncAttackTime = attackTime
    }
    else if (syncAttackTime != getPersistentInteger("attackTime")) {
      attackTime = getPersistentInteger("attackTime")
      syncAttackTime = attackTime
    }
    if (syncEntityLivingToAttack != getAITarget) {
      variableInteger.put("entityLivingToAttackID", if (getAITarget != null) getAITarget.getEntityId else 0)
      syncEntityLivingToAttack = getAITarget
    }
    else if (syncEntityLivingToAttack != worldObj.getEntityByID(getInteger("entityLivingToAttackID")) && getInteger("entityLivingToAttackID") != 0) {
      setRevengeTarget(worldObj.getEntityByID(getInteger("entityLivingToAttackID")).asInstanceOf[EntityLivingBase])
      syncEntityLivingToAttack = getAITarget
    }
    if (syncAbsorptionAmount != getAbsorptionAmount) {
      persistentFloat.put("absorptionAmount", getAbsorptionAmount)
      syncAbsorptionAmount = getAbsorptionAmount
    }
    else if (syncAbsorptionAmount != getPersistentFloat("absorptionAmount")) {
      setAbsorptionAmount(getPersistentFloat("absorptionAmount"))
      syncAbsorptionAmount = getAbsorptionAmount
    }
  }

  private var syncLivingSoundTime, syncTalkInterval: Int = 0

  private def syncSound() {
    if (syncNumTicksToChaseTarget != getNumTicksToChaseTarget) {
      variableInteger.put("numTicksToChaseTarget", getNumTicksToChaseTarget)
      syncNumTicksToChaseTarget = getNumTicksToChaseTarget
    }
    else if (syncNumTicksToChaseTarget != getInteger("numTicksToChaseTarget")) {
      setNumTicksToChaseTarget(getInteger("numTicksToChaseTarget"))
      syncNumTicksToChaseTarget = getNumTicksToChaseTarget
    }
    if (syncLivingSoundTime != livingSoundTime) {
      persistentInteger.put("livingSoundTime", livingSoundTime)
      syncLivingSoundTime = livingSoundTime
    }
    else if (syncLivingSoundTime != getPersistentInteger("livingSoundTime")) {
      livingSoundTime = getPersistentInteger("livingSoundTime")
      syncLivingSoundTime = livingSoundTime
    }
    if (syncTalkInterval != talkInterval) {
      persistentInteger.put("talkInterval", talkInterval)
      syncTalkInterval = talkInterval
    }
    else if (syncTalkInterval != getPersistentInteger("talkInterval")) {
      talkInterval = getPersistentInteger("talkInterval")
      syncTalkInterval = talkInterval
    }
  }

  private def getVariableItemStackArrayLength(name: String): Int = {
    if (variableItemStack.contains(name + ".0") && variableItemStack.get(name + ".0") != null) {
      var length: Int = 0
      var keepGoing: Boolean = true
      while (keepGoing) {
        if (variableItemStack.contains(name + "." + length) && variableItemStack.get(name + "." + length) != null) {
          length += 1
        }
        else {
          keepGoing = false
        }
      }
      return length
    }
    0
  }

  private def getPersistentItemStackArrayLength(name: String): Int = {
    if (persistentItemStack.containsKey(name + ".0") && persistentItemStack.get(name + ".0") != null) {
      var length: Int = 0
      var keepGoing: Boolean = true
      while (keepGoing) {
        if (persistentItemStack.containsKey(name + "." + length) && persistentItemStack.get(name + "." + length) != null) {
          length += 1
        }
        else {
          keepGoing = false
        }
      }
      return length
    }
    0
  }

  private def getFire: Int = {
    var fire: Int = 0
    try {
      val onFire: Field = ReflectionHelper.findField(classOf[Entity], "fire", "field_70151_c")
      onFire.setAccessible(true)
      fire = onFire.getInt(this)
      onFire.setAccessible(false)
    }
    catch {
      case e: Exception =>
    }
    fire
  }

  private def setFireTicks(fire: Int) {
    ObfuscationReflectionHelper.setPrivateValue(classOf[Entity], this, fire, "fire", "field_70151_c")
  }

  private def getEntityRiderPitchDelta: Double = {
    var `var`: Double = 0
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "entityRiderPitchDelta", "field_70149_e")
      field.setAccessible(true)
      `var` = field.getDouble(this)
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
    }
    `var`
  }

  private def getEntityRiderYawDelta: Double = {
    var `var`: Double = 0
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "entityRiderYawDelta", "field_70147_f")
      field.setAccessible(true)
      `var` = field.getDouble(this)
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
    }
    `var`
  }

  private def setEntityRiderPitchDelta(value: Double) {
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "entityRiderPitchDelta", "field_70149_e")
      field.setAccessible(true)
      field.setDouble(this, value)
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
    }
  }

  private def setEntityRiderYawDelta(value: Double) {
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "entityRiderYawDelta", "field_70147_f")
      field.setAccessible(true)
      field.setDouble(this, value)
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
    }
  }

  private def setInvulnerable(value: Boolean) {
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "invulnerable", "field_83001_bt")
      field.setAccessible(true)
      field.setBoolean(this, value)
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
    }
  }

  private def setLastAttackerTime(value: Int) {
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "lastAttackerTime", "field_142016_bo")
      field.setAccessible(true)
      field.setInt(this, value)
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
    }
  }

  private def getLandMovementFactor: Float = {
    var value: Int = 0
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "landMovementFactor", "field_70746_aG")
      field.setAccessible(true)
      value = field.getInt(this)
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
    }
    value
  }

  private def setPersistenceRequired(value: Boolean) {
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "persistenceRequired", "field_82179_bU")
      field.setAccessible(true)
      field.setBoolean(this, value)
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
    }
  }

  private def setIsLeashed(value: Boolean) {
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "isLeashed", "field_110169_bv")
      field.setAccessible(true)
      field.setBoolean(this, value)
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
    }
  }

  private def setLeashedToEntity(value: Entity) {
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "leashedToEntity", "field_110168_bw")
      field.setAccessible(true)
      field.set(this, value)
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
    }
  }

  override def interact(player: EntityPlayer): Boolean = {
    if (player.inventory.getCurrentItem != null && player.inventory.getCurrentItem.isItemEqual(new ItemStack(ModItems.thermometer, 1, 1))) isDead = true
    TraitHandler.entityRightClicked(this, player)
    true
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
    if (compound.hasKey("traitVariables")) {
      val tagList: NBTTagList = compound.getTagList("traitVariables", Constants.NBT.TAG_COMPOUND)
      var compoundBoolean: NBTTagCompound = null
      var compoundByte: NBTTagCompound = null
      var compoundInteger: NBTTagCompound = null
      var compoundFloat: NBTTagCompound = null
      var compoundDouble: NBTTagCompound = null
      var compoundString: NBTTagCompound = null
      var compoundItemStack: NBTTagCompound = null
      for (i <- 0 until tagList.tagCount()) {
        if (tagList.getCompoundTagAt(i).getString("type") == "boolean") {
          compoundBoolean = tagList.getCompoundTagAt(i)
        }
        else if (tagList.getCompoundTagAt(i).getString("type") == "byte") {
          compoundByte = tagList.getCompoundTagAt(i)
        }
        else if (tagList.getCompoundTagAt(i).getString("type") == "integer") {
          compoundInteger = tagList.getCompoundTagAt(i)
        }
        else if (tagList.getCompoundTagAt(i).getString("type") == "float") {
          compoundFloat = tagList.getCompoundTagAt(i)
        }
        else if (tagList.getCompoundTagAt(i).getString("type") == "double") {
          compoundDouble = tagList.getCompoundTagAt(i)
        }
        else if (tagList.getCompoundTagAt(i).getString("type") == "string") {
          compoundString = tagList.getCompoundTagAt(i)
        }
        else if (tagList.getCompoundTagAt(i).getString("type") == "itemStack") {
          compoundItemStack = tagList.getCompoundTagAt(i)
        }
      }
      if (compoundBoolean != null) {
        val size: Int = compoundBoolean.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundBoolean.getString("boolean" + i + "Name")
          val value: Boolean = compoundBoolean.getBoolean("boolean" + i + "Value")
          persistentBoolean.put(name, value)
        }
      }
      if (compoundByte != null) {
        val size: Int = compoundByte.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundByte.getString("byte" + i + "Name")
          val value: Byte = compoundByte.getByte("byte" + i + "Value")
          persistentByte.put(name, value)
        }
      }
      if (compoundInteger != null) {
        val size: Int = compoundInteger.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundInteger.getString("integer" + i + "Name")
          val value: Int = compoundInteger.getInteger("integer" + i + "Value")
          persistentInteger.put(name, value)
        }
      }
      if (compoundFloat != null) {
        val size: Int = compoundFloat.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundFloat.getString("float" + i + "Name")
          val value: Float = compoundFloat.getFloat("float" + i + "Value")
          persistentFloat.put(name, value)
        }
      }
      if (compoundDouble != null) {
        val size: Int = compoundDouble.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundDouble.getString("double" + i + "Name")
          val value: Double = compoundDouble.getDouble("double" + i + "Value")
          persistentDouble.put(name, value)
        }
      }
      if (compoundString != null) {
        val size: Int = compoundString.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundString.getString("string" + i + "Name")
          val value: String = compoundString.getString("string" + i + "Value")
          persistentString.put(name, value)
        }
      }
      if (compoundItemStack != null) {
        val size: Int = compoundItemStack.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundItemStack.getString("itemStack" + i + "Name")
          val stackCompound: NBTTagCompound = compoundItemStack.getCompoundTag("itemStack" + i + "Value")
          val stack: ItemStack = ItemStack.loadItemStackFromNBT(stackCompound)
          persistentItemStack.put(name, stack)
        }
      }
    }
  }

  override def writeToNBT(compound: NBTTagCompound) {
    super.writeToNBT(compound)
    soul.writeToNBT(compound)
    if (persistentBoolean != null) {
      val tagList = new NBTTagList()
      val stringList = new util.ArrayList[String]()
      if (!persistentBoolean.isEmpty) {
        stringList.addAll(persistentBoolean.keySet)
        val booleanCompound = new NBTTagCompound()
        for (i <- 0 until persistentBoolean.size) {
          booleanCompound.setString("boolean" + i + "Name", stringList.get(i))
          booleanCompound.setBoolean("boolean" + i + "Value", persistentBoolean.get(stringList.get(i)))
        }
        booleanCompound.setString("type", "boolean")
        booleanCompound.setInteger("size", persistentBoolean.size)
        tagList.appendTag(booleanCompound)
        stringList.clear()
      }
      if (!persistentByte.isEmpty) {
        stringList.addAll(persistentByte.keySet)
        val byteCompound = new NBTTagCompound()
        for (i <- 0 until persistentByte.size) {
          byteCompound.setString("byte" + i + "Name", stringList.get(i))
          byteCompound.setByte("byte" + i + "Value", persistentByte.get(stringList.get(i)))
        }
        byteCompound.setString("type", "byte")
        byteCompound.setInteger("size", persistentByte.size)
        tagList.appendTag(byteCompound)
        stringList.clear()
      }
      if (!persistentInteger.isEmpty) {
        stringList.addAll(persistentInteger.keySet)
        val integerCompound = new NBTTagCompound()
        for (i <- 0 until persistentInteger.size) {
          integerCompound.setString("integer" + i + "Name", stringList.get(i))
          integerCompound.setInteger("integer" + i + "Value", persistentInteger.get(stringList.get(i)))
        }
        integerCompound.setString("type", "integer")
        integerCompound.setInteger("size", persistentInteger.size)
        tagList.appendTag(integerCompound)
        stringList.clear()
      }
      if (!persistentFloat.isEmpty) {
        stringList.addAll(persistentFloat.keySet)
        val floatCompound = new NBTTagCompound()
        for (i <- 0 until persistentFloat.size) {
          floatCompound.setString("float" + i + "Name", stringList.get(i))
          floatCompound.setFloat("float" + i + "Value", persistentFloat.get(stringList.get(i)))
        }
        floatCompound.setString("type", "float")
        floatCompound.setInteger("size", persistentFloat.size)
        stringList.clear()
      }
      if (!persistentDouble.isEmpty) {
        stringList.addAll(persistentDouble.keySet)
        val doubleCompound = new NBTTagCompound()
        for (i <- 0 until persistentDouble.size) {
          doubleCompound.setString("double" + i + "Name", stringList.get(i))
          doubleCompound.setDouble("double" + i + "Value", persistentDouble.get(stringList.get(i)))
        }
        doubleCompound.setString("type", "double")
        doubleCompound.setInteger("size", persistentDouble.size)
        stringList.clear()
      }
      if (!persistentString.isEmpty) {
        stringList.addAll(persistentString.keySet)
        val stringCompound = new NBTTagCompound()
        for (i <- 0 until persistentString.size) {
          stringCompound.setString("string" + i + "Name", stringList.get(i))
          stringCompound.setString("string" + i + "Value", persistentString.get(stringList.get(i)))
        }
        stringCompound.setString("type", "string")
        stringCompound.setInteger("size", persistentString.size)
        stringList.clear()
      }
      if (!persistentItemStack.isEmpty) {
        stringList.addAll(persistentItemStack.keySet)
        val itemStackCompound = new NBTTagCompound()
        for (i <- 0 until persistentItemStack.size) {
          val stacks = new util.ArrayList[ItemStack](persistentItemStack.values)
          if (stacks.get(i) != null && stacks.get(i).getItem != null &&
            stacks.get(i).stackSize > 0) {
            itemStackCompound.setString("itemStack" + i + "Name", stringList.get(i))
            val compoundStack = new NBTTagCompound()
            itemStackCompound.setTag("itemStack" + i + "Value", stacks.get(i).writeToNBT(compoundStack))
          }
        }
        itemStackCompound.setString("type", "itemStack")
        itemStackCompound.setInteger("size", persistentString.size)
        stringList.clear()
      }
      compound.setTag("traitVariables", tagList)
    }
  }

  protected val persistentBoolean, variableBoolean: ConcurrentHashMap[String, Boolean] = new ConcurrentHashMap[String, Boolean]
  protected val persistentByte, variableByte: ConcurrentHashMap[String, Byte] = new ConcurrentHashMap[String, Byte]
  protected val persistentInteger, variableInteger: ConcurrentHashMap[String, Integer] = new ConcurrentHashMap[String, Integer]
  protected val persistentFloat, variableFloat: ConcurrentHashMap[String, Float] = new ConcurrentHashMap[String, Float]
  protected val persistentDouble, variableDouble: ConcurrentHashMap[String, Double] = new ConcurrentHashMap[String, Double]
  protected val persistentString, variableString: ConcurrentHashMap[String, String] = new ConcurrentHashMap[String, String]
  protected val persistentItemStack, variableItemStack: ConcurrentHashMap[String, ItemStack] = new ConcurrentHashMap[String, ItemStack]

  def setPersistentVariable(name: String, variable: Boolean) {
    persistentBoolean.put(name, variable)
  }

  def setPersistentVariable(name: String, variable: Byte) {
    persistentByte.put(name, variable)
  }

  def setPersistentVariable(name: String, variable: Int) {
    persistentInteger.put(name, variable)
  }

  def setPersistentVariable(name: String, variable: Float) {
    persistentFloat.put(name, variable)
  }

  def setPersistentVariable(name: String, variable: Double) {
    persistentDouble.put(name, variable)
  }

  def setPersistentVariable(name: String, variable: String) {
    persistentString.put(name, variable)
  }

  def setPersistentVariable(name: String, variable: ItemStack) {
    persistentItemStack.remove(name)
    if (variable != null) persistentItemStack.put(name, variable)
  }

  def getPersistentBoolean(name: String): Boolean = {
    if (!persistentBoolean.containsKey(name)) false else persistentBoolean.get(name)
  }

  def getPersistentByte(name: String): Byte = {
    if (!persistentByte.containsKey(name)) 0 else persistentByte.get(name)
  }

  def getPersistentInteger(name: String): Int = {
    if (!persistentInteger.containsKey(name)) 0 else persistentInteger.get(name)
  }

  def getPersistentFloat(name: String): Float = {
    if (!persistentFloat.containsKey(name)) 0 else persistentFloat.get(name)
  }

  def getPersistentDouble(name: String): Double = {
    if (!persistentDouble.containsKey(name)) 0 else persistentDouble.get(name)
  }

  def getPersistentString(name: String): String = {
    if (!persistentString.containsKey(name)) null else persistentString.get(name)
  }

  def getPersistentItemStack(name: String): ItemStack = {
    if (!persistentItemStack.containsKey(name)) null else persistentItemStack.get(name)
  }

  def setVariable(name: String, variable: Boolean) {
    variableBoolean.put(name, variable)
  }

  def setVariable(name: String, variable: Byte) {
    variableByte.put(name, variable)
  }

  def setVariable(name: String, variable: Int) {
    variableInteger.put(name, variable)
  }

  def setVariable(name: String, variable: Float) {
    variableFloat.put(name, variable)
  }

  def setVariable(name: String, variable: Double) {
    variableDouble.put(name, variable)
  }

  def setVariable(name: String, variable: String) {
    variableString.put(name, variable)
  }

  def setVariable(name: String, variable: ItemStack) {
    variableItemStack.put(name, variable)
  }

  def getBoolean(name: String): Boolean = {
    if (!variableBoolean.containsKey(name)) false else variableBoolean.get(name)
  }

  def getByte(name: String): Byte = {
    if (!variableByte.containsKey(name)) 0 else variableByte.get(name)
  }

  def getInteger(name: String): Int = {
    if (!variableInteger.containsKey(name)) 0 else variableInteger.get(name)
  }

  def getFloat(name: String): Float = {
    if (!variableFloat.containsKey(name)) 0 else variableFloat.get(name)
  }

  def getDouble(name: String): Double = {
    if (!variableDouble.containsKey(name)) 0 else variableDouble.get(name)
  }

  def getString(name: String): String = {
    if (!variableString.containsKey(name)) null else variableString.get(name)
  }

  def getItemStack(name: String): ItemStack = {
    if (!variableItemStack.containsKey(name)) null else variableItemStack.get(name)
  }

  override def forceVariableSync {
    this.syncVariables
  }
}

class GIEntityLivingScala(world: World) extends GIEntityLiving(world) {

  def getNewPosRotationIncrements = newPosRotationIncrements
  def setNewPosRotationIncrements(value: Int) {newPosRotationIncrements = value}

  def getNewPosX = newPosX
  def setNewPosX(value: Double) {newPosX = value}

  def getNewPosY = newPosY
  def setNewPosY(value: Double) {newPosY = value}

  def getNewPosZ = newPosZ
  def setNewPosZ(value: Double) {newPosZ = value}

  def getRandomYawVelocity = randomYawVelocity
  def setRandomYawVelocity(value: Float) {randomYawVelocity = value}

  def getNewRotationYaw = newRotationYaw
  def setNewRotationYaw(value: Double) {newRotationYaw = value}

  def getNewRotationPitch = newRotationPitch
  def setNewRotationPitch(value: Double) {newRotationPitch = value}

  def getDefaultPitch = defaultPitch
  def setDefaultPitch(value: Float) {defaultPitch = value}

  def getInWater = inWater
  def setInWater(value: Boolean) {inWater = value}

  def getIsInWeb = isInWeb
  def setIsInWeb(value: Boolean) {isInWeb = value}

  def getIsJumping = isJumping
  def setIsJumping(value: Boolean) {isJumping = value}

  def getInPortal = inPortal;
  def setInPortal(value: Boolean) {inPortal = value}

  def getPortalCounter = portalCounter
  def setPortalCounter(value: Int) {portalCounter = value}

  override def getTeleportDirection = teleportDirection
  def setTeleportDirection(value: Int) {teleportDirection = value}

  def getEquipmentDropChances = equipmentDropChances
  def setEquipmentDropChances(value: Float, index: Int) {equipmentDropChances(index) = value}
  def setEquipmentDropChances(value: Array[Float]) {equipmentDropChances = value}

  def getEntityAge = entityAge
  def setEntityAge(value: Int) {entityAge = value}

  def getAttackingPlayer = attackingPlayer
  def setAttackingPlayer(value: EntityPlayer) {attackingPlayer = value}

  def getRecentlyHit = recentlyHit
  def setRecentlyHit(value: Int) {recentlyHit = value}

  def getScoreValue = scoreValue
  def setScoreValue(value: Int) {scoreValue = value}

  def getLastDamage = lastDamage
  def setLastDamage(value: Float) {lastDamage = value}

  def getExperienceValue = experienceValue
  def setExperienceValue(value: Int) {experienceValue = value}

  def getNumTicksToChaseTarget = numTicksToChaseTarget
  def setNumTicksToChaseTarget(value: Int) {numTicksToChaseTarget = value}

  def getRand = rand;
}