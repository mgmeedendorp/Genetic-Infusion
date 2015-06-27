package seremis.geninfusion.entity

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import cpw.mods.fml.relauncher.{Side, SideOnly}
import io.netty.buffer.ByteBuf
import net.minecraft.block.Block
import net.minecraft.entity.{EntityList, EntityLiving, Entity}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.block.BlockCrystal
import seremis.geninfusion.util.UtilNBT

class EntityClayGolem(world: World) extends Entity(world) with GIEntity with IEntityAdditionalSpawnData {
    setSize(1.4F, 2.9F)

    var startTransformation = false
    var transformationTimer = 100
    val maxTransformationTimer = 100

    var transformationGoal: Option[IEntitySoulCustom] = None

    override def canBeCollidedWith: Boolean = true

    override def writeSpawnData(data: ByteBuf) {
        val compound = new NBTTagCompound
        writeEntityToNBT(compound)

        val abyte = UtilNBT.compoundToByteArray(compound).getOrElse(return)

        data.writeShort(abyte.length.toShort)
        data.writeBytes(abyte)
    }

    override def readSpawnData(data: ByteBuf) {
        val length = data.readShort
        val abyte = new Array[Byte](length)

        data.readBytes(abyte)

        val compound: NBTTagCompound = UtilNBT.byteArrayToCompound(abyte).getOrElse(return)
        readEntityFromNBT(compound)
    }

    override def entityInit() {

    }

    override def writeEntityToNBT(compound: NBTTagCompound) {
        compound.setInteger("transformationTimer", transformationTimer)
        compound.setBoolean("startTransformation", startTransformation)

        val entityCompound = new NBTTagCompound
        transformationGoal.orNull.asInstanceOf[EntityLiving].writeToNBT(entityCompound)
        compound.setTag("transformationGoal", entityCompound)
    }

    override def readEntityFromNBT(compound: NBTTagCompound) {
        transformationTimer = compound.getInteger("transformationTimer")
        startTransformation = compound.getBoolean("startTransformation")

        val entityCompound = compound.getCompoundTag("transformationGoal")
        transformationGoal = Some(EntityList.createEntityFromNBT(entityCompound, worldObj).asInstanceOf[IEntitySoulCustom])
    }

    override def onUpdate() {
        super.onUpdate()

        if(startTransformation && transformationTimer > 0) {
            transformationTimer -= 1
        }

        if(!worldObj.isRemote && transformationTimer == 0) {
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
            entity.asInstanceOf[EntityLiving].writeToNBT(entCompound)

            sendEntityDataToClient(0, UtilNBT.compoundToByteArray(entCompound).getOrElse(return false))

            transformationGoal = Some(entity)
            startTransformation = true

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
            transformationGoal = Some(EntityList.createEntityFromNBT(UtilNBT.byteArrayToCompound(value).getOrElse(return), worldObj).asInstanceOf[IEntitySoulCustom])
            startTransformation = true
        }
    }
}
