package seremis.geninfusion.inventory.slot

import net.minecraft.inventory.IInventory

class ToggleableMoveSlot(inventory: IInventory, id: Int, x: Int, y: Int) extends ToggleableSlot(inventory, id, x, y) {

    override def enable() {
        enabled = true
        xDisplayPosition = x
        yDisplayPosition = y
    }

    override def disable() {
        enabled = false
        xDisplayPosition = x + 1000
        yDisplayPosition = y + 1000
    }
}