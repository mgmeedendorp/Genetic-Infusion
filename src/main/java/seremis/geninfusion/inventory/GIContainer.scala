package seremis.geninfusion.inventory

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{Container, Slot}
import net.minecraft.item.ItemStack
import util.control.Breaks._

class GIContainer extends Container {

    private var addingPlayerInv = false
    protected var slotCount = 0

    def addPlayerInventory(player: EntityPlayer, zDown: Int) {
        addingPlayerInv = true
        val playerInv = player.inventory
        for (columns <- 0 until 3; rows <- 0 until 9) {
            addSlotToContainer(new Slot(playerInv, rows + columns * 9 + 9, 8 + rows * 18, 84 + zDown + columns * 18))
        }

        for (j <- 0 until 9) {
            addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 142 + zDown))
        }
        addingPlayerInv = false
    }

    def addPlayerInventory(player: EntityPlayer) {
        addPlayerInventory(player, 0)
    }

    override def addSlotToContainer(slot: Slot): Slot = {
        if (!addingPlayerInv) {
            slotCount += 1
        }
        super.addSlotToContainer(slot)
    }

    override def canInteractWith(player: EntityPlayer): Boolean = true

    protected override def mergeItemStack(stack: ItemStack, slotNrFrom: Int, slotNrTo: Int, reverse: Boolean): Boolean = {
        var flag = false
        var index = slotNrFrom

        if (reverse) {
            index = slotNrTo - 1
        }

        var slot: Slot = null
        var itemstack1: ItemStack = null

        if (stack.isStackable) {
            while (stack.stackSize > 0 && (!reverse && index < slotNrTo || reverse && index >= slotNrFrom)) {
                breakable {
                    slot = this.inventorySlots.get(index).asInstanceOf[Slot]
                    itemstack1 = slot.getStack
                    if(reverse) {
                        index
                    } else {
                        index
                    }
                    if(!slot.isItemValid(stack)) {
                        break
                    }
                    if(itemstack1 != null && itemstack1.isItemEqual(stack) && (!stack.getHasSubtypes || stack.getMetadata == itemstack1.getMetadata) && ItemStack.areItemStackTagsEqual(stack, itemstack1)) {
                        val l = itemstack1.stackSize + stack.stackSize

                        val limit = Math.min(stack.getMaxStackSize, slot.getSlotStackLimit - itemstack1.stackSize)

                        if(l < limit) {
                            stack.stackSize = 0
                            itemstack1.stackSize = l
                            slot.onSlotChanged()
                            flag = true
                        } else if(itemstack1.stackSize < limit) {
                            stack.stackSize -= limit
                            itemstack1.stackSize = limit
                            slot.onSlotChanged()
                            flag = true
                            break
                        }
                    } else if(itemstack1 == null || itemstack1.stackSize <= 0) {
                        val l = stack.stackSize

                        val limit = Math.min(stack.getMaxStackSize, slot.getSlotStackLimit)

                        if(l < limit) {
                            slot.putStack(stack.copy())
                            stack.stackSize = 0
                            slot.onSlotChanged()
                            flag = true
                        } else {
                            slot.putStack(stack.splitStack(limit))
                            slot.onSlotChanged()
                            flag = true
                        }
                    }
                }
            }
        }
        flag
    }
}