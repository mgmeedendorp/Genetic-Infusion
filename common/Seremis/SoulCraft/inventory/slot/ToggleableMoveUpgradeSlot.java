package Seremis.SoulCraft.inventory.slot;

import Seremis.SoulCraft.item.ItemTransporterModules;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ToggleableMoveUpgradeSlot extends ToggleableMoveSlot {

    public ToggleableMoveUpgradeSlot(IInventory inventory, int id, int x, int y) {
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.itemID == ItemTransporterModules.engine().itemID && stack.getItemDamage() == ItemTransporterModules.engine().getItemDamage()) {
            return super.isItemValid(stack);
        }
        if(stack.itemID == ItemTransporterModules.storage().itemID && stack.getItemDamage() == ItemTransporterModules.storage().getItemDamage()) {
            return super.isItemValid(stack);
        }
        return false;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
