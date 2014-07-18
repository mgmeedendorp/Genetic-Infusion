package seremis.geninfusion.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ToggleableSlot extends GISlot {

    protected boolean enabled = true;

    public ToggleableSlot(IInventory inventory, int id, int x, int y) {
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack is) {
        return enabled && super.isItemValid(is);
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }
}
