package com.seremis.geninfusion.soulentity

import java.util.UUID
import java.{lang, util}

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.genetics.ISoul
import com.seremis.geninfusion.api.lib.FunctionLib
import com.seremis.geninfusion.api.lib.FunctionLib._
import com.seremis.geninfusion.api.soulentity.ISoulEntity
import com.seremis.geninfusion.api.util.TypedName
import com.seremis.geninfusion.soulentity.logic.{FieldLogic, MethodLogic}
import com.seremis.geninfusion.util.UtilNBT
import io.netty.buffer.ByteBuf
import net.minecraft.block.Block
import net.minecraft.block.material.{EnumPushReaction, Material}
import net.minecraft.block.state.IBlockState
import net.minecraft.command.CommandResultStats
import net.minecraft.command.CommandResultStats.Type
import net.minecraft.crash.CrashReportCategory
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.entity.{Entity, EntityLiving, EntityLivingBase}
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.network.datasync.{DataParameter, EntityDataManager}
import net.minecraft.scoreboard.Team
import net.minecraft.server.MinecraftServer
import net.minecraft.util._
import net.minecraft.util.math.{AxisAlignedBB, BlockPos, RayTraceResult, Vec3d}
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.event.HoverEvent
import net.minecraft.world.{Explosion, World}
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData

trait SoulEntityLivingTrait extends EntityLiving with ISoulEntity with IEntityAdditionalSpawnData {

    var soul: ISoul
    val world: World

    val fieldLogic = new FieldLogic(this)
    val methodLogic = new MethodLogic(this)

    override def getSoul: ISoul = soul

    override def getVar[A](name: TypedName[A]): A = fieldLogic.getVar(name)
    override def setVar[A](name: TypedName[A], value: A): Unit = fieldLogic.setVar(name, value)
    override def makePersistent(name: TypedName[_]): Unit = fieldLogic.makePersistent(name)

    override def callMethod[A](name: TypedName[A], args: Any*): A = methodLogic.callMethod(name, () => null.asInstanceOf[A], args)


    override def writeSpawnData(data: ByteBuf) {
        val compound = new NBTTagCompound
        writeToNBT(compound)

        val bytes = UtilNBT.compoundToByteArray(compound)
        data.writeInt(bytes.length)
        data.writeBytes(bytes)
    }

    override def readSpawnData(data: ByteBuf) {
        val length = data.readInt
        val bytes = new Array[Byte](length)

        data.readBytes(bytes)

        val compound: NBTTagCompound = UtilNBT.byteArrayToCompound(bytes)
        readFromNBT(compound)
    }

    override def writeToNBT(compound: NBTTagCompound): Unit = {
        GIApiInterface.dataTypeRegistry.writeValueToNBT(compound, "soul", classOf[ISoul], soul)
        fieldLogic.writeToNBT(compound)
        methodLogic.callMethod(FunctionLib.FuncEntityWriteToNBT, () => super.writeToNBT(compound), compound)
    }

    override def readFromNBT(compound: NBTTagCompound): Unit = {
        soul = GIApiInterface.dataTypeRegistry.readValueFromNBT(compound, "soul", classOf[ISoul])
        fieldLogic.readFromNBT(compound)
        methodLogic.callMethod(FunctionLib.FuncEntityReadFromNBT, () => super.readFromNBT(compound), compound)
    }

    //TODO add EntityLivingBase and EntityLiving methods


    override def getEntityId: Int = methodLogic.callMethod(FuncEntityGetEntityId, () => super.getEntityId)
    override def setEntityId(id: Int): Unit = methodLogic.callMethod(FuncEntitySetEntityId, () => super.setEntityId(id), id)
    override def getTags: util.Set[String] = methodLogic.callMethod(FuncEntityGetTags, () => super.getTags)
    override def addTag(tag: String): Boolean = methodLogic.callMethod(FuncEntityAddTag, () => super.addTag(tag), tag)
    override def removeTag(tag: String): Boolean = methodLogic.callMethod(FuncEntityRemoveTag, () => super.removeTag(tag), tag)
    override def onKillCommand(): Unit = methodLogic.callMethod(FuncEntityOnKillCommand, () => super.onKillCommand())
    //entityInit gets called in Entity constructor, this prevents a NPE.
    override def entityInit(): Unit = if(methodLogic == null) super.entityInit() else methodLogic.callMethod(FuncEntityEntityInit, () => super.entityInit())
    override def getDataManager: EntityDataManager = methodLogic.callMethod(FuncEntityGetDataManager, () => super.getDataManager)
   // override def equals(p_equals_1_ : Object): Boolean = methodLogic.callMethod(FuncEntityEquals, () => super.equals(p_equals_1_), p_equals_1_)
    override def hashCode(): Int = methodLogic.callMethod(FuncEntityHashCode, () => super.hashCode())
    override def preparePlayerToSpawn(): Unit = methodLogic.callMethod(FuncEntityPreparePlayerToSpawn, () => super.preparePlayerToSpawn())
    override def setDead(): Unit = methodLogic.callMethod(FuncEntitySetDead, () => super.setDead())
    override def setDropItemsWhenDead(dropWhenDead: Boolean): Unit = methodLogic.callMethod(FuncEntitySetDropItemsWhenDead, () => super.setDropItemsWhenDead(dropWhenDead), dropWhenDead)
    override def setSize(width: Float, height: Float): Unit = methodLogic.callMethod(FuncEntitySetSize, () => super.setSize(width, height), width, height)
    override def setRotation(yaw: Float, pitch: Float): Unit = methodLogic.callMethod(FuncEntitySetRotation, () => super.setRotation(yaw, pitch), yaw, pitch)
    //setPosition gets called in Entity constructor, this prevents a NPE.
    override def setPosition(x: Double, y: Double, z: Double): Unit = if(methodLogic == null) super.setPosition(x, y, z) else methodLogic.callMethod(FuncEntitySetPosition, () => super.setPosition(x, y, z), x, y, z)
    override def setAngles(yaw: Float, pitch: Float): Unit = methodLogic.callMethod(FuncEntitySetAngles, () => super.setAngles(yaw, pitch), yaw, pitch)
    override def onUpdate(): Unit = methodLogic.callMethod(FuncEntityOnUpdate, () => super.onUpdate())
    override def onEntityUpdate(): Unit = methodLogic.callMethod(FuncEntityOnEntityUpdate, () => super.onEntityUpdate())
    override def decrementTimeUntilPortal(): Unit = methodLogic.callMethod(FuncEntityDecrementTimeUntilPortal, () => super.decrementTimeUntilPortal())
    override def getMaxInPortalTime: Int = methodLogic.callMethod(FuncEntityGetMaxInPortalTime, () => super.getMaxInPortalTime)
    override def setOnFireFromLava(): Unit = methodLogic.callMethod(FuncEntitySetOnFireFromLava, () => super.setOnFireFromLava())
    override def setFire(seconds: Int): Unit = methodLogic.callMethod(FuncEntitySetFire, () => super.setFire(seconds), seconds)
    override def extinguish(): Unit = methodLogic.callMethod(FuncEntityExtinguish, () => super.extinguish())
    override def kill(): Unit = methodLogic.callMethod(FuncEntityKill, () => super.kill())
    override def isOffsetPositionInLiquid(x: Double, y: Double, z: Double): Boolean = methodLogic.callMethod(FuncEntityIsOffsetPositionInLiquid, () => super.isOffsetPositionInLiquid(x, y, z), x, y, z)
    override def moveEntity(x: Double, y: Double, z: Double): Unit = methodLogic.callMethod(FuncEntityMoveEntity, () => super.moveEntity(x, y, z), x, y, z)
    override def resetPositionToBB(): Unit = methodLogic.callMethod(FuncEntityResetPositionToBB, () => super.resetPositionToBB())
    override def getSwimSound: SoundEvent = methodLogic.callMethod(FuncEntityGetSwimSound, () => super.getSwimSound)
    override def getSplashSound: SoundEvent = methodLogic.callMethod(FuncEntityGetSplashSound, () => super.getSplashSound)
    override def doBlockCollisions(): Unit = methodLogic.callMethod(FuncEntityDoBlockCollisions, () => super.doBlockCollisions())
    override def playStepSound(pos: BlockPos, blockIn: Block): Unit = methodLogic.callMethod(FuncEntityPlayStepSound, () => super.playStepSound(pos, blockIn), pos, blockIn)
    override def playSound(soundIn: SoundEvent, volume: Float, pitch: Float): Unit = methodLogic.callMethod(FuncEntityPlaySound, () => super.playSound(soundIn, volume, pitch), soundIn, volume, pitch)
    override def isSilent: Boolean = methodLogic.callMethod(FuncEntityIsSilent, () => super.isSilent)
    override def setSilent(isSilent: Boolean): Unit = methodLogic.callMethod(FuncEntitySetSilent, () => super.setSilent(isSilent), isSilent)
    override def canTriggerWalking: Boolean = methodLogic.callMethod(FuncEntityCanTriggerWalking, () => super.canTriggerWalking)
    override def updateFallState(y: Double, onGroundIn: Boolean, state: IBlockState, pos: BlockPos): Unit = methodLogic.callMethod(FuncEntityUpdateFallState, () => super.updateFallState(y, onGroundIn, state, pos), y, onGroundIn, state, pos)
    override def getCollisionBoundingBox: AxisAlignedBB = methodLogic.callMethod(FuncEntityGetCollisionBoundingBox, () => super.getCollisionBoundingBox)
    override def dealFireDamage(amount: Int): Unit = methodLogic.callMethod(FuncEntityDealFireDamage, () => super.dealFireDamage(amount), amount)
    methodLogic.addFinalMethod(FuncEntityIsImmuneToFire, (args: Seq[Any]) => super.isImmuneToFire())
    override def fall(distance: Float, damageMultiplier: Float): Unit = methodLogic.callMethod(FuncEntityFall, () => super.fall(distance, damageMultiplier), distance, damageMultiplier)
    override def isWet: Boolean = methodLogic.callMethod(FuncEntityIsWet, () => super.isWet)
    override def isInWater: Boolean = methodLogic.callMethod(FuncEntityIsInWater, () => super.isInWater)
    override def handleWaterMovement(): Boolean = methodLogic.callMethod(FuncEntityHandleWaterMovement, () => super.handleWaterMovement())
    override def resetHeight(): Unit = methodLogic.callMethod(FuncEntityResetHeight, () => super.resetHeight())
    override def spawnRunningParticles(): Unit = methodLogic.callMethod(FuncEntitySpawnRunningParticles, () => super.spawnRunningParticles())
    override def createRunningParticles(): Unit = methodLogic.callMethod(FuncEntityCreateRunningParticles, () => super.createRunningParticles())
    override def isInsideOfMaterial(materialIn: Material): Boolean = methodLogic.callMethod(FuncEntityIsInsideOfMaterial, () => super.isInsideOfMaterial(materialIn), materialIn)
    override def isInLava: Boolean = methodLogic.callMethod(FuncEntityIsInLava, () => super.isInLava)
    override def moveFlying(strafe: Float, forward: Float, friction: Float): Unit = methodLogic.callMethod(FuncEntityMoveFlying, () => super.moveFlying(strafe, forward, friction), strafe, forward, friction)
    override def getBrightnessForRender(partialTicks: Float): Int = methodLogic.callMethod(FuncEntityGetBrightnessForRender, () => super.getBrightnessForRender(partialTicks), partialTicks)
    override def getBrightness(partialTicks: Float): Float = methodLogic.callMethod(FuncEntityGetBrightness, () => super.getBrightness(partialTicks), partialTicks)
    override def setWorld(worldIn: World): Unit = methodLogic.callMethod(FuncEntitySetWorld, () => super.setWorld(worldIn), worldIn)
    override def setPositionAndRotation(x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Unit = methodLogic.callMethod(FuncEntitySetPositionAndRotation, () => super.setPositionAndRotation(x, y, z, yaw, pitch), x, y, z, yaw, pitch)
    override def moveToBlockPosAndAngles(pos: BlockPos, rotationYawIn: Float, rotationPitchIn: Float): Unit = methodLogic.callMethod(FuncEntityMoveToBlockPosAndAngles, () => super.moveToBlockPosAndAngles(pos, rotationYawIn, rotationPitchIn), pos, rotationYawIn, rotationPitchIn)
    override def setLocationAndAngles(x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Unit = methodLogic.callMethod(FuncEntitySetLocationAndAngles, () => super.setLocationAndAngles(x, y, z, yaw, pitch), x, y, z, yaw, pitch)
    override def getDistanceToEntity(entityIn: Entity): Float = methodLogic.callMethod(FuncEntityGetDistanceToEntity, () => super.getDistanceToEntity(entityIn), entityIn)
    override def getDistanceSq(x: Double, y: Double, z: Double): Double = methodLogic.callMethod(FuncEntityGetDistanceSq_Coord, () => super.getDistanceSq(x, y, z), x, y, z)
    override def getDistanceSq(pos: BlockPos): Double = methodLogic.callMethod(FuncEntityGetDistanceSq_BlockPos, () => super.getDistanceSq(pos), pos)
    override def getDistanceSqToCenter(pos: BlockPos): Double = methodLogic.callMethod(FuncEntityGetDistanceSqToCenter, () => super.getDistanceSqToCenter(pos), pos)
    override def getDistance(x: Double, y: Double, z: Double): Double = methodLogic.callMethod(FuncEntityGetDistance, () => super.getDistance(x, y, z), x, y, z)
    override def getDistanceSqToEntity(entityIn: Entity): Double = methodLogic.callMethod(FuncEntityGetDistanceSqToEntity, () => super.getDistanceSqToEntity(entityIn), entityIn)
    override def onCollideWithPlayer(entityIn: EntityPlayer): Unit = methodLogic.callMethod(FuncEntityOnCollideWithPlayer, () => super.onCollideWithPlayer(entityIn), entityIn)
    override def applyEntityCollision(entityIn: Entity): Unit = methodLogic.callMethod(FuncEntityApplyEntityCollision, () => super.applyEntityCollision(entityIn), entityIn)
    override def addVelocity(x: Double, y: Double, z: Double): Unit = methodLogic.callMethod(FuncEntityAddVelocity, () => super.addVelocity(x, y, z), x, y, z)
    override def setBeenAttacked(): Unit = methodLogic.callMethod(FuncEntitySetBeenAttacked, () => super.setBeenAttacked())
    override def attackEntityFrom(source: DamageSource, amount: Float): Boolean = methodLogic.callMethod(FuncEntityAttackEntityFrom, () => super.attackEntityFrom(source, amount), source, amount)
    override def getLook(partialTicks: Float): Vec3d = methodLogic.callMethod(FuncEntityGetLook, () => super.getLook(partialTicks), partialTicks)
    methodLogic.addFinalMethod(FuncEntityGetVectorForRotation, (args: Seq[Any]) => super.getVectorForRotation(args(0).asInstanceOf[Float], args(1).asInstanceOf[Float]))
    override def getPositionEyes(partialTicks: Float): Vec3d = methodLogic.callMethod(FuncEntityGetPositionEyes, () => super.getPositionEyes(partialTicks), partialTicks)
    override def rayTrace(blockReachDistance: Double, partialTicks: Float): RayTraceResult = methodLogic.callMethod(FuncEntityRayTrace, () => super.rayTrace(blockReachDistance, partialTicks), blockReachDistance, partialTicks)
    override def canBeCollidedWith: Boolean = methodLogic.callMethod(FuncEntityCanBeCollidedWith, () => super.canBeCollidedWith)
    override def canBePushed: Boolean = methodLogic.callMethod(FuncEntityCanBePushed, () => super.canBePushed)
    override def addToPlayerScore(entityIn: Entity, amount: Int): Unit = methodLogic.callMethod(FuncEntityAddToPlayerScore, () => super.addToPlayerScore(entityIn, amount), entityIn, amount)
    override def isInRangeToRender3d(x: Double, y: Double, z: Double): Boolean = methodLogic.callMethod(FuncEntityIsInRangeToRender3d, () => super.isInRangeToRender3d(x, y, z), x, y, z)
    override def isInRangeToRenderDist(distance: Double): Boolean = methodLogic.callMethod(FuncEntityIsInRangeToRenderDist, () => super.isInRangeToRenderDist(distance), distance)
    override def writeToNBTAtomically(compound: NBTTagCompound): Boolean = methodLogic.callMethod(FuncEntityWriteToNBTAtomically, () => super.writeToNBTAtomically(compound), compound)
    override def writeToNBTOptional(compound: NBTTagCompound): Boolean = methodLogic.callMethod(FuncEntityWriteToNBTOptional, () => super.writeToNBTOptional(compound), compound)
    override def shouldSetPosAfterLoading(): Boolean = methodLogic.callMethod(FuncEntityShouldSetPosAfterLoading, () => super.shouldSetPosAfterLoading())
    methodLogic.addFinalMethod(FuncEntityGetEntityString, (args: Seq[Any]) => super.getEntityString)
    override def readEntityFromNBT(var1: NBTTagCompound): Unit = methodLogic.callMethod(FuncEntityReadEntityFromNBT, () => super.readEntityFromNBT(var1), var1)
    override def writeEntityToNBT(var1: NBTTagCompound): Unit = methodLogic.callMethod(FuncEntityWriteEntityToNBT, () => super.writeEntityToNBT(var1), var1)
    override def onChunkLoad(): Unit = methodLogic.callMethod(FuncEntityOnChunkLoad, () => super.onChunkLoad())
    override def newDoubleNBTList(numbers: Double*): NBTTagList = methodLogic.callMethod(FuncEntityNewDoubleNBTList, () => super.newDoubleNBTList(numbers:_*), numbers)
    override def newFloatNBTList(numbers: Float*): NBTTagList = methodLogic.callMethod(FuncEntityNewFloatNBTList, () => super.newFloatNBTList(numbers:_*), numbers)
    override def dropItem(itemIn: Item, size: Int): EntityItem = methodLogic.callMethod(FuncEntityDropItem, () => super.dropItem(itemIn, size), itemIn, size)
    override def dropItemWithOffset(itemIn: Item, size: Int, offsetY: Float): EntityItem = methodLogic.callMethod(FuncEntityDropItemWithOffset, () => super.dropItemWithOffset(itemIn, size, offsetY), itemIn, size, offsetY)
    override def entityDropItem(stack: ItemStack, offsetY: Float): EntityItem = methodLogic.callMethod(FuncEntityEntityDropItem, () => super.entityDropItem(stack, offsetY), stack, offsetY)
    override def isEntityAlive: Boolean = methodLogic.callMethod(FuncEntityIsEntityAlive, () => super.isEntityAlive)
    override def isEntityInsideOpaqueBlock: Boolean = methodLogic.callMethod(FuncEntityIsEntityInsideOpaqueBlock, () => super.isEntityInsideOpaqueBlock)
    methodLogic.addFinalMethod(FuncEntityProcessInitialInteract, (args: Seq[Any]) => super.processInitialInteract(args(0).asInstanceOf[EntityPlayer], args(1).asInstanceOf[ItemStack], args(2).asInstanceOf[EnumHand]))
    override def getCollisionBox(entityIn: Entity): AxisAlignedBB = methodLogic.callMethod(FuncEntityGetCollisionBox, () => super.getCollisionBox(entityIn), entityIn)
    override def updateRidden(): Unit = methodLogic.callMethod(FuncEntityUpdateRidden, () => super.updateRidden())
    override def updatePassenger(passenger: Entity): Unit = methodLogic.callMethod(FuncEntityUpdatePassenger, () => super.updatePassenger(passenger), passenger)
    override def applyOrientationToEntity(entityToUpdate: Entity): Unit = methodLogic.callMethod(FuncEntityApplyOrientationToEntity, () => super.applyOrientationToEntity(entityToUpdate), entityToUpdate)
    override def getYOffset: Double = methodLogic.callMethod(FuncEntityGetYOffset, () => super.getYOffset)
    override def getMountedYOffset: Double = methodLogic.callMethod(FuncEntityGetMountedYOffset, () => super.getMountedYOffset)
    override def startRiding(entityIn: Entity): Boolean = methodLogic.callMethod(FuncEntityStartRiding, () => super.startRiding(entityIn), entityIn)
    override def startRiding(entityIn: Entity, force: Boolean): Boolean = methodLogic.callMethod(FuncEntityStartRiding, () => super.startRiding(entityIn, force), entityIn, force)
    override def canBeRidden(entityIn: Entity): Boolean = methodLogic.callMethod(FuncEntityCanBeRidden_OneParam, () => super.canBeRidden(entityIn), entityIn)
    override def removePassengers(): Unit = methodLogic.callMethod(FuncEntityRemovePassengers, () => super.removePassengers())
    override def dismountRidingEntity(): Unit = methodLogic.callMethod(FuncEntityDismountRidingEntity, () => super.dismountRidingEntity())
    override def addPassenger(passenger: Entity): Unit = methodLogic.callMethod(FuncEntityAddPassenger, () => super.addPassenger(passenger), passenger)
    override def removePassenger(passenger: Entity): Unit = methodLogic.callMethod(FuncEntityRemovePassenger, () => super.removePassenger(passenger), passenger)
    override def canFitPassenger(passenger: Entity): Boolean = methodLogic.callMethod(FuncEntityCanFitPassenger, () => super.canFitPassenger(passenger), passenger)
    override def setPositionAndRotation2(x: Double, y: Double, z: Double, yaw: Float, pitch: Float, posRotationIncrements: Int, teleport: Boolean): Unit = methodLogic.callMethod(FuncEntitySetPositionAndRotation2, () => super.setPositionAndRotation2(x, y, z, yaw, pitch, posRotationIncrements, teleport), x, y, z, yaw, pitch, posRotationIncrements, teleport)
    override def getCollisionBorderSize: Float = methodLogic.callMethod(FuncEntityGetCollisionBorderSize, () => super.getCollisionBorderSize)
    override def getLookVec: Vec3d = methodLogic.callMethod(FuncEntityGetLookVec, () => super.getLookVec)
    override def setPortal(pos: BlockPos): Unit = methodLogic.callMethod(FuncEntitySetPortal, () => super.setPortal(pos), pos)
    override def getPortalCooldown: Int = methodLogic.callMethod(FuncEntityGetPortalCooldown, () => super.getPortalCooldown)
    override def setVelocity(x: Double, y: Double, z: Double): Unit = methodLogic.callMethod(FuncEntitySetVelocity, () => super.setVelocity(x, y, z), x, y, z)
    override def handleStatusUpdate(id: Byte): Unit = methodLogic.callMethod(FuncEntityHandleStatusUpdate, () => super.handleStatusUpdate(id), id)
    override def performHurtAnimation(): Unit = methodLogic.callMethod(FuncEntityPerformHurtAnimation, () => super.performHurtAnimation())
    override def getHeldEquipment: lang.Iterable[ItemStack] = methodLogic.callMethod(FuncEntityGetHeldEquipment, () => super.getHeldEquipment)
    override def getArmorInventoryList: lang.Iterable[ItemStack] = methodLogic.callMethod(FuncEntityGetArmorInventoryList, () => super.getArmorInventoryList)
    override def getEquipmentAndArmor: lang.Iterable[ItemStack] = methodLogic.callMethod(FuncEntityGetEquipmentAndArmor, () => super.getEquipmentAndArmor)
    override def setItemStackToSlot(slotIn: EntityEquipmentSlot, stack: ItemStack): Unit = methodLogic.callMethod(FuncEntitySetItemStackToSlot, () => super.setItemStackToSlot(slotIn, stack), slotIn, stack)
    override def isBurning: Boolean = methodLogic.callMethod(FuncEntityIsBurning, () => super.isBurning)
    override def isRiding: Boolean = methodLogic.callMethod(FuncEntityIsRiding, () => super.isRiding)
    override def isBeingRidden: Boolean = methodLogic.callMethod(FuncEntityIsBeingRidden, () => super.isBeingRidden)
    override def isSneaking: Boolean = methodLogic.callMethod(FuncEntityIsSneaking, () => super.isSneaking)
    override def setSneaking(sneaking: Boolean): Unit = methodLogic.callMethod(FuncEntitySetSneaking, () => super.setSneaking(sneaking), sneaking)
    override def isSprinting: Boolean = methodLogic.callMethod(FuncEntityIsSprinting, () => super.isSprinting)
    override def setSprinting(sprinting: Boolean): Unit = methodLogic.callMethod(FuncEntitySetSprinting, () => super.setSprinting(sprinting), sprinting)
    override def isGlowing: Boolean = methodLogic.callMethod(FuncEntityIsGlowing, () => super.isGlowing)
    override def setGlowing(glowingIn: Boolean): Unit = methodLogic.callMethod(FuncEntitySetGlowing, () => super.setGlowing(glowingIn), glowingIn)
    override def isInvisible: Boolean = methodLogic.callMethod(FuncEntityIsInvisible, () => super.isInvisible)
    override def isInvisibleToPlayer(player: EntityPlayer): Boolean = methodLogic.callMethod(FuncEntityIsInvisibleToPlayer, () => super.isInvisibleToPlayer(player), player)
    override def getTeam: Team = methodLogic.callMethod(FuncEntityGetTeam, () => super.getTeam)
    override def isOnSameTeam(entityIn: Entity): Boolean = methodLogic.callMethod(FuncEntityIsOnSameTeam, () => super.isOnSameTeam(entityIn), entityIn)
    override def isOnScoreboardTeam(teamIn: Team): Boolean = methodLogic.callMethod(FuncEntityIsOnScoreboardTeam, () => super.isOnScoreboardTeam(teamIn), teamIn)
    override def setInvisible(invisible: Boolean): Unit = methodLogic.callMethod(FuncEntitySetInvisible, () => super.setInvisible(invisible), invisible)
    override def getFlag(flag: Int): Boolean = methodLogic.callMethod(FuncEntityGetFlag, () => super.getFlag(flag), flag)
    override def setFlag(flag: Int, set: Boolean): Unit = methodLogic.callMethod(FuncEntitySetFlag, () => super.setFlag(flag, set), flag, set)
    override def getAir: Int = methodLogic.callMethod(FuncEntityGetAir, () => super.getAir)
    override def setAir(air: Int): Unit = methodLogic.callMethod(FuncEntitySetAir, () => super.setAir(air), air)
    override def onStruckByLightning(lightningBolt: EntityLightningBolt): Unit = methodLogic.callMethod(FuncEntityOnStruckByLightning, () => super.onStruckByLightning(lightningBolt), lightningBolt)
    override def onKillEntity(entityLivingIn: EntityLivingBase): Unit = methodLogic.callMethod(FuncEntityOnKillEntity, () => super.onKillEntity(entityLivingIn), entityLivingIn)
    override def pushOutOfBlocks(x: Double, y: Double, z: Double): Boolean = methodLogic.callMethod(FuncEntityPushOutOfBlocks, () => super.pushOutOfBlocks(x, y, z), x, y, z)
    override def setInWeb(): Unit = methodLogic.callMethod(FuncEntitySetInWeb, () => super.setInWeb())
    override def getName: String = methodLogic.callMethod(FuncEntityGetName, () => super.getName)
    override def getParts: Array[Entity] = methodLogic.callMethod(FuncEntityGetParts, () => super.getParts)
    override def isEntityEqual(entityIn: Entity): Boolean = methodLogic.callMethod(FuncEntityIsEntityEqual, () => super.isEntityEqual(entityIn), entityIn)
    override def getRotationYawHead: Float = methodLogic.callMethod(FuncEntityGetRotationYawHead, () => super.getRotationYawHead)
    override def setRotationYawHead(rotation: Float): Unit = methodLogic.callMethod(FuncEntitySetRotationYawHead, () => super.setRotationYawHead(rotation), rotation)
    override def setRenderYawOffset(offset: Float): Unit = methodLogic.callMethod(FuncEntitySetRenderYawOffset, () => super.setRenderYawOffset(offset), offset)
    override def canAttackWithItem: Boolean = methodLogic.callMethod(FuncEntityCanAttackWithItem, () => super.canAttackWithItem)
    override def hitByEntity(entityIn: Entity): Boolean = methodLogic.callMethod(FuncEntityHitByEntity, () => super.hitByEntity(entityIn), entityIn)
    override def toString: String = methodLogic.callMethod(FuncEntityToString, () => super.toString)
    override def isEntityInvulnerable(source: DamageSource): Boolean = methodLogic.callMethod(FuncEntityIsEntityInvulnerable, () => super.isEntityInvulnerable(source), source)
    override def setEntityInvulnerable(isInvulnerable: Boolean): Unit = methodLogic.callMethod(FuncEntitySetEntityInvulnerable, () => super.setEntityInvulnerable(isInvulnerable), isInvulnerable)
    override def copyLocationAndAnglesFrom(entityIn: Entity): Unit = methodLogic.callMethod(FuncEntityCopyLocationAndAnglesFrom, () => super.copyLocationAndAnglesFrom(entityIn), entityIn)
    override def changeDimension(dimensionIn: Int): Entity = methodLogic.callMethod(FuncEntityChangeDimension, () => super.changeDimension(dimensionIn), dimensionIn)
    override def isNonBoss: Boolean = methodLogic.callMethod(FuncEntityIsNonBoss, () => super.isNonBoss)
    override def getExplosionResistance(explosionIn: Explosion, worldIn: World, pos: BlockPos, blockStateIn: IBlockState): Float = methodLogic.callMethod(FuncEntityGetExplosionResistance, () => super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn), explosionIn, worldIn, pos, blockStateIn)
    override def verifyExplosion(explosionIn: Explosion, worldIn: World, pos: BlockPos, blockStateIn: IBlockState, p_174816_5_ : Float): Boolean = methodLogic.callMethod(FuncEntityVerifyExplosion, () => super.verifyExplosion(explosionIn, worldIn, pos, blockStateIn, p_174816_5_), explosionIn, worldIn, pos, blockStateIn, p_174816_5_)
    override def getMaxFallHeight: Int = methodLogic.callMethod(FuncEntityGetMaxFallHeight, () => super.getMaxFallHeight)
    override def getLastPortalVec: Vec3d = methodLogic.callMethod(FuncEntityGetLastPortalVec, () => super.getLastPortalVec)
    override def getTeleportDirection: EnumFacing = methodLogic.callMethod(FuncEntityGetTeleportDirection, () => super.getTeleportDirection)
    override def doesEntityNotTriggerPressurePlate(): Boolean = methodLogic.callMethod(FuncEntityDoesEntityNotTriggerPressurePlate, () => super.doesEntityNotTriggerPressurePlate())
    override def addEntityCrashInfo(category: CrashReportCategory): Unit = methodLogic.callMethod(FuncEntityAddEntityCrashInfo, () => super.addEntityCrashInfo(category), category)
    override def setUniqueId(uniqueIdIn: UUID): Unit = methodLogic.callMethod(FuncEntitySetUniqueId, () => super.setUniqueId(uniqueIdIn), uniqueIdIn)
    override def canRenderOnFire: Boolean = methodLogic.callMethod(FuncEntityCanRenderOnFire, () => super.canRenderOnFire)
    override def getUniqueID: UUID = methodLogic.callMethod(FuncEntityGetUniqueID, () => super.getUniqueID)
    override def isPushedByWater: Boolean = methodLogic.callMethod(FuncEntityIsPushedByWater, () => super.isPushedByWater)
    override def getDisplayName: ITextComponent = methodLogic.callMethod(FuncEntityGetDisplayName, () => super.getDisplayName)
    override def setCustomNameTag(name: String): Unit = methodLogic.callMethod(FuncEntitySetCustomNameTag, () => super.setCustomNameTag(name), name)
    override def getCustomNameTag: String = methodLogic.callMethod(FuncEntityGetCustomNameTag, () => super.getCustomNameTag)
    override def hasCustomName: Boolean = methodLogic.callMethod(FuncEntityHasCustomName, () => super.hasCustomName)
    override def setAlwaysRenderNameTag(alwaysRenderNameTag: Boolean): Unit = methodLogic.callMethod(FuncEntitySetAlwaysRenderNameTag, () => super.setAlwaysRenderNameTag(alwaysRenderNameTag), alwaysRenderNameTag)
    override def getAlwaysRenderNameTag: Boolean = methodLogic.callMethod(FuncEntityGetAlwaysRenderNameTag, () => super.getAlwaysRenderNameTag)
    override def setPositionAndUpdate(x: Double, y: Double, z: Double): Unit = methodLogic.callMethod(FuncEntitySetPositionAndUpdate, () => super.setPositionAndUpdate(x, y, z), x, y, z)
    override def notifyDataManagerChange(key: DataParameter[_]): Unit = if(methodLogic == null) super.notifyDataManagerChange(key) else methodLogic.callMethod(FuncEntityNotifyDataManagerChange, () => super.notifyDataManagerChange(key), key)
    override def getAlwaysRenderNameTagForRender: Boolean = methodLogic.callMethod(FuncEntityGetAlwaysRenderNameTagForRender, () => super.getAlwaysRenderNameTagForRender)
    override def getHorizontalFacing: EnumFacing = methodLogic.callMethod(FuncEntityGetHorizontalFacing, () => super.getHorizontalFacing)
    override def getAdjustedHorizontalFacing: EnumFacing = methodLogic.callMethod(FuncEntityGetAdjustedHorizontalFacing, () => super.getAdjustedHorizontalFacing)
    override def getHoverEvent: HoverEvent = methodLogic.callMethod(FuncEntityGetHoverEvent, () => super.getHoverEvent)
    override def isSpectatedByPlayer(player: EntityPlayerMP): Boolean = methodLogic.callMethod(FuncEntityIsSpectatedByPlayer, () => super.isSpectatedByPlayer(player), player)
    override def getEntityBoundingBox: AxisAlignedBB = methodLogic.callMethod(FuncEntityGetEntityBoundingBox, () => super.getEntityBoundingBox)
    override def getRenderBoundingBox: AxisAlignedBB = methodLogic.callMethod(FuncEntityGetRenderBoundingBox, () => super.getRenderBoundingBox)
    //setEntityBoundingBox gets called in Entity constructor, this prevents a NPE.
    override def setEntityBoundingBox(bb: AxisAlignedBB): Unit = if(methodLogic == null) super.setEntityBoundingBox(bb) else methodLogic.callMethod(FuncEntitySetEntityBoundingBox, () => super.setEntityBoundingBox(bb), bb)
    override def getEyeHeight: Float = methodLogic.callMethod(FuncEntityGetEyeHeight, () => super.getEyeHeight)
    override def isOutsideBorder: Boolean = methodLogic.callMethod(FuncEntityIsOutsideBorder, () => super.isOutsideBorder)
    override def setOutsideBorder(outsideBorder: Boolean): Unit = methodLogic.callMethod(FuncEntitySetOutsideBorder, () => super.setOutsideBorder(outsideBorder), outsideBorder)
    override def replaceItemInInventory(inventorySlot: Int, itemStackIn: ItemStack): Boolean = methodLogic.callMethod(FuncEntityReplaceItemInInventory, () => super.replaceItemInInventory(inventorySlot, itemStackIn), inventorySlot, itemStackIn)
    override def addChatMessage(component: ITextComponent): Unit = methodLogic.callMethod(FuncEntityAddChatMessage, () => super.addChatMessage(component), component)
    override def canCommandSenderUseCommand(permLevel: Int, commandName: String): Boolean = methodLogic.callMethod(FuncEntityCanCommandSenderUseCommand, () => super.canCommandSenderUseCommand(permLevel, commandName), permLevel, commandName)
    override def getPosition: BlockPos = methodLogic.callMethod(FuncEntityGetPosition, () => super.getPosition)
    override def getPositionVector: Vec3d = methodLogic.callMethod(FuncEntityGetPositionVector, () => super.getPositionVector)
    override def getEntityWorld: World = methodLogic.callMethod(FuncEntityGetEntityWorld, () => super.getEntityWorld)
    override def getCommandSenderEntity: Entity = methodLogic.callMethod(FuncEntityGetCommandSenderEntity, () => super.getCommandSenderEntity)
    override def sendCommandFeedback(): Boolean = methodLogic.callMethod(FuncEntitySendCommandFeedback, () => super.sendCommandFeedback())
    override def setCommandStat(tpe: Type, amount: Int): Unit = methodLogic.callMethod(FuncEntitySetCommandStat, () => super.setCommandStat(tpe, amount), tpe, amount)
    override def getServer: MinecraftServer = methodLogic.callMethod(FuncEntityGetServer, () => super.getServer)
    override def getCommandStats: CommandResultStats = methodLogic.callMethod(FuncEntityGetCommandStats, () => super.getCommandStats)
    override def setCommandStats(entityIn: Entity): Unit = methodLogic.callMethod(FuncEntitySetCommandStats, () => super.setCommandStats(entityIn), entityIn)
    override def applyPlayerInteraction(player: EntityPlayer, vec: Vec3d, stack: ItemStack, hand: EnumHand): EnumActionResult = methodLogic.callMethod(FuncEntityApplyPlayerInteraction, () => super.applyPlayerInteraction(player, vec, stack, hand), player, vec, stack, hand)
    override def isImmuneToExplosions: Boolean = methodLogic.callMethod(FuncEntityIsImmuneToExplosions, () => super.isImmuneToExplosions)
    override def applyEnchantments(entityLivingBaseIn: EntityLivingBase, entityIn: Entity): Unit = methodLogic.callMethod(FuncEntityApplyEnchantments, () => super.applyEnchantments(entityLivingBaseIn, entityIn), entityLivingBaseIn, entityIn)
    override def setBossVisibleTo(player: EntityPlayerMP): Unit = methodLogic.callMethod(FuncEntitySetBossVisibleTo, () => super.setBossVisibleTo(player), player)
    override def setBossNonVisibleTo(player: EntityPlayerMP): Unit = methodLogic.callMethod(FuncEntitySetBossNonVisibleTo, () => super.setBossNonVisibleTo(player), player)
    override def getRotatedYaw(transformRotation: Rotation): Float = methodLogic.callMethod(FuncEntityGetRotatedYaw, () => super.getRotatedYaw(transformRotation), transformRotation)
    override def getMirroredYaw(transformMirror: Mirror): Float = methodLogic.callMethod(FuncEntityGetMirroredYaw, () => super.getMirroredYaw(transformMirror), transformMirror)
    override def func_184213_bq(): Boolean = methodLogic.callMethod(FuncEntityFunc_184213_bq, () => super.func_184213_bq())
    override def setPositionNonDirty(): Boolean = methodLogic.callMethod(FuncEntitySetPositionNonDirty, () => super.setPositionNonDirty())
    override def getControllingPassenger: Entity = methodLogic.callMethod(FuncEntityGetControllingPassenger, () => super.getControllingPassenger)
    override def getPassengers: util.List[Entity] = methodLogic.callMethod(FuncEntityGetPassengers, () => super.getPassengers)
    override def isPassenger(entityIn: Entity): Boolean = methodLogic.callMethod(FuncEntityIsPassenger, () => super.isPassenger(entityIn), entityIn)
    override def getRecursivePassengers: util.Collection[Entity] = methodLogic.callMethod(FuncEntityGetRecursivePassengers, () => super.getRecursivePassengers)
    override def getRecursivePassengersByType[T <: Entity](entityClass: Class[T]): util.Collection[T] = methodLogic.callMethod(FuncEntityGetRecursivePassengersByType, () => super.getRecursivePassengersByType(entityClass), entityClass).asInstanceOf[util.Collection[T]]
    override def getLowestRidingEntity: Entity = methodLogic.callMethod(FuncEntityGetLowestRidingEntity, () => super.getLowestRidingEntity)
    override def isRidingSameEntity(entityIn: Entity): Boolean = methodLogic.callMethod(FuncEntityIsRidingSameEntity, () => super.isRidingSameEntity(entityIn), entityIn)
    override def isRidingOrBeingRiddenBy(entityIn: Entity): Boolean = methodLogic.callMethod(FuncEntityIsRidingOrBeingRiddenBy, () => super.isRidingOrBeingRiddenBy(entityIn), entityIn)
    override def canPassengerSteer: Boolean = methodLogic.callMethod(FuncEntityCanPassengerSteer, () => super.canPassengerSteer)
    override def getRidingEntity: Entity = methodLogic.callMethod(FuncEntityGetRidingEntity, () => super.getRidingEntity)
    override def getPushReaction: EnumPushReaction = methodLogic.callMethod(FuncEntityGetPushReaction, () => super.getPushReaction)
    override def getSoundCategory: SoundCategory = methodLogic.callMethod(FuncEntityGetSoundCategory, () => super.getSoundCategory)

}
