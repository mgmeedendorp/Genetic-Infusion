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
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.util.data.Data
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ISoul, SoulHelper}
import seremis.geninfusion.soul.entity.logic.VariableSyncLogic
import seremis.geninfusion.soul.{Soul, TraitHandler}
import seremis.geninfusion.util.UtilNBT

trait EntitySoulCustomTrait extends EntityLiving with IEntitySoulCustom with IEntityAdditionalSpawnData {

    var syncLogic = new VariableSyncLogic(this)
    var soul: ISoul
    val world: World

    //isDead = true

    override def writeSpawnData(data: ByteBuf) {
        val compound = new NBTTagCompound
        writeToNBT(compound)
        val bytes = UtilNBT.compoundToByteArray(compound).getOrElse(return)
        data.writeShort(bytes.length.toShort)
        data.writeBytes(bytes)
    }

    override def readSpawnData(data: ByteBuf) {
        val length = data.readShort
        val bytes = new Array[Byte](length)

        data.readBytes(bytes)

        var compound: NBTTagCompound = UtilNBT.byteArrayToCompound(bytes).getOrElse(return)
        readFromNBT(compound)
    }

    override def getWorld: World = {
        worldObj
    }

    override def getBoundingBox: AxisAlignedBB = boundingBox

    override def getEntityId: Int = super.getEntityId

    override def onDeathUpdate = super.onDeathUpdate()

    override def setFlag(id: Int, value: Boolean) = super.setFlag(id, value)

    override def getFlag(id: Int): Boolean = super.getFlag(id)

    override def getRandom: Random = new Random()

    //TODO
    //override def isChild: Boolean = getBoolean("isChild")

    override def getTalkInterval: Int = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneTalkInterval)

    override def getDeathSound: String = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneDeathSound)

    override def getLivingSound: String = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneLivingSound)

    override def getHurtSound: String = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneHurtSound)

    override def getSplashSound: String = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneSplashSound)

    override def getSwimSound: String = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneSwimSound)

    override def getSoundVolume: Float = SoulHelper.geneRegistry.getValueFromAllele(this.asInstanceOf[IEntitySoulCustom], Genes.GeneSoundVolume)

    override def getSoundPitch: Float = super.getSoundPitch

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
        if(ForgeHooks.onLivingUpdate(this)) return
        if(firstUpdate) {
            firstUpdate = false
            TraitHandler.firstTick(this)
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

    override def attackEntityFrom(source: DamageSource, damage: Float): Boolean = {
        if(ForgeHooks.onLivingAttack(this, source, damage)) return false
        TraitHandler.attackEntityFrom(this, source, damage)
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

    override def updateAITick {
        TraitHandler.updateAITick(this)
    }

    override def interact(player: EntityPlayer): Boolean = {
        TraitHandler.interact(this, player)
    }

    override def applyEntityCollision(entity: Entity) {
        super.applyEntityCollision(entity)
    }

    override def decreaseAirSupply(amount: Int): Int = super.decreaseAirSupply(amount)

    override def func_110146_f(p_110146_1_ : Float, p_110146_2_ : Float): Float = super.func_110146_f(p_110146_1_, p_110146_2_)

    override def attackEntityAsMob(entity: Entity): Boolean = TraitHandler.attackEntityAsMob(this, entity)

    override def isMovementCeased: Boolean = false

    override def findPlayerToAttack: Entity = TraitHandler.findPlayerToAttack(this)

    override def applyArmorCalculations(source: DamageSource, damage: Float): Float = TraitHandler.applyArmorCalculations(this, source, damage)

    override def applyPotionDamageCalculations(source: DamageSource, damage: Float): Float = TraitHandler.applyPotionDamageCalculations(this, source, damage)

    override def damageArmor(damage: Float) = TraitHandler.damageArmor(this, damage)

    override def setOnFireFromLava = TraitHandler.setOnFireFromLava(this)

    override def getBlockPathWeight(x: Int, y: Int, z: Int): Float = TraitHandler.getBlockPathWeight(this, x, y, z)

    override def updateEntityActionState = TraitHandler.updateEntityActionState(this)

    override def updateWanderPath = TraitHandler.updateWanderPath(this)

    override def canDespawn = super.canDespawn

    override def setBeenAttacked = super.setBeenAttacked()

    override def updatePotionEffects = super.updatePotionEffects()

    override def jump = super.jump()

    override def collideWithNearbyEntities = super.collideWithNearbyEntities()

    override def isWithinHomeDistance(x: Int, y: Int, z: Int): Boolean = TraitHandler.isWithinHomeDistance(this, x, y, z)

    override def isWithinHomeDistanceCurrentPosition: Boolean = TraitHandler.isWithinHomeDistanceCurrentPosition(this)

    override def getHomePosition: ChunkCoordinates = TraitHandler.getHomePosition(this)

    override def hasHome: Boolean = TraitHandler.hasHome(this)

    override def getMaxHomeDistance: Float = TraitHandler.getMaxHomeDistance(this)

    override def detachHome = TraitHandler.detachHome(this)

    override def setHomeArea(x: Int, y: Int, z: Int, maxDistance: Int) = TraitHandler.setHomeArea(this, x, y, z, maxDistance)

    override def attackEntityWithRangedAttack(entity: EntityLivingBase, distanceModified: Float) = TraitHandler.attackEntityWithRangedAttack(this, entity, distanceModified)

    override def onStruckByLightning(lightning: EntityLightningBolt) = TraitHandler.onStruckByLightning(this, lightning)

    override def despawnEntity = super.despawnEntity()

    override def setRotation(rotationYaw: Float, rotationPitch: Float) = super.setRotation(rotationYaw, rotationPitch)

    override def updateArmSwingProgress = super.updateArmSwingProgress()

    override def isAIEnabled: Boolean = SoulHelper.geneRegistry.getValueFromAllele(this, Genes.GeneUseNewAI)

    override def dealFireDamage(damage: Int) = super.dealFireDamage(damage)

    var renderer: RenderLiving = null

    @SideOnly(Side.CLIENT)
    override def getEntityRender: RenderLiving = renderer

    @SideOnly(Side.CLIENT)
    override def setEntityRender(render: RenderLiving) = renderer = render

    override def setDead = TraitHandler.setDead(this)

    override def getCustomNameTag: String = TraitHandler.getCustomNameTag(this)

    override def setCustomNameTag(nameTag: String) = TraitHandler.setCustomNameTag(this, nameTag)

    override def hasCustomNameTag: Boolean = TraitHandler.hasCustomNameTag(this)

    //TODO tamed with DataWatcherHelper
    override def isTamed: Boolean = false

    override def setSize(width: Float, height: Float) = super.setSize(width, height)

    var soulPreserved = false

    override def getSoulPreserved: Boolean = soulPreserved

    override def setSoulPreserved(soulPreserved: Boolean) = this.soulPreserved = soulPreserved

    override def getEntityTexture = TraitHandler.getEntityTexture(this)

    override def readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        soul = new Soul(compound)
        if(compound.hasKey("data")) {
            syncLogic.readFromNBT(compound)
        }
        TraitHandler.readFromNBT(this, compound)
    }

    override def writeToNBT(compound: NBTTagCompound) {
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
