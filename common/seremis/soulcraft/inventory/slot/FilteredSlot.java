package seremis.soulcraft.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class FilteredSlot extends SCSlot {

    public ItemStack filter;
    public int slotStackLimit;

    public FilteredSlot(IInventory inventory, int id, int x, int y, ItemStack filter, int slotStackLimit) {
        super(inventory, id, x, y);
        this.filter = filter;
        this.slotStackLimit = slotStackLimit;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(filter != null) {
            if(stack.itemID == filter.itemID && stack.getItemDamage() == filter.getItemDamage()) {
                return super.isItemValid(stack);
            }
        }
        return false;
    }

    @Override
    public int getSlotStackLimit() {
        return slotStackLimit;
    }
}
