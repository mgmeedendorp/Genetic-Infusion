package seremis.geninfusion.soul.entity

import java.util.UUID

import io.netty.buffer.ByteBuf
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.crash.CrashReportCategory
import net.minecraft.entity._
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util._
import net.minecraft.world.{Explosion, World}
import net.minecraftforge.common.IExtendedEntityProperties
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData
import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.api.lib.reflection.FunctionLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ISoul, SoulHelper}
import seremis.geninfusion.api.util.DataWatcherHelper
import seremis.geninfusion.api.util.data.Data
import seremis.geninfusion.entity.GIEntity
import seremis.geninfusion.network.packet.PacketAddDataWatcherHelperMapping
import seremis.geninfusion.soul.entity.logic.VariableSyncLogic
import seremis.geninfusion.soul.{EntityMethodHandler, Soul}
import seremis.geninfusion.util.UtilNBT

trait EntitySoulCustomTrait extends EntityLiving with IEntitySoulCustom with IEntityAdditionalSpawnData with GIEntity {

    var syncLogic = new VariableSyncLogic(this)
    var soul: ISoul
    val world: World

    var shouldCallEntityInit = true

    override def writeSpawnData(data: ByteBuf) {
        val compound = new NBTTagCompound
        writeToNBT(compound)
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
        readFromNBT(compound)

        if(data.readerIndex() < data.writerIndex) {
            val size = data.readInt()

            for(i <- 0 until size) {
                val packet = new PacketAddDataWatcherHelperMapping()

                packet.fromBytes(data)
                PacketAddDataWatcherHelperMapping.handleMessage(packet, this)
            }
        }
    }

    override def getEntityId(): Int = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetEntityId, () => super.getEntityId)

        super.getEntityId()
    }

    override def setEntityId(id: Int): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetEntityId, () => super.setEntityId(id), id)

        super.setEntityId(id)
    }

    override def getDataWatcher(): DataWatcher = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetDataWatcher, () => super.getDataWatcher)

        super.getDataWatcher()
    }

    override def hashCode(): Int = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityHashCode, () => super.hashCode)

        super.hashCode()
    }

    override def setDead(): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetDead, () => super.setDead)

        super.setDead()
    }

    override def setSize(width: Float, height: Float) = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetSize, () => super.setSize(width, height), width, height)

        super.setSize(width, height)
    }

    override def setRotation(yaw: Float, pitch: Float): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetRotation, () => super.setRotation(yaw, pitch), yaw, pitch)

        super.setRotation(yaw, pitch)
    }

    override def setPosition(x: Double, y: Double, z: Double): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetPosition, () => super.setPosition(x, y, z), x, y, z)

        super.setPosition(x, y, z)
    }

    override def setAngles(yaw: Float, pitch: Float): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetAngles, () => super.setAngles(yaw, pitch), yaw, pitch)

        super.setAngles(yaw, pitch)
    }

    override def getMaxInPortalTime(): Int = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetMaxInPortalTime, () => super.getMaxInPortalTime())

        SoulHelper.geneRegistry.getValueFromAllele(this, Genes.GeneTeleportTimeInPortal)
    }

    override def setOnFireFromLava(): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetOnFireFromLava, () => super.setOnFireFromLava())

        super.setOnFireFromLava()
    }

    override def setFire(seconds: Int): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetFire, () => super.setFire(seconds), seconds)

        super.setFire(seconds)
    }

    override def extinguish(): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityExtinguish, () => super.extinguish())

        super.extinguish()
    }

    override def getSwimSound(): String = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetSwimSound, () => super.getSwimSound())

        super.getSwimSound()
    }

    override def getEntityBoundingBox(): AxisAlignedBB = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetBoundingBox, () => super.getEntityBoundingBox())

        super.getEntityBoundingBox()
    }

    override def isInWater(): Boolean = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityIsInWater, () => super.isInWater())

        super.isInWater()
    }

    override def getSplashSound(): String = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetSplashSound, () => super.getSplashSound())

        SoulHelper.geneRegistry.getValueFromAllele(this, Genes.GeneSplashSound)
    }

    override def getEyeHeight(): Float = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetEyeHeight, () => super.getEyeHeight())

        super.getEyeHeight()
    }

    override def setWorld(worldIn: World): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetWorld, () => super.setWorld(worldIn), worldIn)

        super.setWorld(worldIn)
    }

    override def setPositionAndRotation(x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetPositionAndRotation, () => super.setPositionAndRotation(x, y, z, yaw, pitch), x, y, z, yaw, pitch)

        super.setPositionAndRotation(x, y, z, yaw, pitch)
    }

    override def setPositionAndRotation2(x: Double, y: Double, z: Double, yaw: Float, pitch: Float, rotationIncrements: Int): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetPositionAndRotation2, () => super.setPositionAndRotation2(x, y, z, yaw, pitch, rotationIncrements), x, y, z, yaw, pitch, rotationIncrements)

        super.setPositionAndRotation2(x, y, z, yaw, pitch, rotationIncrements)
    }


    override def setLocationAndAngles(x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetLocationAndAngles, () => super.setLocationAndAngles(x, y, z, yaw, pitch), x, y, z, yaw, pitch)

        super.setLocationAndAngles(x, y, z, yaw, pitch)
    }

    override def setBeenAttacked(): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetBeenAttacked, () => super.setBeenAttacked())

        super.setBeenAttacked()
    }

    //TODO fix these
    override def dropItem(itemIn: Item, size: Int): EntityItem = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityDropItem, () => super.dropItem(itemIn, size), itemIn, size)

        super.dropItem(itemIn, size)
    }
    override def dropItemWithOffset(itemIn: Item, size: Int, p_145778_3_ : Float): EntityItem = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityDropItemWithOffset, () => super.dropItemWithOffset(itemIn, size, p_145778_3_), itemIn, size, p_145778_3_)

        super.dropItemWithOffset(itemIn, size, p_145778_3_)
    }
    override def entityDropItem(itemStackIn: ItemStack, offsetY: Float): EntityItem = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityEntityDropItem, () => super.entityDropItem(itemStackIn, offsetY), itemStackIn, offsetY)

        super.entityDropItem(itemStackIn, offsetY)
    }

    override def getCollisionBox(entityIn: Entity): AxisAlignedBB = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetCollisionBox, () => super.getCollisionBox(entityIn), entityIn)

        super.getCollisionBox(entityIn)
    }

    override def getYOffset(): Double = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetYOffset, () => super.getYOffset())

        super.getYOffset()
    }

    override def getLookVec(): Vec3 = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetLookVec, () => super.getLookVec())

        super.getLookVec()
    }

    override def getPortalCooldown(): Int = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetPortalCooldown, () => super.getPortalCooldown())

        super.getPortalCooldown()
    }
    override def setVelocity(x: Double, y: Double, z: Double): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetVelocity, () => super.setVelocity(x, y, z), x, y, z)

        super.setVelocity(x, y, z)
    }

    override def getInventory(): Array[ItemStack] = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetInventory, () => super.getInventory)

        super.getInventory()
    }

    override def setCurrentItemOrArmor(slotIn: Int, itemStackIn: ItemStack): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetCurrentItemOrArmor, () => super.setCurrentItemOrArmor(slotIn, itemStackIn), slotIn, itemStackIn)

        super.setCurrentItemOrArmor(slotIn, itemStackIn)
    }

    override def isBurning(): Boolean = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityIsBurning, () => super.isBurning())

        super.isBurning()
    }

    override def isRiding(): Boolean = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityIsRiding, () => super.isRiding())

        super.isRiding()
    }

    override def isSneaking(): Boolean = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityIsSneaking, () => super.isSneaking())

        super.isSneaking()
    }

    override def setSneaking(sneaking: Boolean): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetSneaking, () => super.setSneaking(sneaking), sneaking)

        super.setSneaking(sneaking)
    }

    override def isSprinting(): Boolean = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityIsSprinting, () => isSprinting())

        super.isSprinting()
    }

    override def setSprinting(sprinting: Boolean): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetSprinting, () => super.setSprinting(sprinting), sprinting)

        super.setSprinting(sprinting)
    }

    override def isInvisible(): Boolean = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityIsInvisible, () => super.isInvisible())

        super.isInvisible()
    }

    override def isInvisibleToPlayer(player: EntityPlayer): Boolean = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityIsInvisibleToPlayer, () => super.isInvisibleToPlayer(player), player)

        super.isInvisibleToPlayer(player)
    }

    override def setInvisible(invisible: Boolean): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetInvisible, () => super.setInvisible(invisible), invisible)

        super.setInvisible(invisible)
    }

    override def isEating(): Boolean = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityIsEating, () => super.isEating())

        super.isEating()
    }

    override def setEating(eating: Boolean): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetEating, () => super.setEating(eating), eating)

        super.setEating(eating)
    }

    override def getFlag(flag: Int): Boolean = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetFlag, () => super.getFlag(flag), flag)

        super.getFlag(flag)
    }

    override def setFlag(flag: Int, set: Boolean): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetFlag, () => super.setFlag(flag, set), flag, set)

        super.setFlag(flag, set)
    }

    override def getAir(): Int = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetAir, () => super.getAir())

        super.getAir()
    }

    override def setAir(air: Int): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetAir, () => super.setAir(air), air)

        super.setAir(air)
    }

    override def setInWeb(): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetInWeb, () => super.setInWeb())

        super.setInWeb()
    }

    override def getCommandSenderName(): String = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetCommandSenderName, () => super.getCommandSenderName())

        super.getCommandSenderName()
    }

    override def getRotationYawHead(): Float = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetRotationYawHead, () => super.getRotationYawHead())

        super.getRotationYawHead()
    }

    override def setRotationYawHead(rotation: Float): Unit = {
        EntityMethodHandler.handleMethodCall(this, FuncEntitySetRotationYawHead, () => super.setRotationYawHead(rotation), rotation)

        super.setRotationYawHead(rotation)
    }

    override def isEntityInvulnerable(): Boolean = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityIsEntityInvulnerable, () => super.isEntityInvulnerable())

        super.isEntityInvulnerable()
    }

    override def getTeleportDirection(): Int = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetTeleportDirection, () => super.getTeleportDirection())

        super.getTeleportDirection()
    }

    override def canRenderOnFire(): Boolean = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityCanRenderOnFire, () => super.canRenderOnFire())

        super.canRenderOnFire()
    }

    override def getUniqueID(): UUID = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetUniqueID, () => super.getUniqueID())

        super.getUniqueID()
    }

    override def isPushedByWater(): Boolean = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityIsPushedByWater, () => super.isPushedByWater())

        super.isPushedByWater()
    }

    override def getFormattedCommandSenderName(): IChatComponent = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetFormattedCommandSenderName, () => super.getFormattedCommandSenderName())

        super.getFormattedCommandSenderName()
    }

    override def getPersistentID(): UUID = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetPersistentID, () => super.getPersistentID())

        super.getPersistentID()
    }

    override def getExtendedProperties(identifier: String): IExtendedEntityProperties = {
        EntityMethodHandler.handleMethodCall(this, FuncEntityGetExtendedProperties, () => super.getExtendedProperties(identifier), identifier)

        super.getExtendedProperties(identifier)
    }





    override def entityInit(): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityEntityInit, () => super.entityInit())
    override def equals(p_equals_1_ : Object): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityEquals, () => super.equals(p_equals_1_), p_equals_1_)
    override def preparePlayerToSpawn(): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityPreparePlayerToSpawn, () => super.preparePlayerToSpawn())
    override def onUpdate(): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityOnUpdate, () => super.onUpdate())
    override def onEntityUpdate(): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityOnEntityUpdate, () => super.onEntityUpdate())
    override def kill(): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityKill, () => super.kill())
    override def isOffsetPositionInLiquid(x: Double, y: Double, z: Double): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityIsOffsetPositionInLiquid, () => super.isOffsetPositionInLiquid(x, y, z), x, y, z)
    override def moveEntity(x: Double, y: Double, z: Double): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityMoveEntity, () => super.moveEntity(x, y, z), x, y, z)
    override def doBlockCollisions(): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityDoBlockCollisions, () => super.doBlockCollisions())
    override def playStepSound(x: Int, y: Int, z: Int, blockIn: Block): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityPlayStepSound, () => super.playStepSound(x, y, z, blockIn), x, y, z, blockIn)
    override def playSound(name: String, volume: Float, pitch: Float): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityPlaySound, () => super.playSound(name, volume, pitch), name, volume, pitch)
    override def canTriggerWalking(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityCanTriggerWalking, () => super.canTriggerWalking())
    override def updateFallState(distanceFallenThisTick: Double, isOnGround: Boolean): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityUpdateFallState, () => super.updateFallState(distanceFallenThisTick, isOnGround), distanceFallenThisTick, isOnGround)
    override def dealFireDamage(amount: Int): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityDealFireDamage, () => super.dealFireDamage(amount), amount)
    override def fall(distance: Float): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityFall, () => super.fall(distance), distance)
    override def isWet(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityIsWet, () => super.isWet())
    override def handleWaterMovement(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityHandleWaterMovement, () => super.handleWaterMovement())
    override def handleLavaMovement(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityHandleLavaMovement, () => super.handleLavaMovement())
    override def isInsideOfMaterial(materialIn: Material): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityIsInsideOfMaterial, () => super.isInsideOfMaterial(materialIn), materialIn)
    override def moveFlying(strafe: Float, forward: Float, friction: Float): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityMoveFlying, () => super.moveFlying(strafe, forward, friction), strafe, forward, friction)
    override def getBrightnessForRender(p_70070_1_ : Float): Int = EntityMethodHandler.handleMethodCall(this, FuncEntityGetBrightnessForRender, () => super.getBrightnessForRender(p_70070_1_), p_70070_1_)
    override def getBrightness(p_70013_1_ : Float): Float = EntityMethodHandler.handleMethodCall(this, FuncEntityGetBrightness, () => super.getBrightness(p_70013_1_), p_70013_1_)
    override def getDistanceToEntity(entityIn: Entity): Float = EntityMethodHandler.handleMethodCall(this, FuncEntityGetDistanceToEntity, () => super.getDistanceToEntity(entityIn), entityIn)
    override def getDistanceSq(x: Double, y: Double, z: Double): Double = EntityMethodHandler.handleMethodCall(this, FuncEntityGetDistanceSq, () => super.getDistanceSq(x, y, z), x, y, z)
    override def getDistance(x: Double, y: Double, z: Double): Double = EntityMethodHandler.handleMethodCall(this, FuncEntityGetDistance, () => super.getDistance(x, y, z), x, y, z)
    override def getDistanceSqToEntity(entityIn: Entity): Double = EntityMethodHandler.handleMethodCall(this, FuncEntityGetDistanceSqToEntity, () => super.getDistanceSqToEntity(entityIn), entityIn)
    override def onCollideWithPlayer(entityIn: EntityPlayer): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityOnCollideWithPlayer, () => super.onCollideWithPlayer(entityIn), entityIn)
    override def applyEntityCollision(entityIn: Entity): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityApplyEntityCollision, () => super.applyEntityCollision(entityIn), entityIn)
    override def addVelocity(x: Double, y: Double, z: Double): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityAddVelocity, () => super.addVelocity(x, y, z), x, y, z)
    override def attackEntityFrom(source: DamageSource, amount: Float): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityAttackEntityFrom, () => super.attackEntityFrom(source, amount), source, amount)
    override def canBeCollidedWith(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityCanBeCollidedWith, () => super.canBeCollidedWith())
    override def canBePushed(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityCanBePushed, () => super.canBePushed())
    override def addToPlayerScore(entityIn: Entity, amount: Int): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityAddToPlayerScore, () => super.addToPlayerScore(entityIn, amount), entityIn, amount)
    override def isInRangeToRender3d(x: Double, y: Double, z: Double): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityIsInRangeToRender3d, () => super.isInRangeToRender3d(x, y, z), x, y, z)
    override def isInRangeToRenderDist(distance: Double): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityIsInRangeToRenderDist, () => super.isInRangeToRenderDist(distance), distance)
    override def writeMountToNBT(tagCompund: NBTTagCompound): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityWriteMountToNBT, () => super.writeMountToNBT(tagCompund), tagCompund)
    override def writeToNBTOptional(tagCompund: NBTTagCompound): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityWriteToNBTOptional, () => super.writeToNBTOptional(tagCompund), tagCompund)
    override def shouldSetPosAfterLoading(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityShouldSetPosAfterLoading, () => super.shouldSetPosAfterLoading())
    override def readEntityFromNBT(tagCompund: NBTTagCompound): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityReadEntityFromNBT, () => super.readEntityFromNBT(tagCompund), tagCompund)
    override def writeEntityToNBT(tagCompound: NBTTagCompound): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityWriteEntityToNBT, () => super.writeEntityToNBT(tagCompound), tagCompound)
    override def onChunkLoad(): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityOnChunkLoad, () => super.onChunkLoad())
    override def getShadowSize(): Float = EntityMethodHandler.handleMethodCall(this, FuncEntityGetShadowSize, () => super.getShadowSize())
    override def isEntityAlive(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityIsEntityAlive, () => super.isEntityAlive())
    override def isEntityInsideOpaqueBlock(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityIsEntityInsideOpaqueBlock, () => super.isEntityInsideOpaqueBlock())
    override def updateRidden(): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityUpdateRidden, () => updateRidden())
    override def updateRiderPosition(): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityUpdateRiderPosition, () => updateRiderPosition())
    override def getMountedYOffset(): Double = EntityMethodHandler.handleMethodCall(this, FuncEntityGetMountedYOffset, () => super.getMountedYOffset())
    override def mountEntity(entityIn: Entity): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityMountEntity, () => super.mountEntity(entityIn), entityIn)
    override def getCollisionBorderSize(): Float = EntityMethodHandler.handleMethodCall(this, FuncEntityGetCollisionBorderSize, () => super.getCollisionBorderSize())
    override def setInPortal(): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntitySetInPortal, () => super.setInPortal())
    override def handleHealthUpdate(p_70103_1_ : Byte): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityHandleHealthUpdate, () => super.handleHealthUpdate(p_70103_1_), p_70103_1_)
    override def performHurtAnimation(): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityPerformHurtAnimation, () => super.performHurtAnimation())
    override def onStruckByLightning(lightningBolt: EntityLightningBolt): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityOnStruckByLightning, () => super.onStruckByLightning(lightningBolt), lightningBolt)
    override def onKillEntity(entityLivingIn: EntityLivingBase): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityOnKillEntity, () => super.onKillEntity(entityLivingIn), entityLivingIn)
    override def pushOutOfBlocks(x: Double, y: Double, z: Double): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityPushOutOfBlocks, () => super.pushOutOfBlocks(x, y, z), x, y, z)
    override def getParts(): Array[Entity] = EntityMethodHandler.handleMethodCall(this, FuncEntityGetParts, () => super.getParts())
    override def isEntityEqual(entityIn: Entity): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityIsEntityEqual, () => super.isEntityEqual(entityIn), entityIn)
    override def canAttackWithItem(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityCanAttackWithItem, () => super.canAttackWithItem())
    override def hitByEntity(entityIn: Entity): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityHitByEntity, () => super.hitByEntity(entityIn), entityIn)
    override def toString(): String = EntityMethodHandler.handleMethodCall(this, FuncEntityToString, () => super.toString())
    override def copyLocationAndAnglesFrom(entityIn: Entity): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityCopyLocationAndAnglesFrom, () => super.copyLocationAndAnglesFrom(entityIn), entityIn)
    override def copyDataFrom(entityIn: Entity, unused: Boolean): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityCopyDataFrom, () => super.copyDataFrom(entityIn, unused), entityIn, unused)
    override def travelToDimension(dimensionId: Int): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityTravelToDimension, () => super.travelToDimension(dimensionId), dimensionId)
    override def getExplosionResistance(explosionIn: Explosion, worldIn: World, x: Int, y: Int, z: Int, blockIn: Block): Float = EntityMethodHandler.handleMethodCall(this, FuncEntityGetExplosionResistance, () => super.getExplosionResistance(explosionIn, worldIn, x, y, z, blockIn), explosionIn, worldIn, x, y, z, blockIn)
    override def func_145774_a(explosionIn: Explosion, worldIn: World, x: Int, y: Int, z: Int, blockIn: Block, unused: Float): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityFunc_145774_a, () => super.func_145774_a(explosionIn, worldIn, x, y, z, blockIn, unused), explosionIn, worldIn, x, y, z, blockIn, unused)
    override def getMaxFallHeight(): Int = EntityMethodHandler.handleMethodCall(this, FuncEntityGetMaxFallHeight, () => super.getMaxFallHeight())
    override def doesEntityNotTriggerPressurePlate(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityDoesEntityNotTriggerPressurePlate, () => super.doesEntityNotTriggerPressurePlate())
    override def addEntityCrashInfo(category: CrashReportCategory): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityAddEntityCrashInfo, () => super.addEntityCrashInfo(category), category)
    override def func_145781_i(p_145781_1_ : Int): Unit = EntityMethodHandler.handleMethodCall(this, FuncEntityFunc_145781_i, () => super.func_145781_i(p_145781_1_), p_145781_1_)
    override def getEntityData(): NBTTagCompound = EntityMethodHandler.handleMethodCall(this, FuncEntityGetEntityData, () => super.getEntityData())
    override def shouldRiderSit(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityShouldRiderSit, () => super.shouldRiderSit())
    override def getPickedResult(target: MovingObjectPosition): ItemStack = EntityMethodHandler.handleMethodCall(this, FuncEntityGetPickedResult, () => super.getPickedResult(target), target)
    override def shouldRenderInPass(pass: Int): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityShouldRenderInPass, () => super.shouldRenderInPass(pass), pass)
    override def isCreatureType(typ: EnumCreatureType, forSpawnCount: Boolean): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityIsCreatureType, () => super.isCreatureType(typ, forSpawnCount), typ, forSpawnCount)
    override def registerExtendedProperties(identifier: String, properties: IExtendedEntityProperties): String = EntityMethodHandler.handleMethodCall(this, FuncEntityRegisterExtendedProperties, () => super.registerExtendedProperties(identifier, properties), identifier, properties)
    override def canRiderInteract(): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityCanRiderInteract, () => super.canRiderInteract())
    override def shouldDismountInWater(rider: Entity): Boolean = EntityMethodHandler.handleMethodCall(this, FuncEntityShouldDismountInWater, () => super.shouldDismountInWater(rider), rider)

    override def attackEntityWithRangedAttack(p_82196_1_ : EntityLivingBase, p_82196_2_ : Float): Unit = EntityMethodHandler.handleMethodCall(this, FuncIRangedAttackMobAttackEntityWithRangedAttack, () => ())


    override def readFromNBT(compound: NBTTagCompound) {
        soul = new Soul(compound)

        if(shouldCallEntityInit) {
            entityInit()
            shouldCallEntityInit = false
        }

        if(compound.hasKey("data")) {
            syncLogic.readFromNBT(compound)
        }

        EntityMethodHandler.handleMethodCall(this, FuncEntityReadFromNBT, () => super.readFromNBT(compound))
    }

    override def writeToNBT(compound: NBTTagCompound) {
        soul.writeToNBT(compound)
        syncLogic.writeToNBT(compound)

        EntityMethodHandler.handleMethodCall(this, FuncEntityWriteToNBT, () => super.writeToNBT(compound))
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


    override def callMethod[T](srgName: String, args: Any*): T = {
        val superJavaMethod = super.getClass.getMethod(srgName, args.map(arg => arg.getClass):_*)
        val superMethod = () => superJavaMethod.invoke(this, args.map(arg => arg.asInstanceOf[AnyRef]):_*).asInstanceOf[T]
        //TODO test this

        EntityMethodHandler.handleMethodCall[T](this, srgName, superMethod, args)
    }
}
