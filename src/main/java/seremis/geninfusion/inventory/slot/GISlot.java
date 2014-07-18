package seremis.geninfusion.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GISlot extends Slot {

    private IInventory tile;

    public GISlot(IInventory inventory, int id, int x, int y) {
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
            return Math.min(itemStack.getMaxStackSize(), tile.getInventoryStackLimit());
        }

        return tile.getInventoryStackLimit();
    }

}
