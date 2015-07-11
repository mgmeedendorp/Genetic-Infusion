package seremis.geninfusion.inventory.slot

import net.minecraft.inventory.{IInventory, Slot}
import net.minecraft.item.ItemStack

class GISlot(inventory: IInventory, id: Int, x: Int, y: Int) extends Slot(inventory, id, x, y) {

    val tile: IInventory = inventory

    override def isItemValid(itemStack: ItemStack): Boolean = {
        tile.isItemValidForSlot(this.slotNumber, itemStack)
    }

    override def getSlotStackLimit: Int = {
        val itemStack = tile.getStackInSlot(this.slotNumber)
        if (itemStack != null) {
            return Math.min(itemStack.getMaxStackSize, tile.getInventoryStackLimit)
        }
        tile.getInventoryStackLimit
    }
}