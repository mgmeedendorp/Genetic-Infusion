package Seremis.SoulCraft.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SCSlot extends Slot {

    private IInventory tile;

    public SCSlot(IInventory inventory, int id, int x, int y) {
        super(inventory, id, x, y);
        this.tile = inventory;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return this.tile.isItemValidForSlot(this.slotNumber, itemStack);
    }

    @Override
    public int getSlotStackLimit() {
        ItemStack itemStack = tile.getStackInSlot(this.slotNumber);

        if(itemStack != null) {
            return itemStack.getMaxStackSize();
        }

        return tile.getInventoryStackLimit();
    }

}
