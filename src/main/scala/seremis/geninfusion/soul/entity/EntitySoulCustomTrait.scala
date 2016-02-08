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
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ISoul}
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

    override def getEntityId(): Int = EntityMethodHandler.handleMethodCall(this, EntityGetEntityId)
    override def setEntityId(id : Int): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetEntityId, id)
    override def entityInit(): Unit = EntityMethodHandler.handleMethodCall(this, EntityEntityInit)
    override def getDataWatcher(): DataWatcher = EntityMethodHandler.handleMethodCall(this, EntityGetDataWatcher)
    override def equals(p_equals_1_ : Object): Boolean = EntityMethodHandler.handleMethodCall(this, EntityEquals, p_equals_1_)
    override def hashCode(): Int = EntityMethodHandler.handleMethodCall(this, EntityHashCode)
    override def preparePlayerToSpawn(): Unit = EntityMethodHandler.handleMethodCall(this, EntityPreparePlayerToSpawn)
    override def setDead(): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetDead)
    override def setSize(width : Float, height : Float): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetSize, width, height)
    override def setRotation(yaw : Float, pitch : Float): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetRotation, yaw, pitch)
    override def setPosition(x : Double, y : Double, z : Double): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetPosition, x, y, z)
    override def setAngles(yaw : Float, pitch : Float): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetAngles, yaw, pitch)
    override def onUpdate(): Unit = EntityMethodHandler.handleMethodCall(this, EntityOnUpdate)
    override def onEntityUpdate(): Unit = EntityMethodHandler.handleMethodCall(this, EntityOnEntityUpdate)
    override def getMaxInPortalTime(): Int = EntityMethodHandler.handleMethodCall(this, EntityGetMaxInPortalTime)
    override def setOnFireFromLava(): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetOnFireFromLava)
    override def setFire(seconds : Int): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetFire, seconds)
    override def extinguish(): Unit = EntityMethodHandler.handleMethodCall(this, EntityExtinguish)
    override def kill(): Unit = EntityMethodHandler.handleMethodCall(this, EntityKill)
    override def isOffsetPositionInLiquid(x : Double, y : Double, z : Double): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsOffsetPositionInLiquid, x, y, z)
    override def moveEntity(x : Double, y : Double, z : Double): Unit = EntityMethodHandler.handleMethodCall(this, EntityMoveEntity, x, y, z)
    override def getSwimSound(): String = EntityMethodHandler.handleMethodCall(this, EntityGetSwimSound)
    override def doBlockCollisions(): Unit = EntityMethodHandler.handleMethodCall(this, EntityDoBlockCollisions)
    override def playStepSound(x : Int, y : Int, z : Int, blockIn : Block): Unit = EntityMethodHandler.handleMethodCall(this, EntityPlayStepSound, x, y, z, blockIn)
    override def playSound(name : String, volume : Float, pitch : Float): Unit = EntityMethodHandler.handleMethodCall(this, EntityPlaySound, name, volume, pitch)
    override def canTriggerWalking(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityCanTriggerWalking)
    override def updateFallState(distanceFallenThisTick : Double, isOnGround : Boolean): Unit = EntityMethodHandler.handleMethodCall(this, EntityUpdateFallState, distanceFallenThisTick, isOnGround)
    override def getBoundingBox(): AxisAlignedBB = EntityMethodHandler.handleMethodCall(this, EntityGetBoundingBox)
    override def dealFireDamage(amount : Int): Unit = EntityMethodHandler.handleMethodCall(this, EntityDealFireDamage, amount)
    override def isImmuneToFire(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsImmuneToFire)
    override def fall(distance : Float): Unit = EntityMethodHandler.handleMethodCall(this, EntityFall, distance)
    override def isWet(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsWet)
    override def isInWater(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsInWater)
    override def handleWaterMovement(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityHandleWaterMovement)
    override def getSplashSound(): String = EntityMethodHandler.handleMethodCall(this, EntityGetSplashSound)
    override def isInsideOfMaterial(materialIn : Material): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsInsideOfMaterial, materialIn)
    override def getEyeHeight(): Float = EntityMethodHandler.handleMethodCall(this, EntityGetEyeHeight)
    override def handleLavaMovement(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityHandleLavaMovement)
    override def moveFlying(strafe : Float, forward : Float, friction : Float): Unit = EntityMethodHandler.handleMethodCall(this, EntityMoveFlying, strafe, forward, friction)
    override def getBrightnessForRender(p_70070_1_ : Float): Int = EntityMethodHandler.handleMethodCall(this, EntityGetBrightnessForRender, p_70070_1_)
    override def getBrightness(p_70013_1_ : Float): Float = EntityMethodHandler.handleMethodCall(this, EntityGetBrightness, p_70013_1_)
    override def setWorld(worldIn : World): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetWorld, worldIn)
    override def setPositionAndRotation(x : Double, y : Double, z : Double, yaw : Float, pitch : Float): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetPositionAndRotation, x, y, z, yaw, pitch)
    override def setLocationAndAngles(x : Double, y : Double, z : Double, yaw : Float, pitch : Float): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetLocationAndAngles, x, y, z, yaw, pitch)
    override def getDistanceToEntity(entityIn : Entity): Float = EntityMethodHandler.handleMethodCall(this, EntityGetDistanceToEntity, entityIn)
    override def getDistanceSq(x : Double, y : Double, z : Double): Double = EntityMethodHandler.handleMethodCall(this, EntityGetDistanceSq, x, y, z)
    override def getDistance(x : Double, y : Double, z : Double): Double = EntityMethodHandler.handleMethodCall(this, EntityGetDistance, x, y, z)
    override def getDistanceSqToEntity(entityIn : Entity): Double = EntityMethodHandler.handleMethodCall(this, EntityGetDistanceSqToEntity, entityIn)
    override def onCollideWithPlayer(entityIn : EntityPlayer): Unit = EntityMethodHandler.handleMethodCall(this, EntityOnCollideWithPlayer, entityIn)
    override def applyEntityCollision(entityIn : Entity): Unit = EntityMethodHandler.handleMethodCall(this, EntityApplyEntityCollision, entityIn)
    override def addVelocity(x : Double, y : Double, z : Double): Unit = EntityMethodHandler.handleMethodCall(this, EntityAddVelocity, x, y, z)
    override def setBeenAttacked(): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetBeenAttacked)
    override def attackEntityFrom(source : DamageSource, amount : Float): Boolean = EntityMethodHandler.handleMethodCall(this, EntityAttackEntityFrom, source, amount)
    override def canBeCollidedWith(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityCanBeCollidedWith)
    override def canBePushed(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityCanBePushed)
    override def addToPlayerScore(entityIn : Entity, amount : Int): Unit = EntityMethodHandler.handleMethodCall(this, EntityAddToPlayerScore, entityIn, amount)
    override def isInRangeToRender3d(x : Double, y : Double, z : Double): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsInRangeToRender3d, x, y, z)
    override def isInRangeToRenderDist(distance : Double): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsInRangeToRenderDist, distance)
    override def writeMountToNBT(tagCompund : NBTTagCompound): Boolean = EntityMethodHandler.handleMethodCall(this, EntityWriteMountToNBT, tagCompund)
    override def writeToNBTOptional(tagCompund : NBTTagCompound): Boolean = EntityMethodHandler.handleMethodCall(this, EntityWriteToNBTOptional, tagCompund)
   // override def writeToNBT(tagCompund : NBTTagCompound): Unit = EntityMethodHandler.handleMethodCall(this, EntityWriteToNBT, tagCompund)
   // override def readFromNBT(tagCompund : NBTTagCompound): Unit = EntityMethodHandler.handleMethodCall(this, EntityReadFromNBT, tagCompund)
    override def shouldSetPosAfterLoading(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityShouldSetPosAfterLoading)
    override def getEntityString(): String = EntityMethodHandler.handleMethodCall(this, EntityGetEntityString)
    override def readEntityFromNBT(tagCompund : NBTTagCompound): Unit = EntityMethodHandler.handleMethodCall(this, EntityReadEntityFromNBT, tagCompund)
    override def writeEntityToNBT(tagCompound : NBTTagCompound): Unit = EntityMethodHandler.handleMethodCall(this, EntityWriteEntityToNBT, tagCompound)
    override def onChunkLoad(): Unit = EntityMethodHandler.handleMethodCall(this, EntityOnChunkLoad)
    override def newDoubleNBTList(numbers : Double*): NBTTagList = EntityMethodHandler.handleMethodCall(this, EntityNewDoubleNBTList, numbers)
    override def newFloatNBTList(numbers : Float*): NBTTagList = EntityMethodHandler.handleMethodCall(this, EntityNewFloatNBTList, numbers)
    override def dropItem(itemIn : Item, size : Int): EntityItem = EntityMethodHandler.handleMethodCall(this, EntityDropItem, itemIn, size)
    override def dropItemWithOffset(itemIn : Item, size : Int, p_145778_3_ : Float): EntityItem = EntityMethodHandler.handleMethodCall(this, EntityDropItemWithOffset, itemIn, size, p_145778_3_)
    override def entityDropItem(itemStackIn : ItemStack, offsetY : Float): EntityItem = EntityMethodHandler.handleMethodCall(this, EntityEntityDropItem, itemStackIn, offsetY)
    override def getShadowSize(): Float = EntityMethodHandler.handleMethodCall(this, EntityGetShadowSize)
    override def isEntityAlive(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsEntityAlive)
    override def isEntityInsideOpaqueBlock(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsEntityInsideOpaqueBlock)
    override def interactFirst(player : EntityPlayer): Boolean = EntityMethodHandler.handleMethodCall(this, EntityInteractFirst, player)
    override def getCollisionBox(entityIn : Entity): AxisAlignedBB = EntityMethodHandler.handleMethodCall(this, EntityGetCollisionBox, entityIn)
    override def updateRidden(): Unit = EntityMethodHandler.handleMethodCall(this, EntityUpdateRidden)
    override def updateRiderPosition(): Unit = EntityMethodHandler.handleMethodCall(this, EntityUpdateRiderPosition)
    override def getYOffset(): Double = EntityMethodHandler.handleMethodCall(this, EntityGetYOffset)
    override def getMountedYOffset(): Double = EntityMethodHandler.handleMethodCall(this, EntityGetMountedYOffset)
    override def mountEntity(entityIn : Entity): Unit = EntityMethodHandler.handleMethodCall(this, EntityMountEntity, entityIn)
    override def setPositionAndRotation2(x : Double, y : Double, z : Double, yaw : Float, pitch : Float, rotationIncrements : Int): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetPositionAndRotation2, x, y, z, yaw, pitch, rotationIncrements)
    override def getCollisionBorderSize(): Float = EntityMethodHandler.handleMethodCall(this, EntityGetCollisionBorderSize)
    override def getLookVec(): Vec3 = EntityMethodHandler.handleMethodCall(this, EntityGetLookVec)
    override def setInPortal(): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetInPortal)
    override def getPortalCooldown(): Int = EntityMethodHandler.handleMethodCall(this, EntityGetPortalCooldown)
    override def setVelocity(x : Double, y : Double, z : Double): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetVelocity, x, y, z)
    override def handleHealthUpdate(p_70103_1_ : Byte): Unit = EntityMethodHandler.handleMethodCall(this, EntityHandleHealthUpdate, p_70103_1_)
    override def performHurtAnimation(): Unit = EntityMethodHandler.handleMethodCall(this, EntityPerformHurtAnimation)
    override def getInventory(): Array[ItemStack] = EntityMethodHandler.handleMethodCall(this, EntityGetInventory)
    override def setCurrentItemOrArmor(slotIn : Int, itemStackIn : ItemStack): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetCurrentItemOrArmor, slotIn, itemStackIn)
    override def isBurning(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsBurning)
    override def isRiding(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsRiding)
    override def isSneaking(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsSneaking)
    override def setSneaking(sneaking : Boolean): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetSneaking, sneaking)
    override def isSprinting(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsSprinting)
    override def setSprinting(sprinting : Boolean): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetSprinting, sprinting)
    override def isInvisible(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsInvisible)
    override def isInvisibleToPlayer(player : EntityPlayer): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsInvisibleToPlayer, player)
    override def setInvisible(invisible : Boolean): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetInvisible, invisible)
    override def isEating(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsEating)
    override def setEating(eating : Boolean): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetEating, eating)
    override def getFlag(flag : Int): Boolean = EntityMethodHandler.handleMethodCall(this, EntityGetFlag, flag)
    override def setFlag(flag : Int, set : Boolean): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetFlag, flag, set)
    override def getAir(): Int = EntityMethodHandler.handleMethodCall(this, EntityGetAir)
    override def setAir(air : Int): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetAir, air)
    override def onStruckByLightning(lightningBolt : EntityLightningBolt): Unit = EntityMethodHandler.handleMethodCall(this, EntityOnStruckByLightning, lightningBolt)
    override def onKillEntity(entityLivingIn : EntityLivingBase): Unit = EntityMethodHandler.handleMethodCall(this, EntityOnKillEntity, entityLivingIn)
    override def pushOutOfBlocks(x : Double, y : Double, z : Double): Boolean = EntityMethodHandler.handleMethodCall(this, EntityPushOutOfBlocks, x, y, z)
    override def setInWeb(): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetInWeb)
    override def getCommandSenderName(): String = EntityMethodHandler.handleMethodCall(this, EntityGetCommandSenderName)
    override def getParts(): Array[Entity] = EntityMethodHandler.handleMethodCall(this, EntityGetParts)
    override def isEntityEqual(entityIn : Entity): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsEntityEqual, entityIn)
    override def getRotationYawHead(): Float = EntityMethodHandler.handleMethodCall(this, EntityGetRotationYawHead)
    override def setRotationYawHead(rotation : Float): Unit = EntityMethodHandler.handleMethodCall(this, EntitySetRotationYawHead, rotation)
    override def canAttackWithItem(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityCanAttackWithItem)
    override def hitByEntity(entityIn : Entity): Boolean = EntityMethodHandler.handleMethodCall(this, EntityHitByEntity, entityIn)
    override def toString(): String = EntityMethodHandler.handleMethodCall(this, EntityToString)
    override def isEntityInvulnerable(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsEntityInvulnerable)
    override def copyLocationAndAnglesFrom(entityIn : Entity): Unit = EntityMethodHandler.handleMethodCall(this, EntityCopyLocationAndAnglesFrom, entityIn)
    override def copyDataFrom(entityIn : Entity, unused : Boolean): Unit = EntityMethodHandler.handleMethodCall(this, EntityCopyDataFrom, entityIn, unused)
    override def travelToDimension(dimensionId : Int): Unit = EntityMethodHandler.handleMethodCall(this, EntityTravelToDimension, dimensionId)
    override def getExplosionResistance(explosionIn : Explosion, worldIn : World, x : Int, y : Int, z : Int, blockIn : Block): Float = EntityMethodHandler.handleMethodCall(this, EntityGetExplosionResistance, explosionIn, worldIn, x, y, z, blockIn)
    override def func_145774_a(explosionIn : Explosion, worldIn : World, x : Int, y : Int, z : Int, blockIn : Block, unused : Float): Boolean = EntityMethodHandler.handleMethodCall(this, EntityFunc_145774_a, explosionIn, worldIn, x, y, z, blockIn, unused)
    override def getMaxFallHeight(): Int = EntityMethodHandler.handleMethodCall(this, EntityGetMaxFallHeight)
    override def getTeleportDirection(): Int = EntityMethodHandler.handleMethodCall(this, EntityGetTeleportDirection)
    override def doesEntityNotTriggerPressurePlate(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityDoesEntityNotTriggerPressurePlate)
    override def addEntityCrashInfo(category : CrashReportCategory): Unit = EntityMethodHandler.handleMethodCall(this, EntityAddEntityCrashInfo, category)
    override def canRenderOnFire(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityCanRenderOnFire)
    override def getUniqueID(): UUID = EntityMethodHandler.handleMethodCall(this, EntityGetUniqueID)
    override def isPushedByWater(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsPushedByWater)
    override def getFormattedCommandSenderName(): IChatComponent = EntityMethodHandler.handleMethodCall(this, EntityGetFormattedCommandSenderName)
    override def func_145781_i(p_145781_1_ : Int): Unit = EntityMethodHandler.handleMethodCall(this, EntityFunc_145781_i, p_145781_1_)
    override def getEntityData(): NBTTagCompound = EntityMethodHandler.handleMethodCall(this, EntityGetEntityData)
    override def shouldRiderSit(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityShouldRiderSit)
    override def getPickedResult(target : MovingObjectPosition): ItemStack = EntityMethodHandler.handleMethodCall(this, EntityGetPickedResult, target)
    override def getPersistentID(): UUID = EntityMethodHandler.handleMethodCall(this, EntityGetPersistentID)
    override def resetEntityId(): Unit = EntityMethodHandler.handleMethodCall(this, EntityResetEntityId)
    override def shouldRenderInPass(pass : Int): Boolean = EntityMethodHandler.handleMethodCall(this, EntityShouldRenderInPass, pass)
    override def isCreatureType(typ : EnumCreatureType, forSpawnCount : Boolean): Boolean = EntityMethodHandler.handleMethodCall(this, EntityIsCreatureType, typ, forSpawnCount)
    override def registerExtendedProperties(identifier : String, properties : IExtendedEntityProperties): String = EntityMethodHandler.handleMethodCall(this, EntityRegisterExtendedProperties, identifier, properties)
    override def getExtendedProperties(identifier : String): IExtendedEntityProperties = EntityMethodHandler.handleMethodCall(this, EntityGetExtendedProperties, identifier)
    override def canRiderInteract(): Boolean = EntityMethodHandler.handleMethodCall(this, EntityCanRiderInteract)
    override def shouldDismountInWater(rider : Entity): Boolean = EntityMethodHandler.handleMethodCall(this, EntityShouldDismountInWater, rider)



    override def readFromNBT(compound: NBTTagCompound) = readFromNBT_I(compound)

    def readFromNBT_I(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        soul = new Soul(compound)

        if(shouldCallEntityInit) {
            entityInit_I
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

    override def getObject(name: String): Any = syncLogic.getObject(name)

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
