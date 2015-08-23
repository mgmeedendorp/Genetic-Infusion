package seremis.geninfusion.api.soul

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{Entity, EntityAgeable, EntityLivingBase, IEntityLivingData}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{ChunkCoordinates, DamageSource, ResourceLocation}

trait ITrait {

    def onUpdate(entity: IEntitySoulCustom)

    def interact(entity: IEntitySoulCustom, player: EntityPlayer): Boolean

    def onDeath(entity: IEntitySoulCustom, source: DamageSource)

    def onKillEntity(entity: IEntitySoulCustom, killed: EntityLivingBase)

    def attackEntityFrom(entity: IEntitySoulCustom, source: DamageSource, damage: Float): Boolean

    def onSpawnWithEgg(entity: IEntitySoulCustom, data: IEntityLivingData)

    def playSound(entity: IEntitySoulCustom, name: String, volume: Float, pitch: Float)

    def damageEntity(entity: IEntitySoulCustom, source: DamageSource, damage: Float)

    def updateAITick(entity: IEntitySoulCustom)

    def firstTick(entity: IEntitySoulCustom)

    def attackEntityAsMob(entity: IEntitySoulCustom, entityToAttack: Entity): Boolean

    def attackEntity(entity: IEntitySoulCustom, entityToAttack: Entity, distance: Float)

    def findPlayerToAttack(entity: IEntitySoulCustom): Entity

    def applyArmorCalculations(entity: IEntitySoulCustom, source: DamageSource, damage: Float): Float

    def applyPotionDamageCalculations(entity: IEntitySoulCustom, source: DamageSource, damage: Float): Float

    def damageArmor(entity: IEntitySoulCustom, damage: Float)

    def setOnFireFromLava(entity: IEntitySoulCustom)

    def getBlockPathWeight(entity: IEntitySoulCustom, x: Int, y: Int, z: Int): Float

    def updateEntityActionState(entity: IEntitySoulCustom)

    def updateWanderPath(entity: IEntitySoulCustom)

    def attackEntityWithRangedAttack(entity: IEntitySoulCustom, target: EntityLivingBase, distanceModified: Float)

    def isWithinHomeDistanceCurrentPosition(entity: IEntitySoulCustom): Boolean

    def isWithinHomeDistance(entity: IEntitySoulCustom, x: Int, y: Int, z: Int): Boolean

    def getHomePosition(entity: IEntitySoulCustom): ChunkCoordinates

    def setHomeArea(entity: IEntitySoulCustom, x: Int, y: Int, z: Int, maxDistance: Int)

    def getMaxHomeDistance(entity: IEntitySoulCustom): Float

    def detachHome(entity: IEntitySoulCustom)

    def hasHome(entity: IEntitySoulCustom): Boolean

    def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound)

    def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound)

    def onStruckByLightning(entity: IEntitySoulCustom, lightingBolt: EntityLightningBolt)

    @SideOnly(Side.CLIENT) def render(entity: IEntitySoulCustom, timeModifier: Float, walkSpeed: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float)

    @SideOnly(Side.CLIENT) def preRenderCallback(entity: IEntitySoulCustom, partialTickTime: Float)

    @SideOnly(Side.CLIENT) def getColorMultiplier(entity: IEntitySoulCustom, brightness: Float, partialTickTime: Float): Int

    @SideOnly(Side.CLIENT) def shouldRenderPass(entity: IEntitySoulCustom, renderPass: Int, partialTickTime: Float): Int

    @SideOnly(Side.CLIENT) def inheritRenderPass(entity: IEntitySoulCustom, renderPass: Int, partialTickTime: Float): Int

    @SideOnly(Side.CLIENT) def renderEquippedItems(entity: IEntitySoulCustom, partialTickTime: Float)

    @SideOnly(Side.CLIENT) def getEntityTexture(entity: IEntitySoulCustom): ResourceLocation

    def setDead(entity: IEntitySoulCustom)

    def setCustomNameTag(entity: IEntitySoulCustom, nameTag: String)

    def getCustomNameTag(entity: IEntitySoulCustom): String

    def hasCustomNameTag(entity: IEntitySoulCustom): Boolean

    def onDeathUpdate(entity: IEntitySoulCustom)

    def getExperiencePoints(entity: IEntitySoulCustom, player: EntityPlayer): Int

    def createChild(entity: IEntitySoulCustom, ageable: EntityAgeable): Option[EntityAgeable]

    def isChild(entity: IEntitySoulCustom): Boolean

    def setSize(entity: IEntitySoulCustom, width: Float, height: Float)

    def setScale(entity: IEntitySoulCustom, scale: Float)

    def setScaleForAge(entity: IEntitySoulCustom, isChild: Boolean)

    def getGrowingAge(entity: IEntitySoulCustom): Int

    def setGrowingAge(entity: IEntitySoulCustom, growingAge: Int)

    def addGrowth(entity: IEntitySoulCustom, growth: Int)
}
