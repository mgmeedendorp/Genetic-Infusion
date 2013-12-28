package seremis.soulcraft.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import seremis.soulcraft.item.ItemTransporterModules;

public class UpgradeSlot extends SCSlot {

    public UpgradeSlot(IInventory inventory, int id, int x, int y) {
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.itemID == ItemTransporterModules.engine().itemID && stack.getItemDamage() == ItemTransporterModules.engine().getItemDamage()) {
            return true;
        }
        if(stack.itemID == ItemTransporterModules.storage().itemID && stack.getItemDamage() == ItemTransporterModules.storage().getItemDamage()) {
            return true;
        }
        return false;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
