package seremis.geninfusion.soul.entity

import java.util.Random

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import cpw.mods.fml.relauncher.{Side, SideOnly}
import io.netty.buffer.ByteBuf
import net.minecraft.client.renderer.entity.RenderLiving
import net.minecraft.entity._
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{AxisAlignedBB, ChunkCoordinates, DamageSource}
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import seremis.geninfusion.api.soul.lib.{VariableLib, Genes}
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ISoul, SoulHelper}
import seremis.geninfusion.api.util.data.Data
import seremis.geninfusion.soul.entity.logic.VariableSyncLogic
import seremis.geninfusion.soul.{Soul, TraitHandler}
import seremis.geninfusion.util.UtilNBT

trait EntitySoulCustomTrait extends EntityLiving with IEntitySoulCustom with IEntityAdditionalSpawnData {

    var syncLogic = new VariableSyncLogic(this)
    var soul: ISoul
    val world: World

    override def writeSpawnData(data: ByteBuf) {
        val compound = new NBTTagCompound
        writeToNBT_I(compound)
        val bytes = UtilNBT.compoundToByteArray(compound).getOrElse(return)
        data.writeShort(bytes.length.toShort)
        data.writeBytes(bytes)
    }

    override def readSpawnData(data: ByteBuf) {
        val length = data.readShort
        val bytes = new Array[Byte](length)

        data.readBytes(bytes)

        val compound: NBTTagCompound = UtilNBT.byteArrayToCompound(bytes).getOrElse(return)
        readFromNBT_I(compound)
    }

    override def getWorld_I: World = {
        worldObj
    }

    override def getBoundingBox = boundingBox
    override def getBoundingBox_I: AxisAlignedBB = boundingBox

    override def getEntityId_I: Int = super.getEntityId

    override def onDeathUpdate = onDeathUpdate_I
    override def onDeathUpdate_I = TraitHandler.onDeathUpdate(this)

    override def setFlag(id: Int, value: Boolean) = setFlag_I(id, value)
    override def setFlag_I(id: Int, value: Boolean) = super.setFlag(id, value)

    override def getFlag(id: Int) = getFlag_I(id)
    override def getFlag_I(id: Int): Boolean = super.getFlag(id)

    override def getRandom_I: Random = new Random()

    override def isChild: Boolean = TraitHandler.isChild(this)

    override def getTalkInterval: Int = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneTalkInterval)

    override def getDeathSound: String = getDeathSound_I
    override def getDeathSound_I: String = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneDeathSound)

    override def getLivingSound: String = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneLivingSound)

    override def getHurtSound: String = getHurtSound_I
    override def getHurtSound_I: String = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneHurtSound)

    override def getSplashSound: String = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneSplashSound)

    override def getSwimSound: String = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneSwimSound)

    override def getSoundVolume: Float = getSoundVolume_I
    override def getSoundVolume_I: Float = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneSoundVolume)

    override def getSoundPitch_I: Float = super.getSoundPitch

    override def applyEntityAttributes() {
        this.getAttributeMap.registerAttribute(SharedMonsterAttributes.maxHealth)
        this.getAttributeMap.registerAttribute(SharedMonsterAttributes.knockbackResistance)
        this.getAttributeMap.registerAttribute(SharedMonsterAttributes.movementSpeed)
        this.getAttributeMap.registerAttribute(SharedMonsterAttributes.attackDamage)
        this.getAttributeMap.registerAttribute(SharedMonsterAttributes.followRange)
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D)
    }

    override def onUpdate() {
        if(ForgeHooks.onLivingUpdate(this)) return
        if(getBoolean(VariableLib.EntityFirstUpdate)) {
            TraitHandler.firstTick(this)
            setBoolean(VariableLib.EntityFirstUpdate, false)
        }
        TraitHandler.entityUpdate(this)
    }

    override def onDeath(source: DamageSource) {
        if(ForgeHooks.onLivingDeath(this, source))
            return

        TraitHandler.entityDeath(this, source)
    }

    override def onKillEntity(entity: EntityLivingBase) {
        TraitHandler.onKillEntity(this, entity)
    }

    override def attackEntityFrom(source: DamageSource, damage: Float) = attackEntityFrom_I(source, damage)
    override def attackEntityFrom_I(source: DamageSource, damage: Float): Boolean = {
        if(ForgeHooks.onLivingAttack(this, source, damage)) return false
        TraitHandler.attackEntityFrom(this, source, damage)
    }

    override def attackEntity_I(entity: Entity, distance: Float) {
        TraitHandler.attackEntity(this, entity, distance)
    }

    override def damageEntity(source: DamageSource, damage: Float) = damageEntity_I(source, damage)
    override def damageEntity_I(source: DamageSource, damage: Float) {
        TraitHandler.damageEntity(this, source, damage)
    }

    override def onSpawnWithEgg(data: IEntityLivingData): IEntityLivingData = {
        TraitHandler.spawnEntityFromEgg(this, data)
    }

    override def playSound(name: String, volume: Float, pitch: Float) = playSound_I(name, volume, pitch)
    override def playSound_I(name: String, volume: Float, pitch: Float) {
        TraitHandler.playSoundAtEntity(this, name, volume, pitch)
    }

    override def updateAITick() = updateAITick_I()
    override def updateAITick_I() {
        TraitHandler.updateAITick(this)
    }

    override def interact(player: EntityPlayer): Boolean = {
        TraitHandler.interact(this, player)
    }

    override def applyEntityCollision(entity: Entity) {
        super.applyEntityCollision(entity)
    }

    override def decreaseAirSupply_I(amount: Int): Int = super.decreaseAirSupply(amount)

    override def func_110146_f_I(p_110146_1_ : Float, p_110146_2_ : Float): Float = super.func_110146_f(p_110146_1_, p_110146_2_)

    override def attackEntityAsMob(entity: Entity) = attackEntityAsMob_I(entity)
    override def attackEntityAsMob_I(entity: Entity): Boolean = TraitHandler.attackEntityAsMob(this, entity)

    override def isMovementCeased_I: Boolean = false

    override def findPlayerToAttack_I: Entity = TraitHandler.findPlayerToAttack(this)

    override def applyArmorCalculations(source: DamageSource, damage: Float) = applyArmorCalculations(source, damage)
    override def applyArmorCalculations_I(source: DamageSource, damage: Float): Float = TraitHandler.applyArmorCalculations(this, source, damage)

    override def applyPotionDamageCalculations(source: DamageSource, damage: Float) = applyPotionDamageCalculations_I(source, damage)
    override def applyPotionDamageCalculations_I(source: DamageSource, damage: Float): Float = TraitHandler.applyPotionDamageCalculations(this, source, damage)

    override def damageArmor(damage: Float) = damageArmor_I(damage)
    override def damageArmor_I(damage: Float) = TraitHandler.damageArmor(this, damage)

    override def setOnFireFromLava = setOnFireFromLava_I
    override def setOnFireFromLava_I = TraitHandler.setOnFireFromLava(this)

    override def getBlockPathWeight_I(x: Int, y: Int, z: Int): Float = TraitHandler.getBlockPathWeight(this, x, y, z)

    override def updateEntityActionState = updateEntityActionState_I
    override def updateEntityActionState_I = TraitHandler.updateEntityActionState(this)

    override def updateWanderPath_I = TraitHandler.updateWanderPath(this)

    override def canDespawn = canDespawn_I
    override def canDespawn_I = super.canDespawn

    override def setBeenAttacked = setBeenAttacked_I
    override def setBeenAttacked_I = super.setBeenAttacked()

    override def updatePotionEffects = updatePotionEffects_I
    override def updatePotionEffects_I = super.updatePotionEffects()

    override def jump = jump_I
    override def jump_I = super.jump()

    override def collideWithNearbyEntities = collideWithNearbyEntities_I
    override def collideWithNearbyEntities_I = super.collideWithNearbyEntities()

    override def isWithinHomeDistance_I(x: Int, y: Int, z: Int): Boolean = TraitHandler.isWithinHomeDistance(this, x, y, z)

    override def isWithinHomeDistanceCurrentPosition_I: Boolean = TraitHandler.isWithinHomeDistanceCurrentPosition(this)

    override def getHomePosition_I: ChunkCoordinates = TraitHandler.getHomePosition(this)

    override def hasHome_I: Boolean = TraitHandler.hasHome(this)

    override def getMaxHomeDistance_I: Float = TraitHandler.getMaxHomeDistance(this)

    override def detachHome_I = TraitHandler.detachHome(this)

    override def setHomeArea_I(x: Int, y: Int, z: Int, maxDistance: Int) = TraitHandler.setHomeArea(this, x, y, z, maxDistance)

    override def attackEntityWithRangedAttack(entity: EntityLivingBase, distanceModified: Float) = TraitHandler.attackEntityWithRangedAttack(this, entity, distanceModified)
    override def onStruckByLightning(lightning: EntityLightningBolt) = TraitHandler.onStruckByLightning(this, lightning)

    override def despawnEntity_I = super.despawnEntity()

    override def setRotation_I(rotationYaw: Float, rotationPitch: Float) = super.setRotation(rotationYaw, rotationPitch)

    override def updateArmSwingProgress_I = super.updateArmSwingProgress()

    override def isAIEnabled: Boolean = SoulHelper.geneRegistry.getValueFromAllele(this, Genes.GeneUseNewAI)

    override def dealFireDamage(damage: Int) = dealFireDamage_I(damage)
    override def dealFireDamage_I(damage: Int) = super.dealFireDamage(damage)

    var renderer: RenderLiving = null

    @SideOnly(Side.CLIENT)
    override def getEntityRender_I: RenderLiving = renderer

    @SideOnly(Side.CLIENT)
    override def setEntityRender_I(render: RenderLiving) = renderer = render

    override def setDead() = TraitHandler.setDead(this)

    override def getCustomNameTag = getCustomNameTag_I
    override def getCustomNameTag_I: String = TraitHandler.getCustomNameTag(this)

    override def setCustomNameTag(nameTag: String) = setCustomNameTag_I(nameTag)
    override def setCustomNameTag_I(nameTag: String) = TraitHandler.setCustomNameTag(this, nameTag)

    override def hasCustomNameTag = hasCustomNameTag_I
    override def hasCustomNameTag_I: Boolean = TraitHandler.hasCustomNameTag(this)

    //TODO tamed
    override def isTamed_I: Boolean = false

    //TODO fix this
    //override def setSize(width: Float, height: Float) = setSize_I(width, height)
    override def setSize_I(width: Float, height: Float) = TraitHandler.setSize(this, width, height)

    override def getEntityTexture_I = TraitHandler.getEntityTexture(this)

    override def getEntityData_I = super.getEntityData

    override def getExperiencePoints(player: EntityPlayer): Int = getExperiencePoints_I(player)
    override def getExperiencePoints_I(player: EntityPlayer): Int = TraitHandler.getExperiencePoints(this, player)

    override def createChild_I(ageable: EntityAgeable): EntityAgeable = TraitHandler.createChild(this, ageable)

    override def setScale_I(scale: Float) = TraitHandler.setScale(this, scale)

    override def setScaleForAge_I(isChild: Boolean) = TraitHandler.setScaleForAge(this, isChild)

    override def getGrowingAge_I: Int = TraitHandler.getGrowingAge(this)

    override def setGrowingAge_I(growingAge: Int) = TraitHandler.setGrowingAge(this, growingAge)

    override def addGrowth_I(growingAge: Int) = TraitHandler.addGrowth(this, growingAge)

    override def getDataWatcher_I = getDataWatcher

    override def readFromNBT(compound: NBTTagCompound) = readFromNBT_I(compound)

    def readFromNBT_I(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        soul = new Soul(compound)
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
}
