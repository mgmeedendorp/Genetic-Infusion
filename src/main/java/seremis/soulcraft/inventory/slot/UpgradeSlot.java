package seremis.soulcraft.inventory.slot;

import seremis.soulcraft.item.ItemTransporterModules;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class UpgradeSlot extends SCSlot {

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
