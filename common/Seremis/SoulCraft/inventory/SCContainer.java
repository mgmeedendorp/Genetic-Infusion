package Seremis.SoulCraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SCContainer extends Container {

    private boolean addingPlayerInv = false;
    protected int slotCount = 0;

    public void addPlayerInventory(EntityPlayer player, int zDown) {
        addingPlayerInv = true;
        InventoryPlayer playerInv = player.inventory;
        for(int columns = 0; columns < 3; ++columns) {
            for(int rows = 0; rows < 9; ++rows) {
                addSlotToContainer(new Slot(playerInv, rows + columns * 9 + 9, 8 + rows * 18, 84 + zDown + columns * 18));
            }
        }

        for(int j = 0; j < 9; ++j) {
            addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 142 + zDown));
        }
        addingPlayerInv = false;
    }

    public void addPlayerInventory(EntityPlayer player) {
        addPlayerInventory(player, 0);
    }

    @Override
    public Slot addSlotToContainer(Slot slot) {
        if(!addingPlayerInv) {
            slotCount++;
        }
        return super.addSlotToContainer(slot);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    protected boolean mergeItemStack(ItemStack stack, int slotNrFrom, int slotNrTo, boolean reverse) {
        boolean flag1 = false;
        int index = slotNrFrom;

        if(reverse) {
            index = slotNrTo - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if(stack.isStackable()) {
            while(stack.stackSize > 0 && (!reverse && index < slotNrTo || reverse && index >= slotNrFrom)) {
                slot = (Slot) this.inventorySlots.get(index);
                itemstack1 = slot.getStack();

                if(reverse) {
                    --index;
                } else {
                    ++index;
                }
                
                if(!slot.isItemValid(stack)) {
                    continue;
                }
                
                if(itemstack1 != null && itemstack1.itemID == stack.itemID && (!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, itemstack1)) {
                    int l = itemstack1.stackSize + stack.stackSize;

                    int limit = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit()-itemstack1.stackSize);

                    if(l < limit) {
                        stack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    } else if(itemstack1.stackSize < limit) {
                        stack.stackSize -= limit;
                        itemstack1.stackSize = limit;
                        slot.onSlotChanged();
                        flag1 = true;
                        break;
                    }
                } else if(itemstack1 == null || itemstack1.stackSize <= 0) {
                    int l = stack.stackSize;

                    int limit = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
                    
                    if(l < limit) {
                        slot.putStack(stack.copy());
                        stack.stackSize = 0;
                        slot.onSlotChanged();
                        flag1 = true;
                    } else {
                        slot.putStack(stack.splitStack(limit));
                        slot.onSlotChanged();
                        flag1 = true;
                        continue;
                    }
                }
            }
        }
        return flag1;
    }
}
