package seremis.geninfusion.inventory.slot

import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

class ToggleableSlot(inventory: IInventory, id: Int, x: Int, y: Int) extends GISlot(inventory, id, x, y) {

    var enabled: Boolean = true

    override def isItemValid(is: ItemStack): Boolean = enabled && super.isItemValid(is)

    def enable() {
        enabled = true
    }

    def disable() {
        enabled = false
    }
}
