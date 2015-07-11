package seremis.geninfusion.inventory.slot

import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

class FilteredSlot(inventory: IInventory, id: Int, x: Int, y: Int, filter: ItemStack, slotStackLimit: Int) extends GISlot(inventory, id, x, y) {

    override def isItemValid(stack: ItemStack): Boolean = {
        if (filter != null) {
            if (stack.isItemEqual(filter)) {
                return super.isItemValid(stack)
            }
        }
        false
    }

    override def getSlotStackLimit: Int = slotStackLimit
}