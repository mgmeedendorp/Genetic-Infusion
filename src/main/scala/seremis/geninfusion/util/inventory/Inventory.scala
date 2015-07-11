package seremis.geninfusion.util.inventory

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.util.Constants

class Inventory(var inventorySize: Int, var name: String, var inventoryStackLimit: Int, tile: TileEntity) extends IInventory {

    private var inventory: Array[ItemStack] = new Array[ItemStack](inventorySize)

    def this(compound: NBTTagCompound, tile: TileEntity) {
        this(0, "", 0, tile)
        readFromNBT(compound)
    }

    override def getSizeInventory: Int = inventorySize

    override def getStackInSlot(slot: Int): ItemStack = inventory(slot)

    override def decrStackSize(slot: Int, amount: Int): ItemStack = {
        var stack = getStackInSlot(slot)
        if (stack != null) {
            if (stack.stackSize <= amount) {
                setInventorySlotContents(slot, null)
            } else {
                stack = stack.splitStack(amount)
                if (stack.stackSize <= 0) {
                    setInventorySlotContents(slot, null)
                }
            }
        }
        stack
    }

    override def getStackInSlotOnClosing(slot: Int): ItemStack = null

    override def setInventorySlotContents(slot: Int, stack: ItemStack) {
        if (slot >= getSizeInventory) return

        inventory(slot) = stack

        if (stack != null && stack.stackSize > getInventoryStackLimit) {
            stack.stackSize = getInventoryStackLimit
        }

        markDirty()
    }

    override def getInventoryName: String = name

    override def isCustomInventoryName: Boolean = false

    override def markDirty() {
        if (tile != null) tile.markDirty()
    }

    override def isUseableByPlayer(player: EntityPlayer): Boolean = true

    override def openChest() {}

    override def closeChest() {}

    override def isItemValidForSlot(slot: Int, stack: ItemStack): Boolean = true

    def getItemStacks: Array[ItemStack] = inventory

    def setItemStacks(stacks: Array[ItemStack]) {
        inventory = stacks
    }

    def writeToNBT(compound: NBTTagCompound) {
        val inventory = new NBTTagCompound()
        if (getInventoryName != null) inventory.setString("name", getInventoryName)
        inventory.setInteger("stackLimit", getInventoryStackLimit)
        inventory.setInteger("length", getSizeInventory)
        Inventory.writeInventoryToNBT(this, inventory)
        compound.setTag("inventory", inventory)
    }

    def readFromNBT(compound: NBTTagCompound) {
        if (compound.hasKey("inventory")) {
            val inventory = compound.getCompoundTag("inventory")
            if (inventory.hasKey("name")) name = inventory.getString("name")
            inventoryStackLimit = inventory.getInteger("stackLimit")
            inventorySize = inventory.getInteger("length")
            this.inventory = Inventory.readInventoryFromNBT(this, inventory)
        }
    }

    override def getInventoryStackLimit: Int = inventoryStackLimit
}

object Inventory {
    def writeInventoryToNBT(tile: IInventory, compound: NBTTagCompound) {
        val nbtTagList = new NBTTagList()

        for (i <- 0 until tile.getSizeInventory if tile.getStackInSlot(i) != null) {
            val compound2 = new NBTTagCompound()

            compound2.setByte("Slot", i.toByte)

            tile.getStackInSlot(i).writeToNBT(compound2)

            nbtTagList.appendTag(compound2)
        }

        compound.setTag("Items", nbtTagList)
    }

    def readInventoryFromNBT(tile: IInventory, compound: NBTTagCompound): Array[ItemStack] = {
        val nbtTagList = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND)

        val inv = Array.ofDim[ItemStack](tile.getSizeInventory)

        for (i <- 0 until nbtTagList.tagCount()) {
            val compound2 = nbtTagList.getCompoundTagAt(i)

            val var5 = compound2.getByte("Slot") & 255

            if (var5 >= 0 && var5 < tile.getSizeInventory) {
                inv(var5) = ItemStack.loadItemStackFromNBT(compound2)
            }
        }
        inv
    }
}