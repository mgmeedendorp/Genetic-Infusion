package seremis.geninfusion.entity

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import cpw.mods.fml.relauncher.{Side, SideOnly}
import io.netty.buffer.ByteBuf
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{Entity, EntityList, EntityLiving}
import net.minecraft.init.Blocks
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{AxisAlignedBB, DamageSource}
import net.minecraft.world.World
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.api.util.render.model.Model
import seremis.geninfusion.block.BlockCrystal
import seremis.geninfusion.util.{UtilBlock, UtilNBT}

class EntityClayGolem(world: World) extends Entity(world) with GIEntity with IEntityAdditionalSpawnData {
    setSize(1.3F, 2.9F)
    preventEntitySpawning = true


    private var startTransformation = false
    private var transformationTimer = 0
    private val maxTransformationTimer = 100
    private var waitAfterTransformation = 0
    private val maxWaitAfterTransformation = 10

    private var transformationGoal: Option[IEntitySoulCustom] = None
    private var transformationGoalModel: Option[Model] = None

    var firstRenderTick = true
    var currentRenderModel: Option[Model] = None

    var clayAtCreation = new Array[ItemStack](4)

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

        if(clayAtCreation.nonEmpty) {
            for(i <- clayAtCreation.indices) {
                compound.setTag("clayAtCreation." + i, clayAtCreation(i).writeToNBT(new NBTTagCompound()))
            }
        }

        val entityCompound = new NBTTagCompound
        transformationGoal.foreach(goal => {
            goal.asInstanceOf[EntityLiving].writeToNBT(entityCompound)
            compound.setTag("transformationGoal", entityCompound)
        })
    }

    override def readEntityFromNBT(compound: NBTTagCompound) {
        transformationTimer = compound.getInteger("transformationTimer")

        if(compound.hasKey("clayAtCreation.0")) {
            for(i <- clayAtCreation.indices) {
                clayAtCreation(i) = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("clayAtCreation." + i))
            }
        }

        if(compound.hasKey("transformationGoal")) {
            val entityCompound = compound.getCompoundTag("transformationGoal")

            val entity = SoulHelper.instanceHelper.getSoulEntityInstance(entityCompound, worldObj)
            setTransformationGoal(Option(entity))
        }
    }

    override def onUpdate() {
        super.onUpdate()

        if(worldObj.isRemote && startTransformation && transformationTimer < maxTransformationTimer) {
            transformationTimer += 1
        }

        if(worldObj.isRemote && transformationTimer == maxTransformationTimer) {
            if(waitAfterTransformation < maxWaitAfterTransformation) {
                startTransformation = false
                waitAfterTransformation += 1
            }
            if(waitAfterTransformation == maxWaitAfterTransformation) {
                sendEntityDataToServer(0, Array(0.toByte))
            }
        }

        if(!worldObj.isRemote && transformationTimer == maxTransformationTimer) {
            transformationGoal.get.setRotation_I(rotationYaw, rotationPitch)
            worldObj.spawnEntityInWorld(transformationGoal.get.asInstanceOf[EntityLiving])
            setDead()
        }

        if(!worldObj.isRemote)
            motionY -= 0.03999999910593033D


        motionX *= 0.98D
        motionY *= 0.98D
        motionZ *= 0.98D

        moveEntity(motionX, motionY, motionZ)
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

            if(!player.capabilities.isCreativeMode) {
                var stack = player.getCurrentEquippedItem

                if(stack.stackSize > 1)
                    stack.stackSize -= 1
                else
                    stack = null

                player.setCurrentItemOrArmor(0, stack)
                player.inventoryContainer.detectAndSendChanges()
            }

            return true
        }
        false
    }

    @SideOnly(Side.CLIENT)
    override def receivePacketOnClient(id: Int, value: Array[Byte]) {
        if(id == 0) {
            val entity = EntityList.createEntityFromNBT(UtilNBT.byteArrayToCompound(value).getOrElse(return), worldObj).asInstanceOf[IEntitySoulCustom]
            if(entity != null)
                setTransformationGoal(Some(entity))
            else
                setTransformationGoal(None)
        }
    }

    override def receivePacketOnServer(id: Int, value: Array[Byte]) {
        if(id == 0) {
            transformationTimer = maxTransformationTimer
        }
    }

    def setTransformationGoal(entity: Option[IEntitySoulCustom]) {
        transformationGoal = entity
        entity.foreach(goal => transformationGoalModel = Some(SoulHelper.geneRegistry.getValueFromAllele(goal, Genes.GeneModel)))
        startTransformation = transformationGoal.nonEmpty
    }

    def getTransformationGoal: Option[IEntitySoulCustom] = transformationGoal

    def getTransformationGoalModel: Option[Model] = transformationGoalModel

    def isTransformating: Boolean = startTransformation

    def getTransformationTimer: Int = transformationTimer

    def getMaxTransformationTimer: Int = maxTransformationTimer

    def isWaitingAfterTransformation: Boolean = transformationTimer == maxTransformationTimer && waitAfterTransformation <= maxWaitAfterTransformation

    override def attackEntityFrom(source: DamageSource, amount: Float): Boolean = {
        if(!world.isRemote && !isDead && !isTransformating) {
            setBeenAttacked()

            val x = Math.floor(posX).toInt
            val y = Math.floor(posY).toInt
            val z = Math.floor(posZ).toInt

            val rotated = rotationYaw == 90

            if(blockIsNoTile(x, y, z))
                world.setBlock(x, y, z, getClayBlockAtCreation(0), getClayMetaAtCreation(0), 3)

            if(blockIsNoTile(x, y + 1, z))
                world.setBlock(x, y + 1, z, getClayBlockAtCreation(1), getClayMetaAtCreation(1), 3)

            if(blockIsNoTile(x + 1, y + 1, z) && !rotated)
                world.setBlock(x + 1, y + 1, z, getClayBlockAtCreation(2), getClayMetaAtCreation(2), 3)
            else
                world.setBlock(x, y + 1, z + 1, getClayBlockAtCreation(2), getClayMetaAtCreation(2), 3)

            if(blockIsNoTile(x - 1, y + 1, z) && !rotated)
                world.setBlock(x - 1, y + 1, z, getClayBlockAtCreation(3), getClayMetaAtCreation(3), 3)
            else
                world.setBlock(x, y + 1, z - 1, getClayBlockAtCreation(2), getClayMetaAtCreation(2), 3)

            UtilBlock.dropItemInWorld(x, y + 2, z, world, Item.getItemFromBlock(Blocks.pumpkin), 1)

            setDead()
        }

        true
    }

    def blockIsNoTile(x: Int, y: Int, z: Int): Boolean = {
        !world.getBlock(x, y, z).hasTileEntity(world.getBlockMetadata(x, y, z))
    }

    def setClayAtCreation(position: Int, stack: ItemStack) {
        clayAtCreation(position) = stack
    }

    def getClayBlockAtCreation(position: Int): Block = Block.getBlockFromItem(clayAtCreation(position).getItem)

    def getClayMetaAtCreation(position: Int): Int = clayAtCreation(position).getMetadata

    override def getCollisionBox(entity: Entity): AxisAlignedBB = boundingBox

    override def getBoundingBox: AxisAlignedBB = boundingBox

    @SideOnly(Side.CLIENT)
    override def setPositionAndRotation2(x: Double, y: Double, z: Double, yaw: Float, pitch: Float, rotationIncrements: Int) {
        setPosition(x, y, z)
        setRotation(yaw, pitch)
    }
}
