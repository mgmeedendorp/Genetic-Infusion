package seremis.geninfusion.entity

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import cpw.mods.fml.relauncher.{Side, SideOnly}
import io.netty.buffer.ByteBuf
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{Entity, EntityList, EntityLiving}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.api.util.render.model.Model
import seremis.geninfusion.block.BlockCrystal
import seremis.geninfusion.util.UtilNBT

class EntityClayGolem(world: World) extends Entity(world) with GIEntity with IEntityAdditionalSpawnData {
    setSize(1.4F, 2.9F)

    private var startTransformation = false
    private var transformationTimer = 0
    private val maxTransformationTimer = 100

    private var transformationGoal: Option[IEntitySoulCustom] = None
    private var transformationGoalModel: Option[Model] = None

    var firstRenderTick = true
    var currentRenderModel: Option[Model] = None

    override def canBeCollidedWith: Boolean = true

    override def writeSpawnData(data: ByteBuf) {
        val compound = new NBTTagCompound
        writeEntityToNBT(compound)

        val bytes = UtilNBT.compoundToByteArray(compound).getOrElse(return)

        data.writeShort(bytes.length.toShort)
        data.writeBytes(bytes)
    }

    override def readSpawnData(data: ByteBuf) {
        val length = data.readShort
        val bytes = new Array[Byte](length)

        data.readBytes(bytes)

        val compound: NBTTagCompound = UtilNBT.byteArrayToCompound(bytes).getOrElse(return)
        readEntityFromNBT(compound)
    }

    override def entityInit() {

    }

    override def writeEntityToNBT(compound: NBTTagCompound) {
        compound.setInteger("transformationTimer", transformationTimer)
        compound.setBoolean("startTransformation", startTransformation)

        val entityCompound = new NBTTagCompound
        transformationGoal.foreach(goal => {
            goal.asInstanceOf[EntityLiving].writeToNBT(entityCompound)
            compound.setTag("transformationGoal", entityCompound)
        })
    }

    override def readEntityFromNBT(compound: NBTTagCompound) {
        transformationTimer = compound.getInteger("transformationTimer")
        startTransformation = compound.getBoolean("startTransformation")

        if(compound.hasKey("transformationGoal")) {
            val entityCompound = compound.getCompoundTag("transformationGoal")
            setTransformationGoal(Some(EntityList.createEntityFromNBT(entityCompound, worldObj).asInstanceOf[IEntitySoulCustom]))
        } else {
            setTransformationGoal(None)
        }
    }

    override def onUpdate() {
        super.onUpdate()

        if(startTransformation && transformationTimer < maxTransformationTimer) {
            transformationTimer += 1
        }

        if(!worldObj.isRemote && transformationTimer == maxTransformationTimer) {
            worldObj.spawnEntityInWorld(transformationGoal.get.asInstanceOf[EntityLiving])
            setDead()
        }
    }

    override def interactFirst(player: EntityPlayer): Boolean = {
        if(!worldObj.isRemote && !startTransformation && player.getCurrentEquippedItem != null && Block.getBlockFromItem(player.getCurrentEquippedItem.getItem).isInstanceOf[BlockCrystal] && player.getCurrentEquippedItem.hasTagCompound) {
            val compound = player.getCurrentEquippedItem.getTagCompound

            val soul = SoulHelper.instanceHelper.getISoulInstance(compound).getOrElse(return false)
            val entity = SoulHelper.instanceHelper.getSoulEntityInstance(worldObj, soul, posX, posY, posZ)

            val entCompound = new NBTTagCompound
            entity.asInstanceOf[EntityLiving].writeToNBTOptional(entCompound)

            sendEntityDataToClient(0, UtilNBT.compoundToByteArray(entCompound).getOrElse(Array(0.toByte)))

            setTransformationGoal(Some(entity))

            val stack = player.getCurrentEquippedItem
            if(stack.stackSize > 1) {
                stack.stackSize -= 1
            } else {
                player.setCurrentItemOrArmor(0, null)
            }

            return true
        }
        false
    }

    @SideOnly(Side.CLIENT)
    override def receivePacketOnClient(id: Int, value: Array[Byte]) {
        if(id == 0) {
            val entity = EntityList.createEntityFromNBT(UtilNBT.byteArrayToCompound(value).getOrElse(return), worldObj).asInstanceOf[IEntitySoulCustom]
            val nbt = UtilNBT.byteArrayToCompound(value)
            if(entity != null)
                setTransformationGoal(Some(entity))
            else
                setTransformationGoal(None)
        }
    }

    def setTransformationGoal(entity: Option[IEntitySoulCustom]) {
        transformationGoal = entity
        entity.foreach(goal => transformationGoalModel = Some(new Model(SoulHelper.geneRegistry.getValueFromAllele(goal, Genes.GeneModel))))
        startTransformation = transformationGoal.nonEmpty
    }

    def getTransformationGoal: Option[IEntitySoulCustom] = transformationGoal

    def getTransformationGoalModel: Option[Model] = transformationGoalModel

    def isTransformating: Boolean = startTransformation

    def getTransformationTimer: Int = transformationTimer

    def getMaxTranformationTimer: Int = maxTransformationTimer
}
