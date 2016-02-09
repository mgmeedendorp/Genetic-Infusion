package seremis.geninfusion.soul.entity

import java.util.UUID

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import io.netty.buffer.ByteBuf
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.crash.CrashReportCategory
import net.minecraft.entity._
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.util._
import net.minecraft.world.{Explosion, World}
import net.minecraftforge.common.IExtendedEntityProperties
import seremis.geninfusion.api.lib.reflection.FunctionLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ISoul, SoulHelper}
import seremis.geninfusion.api.util.DataWatcherHelper
import seremis.geninfusion.api.util.data.Data
import seremis.geninfusion.entity.GIEntity
import seremis.geninfusion.network.packet.PacketAddDataWatcherHelperMapping
import seremis.geninfusion.soul.entity.logic.VariableSyncLogic
import seremis.geninfusion.soul.{EntityMethodHandler, Soul, TraitHandler}
import seremis.geninfusion.util.UtilNBT

trait EntitySoulCustomTrait extends EntityLiving with IEntitySoulCustom with IEntityAdditionalSpawnData with GIEntity {

    var syncLogic = new VariableSyncLogic(this)
    var soul: ISoul
    val world: World

    var shouldCallEntityInit = true

    override def writeSpawnData(data: ByteBuf) {
        val compound = new NBTTagCompound
        writeToNBT_I(compound)
        val bytes = UtilNBT.compoundToByteArray(compound).getOrElse(return)
        data.writeInt(bytes.length)
        data.writeBytes(bytes)

        if(DataWatcherHelper.cachedPackets.contains(getEntityId)) {
            data.writeInt(DataWatcherHelper.cachedPackets.get(getEntityId).get.size)

            DataWatcherHelper.cachedPackets.get(getEntityId).get.foreach(packet => {
                packet.toBytes(data)
            })
        }
    }

    override def readSpawnData(data: ByteBuf) {
        val length = data.readInt
        val bytes = new Array[Byte](length)

        data.readBytes(bytes)

        val compound: NBTTagCompound = UtilNBT.byteArrayToCompound(bytes).getOrElse(return)
        readFromNBT_I(compound)

        if(data.readerIndex() < data.writerIndex) {
            val size = data.readInt()

            for(i <- 0 until size) {
                val packet = new PacketAddDataWatcherHelperMapping()

                packet.fromBytes(data)
                PacketAddDataWatcherHelperMapping.handleMessage(packet, this)
            }
        }
    }

    override def getEntityId(): Int = if(inRegistry(EntityGetEntityId)) EntityMethodHandler.handleMethodCall(this, EntityGetEntityId) else super.getEntityId()
    override def setEntityId(id: Int): Unit = if(inRegistry(EntitySetEntityId)) EntityMethodHandler.handleMethodCall(this, EntitySetEntityId, id) else super.setEntityId(id)
    override def entityInit(): Unit = if(inRegistry(EntityEntityInit)) EntityMethodHandler.handleMethodCall(this, EntityEntityInit) else super.entityInit()
    override def getDataWatcher(): DataWatcher = if(inRegistry(EntityGetDataWatcher)) EntityMethodHandler.handleMethodCall(this, EntityGetDataWatcher) else super.getDataWatcher()
    override def equals(p_equals_1_ : Object): Boolean = if(inRegistry(EntityEquals)) EntityMethodHandler.handleMethodCall(this, EntityEquals, p_equals_1_) else super.equals(p_equals_1_)
    override def hashCode(): Int = if(inRegistry(EntityHashCode)) EntityMethodHandler.handleMethodCall(this, EntityHashCode) else super.hashCode()
    override def preparePlayerToSpawn(): Unit = if(inRegistry(EntityPreparePlayerToSpawn)) EntityMethodHandler.handleMethodCall(this, EntityPreparePlayerToSpawn) else super.preparePlayerToSpawn()
    override def setDead(): Unit = if(inRegistry(EntitySetDead)) EntityMethodHandler.handleMethodCall(this, EntitySetDead) else super.setDead()
    override def setSize(width: Float, height: Float): Unit = if(inRegistry(EntitySetSize)) EntityMethodHandler.handleMethodCall(this, EntitySetSize, width, height) else super.setSize(width, height)
    override def setRotation(yaw: Float, pitch: Float): Unit = if(inRegistry(EntitySetRotation)) EntityMethodHandler.handleMethodCall(this, EntitySetRotation, yaw, pitch) else super.setRotation(yaw, pitch)
    override def setPosition(x: Double, y: Double, z: Double): Unit = if(inRegistry(EntitySetPosition)) EntityMethodHandler.handleMethodCall(this, EntitySetPosition, x, y, z) else super.setPosition(x, y, z)
    override def setAngles(yaw: Float, pitch: Float): Unit = if(inRegistry(EntitySetAngles)) EntityMethodHandler.handleMethodCall(this, EntitySetAngles, yaw, pitch) else super.setAngles(yaw, pitch)
    override def onUpdate(): Unit = if(inRegistry(EntityOnUpdate)) EntityMethodHandler.handleMethodCall(this, EntityOnUpdate) else super.onUpdate()
    override def onEntityUpdate(): Unit = if(inRegistry(EntityOnEntityUpdate)) EntityMethodHandler.handleMethodCall(this, EntityOnEntityUpdate) else super.onEntityUpdate()
    override def getMaxInPortalTime(): Int = if(inRegistry(EntityGetMaxInPortalTime)) EntityMethodHandler.handleMethodCall(this, EntityGetMaxInPortalTime) else super.getMaxInPortalTime()
    override def setOnFireFromLava(): Unit = if(inRegistry(EntitySetOnFireFromLava)) EntityMethodHandler.handleMethodCall(this, EntitySetOnFireFromLava) else super.setOnFireFromLava()
    override def setFire(seconds: Int): Unit = if(inRegistry(EntitySetFire)) EntityMethodHandler.handleMethodCall(this, EntitySetFire, seconds) else super.setFire(seconds)
    override def extinguish(): Unit = if(inRegistry(EntityExtinguish)) EntityMethodHandler.handleMethodCall(this, EntityExtinguish) else super.extinguish()
    override def kill(): Unit = if(inRegistry(EntityKill)) EntityMethodHandler.handleMethodCall(this, EntityKill) else super.kill()
    override def isOffsetPositionInLiquid(x: Double, y: Double, z: Double): Boolean = if(inRegistry(EntityIsOffsetPositionInLiquid)) EntityMethodHandler.handleMethodCall(this, EntityIsOffsetPositionInLiquid, x, y, z) else super.isOffsetPositionInLiquid(x, y, z)
    override def moveEntity(x: Double, y: Double, z: Double): Unit = if(inRegistry(EntityMoveEntity)) EntityMethodHandler.handleMethodCall(this, EntityMoveEntity, x, y, z) else super.moveEntity(x, y, z)
    override def getSwimSound(): String = if(inRegistry(EntityGetSwimSound)) EntityMethodHandler.handleMethodCall(this, EntityGetSwimSound) else super.getSwimSound()
    override def doBlockCollisions(): Unit = if(inRegistry(EntityDoBlockCollisions)) EntityMethodHandler.handleMethodCall(this, EntityDoBlockCollisions) else super.doBlockCollisions()
    override def playStepSound(x: Int, y: Int, z: Int, blockIn: Block): Unit = if(inRegistry(EntityPlayStepSound)) EntityMethodHandler.handleMethodCall(this, EntityPlayStepSound, x, y, z, blockIn) else super.playStepSound(x, y, z, blockIn)
    override def playSound(name: String, volume: Float, pitch: Float): Unit = if(inRegistry(EntityPlaySound)) EntityMethodHandler.handleMethodCall(this, EntityPlaySound, name, volume, pitch) else super.playSound(name, volume, pitch)
    override def canTriggerWalking(): Boolean = if(inRegistry(EntityCanTriggerWalking)) EntityMethodHandler.handleMethodCall(this, EntityCanTriggerWalking) else super.canTriggerWalking()
    override def updateFallState(distanceFallenThisTick: Double, isOnGround: Boolean): Unit = if(inRegistry(EntityUpdateFallState)) EntityMethodHandler.handleMethodCall(this, EntityUpdateFallState, distanceFallenThisTick, isOnGround) else super.updateFallState(distanceFallenThisTick, isOnGround)
    override def getBoundingBox(): AxisAlignedBB = if(inRegistry(EntityGetBoundingBox)) EntityMethodHandler.handleMethodCall(this, EntityGetBoundingBox) else super.getBoundingBox()
    override def dealFireDamage(amount: Int): Unit = if(inRegistry(EntityDealFireDamage)) EntityMethodHandler.handleMethodCall(this, EntityDealFireDamage, amount) else super.dealFireDamage(amount)
    override def isImmuneToFire(): Boolean = if(inRegistry(EntityIsImmuneToFire)) EntityMethodHandler.handleMethodCall(this, EntityIsImmuneToFire) else super.isImmuneToFire()
    override def fall(distance: Float): Unit = if(inRegistry(EntityFall)) EntityMethodHandler.handleMethodCall(this, EntityFall, distance) else super.fall(distance)
    override def isWet(): Boolean = if(inRegistry(EntityIsWet)) EntityMethodHandler.handleMethodCall(this, EntityIsWet) else super.isWet()
    override def isInWater(): Boolean = if(inRegistry(EntityIsInWater)) EntityMethodHandler.handleMethodCall(this, EntityIsInWater) else super.isInWater()
    override def handleWaterMovement(): Boolean = if(inRegistry(EntityHandleWaterMovement)) EntityMethodHandler.handleMethodCall(this, EntityHandleWaterMovement) else super.handleWaterMovement()
    override def getSplashSound(): String = if(inRegistry(EntityGetSplashSound)) EntityMethodHandler.handleMethodCall(this, EntityGetSplashSound) else super.getSplashSound()
    override def isInsideOfMaterial(materialIn: Material): Boolean = if(inRegistry(EntityIsInsideOfMaterial)) EntityMethodHandler.handleMethodCall(this, EntityIsInsideOfMaterial, materialIn) else super.isInsideOfMaterial(materialIn)
    override def getEyeHeight(): Float = if(inRegistry(EntityGetEyeHeight)) EntityMethodHandler.handleMethodCall(this, EntityGetEyeHeight) else super.getEyeHeight()
    override def handleLavaMovement(): Boolean = if(inRegistry(EntityHandleLavaMovement)) EntityMethodHandler.handleMethodCall(this, EntityHandleLavaMovement) else super.handleLavaMovement()
    override def moveFlying(strafe: Float, forward: Float, friction: Float): Unit = if(inRegistry(EntityMoveFlying)) EntityMethodHandler.handleMethodCall(this, EntityMoveFlying, strafe, forward, friction) else super.moveFlying(strafe, forward, friction)
    override def getBrightnessForRender(p_70070_1_ : Float): Int = if(inRegistry(EntityGetBrightnessForRender)) EntityMethodHandler.handleMethodCall(this, EntityGetBrightnessForRender, p_70070_1_) else super.getBrightnessForRender(p_70070_1_)
    override def getBrightness(p_70013_1_ : Float): Float = if(inRegistry(EntityGetBrightness)) EntityMethodHandler.handleMethodCall(this, EntityGetBrightness, p_70013_1_) else super.getBrightness(p_70013_1_)
    override def setWorld(worldIn: World): Unit = if(inRegistry(EntitySetWorld)) EntityMethodHandler.handleMethodCall(this, EntitySetWorld, worldIn) else super.setWorld(worldIn)
    override def setPositionAndRotation(x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Unit = if(inRegistry(EntitySetPositionAndRotation)) EntityMethodHandler.handleMethodCall(this, EntitySetPositionAndRotation, x, y, z, yaw, pitch) else super.setPositionAndRotation(x, y, z, yaw, pitch)
    override def setLocationAndAngles(x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Unit = if(inRegistry(EntitySetLocationAndAngles)) EntityMethodHandler.handleMethodCall(this, EntitySetLocationAndAngles, x, y, z, yaw, pitch) else super.setLocationAndAngles(x, y, z, yaw, pitch)
    override def getDistanceToEntity(entityIn: Entity): Float = if(inRegistry(EntityGetDistanceToEntity)) EntityMethodHandler.handleMethodCall(this, EntityGetDistanceToEntity, entityIn) else super.getDistanceToEntity(entityIn)
    override def getDistanceSq(x: Double, y: Double, z: Double): Double = if(inRegistry(EntityGetDistanceSq)) EntityMethodHandler.handleMethodCall(this, EntityGetDistanceSq, x, y, z) else super.getDistanceSq(x, y, z)
    override def getDistance(x: Double, y: Double, z: Double): Double = if(inRegistry(EntityGetDistance)) EntityMethodHandler.handleMethodCall(this, EntityGetDistance, x, y, z) else super.getDistance(x, y, z)
    override def getDistanceSqToEntity(entityIn: Entity): Double = if(inRegistry(EntityGetDistanceSqToEntity)) EntityMethodHandler.handleMethodCall(this, EntityGetDistanceSqToEntity, entityIn) else super.getDistanceSqToEntity(entityIn)
    override def onCollideWithPlayer(entityIn: EntityPlayer): Unit = if(inRegistry(EntityOnCollideWithPlayer)) EntityMethodHandler.handleMethodCall(this, EntityOnCollideWithPlayer, entityIn) else super.onCollideWithPlayer(entityIn)
    override def applyEntityCollision(entityIn: Entity): Unit = if(inRegistry(EntityApplyEntityCollision)) EntityMethodHandler.handleMethodCall(this, EntityApplyEntityCollision, entityIn) else super.applyEntityCollision(entityIn)
    override def addVelocity(x: Double, y: Double, z: Double): Unit = if(inRegistry(EntityAddVelocity)) EntityMethodHandler.handleMethodCall(this, EntityAddVelocity, x, y, z) else super.addVelocity(x, y, z)
    override def setBeenAttacked(): Unit = if(inRegistry(EntitySetBeenAttacked)) EntityMethodHandler.handleMethodCall(this, EntitySetBeenAttacked) else super.setBeenAttacked()
    override def attackEntityFrom(source: DamageSource, amount: Float): Boolean = if(inRegistry(EntityAttackEntityFrom)) EntityMethodHandler.handleMethodCall(this, EntityAttackEntityFrom, source, amount) else super.attackEntityFrom(source, amount)
    override def canBeCollidedWith(): Boolean = if(inRegistry(EntityCanBeCollidedWith)) EntityMethodHandler.handleMethodCall(this, EntityCanBeCollidedWith) else super.canBeCollidedWith()
    override def canBePushed(): Boolean = if(inRegistry(EntityCanBePushed)) EntityMethodHandler.handleMethodCall(this, EntityCanBePushed) else super.canBePushed()
    override def addToPlayerScore(entityIn: Entity, amount: Int): Unit = if(inRegistry(EntityAddToPlayerScore)) EntityMethodHandler.handleMethodCall(this, EntityAddToPlayerScore, entityIn, amount) else super.addToPlayerScore(entityIn, amount)
    override def isInRangeToRender3d(x: Double, y: Double, z: Double): Boolean = if(inRegistry(EntityIsInRangeToRender3d)) EntityMethodHandler.handleMethodCall(this, EntityIsInRangeToRender3d, x, y, z) else super.isInRangeToRender3d(x, y, z)
    override def isInRangeToRenderDist(distance: Double): Boolean = if(inRegistry(EntityIsInRangeToRenderDist)) EntityMethodHandler.handleMethodCall(this, EntityIsInRangeToRenderDist, distance) else super.isInRangeToRenderDist(distance)
    override def writeMountToNBT(tagCompund: NBTTagCompound): Boolean = if(inRegistry(EntityWriteMountToNBT)) EntityMethodHandler.handleMethodCall(this, EntityWriteMountToNBT, tagCompund) else super.writeMountToNBT(tagCompund)
    override def writeToNBTOptional(tagCompund: NBTTagCompound): Boolean = if(inRegistry(EntityWriteToNBTOptional)) EntityMethodHandler.handleMethodCall(this, EntityWriteToNBTOptional, tagCompund) else super.writeToNBTOptional(tagCompund)
    //override def writeToNBT(tagCompund: NBTTagCompound): Unit = if(inRegistry(EntityWriteToNBT)) EntityMethodHandler.handleMethodCall(this, EntityWriteToNBT, tagCompund) else super.writeToNBT(tagCompund)
    //override def readFromNBT(tagCompund: NBTTagCompound): Unit = if(inRegistry(EntityReadFromNBT)) EntityMethodHandler.handleMethodCall(this, EntityReadFromNBT, tagCompund) else super.readFromNBT(tagCompund)
    override def shouldSetPosAfterLoading(): Boolean = if(inRegistry(EntityShouldSetPosAfterLoading)) EntityMethodHandler.handleMethodCall(this, EntityShouldSetPosAfterLoading) else super.shouldSetPosAfterLoading()
    override def getEntityString(): String = if(inRegistry(EntityGetEntityString)) EntityMethodHandler.handleMethodCall(this, EntityGetEntityString) else super.getEntityString()
    override def readEntityFromNBT(tagCompund: NBTTagCompound): Unit = if(inRegistry(EntityReadEntityFromNBT)) EntityMethodHandler.handleMethodCall(this, EntityReadEntityFromNBT, tagCompund) else super.readEntityFromNBT(tagCompund)
    override def writeEntityToNBT(tagCompound: NBTTagCompound): Unit = if(inRegistry(EntityWriteEntityToNBT)) EntityMethodHandler.handleMethodCall(this, EntityWriteEntityToNBT, tagCompound) else super.writeEntityToNBT(tagCompound)
    override def onChunkLoad(): Unit = if(inRegistry(EntityOnChunkLoad)) EntityMethodHandler.handleMethodCall(this, EntityOnChunkLoad) else super.onChunkLoad()
    override def newDoubleNBTList(numbers: Double*): NBTTagList = if(inRegistry(EntityNewDoubleNBTList)) EntityMethodHandler.handleMethodCall(this, EntityNewDoubleNBTList, numbers) else super.newDoubleNBTList(numbers)
    override def newFloatNBTList(numbers: Float*): NBTTagList = if(inRegistry(EntityNewFloatNBTList)) EntityMethodHandler.handleMethodCall(this, EntityNewFloatNBTList, numbers) else super.newFloatNBTList(numbers)
    override def dropItem(itemIn: Item, size: Int): EntityItem = if(inRegistry(EntityDropItem_)) EntityMethodHandler.handleMethodCall(this, EntityDropItem_, itemIn, size) else super.dropItem(itemIn, size)
    override def dropItemWithOffset(itemIn: Item, size: Int, p_145778_3_ : Float): EntityItem = if(inRegistry(EntityDropItemWithOffset)) EntityMethodHandler.handleMethodCall(this, EntityDropItemWithOffset, itemIn, size, p_145778_3_) else super.dropItemWithOffset(itemIn, size, p_145778_3_)
    override def entityDropItem(itemStackIn: ItemStack, offsetY: Float): EntityItem = if(inRegistry(EntityEntityDropItem)) EntityMethodHandler.handleMethodCall(this, EntityEntityDropItem, itemStackIn, offsetY) else super.entityDropItem(itemStackIn, offsetY)
    override def getShadowSize(): Float = if(inRegistry(EntityGetShadowSize)) EntityMethodHandler.handleMethodCall(this, EntityGetShadowSize) else super.getShadowSize()
    override def isEntityAlive(): Boolean = if(inRegistry(EntityIsEntityAlive)) EntityMethodHandler.handleMethodCall(this, EntityIsEntityAlive) else super.isEntityAlive()
    override def isEntityInsideOpaqueBlock(): Boolean = if(inRegistry(EntityIsEntityInsideOpaqueBlock)) EntityMethodHandler.handleMethodCall(this, EntityIsEntityInsideOpaqueBlock) else super.isEntityInsideOpaqueBlock()
    override def interactFirst(player: EntityPlayer): Boolean = if(inRegistry(EntityInteractFirst)) EntityMethodHandler.handleMethodCall(this, EntityInteractFirst, player) else super.interactFirst(player)
    override def getCollisionBox(entityIn: Entity): AxisAlignedBB = if(inRegistry(EntityGetCollisionBox)) EntityMethodHandler.handleMethodCall(this, EntityGetCollisionBox, entityIn) else super.getCollisionBox(entityIn)
    override def updateRidden(): Unit = if(inRegistry(EntityUpdateRidden)) EntityMethodHandler.handleMethodCall(this, EntityUpdateRidden) else super.updateRidden()
    override def updateRiderPosition(): Unit = if(inRegistry(EntityUpdateRiderPosition)) EntityMethodHandler.handleMethodCall(this, EntityUpdateRiderPosition) else super.updateRiderPosition()
    override def getYOffset(): Double = if(inRegistry(EntityGetYOffset)) EntityMethodHandler.handleMethodCall(this, EntityGetYOffset) else super.getYOffset()
    override def getMountedYOffset(): Double = if(inRegistry(EntityGetMountedYOffset)) EntityMethodHandler.handleMethodCall(this, EntityGetMountedYOffset) else super.getMountedYOffset()
    override def mountEntity(entityIn: Entity): Unit = if(inRegistry(EntityMountEntity)) EntityMethodHandler.handleMethodCall(this, EntityMountEntity, entityIn) else super.mountEntity(entityIn)
    override def setPositionAndRotation2(x: Double, y: Double, z: Double, yaw: Float, pitch: Float, rotationIncrements: Int): Unit = if(inRegistry(EntitySetPositionAndRotation2)) EntityMethodHandler.handleMethodCall(this, EntitySetPositionAndRotation2, x, y, z, yaw, pitch, rotationIncrements) else super.setPositionAndRotation2(x, y, z, yaw, pitch, rotationIncrements)
    override def getCollisionBorderSize(): Float = if(inRegistry(EntityGetCollisionBorderSize)) EntityMethodHandler.handleMethodCall(this, EntityGetCollisionBorderSize) else super.getCollisionBorderSize()
    override def getLookVec(): Vec3 = if(inRegistry(EntityGetLookVec)) EntityMethodHandler.handleMethodCall(this, EntityGetLookVec) else super.getLookVec()
    override def setInPortal(): Unit = if(inRegistry(EntitySetInPortal)) EntityMethodHandler.handleMethodCall(this, EntitySetInPortal) else super.setInPortal()
    override def getPortalCooldown(): Int = if(inRegistry(EntityGetPortalCooldown)) EntityMethodHandler.handleMethodCall(this, EntityGetPortalCooldown) else super.getPortalCooldown()
    override def setVelocity(x: Double, y: Double, z: Double): Unit = if(inRegistry(EntitySetVelocity)) EntityMethodHandler.handleMethodCall(this, EntitySetVelocity, x, y, z) else super.setVelocity(x, y, z)
    override def handleHealthUpdate(p_70103_1_ : Byte): Unit = if(inRegistry(EntityHandleHealthUpdate)) EntityMethodHandler.handleMethodCall(this, EntityHandleHealthUpdate, p_70103_1_) else super.handleHealthUpdate(p_70103_1_)
    override def performHurtAnimation(): Unit = if(inRegistry(EntityPerformHurtAnimation)) EntityMethodHandler.handleMethodCall(this, EntityPerformHurtAnimation) else super.performHurtAnimation()
    override def getInventory(): Array[ItemStack] = if(inRegistry(EntityGetInventory)) EntityMethodHandler.handleMethodCall(this, EntityGetInventory) else super.getInventory()
    override def setCurrentItemOrArmor(slotIn: Int, itemStackIn: ItemStack): Unit = if(inRegistry(EntitySetCurrentItemOrArmor)) EntityMethodHandler.handleMethodCall(this, EntitySetCurrentItemOrArmor, slotIn, itemStackIn) else super.setCurrentItemOrArmor(slotIn, itemStackIn)
    override def isBurning(): Boolean = if(inRegistry(EntityIsBurning)) EntityMethodHandler.handleMethodCall(this, EntityIsBurning) else super.isBurning()
    override def isRiding(): Boolean = if(inRegistry(EntityIsRiding)) EntityMethodHandler.handleMethodCall(this, EntityIsRiding) else super.isRiding()
    override def isSneaking(): Boolean = if(inRegistry(EntityIsSneaking)) EntityMethodHandler.handleMethodCall(this, EntityIsSneaking) else super.isSneaking()
    override def setSneaking(sneaking: Boolean): Unit = if(inRegistry(EntitySetSneaking)) EntityMethodHandler.handleMethodCall(this, EntitySetSneaking, sneaking) else super.setSneaking(sneaking)
    override def isSprinting(): Boolean = if(inRegistry(EntityIsSprinting)) EntityMethodHandler.handleMethodCall(this, EntityIsSprinting) else super.isSprinting()
    override def setSprinting(sprinting: Boolean): Unit = if(inRegistry(EntitySetSprinting)) EntityMethodHandler.handleMethodCall(this, EntitySetSprinting, sprinting) else super.setSprinting(sprinting)
    override def isInvisible(): Boolean = if(inRegistry(EntityIsInvisible)) EntityMethodHandler.handleMethodCall(this, EntityIsInvisible) else super.isInvisible()
    override def isInvisibleToPlayer(player: EntityPlayer): Boolean = if(inRegistry(EntityIsInvisibleToPlayer)) EntityMethodHandler.handleMethodCall(this, EntityIsInvisibleToPlayer, player) else super.isInvisibleToPlayer(player)
    override def setInvisible(invisible: Boolean): Unit = if(inRegistry(EntitySetInvisible)) EntityMethodHandler.handleMethodCall(this, EntitySetInvisible, invisible) else super.setInvisible(invisible)
    override def isEating(): Boolean = if(inRegistry(EntityIsEating)) EntityMethodHandler.handleMethodCall(this, EntityIsEating) else super.isEating()
    override def setEating(eating: Boolean): Unit = if(inRegistry(EntitySetEating)) EntityMethodHandler.handleMethodCall(this, EntitySetEating, eating) else super.setEating(eating)
    override def getFlag(flag: Int): Boolean = if(inRegistry(EntityGetFlag)) EntityMethodHandler.handleMethodCall(this, EntityGetFlag, flag) else super.getFlag(flag)
    override def setFlag(flag: Int, set: Boolean): Unit = if(inRegistry(EntitySetFlag)) EntityMethodHandler.handleMethodCall(this, EntitySetFlag, flag, set) else super.setFlag(flag, set)
    override def getAir(): Int = if(inRegistry(EntityGetAir)) EntityMethodHandler.handleMethodCall(this, EntityGetAir) else super.getAir()
    override def setAir(air: Int): Unit = if(inRegistry(EntitySetAir)) EntityMethodHandler.handleMethodCall(this, EntitySetAir, air) else super.setAir(air)
    override def onStruckByLightning(lightningBolt: EntityLightningBolt): Unit = if(inRegistry(EntityOnStruckByLightning)) EntityMethodHandler.handleMethodCall(this, EntityOnStruckByLightning, lightningBolt) else super.onStruckByLightning(lightningBolt)
    override def onKillEntity(entityLivingIn: EntityLivingBase): Unit = if(inRegistry(EntityOnKillEntity)) EntityMethodHandler.handleMethodCall(this, EntityOnKillEntity, entityLivingIn) else super.onKillEntity(entityLivingIn)
    override def pushOutOfBlocks(x: Double, y: Double, z: Double): Boolean = if(inRegistry(EntityPushOutOfBlocks)) EntityMethodHandler.handleMethodCall(this, EntityPushOutOfBlocks, x, y, z) else super.pushOutOfBlocks(x, y, z)
    override def setInWeb(): Unit = if(inRegistry(EntitySetInWeb)) EntityMethodHandler.handleMethodCall(this, EntitySetInWeb) else super.setInWeb()
    override def getCommandSenderName(): String = if(inRegistry(EntityGetCommandSenderName)) EntityMethodHandler.handleMethodCall(this, EntityGetCommandSenderName) else super.getCommandSenderName()
    override def getParts(): Array[Entity] = if(inRegistry(EntityGetParts)) EntityMethodHandler.handleMethodCall(this, EntityGetParts) else super.getParts()
    override def isEntityEqual(entityIn: Entity): Boolean = if(inRegistry(EntityIsEntityEqual)) EntityMethodHandler.handleMethodCall(this, EntityIsEntityEqual, entityIn) else super.isEntityEqual(entityIn)
    override def getRotationYawHead(): Float = if(inRegistry(EntityGetRotationYawHead)) EntityMethodHandler.handleMethodCall(this, EntityGetRotationYawHead) else super.getRotationYawHead()
    override def setRotationYawHead(rotation: Float): Unit = if(inRegistry(EntitySetRotationYawHead)) EntityMethodHandler.handleMethodCall(this, EntitySetRotationYawHead, rotation) else super.setRotationYawHead(rotation)
    override def canAttackWithItem(): Boolean = if(inRegistry(EntityCanAttackWithItem)) EntityMethodHandler.handleMethodCall(this, EntityCanAttackWithItem) else super.canAttackWithItem()
    override def hitByEntity(entityIn: Entity): Boolean = if(inRegistry(EntityHitByEntity)) EntityMethodHandler.handleMethodCall(this, EntityHitByEntity, entityIn) else super.hitByEntity(entityIn)
    override def toString(): String = if(inRegistry(EntityToString)) EntityMethodHandler.handleMethodCall(this, EntityToString) else super.toString()
    override def isEntityInvulnerable(): Boolean = if(inRegistry(EntityIsEntityInvulnerable)) EntityMethodHandler.handleMethodCall(this, EntityIsEntityInvulnerable) else super.isEntityInvulnerable()
    override def copyLocationAndAnglesFrom(entityIn: Entity): Unit = if(inRegistry(EntityCopyLocationAndAnglesFrom)) EntityMethodHandler.handleMethodCall(this, EntityCopyLocationAndAnglesFrom, entityIn) else super.copyLocationAndAnglesFrom(entityIn)
    override def copyDataFrom(entityIn: Entity, unused: Boolean): Unit = if(inRegistry(EntityCopyDataFrom)) EntityMethodHandler.handleMethodCall(this, EntityCopyDataFrom, entityIn, unused) else super.copyDataFrom(entityIn, unused)
    override def travelToDimension(dimensionId: Int): Unit = if(inRegistry(EntityTravelToDimension)) EntityMethodHandler.handleMethodCall(this, EntityTravelToDimension, dimensionId) else super.travelToDimension(dimensionId)
    override def getExplosionResistance(explosionIn: Explosion, worldIn: World, x: Int, y: Int, z: Int, blockIn: Block): Float = if(inRegistry(EntityGetExplosionResistance)) EntityMethodHandler.handleMethodCall(this, EntityGetExplosionResistance, explosionIn, worldIn, x, y, z, blockIn) else super.getExplosionResistance(explosionIn, worldIn, x, y, z, blockIn)
    override def func_145774_a(explosionIn: Explosion, worldIn: World, x: Int, y: Int, z: Int, blockIn: Block, unused: Float): Boolean = if(inRegistry(EntityFunc_145774_a)) EntityMethodHandler.handleMethodCall(this, EntityFunc_145774_a, explosionIn, worldIn, x, y, z, blockIn, unused) else super.func_145774_a(explosionIn, worldIn, x, y, z, blockIn, unused)
    override def getMaxFallHeight(): Int = if(inRegistry(EntityGetMaxFallHeight)) EntityMethodHandler.handleMethodCall(this, EntityGetMaxFallHeight) else super.getMaxFallHeight()
    override def getTeleportDirection(): Int = if(inRegistry(EntityGetTeleportDirection)) EntityMethodHandler.handleMethodCall(this, EntityGetTeleportDirection) else super.getTeleportDirection()
    override def doesEntityNotTriggerPressurePlate(): Boolean = if(inRegistry(EntityDoesEntityNotTriggerPressurePlate)) EntityMethodHandler.handleMethodCall(this, EntityDoesEntityNotTriggerPressurePlate) else super.doesEntityNotTriggerPressurePlate()
    override def addEntityCrashInfo(category: CrashReportCategory): Unit = if(inRegistry(EntityAddEntityCrashInfo)) EntityMethodHandler.handleMethodCall(this, EntityAddEntityCrashInfo, category) else super.addEntityCrashInfo(category)
    override def canRenderOnFire(): Boolean = if(inRegistry(EntityCanRenderOnFire)) EntityMethodHandler.handleMethodCall(this, EntityCanRenderOnFire) else super.canRenderOnFire()
    override def getUniqueID(): UUID = if(inRegistry(EntityGetUniqueID)) EntityMethodHandler.handleMethodCall(this, EntityGetUniqueID) else super.getUniqueID()
    override def isPushedByWater(): Boolean = if(inRegistry(EntityIsPushedByWater)) EntityMethodHandler.handleMethodCall(this, EntityIsPushedByWater) else super.isPushedByWater()
    override def getFormattedCommandSenderName(): IChatComponent = if(inRegistry(EntityGetFormattedCommandSenderName)) EntityMethodHandler.handleMethodCall(this, EntityGetFormattedCommandSenderName) else super.getFormattedCommandSenderName()
    override def func_145781_i(p_145781_1_ : Int): Unit = if(inRegistry(EntityFunc_145781_i)) EntityMethodHandler.handleMethodCall(this, EntityFunc_145781_i, p_145781_1_) else super.func_145781_i(p_145781_1_)
    override def getEntityData(): NBTTagCompound = if(inRegistry(EntityGetEntityData)) EntityMethodHandler.handleMethodCall(this, EntityGetEntityData) else super.getEntityData()
    override def shouldRiderSit(): Boolean = if(inRegistry(EntityShouldRiderSit)) EntityMethodHandler.handleMethodCall(this, EntityShouldRiderSit) else super.shouldRiderSit()
    override def getPickedResult(target: MovingObjectPosition): ItemStack = if(inRegistry(EntityGetPickedResult)) EntityMethodHandler.handleMethodCall(this, EntityGetPickedResult, target) else super.getPickedResult(target)
    override def getPersistentID(): UUID = if(inRegistry(EntityGetPersistentID)) EntityMethodHandler.handleMethodCall(this, EntityGetPersistentID) else super.getPersistentID()
    override def resetEntityId(): Unit = if(inRegistry(EntityResetEntityId)) EntityMethodHandler.handleMethodCall(this, EntityResetEntityId) else super.resetEntityId()
    override def shouldRenderInPass(pass: Int): Boolean = if(inRegistry(EntityShouldRenderInPass)) EntityMethodHandler.handleMethodCall(this, EntityShouldRenderInPass, pass) else super.shouldRenderInPass(pass)
    override def isCreatureType(typ: EnumCreatureType, forSpawnCount: Boolean): Boolean = if(inRegistry(EntityIsCreatureType)) EntityMethodHandler.handleMethodCall(this, EntityIsCreatureType, typ, forSpawnCount) else super.isCreatureType(typ, forSpawnCount)
    override def registerExtendedProperties(identifier: String, properties: IExtendedEntityProperties): String = if(inRegistry(EntityRegisterExtendedProperties)) EntityMethodHandler.handleMethodCall(this, EntityRegisterExtendedProperties, identifier, properties) else super.registerExtendedProperties(identifier, properties)
    override def getExtendedProperties(identifier: String): IExtendedEntityProperties = if(inRegistry(EntityGetExtendedProperties)) EntityMethodHandler.handleMethodCall(this, EntityGetExtendedProperties, identifier) else super.getExtendedProperties(identifier)
    override def canRiderInteract(): Boolean = if(inRegistry(EntityCanRiderInteract)) EntityMethodHandler.handleMethodCall(this, EntityCanRiderInteract) else super.canRiderInteract()
    override def shouldDismountInWater(rider: Entity): Boolean = if(inRegistry(EntityShouldDismountInWater)) EntityMethodHandler.handleMethodCall(this, EntityShouldDismountInWater, rider) else super.shouldDismountInWater(rider)


    private def inRegistry(srgName: String): Boolean = {
        SoulHelper.entityMethodRegistry.getMethodBySrgName(srgName).nonEmpty
    }

    override def readFromNBT(compound: NBTTagCompound) = readFromNBT_I(compound)

    def readFromNBT_I(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        soul = new Soul(compound)

        if(shouldCallEntityInit) {
            entityInit()
            shouldCallEntityInit = false
        }

        if(compound.hasKey("data")) {
            syncLogic.readFromNBT(compound)
        }
        TraitHandler.readFromNBT(this, compound)
    }

    override def writeToNBT(compound: NBTTagCompound) = writeToNBT_I(compound)

    def writeToNBT_I(compound: NBTTagCompound) {
        super.writeToNBT(compound)
        soul.writeToNBT(compound)
        syncLogic.writeToNBT(compound)
        TraitHandler.writeToNBT(this, compound)
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

    override def setObject(name: String, variable: Any) = syncLogic.setObject(name, variable)

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

    override def getObject[T](name: String): T = syncLogic.getObject(name)

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


    override def callMethod[T](srgName: String, args: Any*): T = EntityMethodHandler.handleMethodCall(this, srgName, args)
}
