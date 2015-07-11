package seremis.geninfusion.soul.traits

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{Entity, EntityLivingBase, IEntityLivingData}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{ChunkCoordinates, DamageSource}
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ITrait}

class Trait extends ITrait {

    override def onUpdate(entity: IEntitySoulCustom) {}

    override def interact(entity: IEntitySoulCustom, player: EntityPlayer): Boolean = false

    override def onDeath(entity: IEntitySoulCustom, source: DamageSource) {}

    override def onKillEntity(entity: IEntitySoulCustom, killed: EntityLivingBase) {}

    override def attackEntityFrom(entity: IEntitySoulCustom, source: DamageSource, damage: Float): Boolean = false

    override def onSpawnWithEgg(entity: IEntitySoulCustom, data: IEntityLivingData) {}

    override def playSound(entity: IEntitySoulCustom, name: String, volume: Float, pitch: Float) {}

    override def damageEntity(entity: IEntitySoulCustom, source: DamageSource, damage: Float) {}

    override def updateAITick(entity: IEntitySoulCustom) {}

    override def firstTick(entity: IEntitySoulCustom) {}

    override def attackEntityAsMob(entity: IEntitySoulCustom, entityToAttack: Entity): Boolean = false

    override def attackEntity(entity: IEntitySoulCustom, entityToAttack: Entity, distance: Float) {}

    override def findPlayerToAttack(entity: IEntitySoulCustom): Entity = null

    override def applyArmorCalculations(entity: IEntitySoulCustom, source: DamageSource, damage: Float): Float = 0.0F

    override def applyPotionDamageCalculations(entity: IEntitySoulCustom, source: DamageSource, damage: Float): Float = 0.0F

    override def damageArmor(entity: IEntitySoulCustom, damage: Float) {}

    override def setOnFireFromLava(entity: IEntitySoulCustom) {}

    override def getBlockPathWeight(entity: IEntitySoulCustom, x: Int, y: Int, z: Int): Float = 0.0F

    override def updateEntityActionState(entity: IEntitySoulCustom) {}

    override def updateWanderPath(entity: IEntitySoulCustom) {}

    override def attackEntityWithRangedAttack(entity: IEntitySoulCustom, target: EntityLivingBase, distanceModified: Float) {}

    override def isWithinHomeDistanceCurrentPosition(entity: IEntitySoulCustom): Boolean = false

    override def isWithinHomeDistance(entity: IEntitySoulCustom, x: Int, y: Int, z: Int): Boolean = false

    override def getHomePosition(entity: IEntitySoulCustom): ChunkCoordinates = null

    override def setHomeArea(entity: IEntitySoulCustom, x: Int, y: Int, z: Int, maxDistance: Int) {}

    override def getMaxHomeDistance(entity: IEntitySoulCustom): Float = 0.0F

    override def detachHome(entity: IEntitySoulCustom) {}

    override def hasHome(entity: IEntitySoulCustom): Boolean = false

    override def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {}

    override def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {}

    override def onStruckByLightning(entity: IEntitySoulCustom, lightingBolt: EntityLightningBolt) {}

    @SideOnly(Side.CLIENT)
    override def render(entity: IEntitySoulCustom, timeModifier: Float, limbSwing: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float) {}

    @SideOnly(Side.CLIENT)
    override def preRenderCallback(entity: IEntitySoulCustom, partialTickTime: Float) {}

    @SideOnly(Side.CLIENT)
    override def inheritRenderPass(entity: IEntitySoulCustom, renderPass: Int, partialTickTime: Float): Int = 0

    @SideOnly(Side.CLIENT)
    override def shouldRenderPass(entity: IEntitySoulCustom, renderPass: Int, partialTickTime: Float): Int = 0

    @SideOnly(Side.CLIENT)
    override def getColorMultiplier(entity: IEntitySoulCustom, brightness: Float, partialTickTime: Float): Int = 0

    @SideOnly(Side.CLIENT)
    override def renderEquippedItems(entity: IEntitySoulCustom, partialTickTime: Float) {}

    @SideOnly(Side.CLIENT)
    override def getEntityTexture(entity: IEntitySoulCustom): String = null

    override def setDead(entity: IEntitySoulCustom) {}

    override def setCustomNameTag(entity: IEntitySoulCustom, nameTag: String) {}

    override def getCustomNameTag(entity: IEntitySoulCustom): String = null

    override def hasCustomNameTag(entity: IEntitySoulCustom): Boolean = false
}