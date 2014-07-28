package seremis.geninfusion.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.item.ItemTransporterModules;

public class UpgradeSlot extends GISlot {

    public UpgradeSlot(IInventory inventory, int id, int x, int y) {
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.isItemEqual(ItemTransporterModules.engine()) && stack.getItemDamage() == ItemTransporterModules.engine().getItemDamage()) {
            return true;
        }
        if(stack.isItemEqual(ItemTransporterModules.storage()) && stack.getItemDamage() == ItemTransporterModules.storage().getItemDamage()) {
            return true;
        }
        return false;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
